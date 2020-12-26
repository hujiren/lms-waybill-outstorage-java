package com.apl.lms.waybill.outstorage.pojo.vo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author hjr start
 * @Classname WaybillOutstorageVo
 * @Date 2020/12/23 15:12
 */
@Data
@ApiModel(value = "出库运单详细信息返回对象", description = "出库运单详细信息返回对象")
public class WaybillWaitOutstorageInfoVo implements Serializable {

    @ApiModelProperty(name = "id", value = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(name = "outBatchId" , value = "批次id")
    private Long outBatchId;

    @ApiModelProperty(name = "outBatchSn" , value = "批次号")
    private String outBatchSn;

    @ApiModelProperty(name = "referenceSn" , value = "参考单号 原单号 ：客户单号 运单号")
    private String referenceSn;

    @ApiModelProperty(name = "trackingSn" , value = "转单号, 跟踪号")
    private String trackingSn;

    @ApiModelProperty(name = "customerId" , value = "客户id")
    private Long customerId;

    @ApiModelProperty(name = "customerName" , value = "客户")
    private String customerName;

    @ApiModelProperty(name = "partnerId" , value = "服务商id")
    private Long partnerId;

    @ApiModelProperty(name = "partnerName" , value = "服务商")
    private String partnerName;

    @ApiModelProperty(name = "receivingTime" , value = "收货时间")
    private Timestamp receivingTime;

    @ApiModelProperty(name = "destCountryCode" , value = "国家简码")
    private String destCountryCode;

    @ApiModelProperty(name = "countryNameCn" , value = "国家")
    private String countryNameCn;

    @ApiModelProperty(name = "ctns" , value = "箱数")
    private Integer ctns;

    @ApiModelProperty(name = "declareValue", value = "申报价值")
    private Double declareValue;

    @ApiModelProperty(name = "destZipCode" , value = "邮编")
    private String destZipCode;

    @ApiModelProperty(name = "tariffPayer", value = "关税支付 收件人付/发件人付")
    private Integer tariffPayer;

    @ApiModelProperty(name = "commodityName" , value = "中文品名")
    private String commodityName;

    @ApiModelProperty(name = "commodityNameEn" , value = "申报品名,英文品名")
    private String commodityNameEn;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型")
    private String channelCategory;

    @ApiModelProperty(name = "channelName" , value = "渠道名称, 入仓渠道")
    private String channelName;

    @ApiModelProperty(name = "outChannelName" , value = "渠道名称, 出仓渠道")
    private String outChannelName;

    @ApiModelProperty(name = "inActualWeight" , value = "收货实重")
    private Double inActualWeight;

    @ApiModelProperty(name = "inVolumeWeight" , value = "收货体积重")
    private Double inVolumeWeight;

    @ApiModelProperty(name = "inChargeWeight" , value = "收货计费重")
    private Double inChargeWeight;

    @ApiModelProperty(name = "cargoType" , value = "入包裹类型")
    private String cargoType;

    @ApiModelProperty(name = "outActualWeight" , value = "出货实重")
    private Double outActualWeight;

    @ApiModelProperty(name = "outVolume" , value = "出货体积")
    private Double outVolume;

    @ApiModelProperty(name = "outVolumeWeight" , value = "出货体积重")
    private Double outVolumeWeight;

    @ApiModelProperty(name = "outChargeWeight" , value = "出货计费重")
    private Double outChargeWeight;

    @ApiModelProperty(name = "outStatus" , value = "出货状态 0未加入 1已加入出货列表 2已出货")
    private Integer outStatus;

    @ApiModelProperty(name = "internallyRemark" , value = "内部备注")
    private String internallyRemark;

}
