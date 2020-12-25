package com.apl.lms.waybill.outstorage.mapper;

import com.apl.lms.waybill.outstorage.pojo.bo.WaybillOutstorageBo;
import com.apl.lms.waybill.outstorage.pojo.po.WaybillPo;
import com.apl.lms.waybill.outstorage.pojo.dto.WaybillKeyDto;
import com.apl.lms.waybill.outstorage.pojo.vo.WaybillWaitOutstorageInfoVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Pattern;

/**
 * <p>
 * 运单 Mapper 接口
 * </p>
 *
 * @author hjr
 * @since 2020-12-21
 */
public interface WaybillMapper extends BaseMapper<WaybillPo> {

    /**
     * @Desc: 获取等待加入出货列表数据
     * @Author: ${cfg.author}
     * @Date: 2020-12-21
     */
    List<WaybillOutstorageBo> getWaitOutstorageList(@Param("key") WaybillKeyDto keyDto, @Param("receivingTime") Timestamp receivingTime);

    /**
     * 获取已加入出货列表数据
     * @param
     * @param
     * @return
     */
    List<WaybillOutstorageBo> getOutstorageList(@Param("partnerId") Long partnerId,
                                                @Param("outBatchSn") String outBatchSn,
                                                @Param("currentTime") Timestamp currentTime);

    /**
     * 根据批次号和服务商id批量查询出货单号ids
     * @param outBatchSn
     * @param partnerId
     * @return
     */
    List<Long> getWaybillIdsBatch(@Param("outBatchSn") String outBatchSn, @Param("partnerId") Long partnerId);


}