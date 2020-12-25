package com.apl.lms.waybill.outstorage.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hjr start
 * @Classname TransferPageVo
 * @Date 2020/12/24 16:13
 */
@Data
public class TransferPageVo implements Serializable {

    private List<TransferInfoVo> transferInfoVoList;

    private Integer pageSize;

    private Integer pageIndex;

    private Integer count;
}
