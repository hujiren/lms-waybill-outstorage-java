package com.apl.lms.waybill.outstorage.pojo.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author hjr start
 * @Classname AccountsPayablePo
 * @Date 2020/12/23 18:25
 */
@Data
@ApiModel(value = "应付款-持久化对象", description = "应付款-持久化对象")
public class AccountsPayablePo implements Serializable {

    @ApiModelProperty(name = "id", value = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(name = "waybillId", value = "运单id")
    private Long waybillId;

    @ApiModelProperty(name = "amount", value = "金额")
    private Double amount;

    @ApiModelProperty(name = "currency", value = "币制")
    private String currency;

    @ApiModelProperty(name = "chargeCode", value = "费用code")
    private Integer chargeCode;

    @ApiModelProperty(name = "chargeName", value = "费用名称")
    private String chargeName;

    @ApiModelProperty(name = "exchangeRate", value = "汇率")
    private Double exchangeRate;

    @ApiModelProperty(name = "formula", value = "计算公式")
    private String formula;

    @ApiModelProperty(name = "chargeRemark", value = "备注")
    private String chargeRemark;

    @ApiModelProperty(name = "auditing", value = "审核")
    private Integer auditing;

    @ApiModelProperty(name = "billId", value = "账单Id")
    private Long billId;

    @ApiModelProperty(name = "chargeOff", value = "销账金额")
    private Long chargeOff;

    @ApiModelProperty(name = "recordingTime", value = "记录时间")
    private Timestamp recordingTime;

    @ApiModelProperty(name = "relatedId", value = "往来单位id")
    private Long relatedId;

}
