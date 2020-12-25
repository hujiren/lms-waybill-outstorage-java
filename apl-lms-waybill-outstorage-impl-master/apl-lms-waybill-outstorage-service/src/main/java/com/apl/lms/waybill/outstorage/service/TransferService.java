package com.apl.lms.waybill.outstorage.service;

import com.apl.lms.waybill.outstorage.pojo.dto.TransferKeyDto;
import com.apl.lms.waybill.outstorage.pojo.dto.TransferAddDto;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.waybill.outstorage.pojo.po.TransferPo;
import com.apl.lms.waybill.outstorage.pojo.vo.TransferInfoVo;
import com.apl.lms.waybill.outstorage.pojo.vo.TransferPageVo;

/**
 * <p>
 * 转运信息 service接口
 * </p>
 *
 * @author hjr
 * @since 2020-12-24
 */
public interface TransferService {

    /**
     * @Desc: 新增转运信息
     * @author hjr
     * @since 2020-12-24
     */
    ResultUtil<Long> add(TransferAddDto transferAddDto);


    /**
     * @Desc: 删除转运信息
     * @author hjr
     * @since 2020-12-24
     */
    ResultUtil<Boolean> delById(Long id);


    /**
     * @Desc: 获取详细
     * @author hjr
     * @since 2020-12-24
     */
    ResultUtil<TransferInfoVo> selectById(Long id);


    /**
     * @Desc: 分页查找 转运信息列表
     * @author hjr
     * @since 2020-12-24
     */
    ResultUtil<TransferPageVo> getList(PageDto pageDto, TransferKeyDto keyDto);

    /**
     * 更新
     * @param transferPo
     * @return
     */
    ResultUtil<Boolean> upd(TransferPo transferPo);
}