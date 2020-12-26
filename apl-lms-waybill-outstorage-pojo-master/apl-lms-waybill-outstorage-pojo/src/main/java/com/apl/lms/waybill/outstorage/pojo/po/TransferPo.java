package com.apl.lms.waybill.outstorage.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import com.baomidou.mybatisplus.annotation.TableName;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import lombok.Data;

/**
 * <p>
 * 转运信息 持久化对象
 * </p>
 *
 * @author hjr
 * @since 2020-12-24
 */
@Data
@TableName("transfer")
@ApiModel(value="转运信息 持久化对象", description="转运信息 持久化对象")
public class TransferPo extends Model<TransferPo> {

    @ApiModelProperty(name = "id" , value = "转运信息id", required = true)
    @TableId(value = "id", type = IdType.INPUT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(name = "outBatchId" , value = "出货批次id", required = true)
    private Long outBatchId;

    @ApiModelProperty(name = "createTime" , value = "创建时间" , required = true)
    private Timestamp createTime;

    @ApiModelProperty(name = "partnerId" , value = "服务商" , required = true)
    @NotNull(message = "服务商不能为空")
    @Min(value = 0 , message = "服务商不合法")
    private Long partnerId;

    @ApiModelProperty(name = "bagNo" , value = "袋号")
    private String bagNo;

    @ApiModelProperty(name = "carrier" , value = "运输商")
    private String carrier;

    @ApiModelProperty(name = "ladingNo" , value = "提单号")
    private String ladingNo;

    @ApiModelProperty(name = "cutInformationTime" , value = "截补料时间")
    private Timestamp cutInformationTime;

    @ApiModelProperty(name = "cutOffTime" , value = "截单时间")
    private Timestamp cutOffTime;

    @ApiModelProperty(name = "estimateStartTime" , value = "预计起航时间")
    private Timestamp estimateStartTime;

    @ApiModelProperty(name = "actualStartTime" , value = "实计起航时间")
    private Timestamp actualStartTime;

    @ApiModelProperty(name = "estimateArriveTime" , value = "预行到达时间")
    private Timestamp estimateArriveTime;

    @ApiModelProperty(name = "actualArriveTime" , value = "实际到达时间")
    private Timestamp actualArriveTime;

    @ApiModelProperty(name = "ctns" , value = "总箱数")
    private Integer ctns;

    @ApiModelProperty(name = "actualWeight" , value = "实重")
    private Double actualWeight;

    @ApiModelProperty(name = "volume" , value = "体积")
    private Double volume;

    @ApiModelProperty(name = "volumeWeight" , value = "体积重")
    private Double volumeWeight;

    @ApiModelProperty(name = "chargeWeight" , value = "计费重")
    private Double chargeWeight;

    @ApiModelProperty(name = "transferStatus" , value = "转运状态")
    private Integer transferStatus;

    @ApiModelProperty(name = "remark" , value = "备注")
    private String remark;


    private static final long serialVersionUID=1L;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
