package com.apl.lms.waybill.outstorage.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * <p>
 * 出货批次
 * </p>
 *
 * @author hjr
 * @since 2020-12-21
 */
@Data
@ApiModel(value="出货交接单-关键字查询对象", description="出货交接单-关键字查询对象")
public class OutstorageBatchKeyDto implements Serializable{

private static final long serialVersionUID=1L;

    @ApiModelProperty(name = "startOutTime", value = "起始出货时间", required = true)
    @NotNull(message = "起始出货时间不能为空")
    @Min(value = 0, message = "起始出货时间不合法")
    private Long startOutTime;

    @ApiModelProperty(name = "endOutTime", value = "截止出货时间", required = true)
    @NotNull(message = "截止出货时间不能为空")
    @Min(value = 0, message = "截止出货时间不合法")
    private Long endOutTime;

    @ApiModelProperty(name = "partnerId", value = "服务商id")
    private Long partnerId;

    @ApiModelProperty(name = "outBatchSn", value = "批次号")
    private String outBatchSn;


    public Long getPartnerId(){
        if(null == this.partnerId)
            this.partnerId = 0L;
        return this.partnerId;
    }
}
