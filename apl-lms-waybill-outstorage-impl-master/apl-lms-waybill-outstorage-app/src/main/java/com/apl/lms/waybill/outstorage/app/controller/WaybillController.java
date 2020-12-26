package com.apl.lms.waybill.outstorage.app.controller;

import com.apl.lib.utils.StringUtil;
import com.apl.lms.waybill.outstorage.pojo.bo.WaybillOutstorageBo;
import com.apl.lms.waybill.outstorage.pojo.dto.WaybillOutstorageDto;
import com.apl.lms.waybill.outstorage.pojo.dto.WaybillSaveDto;
import com.apl.lms.waybill.outstorage.pojo.dto.waybillWaitSaveDto;
import com.apl.lms.waybill.outstorage.pojo.vo.WaybillWaitOutstorageInfoVo;
import com.apl.lms.waybill.outstorage.service.WaybillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.apl.lms.waybill.outstorage.pojo.dto.WaybillKeyDto;
import com.apl.lib.utils.ResultUtil;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.time.*;
import java.util.List;

/**
 *
 * @author hjr
 * @since 2020-12-21
 */
@RestController
@RequestMapping(value = "/waybill")
@Validated
@Api(value = "出货",tags = "出货")
@Slf4j
public class WaybillController {

    @Autowired
    public WaybillService waybillService;


    @PostMapping(value = "/get-wait-outstorage-list")
    @ApiOperation(value =  "查找未加入出货列表数据" , notes = "查找未加入出货列表数据")
    public ResultUtil<List<WaybillOutstorageBo>> getNoJoinList(@Validated WaybillKeyDto keyDto) throws Exception {
        return waybillService.getWaitOutstorageList(keyDto);
    }


    @PostMapping(value = "/get-outstorage-list")
    @ApiOperation(value =  "查找已加入出货列表数据" , notes = "查找已加入出货列表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "partnerId", value = "服务商id", paramType = "query", required = true),
            @ApiImplicitParam(name = "outBatchSn", value = "批次号", paramType = "query", required = true)
    })
    public ResultUtil<List<WaybillOutstorageBo>> getHasJoinedList(
            @Min(value = 0, message = "服务商id不合法") @NotNull(message = "服务商id不合法") Long partnerId,
            @Length(max = 30, message = "批次号不合法") @NotBlank(message = "批次号不合法") String outBatchSn
                                                                  ) throws Exception {
        return waybillService.getOutstorageList(partnerId, outBatchSn);
    }


    @PostMapping(value = "/save-outstorage-list-batch")
    @ApiOperation(value =  "批量保存出货列表" , notes = "批量保存出货列表")
    public ResultUtil<Boolean> saveOutStorageListBatch(@Validated @RequestBody WaybillSaveDto waybillSaveDto) {
        ResultUtil<Boolean> resultUtil = waybillService.saveOutStorageListBatch(waybillSaveDto);
        return resultUtil;
    }


    @PostMapping(value = "/remove-outstorage-list-batch")
    @ApiOperation(value =  "批量移除出货列表" , notes = "批量移除出货列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "outBatchSn", value = "批次号", paramType = "query", required = true),
            @ApiImplicitParam(name = "partnerId", value = "服务商id", paramType = "query", required = true),
            })
    public ResultUtil<Boolean> removeOutStorageListBatch(@Length(max = 30, message = "批次号不合法") @NotBlank(message = "批次号不合法") String outBatchSn,
                                                        @Min(value = 0, message = "服务商id不合法") @NotNull(message = "服务商id不合法") Long partnerId){
        ResultUtil<Boolean> resultUtil = waybillService.removeOutStorageListBatch(outBatchSn, partnerId);
        return resultUtil;
    }


    @PostMapping(value = "/shipping")
    @ApiOperation(value =  "出货" , notes = "出货")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "outBatchSn", value = "批次号", paramType = "query", required = true),
            @ApiImplicitParam(name = "partnerId", value = "服务商id", paramType = "query", required = true),
    })
    public ResultUtil<Boolean> shipping(@Length(max = 30, message = "批次号不合法") @NotBlank(message = "批次号不合法") String outBatchSn,
                                        @Min(value = 0, message = "服务商id不合法") @NotNull(message = "服务商id不合法") Long partnerId){
        ResultUtil<Boolean> resultUtil = waybillService.shipping(outBatchSn, partnerId);
        return resultUtil;
    }


    @PostMapping(value = "/get")
    @ApiOperation(value =  "获取待出货详细信息" , notes = "获取待出货详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "起始日期", paramType = "query"),
            @ApiImplicitParam(name = "sn", value = "运单号/转单号", paramType = "query", required = true)
    })
    public ResultUtil<WaybillWaitOutstorageInfoVo> get(Timestamp startTime,
                                                       @Length(max = 20,min = 6, message = "搜索单号长度介于6-20") @NotBlank(message = "单号不合法") String sn){

        if(null == startTime || startTime.getTime() < 1){
            LocalDateTime localDateTime = LocalDateTime.now().minusMonths(1L);
            ZoneId zone = ZoneId.systemDefault();
            Instant instant = localDateTime.atZone(zone).toInstant();
            long time = instant.toEpochMilli();
            startTime = new Timestamp(time);
        }

        ResultUtil<WaybillWaitOutstorageInfoVo> resultUtil = waybillService.get(startTime, sn);
        return resultUtil;
    }


    @PostMapping(value = "/upd")
    @ApiOperation(value =  "保存运单信息" , notes = "保存运单信息")
    public ResultUtil<Boolean> upd(@Validated waybillWaitSaveDto waybillWaitSaveDto){
        ResultUtil<Boolean> resultUtil = waybillService.upd(waybillWaitSaveDto);
        return resultUtil;
    }


    @GetMapping(value = "/export-waybill-outstorage-excel")
    @ApiOperation(value = "导出出货信息报表", notes = "导出出货信息报表")
    public void exportWaybillStorage(HttpServletResponse response,
                                   @NotBlank(message = "id不能为空") String ids) {

        List<Long> waybillIds =  StringUtil.stringToLongList(ids);
        waybillService.exportWaybillStorage(response, ids);

    }
}
