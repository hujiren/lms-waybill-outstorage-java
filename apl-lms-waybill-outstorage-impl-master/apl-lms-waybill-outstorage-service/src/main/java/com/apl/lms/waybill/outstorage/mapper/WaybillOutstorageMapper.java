package com.apl.lms.waybill.outstorage.mapper;

import com.apl.lms.waybill.outstorage.pojo.po.WaybillOutstoragePo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * 运单出货信息 Mapper 接口
 * </p>
 *
 * @author hjr
 * @since 2020-12-21
 */
public interface WaybillOutstorageMapper extends BaseMapper<WaybillOutstoragePo> {

}