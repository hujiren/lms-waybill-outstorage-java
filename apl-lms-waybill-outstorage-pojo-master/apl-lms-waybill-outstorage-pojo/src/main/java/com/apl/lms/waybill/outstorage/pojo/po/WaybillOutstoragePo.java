package com.apl.lms.waybill.outstorage.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import java.sql.Timestamp;
import lombok.Data;

/**
 * <p>
 * 运单出货信息 持久化对象
 * </p>
 *
 * @author hjr
 * @since 2020-12-21
 */
@Data
@TableName("waybill_outstorage")
@ApiModel(value="运单出货信息-持久化对象", description="运单出货信息-持久化对象")
public class WaybillOutstoragePo extends Model<WaybillOutstoragePo> implements Serializable{

    @TableId(value = "waybillId", type = IdType.INPUT)
    @ApiModelProperty(name = "waybillId", value = "运单id")
    private Long waybillId;

    @ApiModelProperty(name = "partnerId" , value = "服务商id")
    private Long partnerId;

    @ApiModelProperty(name = "outBatchId" , value = "出货批次id")
    private Long outBatchId;

    @ApiModelProperty(name = "outBatchSn" , value = "出货批次号")
    private String outBatchSn;

    @ApiModelProperty(name = "outTime" , value = "出货时间")
    private Timestamp outTime;

    @ApiModelProperty(name = "outChannelName" , value = "出货渠道名称")
    private String outChannelName;

    @ApiModelProperty(name = "outActualWeight" , value = "出货实重")
    private Double outActualWeight;

    @ApiModelProperty(name = "outVolume" , value = "出货体积")
    private Double outVolume;

    @ApiModelProperty(name = "outVolumeWeight" , value = "出货体积重")
    private Double outVolumeWeight;

    @ApiModelProperty(name = "outChargeWeight" , value = "出货计费重")
    private Double outChargeWeight;

    @ApiModelProperty(name = "outVolumeDivisor" , value = "出货体积除数")
    private Integer outVolumeDivisor;

    @ApiModelProperty(name = "outStatus" , value = "出货状态 0未加入 1已加入出货列表 2已出货")
    private Integer outStatus;

    private static final long serialVersionUID=1L;

}