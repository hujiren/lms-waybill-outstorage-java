package com.apl.lms.waybill.outstorage.dao;

import com.apl.db.adb.AdbHelper;
import com.apl.db.adb.AdbPageVo;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lms.waybill.outstorage.pojo.dto.OutstorageBatchKeyDto;
import com.apl.lms.waybill.outstorage.pojo.po.AccountsPayablePo;
import com.apl.lms.waybill.outstorage.pojo.po.OutstorageBatchPo;
import com.apl.lms.waybill.outstorage.mapper.OutstorageBatchMapper;
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
 * <p>
 * 出货批次 service实现类
 * </p>
 *
 * @author hjr
 * @since 2020-12-21
 */
@Repository
@Slf4j
public class OutstorageBatchDao extends ServiceImpl<OutstorageBatchMapper, OutstorageBatchPo>{

    @Autowired
    AdbHelper adbHelper;

    /**
     * 检查批号是否存在
     * @param outBatchSn
     * @return
     */
    public OutstorageBatchPo checkBatchSn(Long partnerId, String outBatchSn) {

        StringBuffer sql = new StringBuffer();
        sql.append("select * from outstorage_batch where partner_id = " +  partnerId);
        sql.append(" and out_batch_sn = :outBatchSn");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("outBatchSn", outBatchSn);

        OutstorageBatchPo outstorageBatchPo = adbHelper.queryObj(sql.toString(), paramMap, OutstorageBatchPo.class);

        return outstorageBatchPo;
    }


    /**
     * 插入出货交接单
     * @param outstorageBatchPo
     * @throws Exception
     */
    public void insertOutstorageBatch(OutstorageBatchPo outstorageBatchPo) throws Exception {

        adbHelper.insert(outstorageBatchPo, "outstorage_batch");
    }

    /**
     * 更新出货交接单出货状态
     * @param outBatchSn
     * @param partnerId
     * @return
     */
    public Integer updateOutStatus(String outBatchSn, Long partnerId, Timestamp newOutTime) {

        StringBuffer sql = new StringBuffer();
        sql.append("update apl_lms_outstorage.outstorage_batch set out_time = :newOutTime,");
        sql.append(" out_status = 2 where out_batch_sn = :outBatchSn");
        sql.append(" and partner_id = " + partnerId);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("newOutTime", newOutTime);
        paramMap.put("outBatchSn", outBatchSn);

        Integer updateNum = adbHelper.update(sql.toString(), paramMap);

        return updateNum;
    }

    /**
     * 分页查询
     * @param pageDto
     * @param keyDto
     * @return
     */
    public AdbPageVo<OutstorageBatchPo> getList(PageDto pageDto, OutstorageBatchKeyDto keyDto) {

        Timestamp startOutTime = new Timestamp(keyDto.getStartOutTime());
        Timestamp endOutTime = new Timestamp(keyDto.getEndOutTime());

        StringBuffer sql = new StringBuffer();
        sql.append("select id, out_time, out_batch_sn, partner_id, poll, weight from apl_lms_outstorage.outstorage_batch where out_status = 2");
        sql.append(" and out_time > :startOutTime and out_time < :endOutTime");
        if(keyDto.getPartnerId() > 0)
            sql.append(" and partner_id = " + keyDto.getPartnerId());
        if(StringUtils.isNotBlank(keyDto.getOutBatchSn()))
            sql.append(" and out_batch_sn = :outBatchSn");

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("startOutTime", startOutTime);
        paramMap.put("endOutTime", endOutTime);
        paramMap.put("outBatchSn", keyDto.getOutBatchSn());

        AdbPageVo<OutstorageBatchPo> adbPageVo = adbHelper.queryPage2(sql.toString(), paramMap, OutstorageBatchPo.class, pageDto.getPageIndex(), pageDto.getPageSize());

        return adbPageVo;
    }

    /**
     * 获取详细信息
     * @param id
     * @return
     */
    public OutstorageBatchPo get(Long id) {
        StringBuffer sql = new StringBuffer();
        sql.append("select * from outstorage_batch where id = :id");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);

        OutstorageBatchPo outstorageBatchPo = adbHelper.queryObj(sql.toString(), paramMap, OutstorageBatchPo.class);
        return outstorageBatchPo;
    }

    /**
     * 获取应付款信息列表
     * @param waybillIds
     * @return
     */
    public List<AccountsPayablePo> getPayableInfo(List<Long> waybillIds) {

        StringBuffer sbId = new StringBuffer();
        for (Long waybillId : waybillIds) {
            if(sbId.length() > 0)
                sbId.append(",");
            sbId.append(waybillId);

        }
        String ids = sbId.toString();

        StringBuffer sql = new StringBuffer();
        sql.append("select id, waybill_id, amount, currency, exchange_rate, formula, charge_name, charge_off from apl_fin.accounts_payable where waybill_id in (" + ids + ")");

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ids", ids);

        List<AccountsPayablePo> accountsPayablePoList = adbHelper.queryList2(sql.toString(), paramMap, AccountsPayablePo.class);

        return accountsPayablePoList;
    }

    public Long getbatchId(Long partnerId, String outBatchSn) {

        StringBuffer sql = new StringBuffer();
        sql.append("select id from apl_lms_outstorage.outstorage_batch where partner_id = " + partnerId + " and out_batch_sn = :outBatchSn");

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("outBatchSn", outBatchSn);
        Long outBatchId = adbHelper.queryObj(sql.toString(), paramMap, Long.class);

        return outBatchId;
    }
}