package com.apl.lms.waybill.outstorage.dao;

import com.apl.db.adb.AdbHelper;
import com.apl.db.adb.AdbPageVo;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lms.waybill.outstorage.mapper.TransferMapper;
import com.apl.lms.waybill.outstorage.pojo.po.TransferPo;
import com.apl.lms.waybill.outstorage.pojo.dto.TransferKeyDto;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 转运信息 service实现类
 * </p>
 *
 * @author hjr
 * @since 2020-12-24
 */
@Repository
@Slf4j
public class TransferDao extends ServiceImpl<TransferMapper, TransferPo> {


    @Autowired
    AdbHelper adbHelper;

    public Integer add(TransferPo transferPo) throws Exception {

        Integer resultNum = adbHelper.insert(transferPo, "apl_lms_outstorage.transfer");
        return resultNum;
    }


    public Integer updById(TransferPo transferPo) throws Exception {

        Integer resultNum = adbHelper.updateById(transferPo, "apl_lms_outstorage.transfer" );
        return resultNum;
    }

    public Integer delById(Long id) throws Exception {

        Integer resultNum = adbHelper.delById("apl_lms_outstorage.transfer", id);
        return resultNum;
    }

    public TransferPo selectById(Long id){

        StringBuffer sql = new StringBuffer();
        sql.append("select id, out_batch_id, create_time, partner_id, bag_no, lading_no, cut_off_time, ctns, actual_weight, volume, volume_weight, charge_weight, transfer_status, remark");
        sql.append(" from apl_lms_outstorage.transfer where id = " + id);
        TransferPo transferPo = adbHelper.queryObj(sql.toString(), null, TransferPo.class);

        return transferPo;
    }

    public AdbPageVo<TransferPo> getList(PageDto pageDto, TransferKeyDto keyDto){

        Timestamp createTime = new Timestamp(keyDto.getCreateTime());

        StringBuffer sql = new StringBuffer();
        sql.append("select id, out_batch_id, create_time, partner_id, bag_no, lading_no, cut_off_time, charge_weight, transfer_status from apl_lms_outstorage.transfer");
        sql.append(" where create_time > :createTime");
        if(keyDto.getPartnerId() > 0)
            sql.append(" and partner_id = " + keyDto.getPartnerId());
        if(null != keyDto.getTransferStatus())
            sql.append(" and transfer_status = " + keyDto.getTransferStatus());
        if(null != keyDto.getSn())
            sql.append(" and (FIND_IN_SET(bag_no, :sn) or FIND_IN_SET(lading_no, :sn))");

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("createTime", createTime);
        paramMap.put("sn", keyDto.getSn());

        AdbPageVo<TransferPo> transferPoAdbPageVo = adbHelper.queryPage2(sql.toString(), paramMap, TransferPo.class, pageDto.getPageIndex(), pageDto.getPageSize());
        return transferPoAdbPageVo;
    }

    public void upd(TransferPo transferPo) throws Exception {

        adbHelper.updateById(transferPo, "apl_lms_outstorage.transfer" );

    }
}