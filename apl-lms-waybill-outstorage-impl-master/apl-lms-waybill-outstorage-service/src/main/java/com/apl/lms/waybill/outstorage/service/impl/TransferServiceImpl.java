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
import com.apl.lms.price.exp.lib.cache.JoinPartner;
import com.apl.lms.price.exp.lib.feign.PriceExpFeign;
import com.apl.lms.waybill.outstorage.dao.TransferDao;
import com.apl.lms.waybill.outstorage.pojo.dto.TransferKeyDto;
import com.apl.lms.waybill.outstorage.pojo.dto.TransferAddDto;
import com.apl.lms.waybill.outstorage.pojo.vo.TransferInfoVo;
import com.apl.lms.waybill.outstorage.pojo.vo.TransferPageVo;
import com.apl.lms.waybill.outstorage.pojo.vo.WaybillTransferVo;
import com.apl.lms.waybill.outstorage.pojo.vo.WaybillWaitOutstorageInfoVo;
import com.apl.lms.waybill.outstorage.service.OutstorageBatchService;
import com.apl.lms.waybill.outstorage.service.TransferService;
import com.apl.lms.waybill.outstorage.service.WaybillService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apl.lms.waybill.outstorage.pojo.po.TransferPo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 转运信息 service实现类
 * </p>
 *
 * @author hjr
 * @since 2020-12-24
 */
@Service
@Slf4j
public class TransferServiceImpl implements TransferService {

    //状态code枚举
    enum TransferServiceCode {

        NO_CORRESPONDING_CARGO_INFORMATION_WAS_FOUND("NO_CORRESPONDING_CARGO_INFORMATION_WAS_FOUND", "该批次没有对应的货物信息");

        private String code;
        private String msg;

