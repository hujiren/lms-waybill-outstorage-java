package com.apl.lms.waybill.outstorage.mapper;

import com.apl.lms.waybill.outstorage.pojo.po.TransferPo;
import com.apl.lms.waybill.outstorage.pojo.dto.TransferKeyDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 转运信息 Mapper 接口
 * </p>
 *
 * @author hjr
 * @since 2020-12-24
 */
@Repository
public interface TransferMapper extends BaseMapper<TransferPo> {

    /**
     * @Desc: 根据id 查找详情
     * @Author: ${cfg.author}
     * @Date: 2020-12-24
     */
    TransferPo getById(@Param("id" ) Long id);

    /**
     * @Desc: 查找列表
     * @Author: ${cfg.author}
     * @Date: 2020-12-24
     */
    List<TransferPo> getList(Page page, @Param("key" ) TransferKeyDto keyDto);


        }