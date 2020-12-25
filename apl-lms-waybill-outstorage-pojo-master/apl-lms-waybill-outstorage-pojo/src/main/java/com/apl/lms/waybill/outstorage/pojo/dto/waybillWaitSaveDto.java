package com.apl.lms.waybill.outstorage.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author hjr start
 * @Classname waybillWaitSaveDto
 * @Date 2020/12/25 16:25
 */
@Data
@ApiModel(value = "出库运单详细信息保存对象", description = "出库运单详细信息保存对象")
public class waybillWaitSaveDto implements Serializable {

    @ApiModelProperty(name = "id", value = "id", required = true)
    @NotNull(message = "id不能为空")
    @Min(value = 0, message = "id不合法")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(name = "partnerId" , value = "服务商id", required = true)
    @NotNull(message = "服务商id不能为空")
    @Min(value = 0, message = "服务商id不合法")
    private Long partnerId;

    @ApiModelProperty(name = "referenceSn" , value = "参考单号 原单号 ：客户单号 运单号")
    private String referenceSn;

    @ApiModelProperty(name = "trackingSn" , value = "转单号, 跟踪号")
    private String trackingSn;

    @ApiModelProperty(name = "ctns" , value = "箱数", required = true)
    @NotNull(message = "箱数不能为空")
    @Min(value = 0, message = "箱数不合法")
    private Integer ctns;

    @ApiModelProperty(name = "declareValue", value = "申报价值", required = true)
    @DecimalMin(value = "0", message = "申报价值不合法")
    @NotNull(message = "申报价值不能为空")
    private BigDecimal declareValue;

    @ApiModelProperty(name = "destZipCode" , value = "邮编", required = true)
    @NotBlank(message = "邮编不能为空")
    @Length(max = 20, message = "邮编最大长度20")
    private String destZipCode;

    @ApiModelProperty(name = "tariffPayer", value = "关税支付 收件人付/发件人付", required = true)
    @NotNull(message = "关税支付不能为空")
    @Min(value = 0, message = "关税支付不合法")
    private Integer tariffPayer;

    @ApiModelProperty(name = "commodityName" , value = "中文品名")
    private String commodityName;

    @ApiModelProperty(name = "commodityNameEn" , value = "申报品名,英文品名", required = true)
    @NotBlank(message = "申报品名不能为空")
    private String commodityNameEn;

    @ApiModelProperty(name = "channelName" , value = "渠道名称, 入仓渠道")
    private String channelName;

    @ApiModelProperty(name = "channelCategory" , value = "渠道类型", required = true)
    @NotBlank(message = "渠道类型不能为空")
    private String channelCategory;

    @ApiModelProperty(name = "outChannelName" , value = "渠道名称, 出仓渠道", required = true)
    @NotBlank(message = "渠道类型不能为空")
    private String outChannelName;

    @ApiModelProperty(name = "inActualWeight" , value = "收货实重")
    private BigDecimal inActualWeight;

    @ApiModelProperty(name = "inVolumeWeight" , value = "收货体积重")
    private BigDecimal inVolumeWeight;

    @ApiModelProperty(name = "inChargeWeight" , value = "收货计费重")
    private BigDecimal inChargeWeight;

    @ApiModelProperty(name = "cargoType" , value = "入包裹类型")
    private String cargoType;

    @ApiModelProperty(name = "outActualWeight" , value = "出货实重", required = true)
    @DecimalMin(value = "0", message = "出货实重不合法")
    @NotNull(message = "出货实重不能为空")
    private BigDecimal outActualWeight;

    @ApiModelProperty(name = "outVolumeWeight" , value = "出货体积重", required = true)
    @DecimalMin(value = "0", message = "出货体积重不合法")
    @NotNull(message = "出货体积重不能为空")
    private BigDecimal outVolumeWeight;

    @ApiModelProperty(name = "outChargeWeight" , value = "出货计费重", required = true)
    @DecimalMin(value = "0", message = "出货计费重不合法")
    @NotNull(message = "出货计费重不能为空")
    private BigDecimal outChargeWeight;

    @ApiModelProperty(name = "internallyRemark" , value = "内部备注")
    private String internallyRemark;
}
