package com.apl.lms.waybill.outstorage.pojo.vo;

import com.apl.lms.waybill.outstorage.pojo.bo.WaybillPayableCombBo;
import com.apl.lms.waybill.outstorage.pojo.po.OutstorageBatchPo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hjr start
 * @Classname OutstorageBatchInfoVo
 * @Date 2020/12/23 17:30
 */
@Data
@ApiModel(value = "出货交接单详细信息返回对象", description = "出货交接单详细信息返回对象")
public class OutstorageBatchInfoVo implements Serializable {

    private OutstorageBatchPo outstorageBatchPo;

    private List<WaybillPayableCombBo> waybillPayableCombBoList;

}
