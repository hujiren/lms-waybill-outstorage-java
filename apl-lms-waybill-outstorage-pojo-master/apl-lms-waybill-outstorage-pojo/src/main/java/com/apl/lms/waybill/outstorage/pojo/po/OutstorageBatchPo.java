package com.apl.lms.waybill.outstorage.pojo.po;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.sql.Timestamp;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 出货批次
 * </p>
 *
 * @author hjr
 * @since 2020-12-21
 */
@Data
@TableName("outstorage_batch")
@ApiModel(value="出货交接单-持久化对象", description="出货交接单-持久化对象")
public class OutstorageBatchPo extends Model<OutstorageBatchPo> implements Serializable{

    private static final long serialVersionUID=1L;

    @ApiModelProperty(name = "id", value = "id", required = true)
    @Min(value = 0, message = "id不能小于0")
    @NotNull(message = "id不能为空")
    @TableId(value = "id", type = IdType.INPUT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(name = "outTime", value = "出货时间", required = true)
    private Timestamp outTime;

    @ApiModelProperty(name= "outBatchSn", value = "批次号,总单号", required = true)
    private String outBatchSn;

    @ApiModelProperty(name = "partnerId", value = "服务商id", required = true)
    private Long partnerId;

    @ApiModelProperty(name = "partnerName", value = "服务商", hidden = true)
    private String partnerName;

    @ApiModelProperty(name = "poll", value = "票数")
    private Integer poll;

    @ApiModelProperty(name = "weight", value = "重量")
    private BigDecimal weight;

    @ApiModelProperty(name = "outStatus", value = "出货状态  1加入出货列表 2已出货")
    private Integer outStatus;


}