package com.apl.lms.waybill.outstorage.dao;

import com.apl.db.adb.AdbHelper;
import com.apl.lms.waybill.outstorage.pojo.po.WaybillOutstoragePo;
import com.apl.lms.waybill.outstorage.mapper.WaybillOutstorageMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hjr start
 * @Classname WaybillOutstorageDao
 * @Date 2020/12/22 14:20
 */
@Repository
@Slf4j
public class WaybillOutstorageDao extends ServiceImpl<WaybillOutstorageMapper, WaybillOutstoragePo> {

    @Autowired
    AdbHelper adbHelper;

    /**
     * 查找相同的ids
     * @param ids
     * @return
     */
    public List<Long> querySameIds(String ids) {

        String sql = "select waybill_id from apl_lms_waybill.waybill_outstorage where waybill_id in ( " + ids + ")";
        List<Long> sameIds = adbHelper.queryList(sql, new Object(), Long.class);

        return sameIds;
    }

    /**
     * 批量更新出货列表数据
     * @param waybillOutstoragePoList
     * @throws Exception
     */
    public void updBatchWaybillOutstorage(List<WaybillOutstoragePo> waybillOutstoragePoList) throws Exception {
        adbHelper.updateBatch(waybillOutstoragePoList, "apl_lms_waybill.waybill_outstorage", "waybillId");

    }

    /**
     * 批量插入出货列表数据
     * @param waybillOutstorageListForAdd
     */
    public void addBatchWayBillOutstorage(List<WaybillOutstoragePo> waybillOutstorageListForAdd) throws Exception {
        adbHelper.insertBatch(waybillOutstorageListForAdd, "apl_lms_waybill.waybill_outstorage", "waybillId");
    }

    /**
     * 批量更新出货列表状态
     * @param waybillIds
     * @return
     */
    public Integer updateOutStatusByIds(List<Long> waybillIds, Timestamp newOutTime, Integer outStatus) {

        StringBuffer sbWaybillId = new StringBuffer();
        for (Long waybillId : waybillIds) {
            if(sbWaybillId.length() > 0)
                sbWaybillId.append(",");
            sbWaybillId.append(waybillId);
        }

        StringBuffer sql = new StringBuffer();
        sql.append("update apl_lms_waybill.waybill_outstorage set out_status = " + outStatus + ", out_batch_id = 0");
        if(null != newOutTime)
            sql.append(" ,out_time = :newOutTime");
        sql.append(" where waybill_id in(" + sbWaybillId + ")");

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("newOutTime", newOutTime);

        Integer resultNum = adbHelper.update(sql.toString(), paramMap);

        return resultNum;
    }

    /**
     * 更新
     * @param waybillOutstoragePo
     * @return
     */
    public Integer updById(WaybillOutstoragePo waybillOutstoragePo) throws Exception {
        Integer resultNum = adbHelper.updateById(waybillOutstoragePo, "apl_lms_waybill.waybill_outstorage", "waybillId");
        return resultNum;
    }


}
