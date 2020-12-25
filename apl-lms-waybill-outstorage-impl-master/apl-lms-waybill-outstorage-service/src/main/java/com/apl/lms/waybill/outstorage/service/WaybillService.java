package com.apl.lms.waybill.outstorage.service;

import com.apl.lms.waybill.outstorage.pojo.bo.WaybillOutstorageBo;
import com.apl.lms.waybill.outstorage.pojo.dto.WaybillSaveDto;
import com.apl.lms.waybill.outstorage.pojo.dto.WaybillKeyDto;
import com.apl.lms.waybill.outstorage.pojo.dto.waybillWaitSaveDto;
import com.apl.lms.waybill.outstorage.pojo.vo.WaybillTransferVo;
import com.apl.lms.waybill.outstorage.pojo.vo.WaybillWaitOutstorageInfoVo;
import com.apl.lib.utils.ResultUtil;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * 运单 service接口
 * </p>
 *
 * @author hjr
 * @since 2020-12-21
 */
public interface WaybillService {


    /**
     * @Desc: 获取等待出货列表
     * @author hjr
     * @since 2020-12-21
     */
    ResultUtil<List<WaybillOutstorageBo>> getWaitOutstorageList(WaybillKeyDto keyDto) throws Exception;


    /**
     * 获取已加入出货列表数据集
     *
     * @param
     * @param outBatchSn
     * @return
     */
    ResultUtil<List<WaybillOutstorageBo>> getOutstorageList(Long partnerId, String outBatchSn) throws Exception;


    /**
     * 批量保存出货列表
     *
     * @param waybillSaveDto
     * @return
     */
    ResultUtil<Boolean> saveOutStorageListBatch(WaybillSaveDto waybillSaveDto);


    /**
     * 批量移除出货列表
     *
     * @param outBatchSn
     * @return
     */
    ResultUtil<Boolean> removeOutStorageListBatch(String outBatchSn, Long partnerId);


    /**
     * 出货
     * @param outBatchSn
     * @param partnerId
     * @return
     */
    ResultUtil<Boolean> shipping(String outBatchSn, Long partnerId);


    /**
     * 查询详细信息
     * @param startTime
     * @param sn
     * @return
     */
    ResultUtil<WaybillWaitOutstorageInfoVo> get(Timestamp startTime, String sn) ;



    /**
     * 修改运单详细信息
     * @param waybillWaitSaveDto
     * @return
     */
    ResultUtil<Boolean> upd(waybillWaitSaveDto waybillWaitSaveDto);

    /**
     * 查询装袋接口参数
     * @param outBatchId
     */
    WaybillTransferVo getOutstorageForTransfer(Long outBatchId);
}