        TransferServiceCode(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    @Autowired
    TransferDao transferDao;

    @Autowired
    WaybillService waybillService;

    @Autowired
    OutstorageBatchService outstorageBatchService;

    @Autowired
    PriceExpFeign priceExpFeign;

    @Autowired
    AplCacheHelper aplCacheHelper;

    JoinFieldInfo joinPartnerFieldInfo = null;

    static final Logger logger = LoggerFactory.getLogger(TransferServiceImpl.class);

    /**
     * 新增
     *
     * @param transferAddDto
     * @return
     */
    @Override
    public ResultUtil<Long> add(TransferAddDto transferAddDto) {

        TransferPo transferPo;
        try {
            //获取批次id
            Long outBatchId = outstorageBatchService.getbatchId(transferAddDto.getPartnerId(), transferAddDto.getOutBatchSn());

            if(null == outBatchId)
                throw new AplException(TransferServiceCode.NO_CORRESPONDING_CARGO_INFORMATION_WAS_FOUND.code,
                        TransferServiceCode.NO_CORRESPONDING_CARGO_INFORMATION_WAS_FOUND.msg);

            //根据批次号和服务商id查询详细参数
//            WaybillWaitOutstorageInfoVo outstorageForTransfer = waybillService.getOutstorageForTransfer(outBatchId);
            WaybillTransferVo waybillTransferVo = waybillService.getOutstorageForTransfer(outBatchId);

            if(null == waybillTransferVo)
                throw new AplException(TransferServiceCode.NO_CORRESPONDING_CARGO_INFORMATION_WAS_FOUND.code,
                        TransferServiceCode.NO_CORRESPONDING_CARGO_INFORMATION_WAS_FOUND.msg);

            transferPo = new TransferPo();
            BeanUtils.copyProperties(transferAddDto,transferPo);
            transferPo.setId(SnowflakeIdWorker.generateId());
            transferPo.setOutBatchId(outBatchId);
            transferPo.setCreateTime(new Timestamp(System.currentTimeMillis()));
            transferPo.setTransferStatus(1);
            transferPo.setCtns(waybillTransferVo.getCtns());
            transferPo.setActualWeight(new BigDecimal(waybillTransferVo.getOutActualWeight()));
            transferPo.setVolume(new BigDecimal(waybillTransferVo.getOutVolume()));
            transferPo.setVolumeWeight(new BigDecimal(waybillTransferVo.getOutVolumeWeight()));
            transferPo.setChargeWeight(new BigDecimal(waybillTransferVo.getOutChargeWeight()));
            transferDao.add(transferPo);

        } catch (Exception e) {

            logger.error(e.getMessage());
            throw new AplException(CommonStatusCode.SAVE_FAIL);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, transferPo.getId());

    }

    /**
     * 更新
     * @param transferPo
     * @return
     */
    @Override
    public ResultUtil<Boolean> upd(TransferPo transferPo) {

        try {

            if(transferPo.getOutBatchId().equals(0)){
                transferDao.upd(transferPo);
            }else if(transferPo.getOutBatchId() > 0){
                TransferPo transferPo1 = new TransferPo();
                transferPo1.setId(transferPo.getId());
                transferPo1.setLadingNo(transferPo.getLadingNo());
                transferPo1.setCutInformationTime(transferPo.getCutInformationTime());
                transferPo1.setCutOffTime(transferPo.getCutOffTime());
                transferPo1.setEstimateStartTime(transferPo.getEstimateStartTime());
                transferPo1.setActualStartTime(transferPo.getActualStartTime());
                transferPo1.setEstimateArriveTime(transferPo.getEstimateArriveTime());
                transferPo1.setActualArriveTime(transferPo.getActualArriveTime());
                transferPo1.setRemark(transferPo.getRemark());
                transferDao.upd(transferPo1);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AplException(CommonStatusCode.SAVE_FAIL);
        }
        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public ResultUtil<Boolean> delById(Long id) {

        try {
            transferDao.delById(id);

        } catch (Exception e) {

            logger.error(e.getMessage());
            return ResultUtil.APPRESULT(e.getMessage(),e.getCause().toString(), false);

        }

        return ResultUtil.APPRESULT(CommonStatusCode.DEL_SUCCESS, true);

    }

    /**
     * 获取详细
     *
     * @param id
     * @return
     */
    @Override
    public ResultUtil<TransferInfoVo> selectById(Long id) {

        TransferInfoVo transferInfoVo;
        try {
            TransferPo transferPo = transferDao.selectById(id);

            transferInfoVo = new TransferInfoVo();
            BeanUtils.copyProperties(transferPo, transferInfoVo);

            List<TransferInfoVo> transferInfoVoList = new ArrayList<>();
            transferInfoVoList.add(transferInfoVo);

            associatedCache(transferInfoVoList);

            transferInfoVo = transferInfoVoList.get(0);


        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AplException(CommonStatusCode.GET_FAIL);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, transferInfoVo);
    }

    /**
     * 查询列表
     *
     * @param pageDto
     * @param keyDto
     * @return
     */
    @Override
    public ResultUtil<TransferPageVo> getList(PageDto pageDto, TransferKeyDto keyDto) {

        TransferPageVo transferPageVo;
        AdbPageVo<TransferPo> adbTransferPageVo = null;
        try {
            adbTransferPageVo = transferDao.getList(pageDto, keyDto);

            if(adbTransferPageVo.getList().size() < 1)
                throw new AplException(CommonStatusCode.GET_FAIL);

            List<TransferInfoVo> transferInfoVoList = new ArrayList<>();

            List<TransferPo> transferPoList = adbTransferPageVo.getList();

            for (TransferPo transferPo : transferPoList) {
                TransferInfoVo transferInfoVo = new TransferInfoVo();
                BeanUtils.copyProperties(transferPo, transferInfoVo);
                transferInfoVoList.add(transferInfoVo);
            }

            associatedCache(transferInfoVoList);

            transferPageVo = new TransferPageVo();
            transferPageVo.setTransferInfoVoList(transferInfoVoList);
            transferPageVo.setCount(adbTransferPageVo.getRsCount());
            transferPageVo.setPageIndex(pageDto.getPageIndex());
            transferPageVo.setPageSize(pageDto.getPageSize());

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AplException(CommonStatusCode.GET_FAIL);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, transferPageVo);
    }


    public void associatedCache(List list) throws Exception {


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
        JoinUtil.join(list, joinTabs);
    }

}