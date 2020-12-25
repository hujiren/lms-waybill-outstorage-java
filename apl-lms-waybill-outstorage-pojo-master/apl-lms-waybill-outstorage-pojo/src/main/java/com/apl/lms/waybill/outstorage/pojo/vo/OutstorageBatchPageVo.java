package com.apl.lms.waybill.outstorage.pojo.vo;

import com.apl.lms.waybill.outstorage.pojo.po.OutstorageBatchPo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hjr start
 * @Classname OutstorageBatchPageVo
 * @Date 2020/12/23 14:24
 */
@Data
@ApiModel(value = "出货交接单-分页返回对象", description = "出货交接单-分页返回对象")
public class OutstorageBatchPageVo implements Serializable {

    private Integer resCount;

    private Integer pageSize;

    private Integer pageIndex;

    private List<OutstorageBatchPo> outstorageBatchPoList;
}
