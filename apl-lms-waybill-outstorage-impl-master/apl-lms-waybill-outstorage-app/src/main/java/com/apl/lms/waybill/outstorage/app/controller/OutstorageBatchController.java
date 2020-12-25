package com.apl.lms.waybill.outstorage.app.controller;

import com.apl.lib.pojo.dto.PageDto;
import com.apl.lib.utils.ResultUtil;
import com.apl.lms.waybill.outstorage.pojo.dto.OutstorageBatchKeyDto;
import com.apl.lms.waybill.outstorage.pojo.vo.OutstorageBatchInfoVo;
import com.apl.lms.waybill.outstorage.pojo.vo.OutstorageBatchPageVo;
import com.apl.lms.waybill.outstorage.service.OutstorageBatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author hjr
 * @since 2020-12-21
 */
@RestController
@RequestMapping(value = "/outstorage_batch")
@Api(value = "出货交接单",tags = "出货交接单")
@Slf4j
public class OutstorageBatchController {

    @Autowired
    public OutstorageBatchService outstorageBatchService;

    @PostMapping("/get-list")
    @ApiOperation(value =  "分页查询出货交接单" , notes = "分页查询出货交接单")
    public ResultUtil<OutstorageBatchPageVo> getList(PageDto pageDto, @Validated OutstorageBatchKeyDto keyDto){
       return  outstorageBatchService.getList(pageDto, keyDto);
    }

    @PostMapping("/get")
    @ApiOperation(value =  "获取出货交接单详细信息" , notes = "获取出货交接单详细信息")
    @ApiImplicitParam(name = "id", value = "批次号id", required = true, paramType = "query")
    public ResultUtil<OutstorageBatchInfoVo> get(Long id){

        return outstorageBatchService.get(id);
    }
}
