package com.apl.lms.waybill.outstorage.pojo.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author hjr start
 * @Classname WaybillOutstorageVo
 * @Date 2020/12/21 15:39
 */
@Data
@ApiModel(value = "出货-交互返回对象", description = "出货-交互返回对象")
public class WaybillOutstorageDto implements Serializable {

    @NotNull(message = "id不能为空")
    @Min(value = 0, message = "id不合法")
    @ApiModelProperty(name = "id", value = "id", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(name = "partnerId" , value = "服务商id", required = true)
    @NotNull(message = "服务商id不能为空")
    @Min(value = 0, message = "服务商id不合法")
    private Long partnerId;

    @ApiModelProperty(name = "outChannelName" , value = "出仓渠道")
    private String outChannelName;

    @ApiModelProperty(name = "outChargeWeight" , value = "出货计费重" , required = true)
    @NotNull(message = "出货计费重不能为空")
    @DecimalMin(value = "0" , message = "出货计费重不合法")
    private BigDecimal outChargeWeight;

    @ApiModelProperty(name = "outActualWeight" , value = "出货实重", required = true)
    @DecimalMin(value = "0" , message = "出货实重不合法")
    @NotNull(message = "出货实重不能为空")
    private BigDecimal outActualWeight;

    @ApiModelProperty(name = "outVolumeWeight" , value = "出货体积重", required = true)
    @DecimalMin(value = "0" , message = "出货体积重不合法")
    @NotNull(message = "出货体积重不能为空")
    private BigDecimal outVolumeWeight;

    @ApiModelProperty(name = "outVolume" , value = "出货体积")
    private BigDecimal outVolume;

    @ApiModelProperty(name = "outVolumeDivisor" , value = "出货体积除数" , required = true)
    @NotNull(message = "出货体积除数不能为空")
    @Min(value = 0 , message = "出货体积除数不合法")
    @Max(value = 9000, message = "出货体积除数不合法")
    private Integer outVolumeDivisor;

    @ApiModelProperty(name = "inChargeWeight" , value = "收货计费重" , required = true)
    @NotNull(message = "收货计费重不能为空")
    @DecimalMin(value = "0", message = "收货计费重不合法")
    private BigDecimal inChargeWeight;

    @ApiModelProperty(name = "outStatus" , value = "出货状态")
    private Integer outStatus;
}