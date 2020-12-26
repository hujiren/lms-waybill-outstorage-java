package com.apl.lms.waybill.outstorage.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author hjr start
 * @Classname WaybillPo
 * @Date 2020/12/21 11:23
 */
@Data
@TableName("waybill")
@ApiModel(value = "出货-持久化对象", description = "出货-持久化对象")
public class WaybillPo extends Model<WaybillPo> implements Serializable {

    @ApiModelProperty(name = "id", value = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(name = "partnerId" , value = "服务商id")
    private Long partnerId;

    @ApiModelProperty(name = "partnerName" , value = "服务商")
    private String partnerName;

    @ApiModelProperty(name = "referenceSn" , value = "参考单号 原单号 ：客户单号 运单号")
    private String referenceSn;

    @ApiModelProperty(name = "trackingSn" , value = "转单号, 跟踪号")
    private String trackingSn;

    @ApiModelProperty(name = "customerId" , value = "客户id")
    private Long customerId;

    @ApiModelProperty(name = "customerName" , value = "客户")
    private String customerName;

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

    @ApiModelProperty(name = "commodityNameEn" , value = "英文品名")
    private String commodityNameEn;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型")
    private String channelCategory;

    @ApiModelProperty(name = "channelName" , value = "渠道名称, 入仓渠道")
    private String channelName;

    @ApiModelProperty(name = "inActualWeight" , value = "收货实重")
    private Double inActualWeight;

    @ApiModelProperty(name = "inVolumeWeight" , value = "收货体积重")
    private Double inVolumeWeight;

    @ApiModelProperty(name = "inChargeWeight" , value = "收货计费重")
    private Double inChargeWeight;

    @ApiModelProperty(name = "cargoType" , value = "入包裹类型")
    private String cargoType;

    @ApiModelProperty(name = "outChargeWeight" , value = "出货计费重")
    private Double outChargeWeight;

    @ApiModelProperty(name = "internallyRemark" , value = "内部备注")
    private String internallyRemark;


    private static final long serialVersionUID=1L;
}
