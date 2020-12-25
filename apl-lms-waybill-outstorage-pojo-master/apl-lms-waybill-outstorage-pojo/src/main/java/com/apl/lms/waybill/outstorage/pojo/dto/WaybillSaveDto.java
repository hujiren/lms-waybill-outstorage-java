package com.apl.lms.waybill.outstorage.pojo.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author hjr start
 * @Classname WaybillSaveDto
 * @Date 2020/12/22 10:23
 */
@Data
@ApiModel(value = "出货列表信息-保存对象", description = "出货列表信息-保存对象")
public class WaybillSaveDto implements Serializable {

    @ApiModelProperty(name = "waybillOutstorageList", value = "出货列表信息")
    private List<WaybillOutstorageDto> waybillOutstorageList;

    @ApiModelProperty(name = "outBatchSn", value = "批次号", required = true)
    @NotBlank(message = "批次号不能为空")
    @Length(max = 30, message = "批次号长度最大30")
    private String outBatchSn;

    @ApiModelProperty(name = "poll", value = "票数")
    private Integer poll;

}