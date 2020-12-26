package com.apl.lms.waybill.outstorage.pojo.bo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author hjr start
 * @Classname WaybillOutstorageVo
 * @Date 2020/12/21 15:39
 */
@Data
@ApiModel(value = "出货-交互/返回对象", description = "出货-交互/返回对象")
public class WaybillOutstorageBo implements Serializable {

    @ApiModelProperty(name = "id", value = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(name = "id", value = "出货批次id")
    private Long outBatchId;

    @ApiModelProperty(name = "outBatchSn", value = "批次号")
    private String outBatchSn;

    @ApiModelProperty(name = "referenceSn" , value = "参考单号 原单号 ：客户单号 运单号")
    private String referenceSn;

    @ApiModelProperty(name = "trackingSn" , value = "转单号, 跟踪号")
    private String trackingSn;

    @ApiModelProperty(name = "partnerId" , value = "服务商id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long partnerId;

    @ApiModelProperty(name = "partnerName" , value = "服务商名称")
    private String partnerName;

    @ApiModelProperty(name = "customerId" , value = "客户id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long customerId;

    @ApiModelProperty(name = "customerName" , value = "客户名称")
    private String customerName;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型")
    private String channelCategory;

    @ApiModelProperty(name = "channelName" , value = "入仓渠道")
    private String channelName;

    @ApiModelProperty(name = "outChannelName" , value = "出仓渠道")
    private String outChannelName;

    @ApiModelProperty(name = "destCountryCode" , value = "国家简码")
    private String destCountryCode;

    @ApiModelProperty(name = "countryNameCn" , value = "国家")
    private String countryNameCn;

    @ApiModelProperty(name = "ctns" , value = "箱数")
    private Integer ctns;

    @ApiModelProperty(name = "inActualWeight" , value = "收货实重")
    private Double inActualWeight;

    @ApiModelProperty(name = "inChargeWeight" , value = "收货计费重")
    private Double inChargeWeight;

    @ApiModelProperty(name = "inVolumeWeight" , value = "收货体积重")
    private Double inVolumeWeight;

    @ApiModelProperty(name = "inVolume" , value = "收货体积")
    private Double inVolume;

    @ApiModelProperty(name = "volumeDivisor" , value = "收货体积除数")
    private Integer volumeDivisor;

    @ApiModelProperty(name = "outChargeWeight" , value = "出货计费重")
    private Double outChargeWeight;

    @ApiModelProperty(name = "outActualWeight" , value = "出货实重")
    private Double outActualWeight;

    @ApiModelProperty(name = "outVolumeWeight" , value = "出货体积重")
    private Double outVolumeWeight;

    @ApiModelProperty(name = "outVolume" , value = "出货体积")
    private Double outVolume;

    @ApiModelProperty(name = "outVolumeDivisor" , value = "出货体积除数")
    private Integer outVolumeDivisor;

    @ApiModelProperty(name = "outStatus" , value = "出货状态" )
    private Integer outStatus;

    @ApiModelProperty(name = "internallyRemark" , value = "内部备注")
    private String internallyRemark;

    @ApiModelProperty(name = "cargoType" , value = "包裹类型")
    private String cargoType;

    @ApiModelProperty(name = "declareValue" , value = "总申报价值")
    private Double declareValue;

    @ApiModelProperty(name = "destZipCode" , value = "收件人邮编")
    private String destZipCode;

    @ApiModelProperty(name = "commodityName" , value = "品名")
    private String commodityName;

    @ApiModelProperty(name = "commodityNameEn" , value = "英文品名")
    private String commodityNameEn;

    @ApiModelProperty(name = "receivingTime" , value = "收货时间")
    private Timestamp receivingTime;
}
