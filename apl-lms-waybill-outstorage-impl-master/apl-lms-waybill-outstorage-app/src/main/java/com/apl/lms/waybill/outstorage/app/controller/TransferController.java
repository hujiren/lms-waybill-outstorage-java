package com.apl.lms.waybill.outstorage.app.controller;

import com.apl.lms.waybill.outstorage.pojo.dto.TransferAddDto;
import com.apl.lms.waybill.outstorage.pojo.po.TransferPo;
import com.apl.lms.waybill.outstorage.pojo.vo.TransferInfoVo;
import com.apl.lms.waybill.outstorage.pojo.vo.TransferPageVo;
import com.apl.lms.waybill.outstorage.service.TransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.apl.lms.waybill.outstorage.pojo.dto.TransferKeyDto;
import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author hjr
 * @since 2020-12-24
 */
@RestController
@RequestMapping(value = "/transfer")
@Validated
@Api(value = "装袋",tags = "装袋")
@Slf4j
public class TransferController {

    @Autowired
    public TransferService transferService;


    @PostMapping(value = "/add")
    @ApiOperation(value =  "新增", notes ="新增")
    public ResultUtil<Long> add(@Validated TransferAddDto transferAddDto) {

        return transferService.add(transferAddDto);
    }

    @PostMapping(value = "/upd")
    @ApiOperation(value =  "更新" , notes = "更新")
    public ResultUtil<Boolean> upd(@Validated TransferPo transferPo) {

        return transferService.upd(transferPo);
    }

    @PostMapping(value = "/del")
    @ApiOperation(value =  "删除" , notes = "删除")
    @ApiImplicitParam(name = "id",value = " id",required = true  , paramType = "query")
    public ResultUtil<Boolean> delById(@NotNull(message = "id不能为空") @Min(value = 1 , message = "id不能小于1") Long id) {

        return transferService.delById(id);
    }

    @PostMapping(value = "/get")
    @ApiOperation(value =  "获取详细" , notes = "获取详细")
    @ApiImplicitParam(name = "id",value = "id",required = true  , paramType = "query")
    public ResultUtil<TransferInfoVo> selectById(@NotNull(message = "id不能为空") @Min(value = 1 , message = "id不能小于1") Long id) {

        return transferService.selectById(id);
    }

    @PostMapping(value = "/get-list")
    @ApiOperation(value =  "分页查找" , notes = "分页查找")
    public ResultUtil<TransferPageVo> getList(PageDto pageDto, @Validated TransferKeyDto keyDto) {

        return transferService.getList(pageDto , keyDto);
    }

}
