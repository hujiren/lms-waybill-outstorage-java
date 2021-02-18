package com.apl.lms.waybill.outstorage.service.impl;
import com.apl.cache.AplCacheHelper;
import com.apl.db.adb.AdbPageVo;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lib.exception.AplException;
import com.apl.lib.join.JoinBase;
import com.apl.lib.join.JoinFieldInfo;
import com.apl.lib.join.JoinUtil;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lib.utils.SnowflakeIdWorker;
import com.apl.lms.common.lib.cache.JoinCountry;
import com.apl.lms.common.lib.feign.LmsCommonFeign;
import com.apl.lms.price.exp.lib.cache.JoinPartner;
import com.apl.lms.price.exp.lib.feign.PriceExpFeign;
import com.apl.lms.waybill.outstorage.dao.OutstorageBatchDao;
import com.apl.lms.waybill.outstorage.dao.WaybillDao;
import com.apl.lms.waybill.outstorage.pojo.bo.WaybillPayableCombBo;
import com.apl.lms.waybill.outstorage.pojo.dto.OutstorageBatchKeyDto;
import com.apl.lms.waybill.outstorage.pojo.po.AccountsPayablePo;
import com.apl.lms.waybill.outstorage.pojo.po.OutstorageBatchPo;
import com.apl.lms.waybill.outstorage.pojo.vo.OutstorageBatchInfoVo;
import com.apl.lms.waybill.outstorage.pojo.vo.OutstorageBatchPageVo;
import com.apl.lms.waybill.outstorage.pojo.vo.WaybillWaitOutstorageInfoVo;
import com.apl.lms.waybill.outstorage.service.OutstorageBatchService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 出货批次 service实现类
 * </p>
 *
 * @author hjr
 * @since 2020-12-21
 */
@Service
@Slf4j
public class OutstorageBatchServiceImpl implements OutstorageBatchService {

    //状态code枚举
    enum OutstorageBatchServiceImplCode {

        OUT_STORAGE_SUCCESS("OUT_STORAGE_SUCCESS", "出货成功!"),
        OUT_STORAGE_FAILED("OUT_STORAGE_FAILED", "出货失败!"),
        CANCEL_STORAGE_SUCCESS("CANCEL_STORAGE_SUCCESS", "取消成功!"),
        CANCEL_STORAGE_FAILED("CANCEL_STORAGE_FAILED", "取消失败!"),
        WAYBILL_ID_QUERY_FAILED("WAYBILL_ID_QUERY_FAILED", "出货运单查询失败"),
        NO_CORRESPONDING_DATA("NO_CORRESPONDING_DATA", "没有对应数据");

        private String code;
        private String msg;

        OutstorageBatchServiceImplCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    static final Logger logger = LoggerFactory.getLogger(OutstorageBatchServiceImpl.class);

    @Autowired
    OutstorageBatchDao outstorageBatchDao;

    @Autowired
    PriceExpFeign priceExpFeign;

    @Autowired
    AplCacheHelper aplCacheHelper;

    @Autowired
    WaybillDao waybillDao;

    @Autowired
    LmsCommonFeign lmsCommonFeign;

    JoinFieldInfo joinPartnerFieldInfo = null;

    JoinFieldInfo joinCountryFieldInfo = null;
    /**
     * 保存出货交接单信息
     * @param outstorageBatchPo
     * @return
     */
    @Override
    public Long saveOutstorageBatch(OutstorageBatchPo outstorageBatchPo) throws Exception {

        OutstorageBatchPo outstorageBatchPo2 = outstorageBatchDao.checkBatchSn(outstorageBatchPo.getPartnerId(), outstorageBatchPo.getOutBatchSn());
        if(null != outstorageBatchPo2 && outstorageBatchPo2.getId() > 0){
            outstorageBatchPo.setId(outstorageBatchPo2.getId());
//            outstorageBatchPo.setOutStatus(outstorageBatchPo2.getOutStatus());
//            outstorageBatchDao.updateOutstorageBatch(outstorageBatchPo);
        }else{
            Long batchId = SnowflakeIdWorker.generateId();
            outstorageBatchPo.setId(batchId);
            outstorageBatchDao.insertOutstorageBatch(outstorageBatchPo);
        }

        return outstorageBatchPo.getId();
    }


