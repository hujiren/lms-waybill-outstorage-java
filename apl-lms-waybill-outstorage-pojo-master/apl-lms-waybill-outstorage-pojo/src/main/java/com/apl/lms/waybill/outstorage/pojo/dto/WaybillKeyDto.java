package com.apl.lms.waybill.outstorage.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.swing.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author hjr start
 * @Classname WaybillKeyDto
 * @Date 2020/12/21 15:06
 */
@Data
public class WaybillKeyDto implements Serializable {

    @ApiModelProperty(name = "partnerId", value = "服务商Id", required = true)
    @NotNull(message = "服务商id不能为空")
    @Min(value = 0, message = "服务商id不合法")
    private Long partnerId;

    @ApiModelProperty(name = "receivingTime", value = "收货时间,起始日期")
    private Long receivingTime;

    public Long getPartnerId(){
        if(null == this.partnerId)
            this.partnerId = 0L;
        return this.partnerId;
    }

    public Long getReceivingTime(){
        if(null == this.receivingTime){
            LocalDateTime localDateTime = LocalDateTime.now().minusMonths(1L);
            ZoneId zone = ZoneId.systemDefault();
            Instant instant = localDateTime.atZone(zone).toInstant();
            long time = instant.toEpochMilli();
            this.receivingTime = time;
        }
        return this.receivingTime;
    }
}
