package com.apl.lms.waybill.outstorage.service;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.waybill.outstorage.pojo.dto.OutstorageBatchKeyDto;
import com.apl.lms.waybill.outstorage.pojo.po.OutstorageBatchPo;
import com.apl.lms.waybill.outstorage.pojo.vo.OutstorageBatchInfoVo;
import com.apl.lms.waybill.outstorage.pojo.vo.OutstorageBatchPageVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * 出货批次 service接口
 * </p>
 *
 * @author hjr
 * @since 2020-12-21
 */
public interface OutstorageBatchService{


    /**
     * 保存出货批次信息
     * @param outstorageBatchPo
     * @return
     */
    Long saveOutstorageBatch(OutstorageBatchPo outstorageBatchPo) throws Exception;


    /**
     * 更新出货交接单出货状态
     * @param outBatchSn
     * @param partnerId
     * @return
     */
    Integer updateOutStatus(String outBatchSn, Long partnerId, Timestamp newOutTime);

    /**
     * 分页查询
     * @param pageDto
     * @param keyDto
     * @return
     */
    ResultUtil<OutstorageBatchPageVo> getList(PageDto pageDto, OutstorageBatchKeyDto keyDto);

    /**
     * 获取出货交接单详细信息
     * @param id
     * @return
     */
    ResultUtil<OutstorageBatchInfoVo> get(Long id);

    /**
     * 获取批次id
     * @param partnerId
     * @param outBatchSn
     * @return
     */
    Long getbatchId(Long partnerId, String outBatchSn);
}