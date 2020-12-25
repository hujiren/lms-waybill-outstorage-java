package com.apl.lms.waybill.outstorage.pojo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author hjr start
 * @Classname WaybillPayableCombBo
 * @Date 2020/12/23 18:48
 */
@Data
@ApiModel(value = "运单&应付款信息-组合对象", description = "运单&应付款信息-组合对象")
public class WaybillPayableCombBo implements Serializable {

    @ApiModelProperty(name = "id", value = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

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

    @ApiModelProperty(name = "destZipCode" , value = "收件人邮编")
    private String destZipCode;

    @ApiModelProperty(name = "tariffPayer", value = "关税支付方 收件人付/发件人付")
    private Integer tariffPayer;

    @ApiModelProperty(name = "commodityName" , value = "品名")
    private String commodityName;

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

    @ApiModelProperty(name = "outVolumeWeight" , value = "出货体积重")
    private Double outVolumeWeight;

    @ApiModelProperty(name = "outChargeWeight" , value = "出货计费重")
    private Double outChargeWeight;

    @ApiModelProperty(name = "outVolume" , value = "出货体积")
    private Double outVolume;

    @ApiModelProperty(name = "commodityNameEn" , value = "英文品名")
    private String commodityNameEn;

    @ApiModelProperty(name = "outStatus" , value = "出货状态 0未加入 1已加入出货列表 2已出货")
    private Integer outStatus;

    @ApiModelProperty(name = "internallyRemark" , value = "内部备注")
    private String internallyRemark;




    @ApiModelProperty(name = "payableId", value = "应付款id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long payableId;

    @ApiModelProperty(name = "recordingTime", value = "记录时间")
    private Timestamp recordingTime;

    @ApiModelProperty(name = "relatedId", value = "往来单位id")
    private Long relatedId;

    @ApiModelProperty(name = "chargeCode", value = "费用code")
    private Integer chargeCode;

    @ApiModelProperty(name = "chargeName", value = "费用名称")
    private String chargeName;

    @ApiModelProperty(name = "amount", value = "金额")
    private Double amount;

    @ApiModelProperty(name = "currency", value = "币制")
    private String currency;

    @ApiModelProperty(name = "exchangeRate", value = "汇率")
    private Double exchangeRate;

    @ApiModelProperty(name = "priceName", value = "报价表名称")
    private String priceName;

    @ApiModelProperty(name = "formula", value = "计算公式")
    private String formula;

    @ApiModelProperty(name = "remark", value = "备注")
    private String remark;

    @ApiModelProperty(name = "auditing", value = "审核")
    private Integer auditing;

    @ApiModelProperty(name = "billId", value = "账单Id")
    private Long billId;

    @ApiModelProperty(name = "chargeOff", value = "销账金额")
    private Long chargeOff;
}
