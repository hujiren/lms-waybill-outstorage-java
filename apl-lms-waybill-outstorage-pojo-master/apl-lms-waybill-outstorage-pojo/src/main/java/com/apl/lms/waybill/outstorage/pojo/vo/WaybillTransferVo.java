package com.apl.lms.waybill.outstorage.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hjr start
 * @Classname WaybillTransferVo
 * @Date 2020/12/25 20:42
 */
@Data
public class WaybillTransferVo implements Serializable {

    @ApiModelProperty(name = "ctns" , value = "箱数")
    private Integer ctns;

    @ApiModelProperty(name = "outActualWeight" , value = "出货实重")
    private Double outActualWeight;

    @ApiModelProperty(name = "outVolume" , value = "出货体积")
    private Double outVolume;

    @ApiModelProperty(name = "outVolumeWeight" , value = "出货体积重")
    private Double outVolumeWeight;

    @ApiModelProperty(name = "outChargeWeight" , value = "出货计费重")
    private Double outChargeWeight;
}
