package com.apl.lms.waybill.outstorage.service;

import com.apl.lms.waybill.outstorage.pojo.po.WaybillOutstoragePo;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * 运单出货信息 service接口
 * </p>
 *
 * @author hjr
 * @since 2020-12-21
 */
public interface WaybillOutstorageService {

    /**
     * 批量保存出货运单信息
     *
     * @param waybillOutstoragePoList
     * @return
     */
    void saveBatchWaybillOutstorage(List<WaybillOutstoragePo> waybillOutstoragePoList, List<Long> waybillIds) throws Exception;

    /**
     * 根据ids批量更新出货状态为0
     *
     * @param waybillIds
     * @return
     */
    Integer updateOutStatusByIds(List<Long> waybillIds, Timestamp newOutTime, Integer outStatus);

    /**
     * 根据Id更新
     * @param waybillOutstoragePo
     * @return
     */
    Integer updById(WaybillOutstoragePo waybillOutstoragePo) throws Exception;
}