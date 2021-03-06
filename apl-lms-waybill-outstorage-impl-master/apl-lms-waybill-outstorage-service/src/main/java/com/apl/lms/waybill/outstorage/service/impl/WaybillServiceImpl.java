package com.apl.lms.waybill.outstorage.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.apl.cache.AplCacheHelper;
import com.apl.lib.exception.AplException;
import com.apl.lib.join.JoinBase;
import com.apl.lib.join.JoinFieldInfo;
import com.apl.lib.join.JoinUtil;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.common.lib.cache.JoinCountry;
import com.apl.lms.common.lib.feign.LmsCommonFeign;
import com.apl.lms.price.exp.lib.cache.JoinPartner;
import com.apl.lms.price.exp.lib.feign.PriceExpFeign;
import com.apl.lms.waybill.outstorage.dao.WaybillDao;
import com.apl.lms.waybill.outstorage.pojo.bo.WaybillOutstorageBo;
import com.apl.lms.waybill.outstorage.pojo.dto.WaybillOutstorageDto;
import com.apl.lms.waybill.outstorage.pojo.dto.WaybillSaveDto;
import com.apl.lms.waybill.outstorage.pojo.dto.waybillWaitSaveDto;
import com.apl.lms.waybill.outstorage.pojo.po.OutstorageBatchPo;
import com.apl.lms.waybill.outstorage.pojo.po.WaybillOutstoragePo;
import com.apl.lms.waybill.outstorage.pojo.po.WaybillPo;
import com.apl.lms.waybill.outstorage.pojo.vo.WaybillTransferVo;
import com.apl.lms.waybill.outstorage.pojo.vo.WaybillWaitOutstorageInfoVo;
import com.apl.lms.waybill.outstorage.service.OutstorageBatchService;
import com.apl.lms.waybill.outstorage.service.WaybillOutstorageService;
import com.apl.lms.waybill.outstorage.service.WaybillService;
import com.apl.sys.lib.cache.JoinCustomer;
import com.apl.sys.lib.feign.InnerFeign;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.apl.lib.constants.CommonStatusCode;
import com.apl.lms.waybill.outstorage.pojo.dto.WaybillKeyDto;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.*;


/**
 * <p>
 * 运单 service实现类
 * </p>
 *
 * @author hjr
 * @since 2020-12-21
 */
@Service
@Slf4j
public class WaybillServiceImpl implements WaybillService {

    //状态code枚举
    enum WaybillServiceCode {

            OUT_STORAGE_SUCCESS("OUT_STORAGE_SUCCESS", "出货成功!"),
            OUT_STORAGE_FAILED("OUT_STORAGE_FAILED", "出货失败!"),
            CANCEL_STORAGE_SUCCESS("CANCEL_STORAGE_SUCCESS", "取消成功!"),
            WAYBILL_ID_QUERY_FAILED("WAYBILL_ID_QUERY_FAILED", "出货运单查询失败"),
            NO_VALID_FILE_WAS_FOUND("NO_VALID_FILE_WAS_FOUND", "没有找到有效文件"),
            TEMPLATE_DOES_NOT_EXIST("Template does not exist", "模板不存在"),
            NO_CORRESPONDING_DATA("NO_CORRESPONDING_DATA", "没有对应数据");

            private String code;
            private String msg;

            WaybillServiceCode(String code, String msg) {
                 this.code = code;
                 this.msg = msg;
            }
        }

    static final Logger logger = LoggerFactory.getLogger(WaybillServiceImpl.class);

    @Autowired
    WaybillDao waybillDao;

    @Autowired
    AplCacheHelper aplCacheHelper;

    @Autowired
    LmsCommonFeign lmsCommonFeign;

    @Autowired
    PriceExpFeign priceExpFeign;

    @Autowired
    InnerFeign innerFeign;

    @Autowired
    WaybillOutstorageService waybillOutstorageService;

    @Autowired
    OutstorageBatchService outstorageBatchService;

    JoinFieldInfo joinPartnerFieldInfo = null;

    JoinFieldInfo joinCountryFieldInfo = null;

    JoinFieldInfo joinCustomerFieldInfo = null;

