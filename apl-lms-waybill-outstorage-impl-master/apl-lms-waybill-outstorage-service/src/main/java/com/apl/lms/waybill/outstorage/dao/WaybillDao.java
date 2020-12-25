package com.apl.lms.waybill.outstorage.dao;

import com.apl.db.adb.AdbHelper;
import com.apl.lib.security.SecurityUser;
import com.apl.lms.waybill.outstorage.mapper.WaybillMapper;
import com.apl.lms.waybill.outstorage.pojo.bo.WaybillOutstorageBo;
import com.apl.lms.waybill.outstorage.pojo.dto.WaybillKeyDto;
import com.apl.lms.waybill.outstorage.pojo.po.WaybillPo;
import com.apl.lms.waybill.outstorage.pojo.vo.WaybillWaitOutstorageInfoVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hjr start
 * @Classname WaybillDao
 * @Date 2020/12/21 16:07
 */
@Repository
@Slf4j
public class WaybillDao extends ServiceImpl<WaybillMapper, WaybillPo> {

    @Autowired
    AdbHelper adbHelper;

    /**
     * 获取等待出货列表数据
     * @param keyDto
     * @return
     */
    public List<WaybillOutstorageBo> getWaitOutstorageList(WaybillKeyDto keyDto, Timestamp receivingTime) {

        List<WaybillOutstorageBo> waybillOutstorageBoList = baseMapper.getWaitOutstorageList(keyDto, receivingTime);

        return waybillOutstorageBoList;
    }

    /**
     * 获取已加入出货列表数据
     * @param
     * @param
     * @return
     */
    public List<WaybillOutstorageBo> getOutstorageList(Long partnerId, String outBatchSn, Timestamp currentTime) {

        List<WaybillOutstorageBo> list = baseMapper.getOutstorageList(partnerId, outBatchSn, currentTime);

        return list;
    }


    /**
     * 根据批次号和服务商id批量查询出货单号ids
     * @param outBatchSn
     * @param partnerId
     * @return
     */
    public List<Long> getWaybillIdsBatch(String outBatchSn, Long partnerId) {

//        StringBuffer sql = new StringBuffer();
//        sql.append("select wb.id from waybill wb left join waybill_outstorage wbo on wb.id = wbo.waybill_id where");
//        sql.append(" wbo.partner_id = " + partnerId);
//        sql.append(" and wbo.out_batch_sn = :outBatchSn");
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("outBatchSn", outBatchSn);
//        List<Long> waybillIdList = adbHelper.queryList2(sql.toString(), paramMap, Long.class);
        List<Long> waybillIdsBatch = baseMapper.getWaybillIdsBatch(outBatchSn, partnerId);
        return waybillIdsBatch;
    }


    /**
     * 获取详细信息
     * @param startTime
     * @param sn
     * @return
     */
    public List<WaybillWaitOutstorageInfoVo> getInfo(Timestamp startTime, String sn) {

        StringBuffer sql = new StringBuffer();
        sql.append("select wb.id, wb.reference_sn, wb.tracking_sn, wb.customer_id, wb.partner_id, wb.receiving_time, wb.dest_country_code, wb.ctns,");
        sql.append(" wb.declare_value, wb.dest_zip_code, wb.tariff_payer, wb.commodity_name, wb.channel_category, wb.channel_name, wbo.out_channel_name,");
        sql.append(" wb.in_actual_weight, wb.in_volume_weight, wb.in_charge_weight, wb.cargo_type, wbo.out_charge_weight, wbo.out_actual_weight,");
        sql.append(" wbo.out_volume_weight, wbo.out_batch_id, wb.commodity_name_en, wb.internally_remark, wbo.out_status from apl_lms_waybill.waybill wb left join");
        sql.append(" apl_lms_waybill.waybill_outstorage wbo on wb.id = wbo.waybill_id where wb.receiving_time > :startTime and wbo.out_status = 0 and");
        sql.append(" (wb.reference_sn like CONCAT('%', :sn, '%') or wb.tracking_sn like CONCAT('%', :sn, '%'))");
        sql.append(" and wb.inner_org_id={tenantValue}");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("startTime", startTime);
        paramMap.put("sn", sn);

        List<WaybillWaitOutstorageInfoVo> waybillWaitOutstorageInfoVoList = adbHelper.queryList2(sql.toString(), paramMap, WaybillWaitOutstorageInfoVo.class);

        return waybillWaitOutstorageInfoVoList;
    }

    /**
     * 更新
     * @param waybillPo
     * @return
     * @throws Exception
     */
    public Integer updById(WaybillPo waybillPo) throws Exception {

        Integer resultNum = adbHelper.updateById(waybillPo, "apl_lms_waybill.waybill", "id");

        return resultNum;
    }

    public List<WaybillWaitOutstorageInfoVo> getInfo2(Long partnerId, String outBatchSn, Long id) {

        StringBuffer sql = new StringBuffer();
        sql.append("select wb.id, wb.reference_sn, wb.tracking_sn, wb.dest_country_code, wbo.out_charge_weight, wb.channel_category, wbo.out_channel_name,");
        sql.append(" wb.internally_remark, wb.ctns, wb.cargo_type ,wbo.out_status from apl_lms_waybill.waybill wb left join apl_lms_waybill.waybill_outstorage wbo");
        sql.append(" on wb.id = wbo.waybill_id where wbo.out_batch_id = " + id);
        if(null != partnerId && partnerId > 0)
            sql.append(" and wb.partner_id = " + partnerId);
        if(StringUtils.isNotBlank(outBatchSn))
            sql.append(" and wbo.out_batch_sn = :outBatchSn");
        sql.append(" and wb.inner_org_id = {tenantValue}");


        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("outBatchSn", outBatchSn);

        List<WaybillWaitOutstorageInfoVo> waybillWaitOutstorageInfoVoList = adbHelper.queryList2(sql.toString(), paramMap, WaybillWaitOutstorageInfoVo.class);

        return waybillWaitOutstorageInfoVoList;
    }

    /**
     * 查询装袋接口参数
     * @param outBatchId
     */
    public WaybillWaitOutstorageInfoVo getOutstorageForTransfer(Long outBatchId) {

        StringBuffer sql = new StringBuffer();
        sql.append("select count(wb.ctns) as ctns, count(wbo.out_actual_weight) as out_actual_weight, count(wbo.out_volume) as out_volume, count(wbo.out_volume_weight) as out_volume_weight,");
        sql.append("  count(wbo.out_charge_weight) as out_charge_weight, from apl_lms_waybill.waybill wb left join apl_lms_waybill.waybill_outstorage wbo");
        sql.append(" on wb.id = wbo.waybill_id where wbo.out_batch_id = " + outBatchId);

        WaybillWaitOutstorageInfoVo waybillWaitOutstorageInfoVo = adbHelper.queryObj(sql.toString(), null, WaybillWaitOutstorageInfoVo.class);

        return waybillWaitOutstorageInfoVo;
    }
}