    /**
     * 更新出货交接单出货状态
     * @param outBatchSn
     * @param partnerId
     * @return
     */
    @Override
    public Integer updateOutStatus(String outBatchSn, Long partnerId, Timestamp newOutTime) {

        Integer resultNum = outstorageBatchDao.updateOutStatus(outBatchSn, partnerId, newOutTime);

        return resultNum;
    }

    /**
     * 分页查询
     * @param pageDto
     * @param keyDto
     * @return
     */
    @Override
    public ResultUtil<OutstorageBatchPageVo> getList(PageDto pageDto, OutstorageBatchKeyDto keyDto) {

        List<OutstorageBatchPo> outstorageBatchPoList = null;
        OutstorageBatchPageVo outstorageBatchPageVo = null;
        try {
            AdbPageVo<OutstorageBatchPo> outstorageBatchPoAdbPageVo = outstorageBatchDao.getList(pageDto, keyDto);

            if(outstorageBatchPoAdbPageVo.getList().size() < 1)
                return ResultUtil.APPRESULT(OutstorageBatchServiceImplCode.NO_CORRESPONDING_DATA.code,
                        OutstorageBatchServiceImplCode.NO_CORRESPONDING_DATA.msg,outstorageBatchPageVo);

            outstorageBatchPoList = outstorageBatchPoAdbPageVo.getList();

            List<JoinBase> joinTabs = new ArrayList<>();
            JoinPartner joinPartner = new JoinPartner(1, priceExpFeign, aplCacheHelper);
            if(null!=joinPartnerFieldInfo) {

                joinPartner.setJoinFieldInfo(joinPartnerFieldInfo);
            }
            else{
                joinPartner.addField("partnerId",  Long.class, "partnerShortName", "partnerName",String.class);

                joinPartnerFieldInfo = joinPartner.getJoinFieldInfo();
            }

            joinTabs.add(joinPartner);
            JoinUtil.join(outstorageBatchPoList, joinTabs);

            outstorageBatchPageVo = new OutstorageBatchPageVo();
            outstorageBatchPageVo.setOutstorageBatchPoList(outstorageBatchPoList);
            outstorageBatchPageVo.setPageIndex(pageDto.getPageIndex());
            outstorageBatchPageVo.setPageSize(pageDto.getPageSize());
            outstorageBatchPageVo.setResCount(outstorageBatchPoAdbPageVo.getRsCount());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AplException(CommonStatusCode.GET_FAIL, null);
        }


        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, outstorageBatchPageVo);
    }