    @Value("${lms.waybill-outstorage.template-file-name:}")
    String templateFileName;

    @Value("${lms.waybill-outstorage.out-file-name:}")
    String outFileName;

    /**
     * 获取未加入出货列表的数据
     * @param keyDto
     * @return
     * @throws Exception
     */
    @Override
    public ResultUtil<List<WaybillOutstorageBo>> getWaitOutstorageList(WaybillKeyDto keyDto) throws Exception {

        Timestamp receivingTime = new Timestamp(keyDto.getReceivingTime());

        List<WaybillOutstorageBo> waybillOutstorageBoList = waybillDao.getWaitOutstorageList(keyDto, receivingTime);

        for (WaybillOutstorageBo waybillOutstorageBo : waybillOutstorageBoList) {

            if(null == waybillOutstorageBo.getOutActualWeight()){
                if(null != waybillOutstorageBo.getInActualWeight())
                    waybillOutstorageBo.setOutActualWeight(waybillOutstorageBo.getInActualWeight());
            }
            if(null == waybillOutstorageBo.getOutChargeWeight()){
                if(null != waybillOutstorageBo.getInChargeWeight())
                    waybillOutstorageBo.setOutChargeWeight(waybillOutstorageBo.getInChargeWeight());
            }
            if(null == waybillOutstorageBo.getOutVolumeDivisor()){
                if(null != waybillOutstorageBo.getVolumeDivisor())
                    waybillOutstorageBo.setOutVolumeDivisor(waybillOutstorageBo.getVolumeDivisor());
            }
            if(null == waybillOutstorageBo.getOutVolumeWeight()){
                if(null != waybillOutstorageBo.getInVolumeWeight())
                    waybillOutstorageBo.setOutVolumeWeight(waybillOutstorageBo.getInVolumeWeight());
            }
            if(null == waybillOutstorageBo.getOutVolume()){
                if(null != waybillOutstorageBo.getInVolume())
                    waybillOutstorageBo.setOutVolume(waybillOutstorageBo.getInVolume());
            }
        }

        //关联缓存字段 国家 服务商 客户
        associatedCacheField(waybillOutstorageBoList);

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS , waybillOutstorageBoList);
    }



    /**
     * 获取已加入出货列表的数据
     * @param
     * @param outBatchSn
     * @return
     * @throws Exception
     */
    @Override
    public ResultUtil<List<WaybillOutstorageBo>> getOutstorageList(Long partnerId, String outBatchSn) throws Exception {

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        List<WaybillOutstorageBo> waybillOutstorageBoList = waybillDao.getOutstorageList(partnerId, outBatchSn, currentTime);

        //关联缓存字段 国家 客户
        associatedCacheField(waybillOutstorageBoList);
        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS , waybillOutstorageBoList);
    }

    /**
     * 保存批次列表
     * @param waybillSaveDto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public ResultUtil<Boolean> saveOutStorageListBatch(WaybillSaveDto waybillSaveDto){

        try {
            Timestamp outTime = new Timestamp(System.currentTimeMillis());
            Double weightSum = new Double(0.000);

            OutstorageBatchPo outstorageBatchPo = new OutstorageBatchPo();
            outstorageBatchPo.setOutTime(outTime);
            outstorageBatchPo.setOutBatchSn(waybillSaveDto.getOutBatchSn());
            outstorageBatchPo.setPartnerId(waybillSaveDto.getWaybillOutstorageList().get(0).getPartnerId());
            outstorageBatchPo.setPoll(waybillSaveDto.getPoll());

            //重量统计
            for (WaybillOutstorageDto waybillOutstorageDto : waybillSaveDto.getWaybillOutstorageList()) {
                weightSum += waybillOutstorageDto.getInChargeWeight();
            }
            outstorageBatchPo.setWeight(weightSum);
            outstorageBatchPo.setOutStatus(1);

            //保存出货交接单 已存在直接返回batch_id  不存在做插入操作
            Long batchId = outstorageBatchService.saveOutstorageBatch(outstorageBatchPo);


            List<WaybillOutstoragePo> waybillOutstoragePoList = new ArrayList<>();
            List<Long> waybillIds = new ArrayList<>();
            WaybillOutstoragePo waybillOutstoragePo;

            for (WaybillOutstorageDto waybillOutstorageDto : waybillSaveDto.getWaybillOutstorageList()) {
                waybillOutstoragePo = new WaybillOutstoragePo();
                BeanUtils.copyProperties(waybillOutstorageDto, waybillOutstoragePo);
                waybillOutstoragePo.setWaybillId(waybillOutstorageDto.getId());
                waybillIds.add(waybillOutstorageDto.getId());
                waybillOutstoragePo.setOutBatchId(batchId);
                waybillOutstoragePo.setOutBatchSn(waybillSaveDto.getOutBatchSn());
                waybillOutstoragePo.setOutTime(outTime);
                if(waybillOutstorageDto.getOutStatus().equals(0))
                    waybillOutstoragePo.setOutStatus(1);
                waybillOutstoragePoList.add(waybillOutstoragePo);
            }

            waybillOutstorageService.saveBatchWaybillOutstorage(waybillOutstoragePoList, waybillIds);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AplException(CommonStatusCode.SAVE_FAIL.code, CommonStatusCode.SAVE_FAIL.msg);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS);
    }

    /**
     * 批量移除出货列表
     * @param outBatchSn
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Boolean> removeOutStorageListBatch(String outBatchSn, Long partnerId) {

        try {
            //根据批次号和服务商id关联查询被取消的出货单号ids
            List<Long> waybillIds = waybillDao.getWaybillIdsBatch(outBatchSn, partnerId);

            if(waybillIds.size() < 1)
                throw new AplException(WaybillServiceCode.WAYBILL_ID_QUERY_FAILED.code, WaybillServiceCode.WAYBILL_ID_QUERY_FAILED.msg);

            //根据ids批量更新waybill_outstorage中的出货状态为0并且将绑定的批次id删除
            waybillOutstorageService.updateOutStatusByIds(waybillIds, null , 0);

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }

        return ResultUtil.APPRESULT(WaybillServiceCode.CANCEL_STORAGE_SUCCESS.code, WaybillServiceCode.CANCEL_STORAGE_SUCCESS.msg, true);
    }


    /**
     * 出货
     * @param outBatchSn
     * @param partnerId
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Boolean> shipping(String outBatchSn, Long partnerId) {

        Timestamp newOutTime = new Timestamp(System.currentTimeMillis());

        try {
            //更新出货交接单出货状态和出货时间
            outstorageBatchService.updateOutStatus(outBatchSn, partnerId, newOutTime);

            //根据批次号和服务商id获取出货单号id
            List<Long> waybillIds = waybillDao.getWaybillIdsBatch(outBatchSn, partnerId);

            if(waybillIds.size() < 1)
                throw new AplException(WaybillServiceCode.WAYBILL_ID_QUERY_FAILED.code, WaybillServiceCode.WAYBILL_ID_QUERY_FAILED.msg);

            //更新出货列表数据的出货状态和出货时间
            waybillOutstorageService.updateOutStatusByIds(waybillIds, newOutTime, 2);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AplException(WaybillServiceCode.OUT_STORAGE_FAILED.code, WaybillServiceCode.OUT_STORAGE_FAILED.msg);
        }

        return ResultUtil.APPRESULT(WaybillServiceCode.OUT_STORAGE_SUCCESS.code, WaybillServiceCode.OUT_STORAGE_SUCCESS.msg, true);
    }


    /**
     * 获取详细信息
     * @param startTime
     * @param sn
     * @return
     */
    @Override
    public ResultUtil<WaybillWaitOutstorageInfoVo> get(Timestamp startTime, String sn) {

        List<WaybillWaitOutstorageInfoVo> waybillWaitOutstorageInfoVoList = null;

        WaybillWaitOutstorageInfoVo waybillWaitOutstorageInfoVo = null;

        try {
            waybillWaitOutstorageInfoVoList = waybillDao.getInfo(startTime, sn);

            if(waybillWaitOutstorageInfoVoList.size() < 1)
                return ResultUtil.APPRESULT(WaybillServiceCode.NO_CORRESPONDING_DATA.code, WaybillServiceCode.NO_CORRESPONDING_DATA.msg, null);

            associatedCacheField(waybillWaitOutstorageInfoVoList);

            waybillWaitOutstorageInfoVo = waybillWaitOutstorageInfoVoList.get(0);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AplException(CommonStatusCode.GET_FAIL, null);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.GET_SUCCESS, waybillWaitOutstorageInfoVo);
    }


    /**
     * 关联缓存字段
     * @param list
     * @throws Exception
     */
    public void associatedCacheField(List list) throws Exception {

        List<JoinBase> joinTabs = new ArrayList<>();

        JoinPartner joinPartner = new JoinPartner(1, priceExpFeign, aplCacheHelper);
        if(null!=joinPartnerFieldInfo) {

            joinPartner.setJoinFieldInfo(joinPartnerFieldInfo);
        }
        else{
            joinPartner.addField("partnerId",  Long.class, "partnerShortName", "partnerName",String.class);

            joinPartnerFieldInfo = joinPartner.getJoinFieldInfo();
        }

        JoinCountry joinCountry = new JoinCountry(1, lmsCommonFeign, aplCacheHelper);
        if(null!=joinCountryFieldInfo) {
            //已经缓存国家反射字段
            joinCountry.setJoinFieldInfo(joinCountryFieldInfo);
        }
        else{
            joinCountry.addField("destCountryCode",  String.class, "nameCn", "countryNameCn",String.class);

            joinCountryFieldInfo = joinCountry.getJoinFieldInfo();
        }


        JoinCustomer joinCustomer = new JoinCustomer(1, innerFeign, aplCacheHelper);
        if (null != joinCustomerFieldInfo) {
            joinCustomer.setJoinFieldInfo(joinCustomerFieldInfo);
        } else {
            joinCustomer.addField("customerId", Long.class, "customerName", "customerName",String.class);
            joinCustomerFieldInfo = joinCustomer.getJoinFieldInfo();
        }


        joinTabs.add(joinPartner);
        joinTabs.add(joinCountry);
        joinTabs.add(joinCustomer);
        JoinUtil.join(list, joinTabs);
    }


    /**
     * 更新
     * @param waybillWaitSaveDto
     * @return
     */
    @Override
    @Transactional
    public ResultUtil<Boolean> upd(waybillWaitSaveDto waybillWaitSaveDto) {

        WaybillPo waybillPo = new WaybillPo();
        BeanUtils.copyProperties(waybillWaitSaveDto, waybillPo);

        WaybillOutstoragePo waybillOutstoragePo = new WaybillOutstoragePo();
        BeanUtils.copyProperties(waybillWaitSaveDto, waybillOutstoragePo);
        waybillOutstoragePo.setWaybillId(waybillWaitSaveDto.getId());


        try {
            waybillDao.updById(waybillPo);
            waybillOutstorageService.updById(waybillOutstoragePo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw  new AplException(CommonStatusCode.SAVE_FAIL);
        }

        return ResultUtil.APPRESULT(CommonStatusCode.SAVE_SUCCESS, true);
    }

    /**
     * 查询装袋接口参数
     * @param outBatchId
     */
    @Override
    public WaybillTransferVo getOutstorageForTransfer(Long outBatchId) {

        WaybillTransferVo outstorageForTransfer = waybillDao.getOutstorageForTransfer(outBatchId);

        return outstorageForTransfer;
    }


    /**
     * 导出出货信息报表
     * @param response
     * @param waybillIds
     */
    @Override
    public void exportWaybillStorage(HttpServletResponse response, String waybillIds) {

        if (null == templateFileName || templateFileName.length() < 2) {
            throw new AplException(WaybillServiceCode.NO_VALID_FILE_WAS_FOUND.code, WaybillServiceCode.NO_VALID_FILE_WAS_FOUND.msg, null);
        }

        //注意 使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setCharacterEncoding("utf-8");
        ExcelWriter excelWriter = null;
        String newTempFileName = null;

        try {
            //关联获取出货详细信息
            List<WaybillWaitOutstorageInfoVo> waybillVoList = waybillDao.getInfo3(waybillIds);
            if(waybillVoList.size() < 1)
                throw new AplException(WaybillServiceCode.NO_CORRESPONDING_DATA.code, WaybillServiceCode.NO_CORRESPONDING_DATA.msg);

            //关联缓存
            associatedCacheField(waybillVoList);

            //取出批次号
            String outBatchSn = waybillVoList.get(0).getOutBatchSn();

            String templateNameByOutBatchSn = templateFileName.replace("template", outBatchSn);
            File templateFile = new File(templateNameByOutBatchSn);
            if (!templateFile.exists()) {
                throw new AplException(WaybillServiceCode.TEMPLATE_DOES_NOT_EXIST.code, WaybillServiceCode.TEMPLATE_DOES_NOT_EXIST.msg, null);
            }

            //返回上一级目录, 即不带文件名的全路径, 构建临时文件全路径
            newTempFileName = templateFile.getParent() + "/export-waybill-outstorage-" + UUID.randomUUID() + ".xlsx";
            FileInputStream fs = new FileInputStream(templateNameByOutBatchSn);
            XSSFWorkbook wb = new XSSFWorkbook(fs);
            wb.setSheetName(0, outBatchSn);

            //输出临时模板文件
            FileOutputStream fileOut = new FileOutputStream(newTempFileName);
            wb.write(fileOut);
            fileOut.close();

            excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(newTempFileName).build();

            String sheetName = null;

            int wayBillTemplateSheetIndex = 0;
            int numberOfSheets = wb.getNumberOfSheets();
            if(numberOfSheets < 1)
                throw new AplException(WaybillServiceCode.TEMPLATE_DOES_NOT_EXIST.code, WaybillServiceCode.TEMPLATE_DOES_NOT_EXIST.msg, null);

            for(int i = 0; i < numberOfSheets; i++){
                sheetName = wb.getSheetAt(i).getSheetName();
                if(sheetName.contains(outBatchSn))
                    wayBillTemplateSheetIndex = i;
            }

            WriteSheet wayBillSheet = EasyExcel.writerSheet(wayBillTemplateSheetIndex).build();
            wayBillSheet.setSheetName(outBatchSn);

            List<Map<String, Object>> waybillOutstorageFillList = new ArrayList<>();
            Map<String, Object> rowMap = null;
            for (WaybillWaitOutstorageInfoVo waybillVo : waybillVoList) {
                int i = 1;
                rowMap = new HashMap<>();
                rowMap.put("num", i);
                rowMap.put("referenceSn", waybillVo.getReferenceSn());
                rowMap.put("trackingSn", waybillVo.getTrackingSn());
                rowMap.put("customerName", waybillVo.getCustomerName());
                rowMap.put("channelCategory", waybillVo.getChannelCategory());
                rowMap.put("countryNameCn", waybillVo.getCountryNameCn());
                rowMap.put("cargoType", waybillVo.getCargoType());
                rowMap.put("outActualWeight", waybillVo.getOutActualWeight());
                rowMap.put("outVolumeWeight", waybillVo.getOutVolumeWeight());
                rowMap.put("outChargeWeight", waybillVo.getOutChargeWeight());
                rowMap.put("partnerName", waybillVo.getPartnerName());
                rowMap.put("outChannelName", waybillVo.getOutChannelName());
                rowMap.put("ctns", waybillVo.getCtns());
                rowMap.put("commodityName", waybillVo.getCommodityName());
                rowMap.put("internallyRemark", waybillVo.getInternallyRemark());
                waybillOutstorageFillList.add(rowMap);
                i++;
            }
            excelWriter.fill(waybillOutstorageFillList, wayBillSheet);

            //web导出
            outFileName = outFileName.replace("template", outBatchSn);
            response.setContentType("application/vnd.ms-excel");
            outFileName = URLEncoder.encode(outFileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + outFileName);

        } catch (Exception e) {

            logger.error(e.getMessage());
            throw new AplException(e.getMessage(), e.getCause().toString());

        }finally {

            if(null!=newTempFileName) {
                File delFile = new File(newTempFileName);
                delFile.delete();
            }

            if (null != excelWriter) {
                excelWriter.finish();
            }
        }
    }

}