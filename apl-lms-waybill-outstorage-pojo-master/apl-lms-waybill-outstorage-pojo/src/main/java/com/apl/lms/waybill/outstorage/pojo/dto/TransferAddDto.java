package com.apl.lms.waybill.outstorage.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author hjr start
 * @Classname TransferSaveDto
 * @Date 2020/12/24 11:45
 */
@Data
@ApiModel(value = "装袋-交互对象", description = "装袋-交互对象")
public class TransferAddDto implements Serializable {

    @ApiModelProperty(name = "outBatchSn" , value = "批次号" , required = true)
    @NotBlank(message = "批次号不能为空")
    @Length(max = 30, message = "批次号最大长度30")
    private String outBatchSn;

    @ApiModelProperty(name = "partnerId" , value = "服务商" , required = true)
    @NotNull(message = "服务商不能为空")
    @Min(value = 0 , message = "服务商不合法")
    private Long partnerId;

    @ApiModelProperty(name = "bagNo" , value = "袋号")
    private String bagNo;

    @ApiModelProperty(name = "ladingNo" , value = "提单号")
    private String ladingNo;

    @ApiModelProperty(name = "remark" , value = "备注")
    private String remark;
}