    /**
     * 获取出货交接单详细信息
     * @param id
     * @return
     */
    @Override
    public ResultUtil<OutstorageBatchInfoVo> get(Long id) {

        OutstorageBatchInfoVo outstorageBatchInfoVo;

        try {
            //获取出货交接单详细信息
            OutstorageBatchPo outstorageBatchPo = outstorageBatchDao.get(id);
            if(null == outstorageBatchPo.getId() || outstorageBatchPo.getId() < 1)
                throw new AplException(OutstorageBatchServiceImplCode.NO_CORRESPONDING_DATA.code, OutstorageBatchServiceImplCode.NO_CORRESPONDING_DATA.msg);

            List<OutstorageBatchPo> outstorageBatchPoList = new ArrayList<>();
            outstorageBatchPoList.add(outstorageBatchPo);

            List<JoinBase> joinTabs = new ArrayList<>();

            JoinPartner joinPartner = new JoinPartner(1, priceExpFeign, aplCacheHelper);
            if(null!=joinPartnerFieldInfo) {

                joinPartner.setJoinFieldInfo(joinPartnerFieldInfo);
            }
            else{
                joinPartner.addField("partnerId",  Long.class, "partnerShortName", "partnerName",String.class);

                joinPartnerFieldInfo = joinPartner.getJoinFieldInfo();
            }

            joinTabs.add(joinPartner);
            JoinUtil.join(outstorageBatchPoList, joinTabs);

            //获取运单详细信息
            List<WaybillWaitOutstorageInfoVo> waybillWaitOutstorageInfoVoList = waybillDao.getInfo2(outstorageBatchPo.getPartnerId(),
                                                                                                    outstorageBatchPo.getOutBatchSn(),
                                                                                                    outstorageBatchPo.getId());

            if(waybillWaitOutstorageInfoVoList.size() < 1)
                throw new AplException(OutstorageBatchServiceImplCode.NO_CORRESPONDING_DATA.code, OutstorageBatchServiceImplCode.NO_CORRESPONDING_DATA.msg);


            //关联国家缓存字段
            List<JoinBase> joinTabs2 = new ArrayList<>();

            JoinCountry joinCountry = new JoinCountry(1, lmsCommonFeign, aplCacheHelper);
            if(null!=joinCountryFieldInfo) {
                //已经缓存国家反射字段
                joinCountry.setJoinFieldInfo(joinCountryFieldInfo);
            }
            else{
                joinCountry.addField("destCountryCode",  String.class, "nameCn", "countryNameCn",String.class);

                joinCountryFieldInfo = joinCountry.getJoinFieldInfo();
            }

            joinTabs2.add(joinCountry);
            JoinUtil.join(waybillWaitOutstorageInfoVoList, joinTabs2);


            //取运单ids
            List<Long> waybillIds = new ArrayList<>();
            for (WaybillWaitOutstorageInfoVo waybillWaitOutstorageInfoVo : waybillWaitOutstorageInfoVoList) {
                waybillIds.add(waybillWaitOutstorageInfoVo.getId());
            }

            //获取应付款信息
            List<AccountsPayablePo> accountsPayableList = outstorageBatchDao.getPayableInfo(waybillIds);
            if(accountsPayableList.size() < 1)
                throw new AplException(OutstorageBatchServiceImplCode.NO_CORRESPONDING_DATA.code, OutstorageBatchServiceImplCode.NO_CORRESPONDING_DATA.msg);


            List<WaybillPayableCombBo> waybillPayableCombList = new ArrayList<>();

            //组装运单信息和应付款信息
            for (WaybillWaitOutstorageInfoVo waybillWaitOutstorageInfoVo : waybillWaitOutstorageInfoVoList) {
                for (AccountsPayablePo accountsPayablePo : accountsPayableList) {
                    if(waybillWaitOutstorageInfoVo.getId().equals(accountsPayablePo.getWaybillId())){
                        WaybillPayableCombBo waybillPayableCombBo = new WaybillPayableCombBo();
                        BeanUtils.copyProperties(waybillWaitOutstorageInfoVo, waybillPayableCombBo);
                        BeanUtils.copyProperties(accountsPayablePo, waybillPayableCombBo);
                        waybillPayableCombBo.setPayableId(accountsPayablePo.getId());
                        waybillPayableCombList.add(waybillPayableCombBo);
                    }
                }
            }

            outstorageBatchInfoVo = new OutstorageBatchInfoVo();
            outstorageBatchInfoVo.setOutstorageBatchPo(outstorageBatchPo);
            outstorageBatchInfoVo.setWaybillPayableCombBoList(waybillPayableCombList);

        } catch (Exception e) {
           logger.error(e.getMessage(), e);
            return ResultUtil.APPRESULT(e.getMessage(),e.getCause().toString(), null);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, outstorageBatchInfoVo);
    }

    /**
     * 获取批次id
     * @param partnerId
     * @param outBatchSn
     * @return
     */
    @Override
    public Long getbatchId(Long partnerId, String outBatchSn) {
        return outstorageBatchDao.getbatchId(partnerId, outBatchSn);
    }
}