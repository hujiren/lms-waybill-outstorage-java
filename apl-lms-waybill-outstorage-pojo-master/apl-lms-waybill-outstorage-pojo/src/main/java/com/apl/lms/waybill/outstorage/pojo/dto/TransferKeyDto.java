package com.apl.lms.waybill.outstorage.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * <p>
 * 转运信息 分页查询对象
 * </p>
 *
 * @author hjr
 * @since 2020-12-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="转运信息 分页查询对象", description="转运信息 分页查询对象")
public class TransferKeyDto implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(name = "sn", value = "按 提单号/袋号  模糊搜索")
    private String sn;

    @ApiModelProperty(name = "createTime", value = "创建日期")
    private Long createTime;

    @ApiModelProperty(name = "partnerId", value = "服务商")
    private Long partnerId;

    @ApiModelProperty(name = "transferStatus", value = "转运状态")
    private Integer transferStatus;

    public String getSn() {
        if (sn != null && sn.trim().equals(""))
            sn = null;

        return sn;
    }

    public Long getPartnerId(){
        if(null == this.partnerId)
            this.partnerId = 0L;
        return  this.partnerId;
    }

    public Long getCreateTime(){
        if(null == this.createTime){
            LocalDateTime localDateTime = LocalDateTime.now().minusMonths(1L);
            ZoneId zone = ZoneId.systemDefault();
            Instant instant = localDateTime.atZone(zone).toInstant();
            long time = instant.toEpochMilli();
            this.createTime = time;
        }
        return this.createTime;
    }

    public Integer getTransferStatus(){
        if(this.transferStatus < 0)
            this.transferStatus = null;
        return this.transferStatus;
    }
}
