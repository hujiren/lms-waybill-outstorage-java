package com.apl.lms.waybill.outstorage.service.impl;

import com.apl.lms.waybill.outstorage.dao.WaybillOutstorageDao;
import com.apl.lms.waybill.outstorage.service.WaybillOutstorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apl.lms.waybill.outstorage.pojo.po.WaybillOutstoragePo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 运单出货信息 service实现类
 * </p>
 *
 * @author hjr
 * @since 2020-12-21
 */
@Service
@Slf4j
public class WaybillOutstorageServiceImpl implements WaybillOutstorageService {

    //状态code枚举
    /*enum WaybillOutstorageServiceCode {

            ;

            private String code;
            private String msg;

            WaybillOutstorageServiceCode(String code, String msg) {
                 this.code = code;
                 this.msg = msg;
            }
        }*/

    @Autowired
    WaybillOutstorageDao waybillOutstorageDao;

    /**
     * 批量保存
     * @param waybillOutstoragePoList
     * @throws Exception
     */
    @Override
    public void saveBatchWaybillOutstorage(List<WaybillOutstoragePo> waybillOutstoragePoList, List<Long> waybillIds) throws Exception {

        StringBuffer sbIds = new StringBuffer();
        for (Long waybillId : waybillIds) {
            if(sbIds.length() > 0)
                sbIds.append(",");
            sbIds.append(waybillId);
        }

        List<Long> sameIds = waybillOutstorageDao.querySameIds(sbIds.toString());

        if(sameIds.size() < 1){
            waybillOutstorageDao.addBatchWayBillOutstorage(waybillOutstoragePoList);
            return;
        }

        List<Long> diffIds = new ArrayList<>();

        Map<Long, Integer> minMap = new HashMap<>();
        for (Long sameId : sameIds) {
            minMap.put(sameId, 1);
        }

        for (Long waybillId : waybillIds) {
            if(null == minMap.get(waybillId))
                diffIds.add(waybillId);
        }

        List<WaybillOutstoragePo> waybillOutstorageListForAdd = new ArrayList<>();
        List<WaybillOutstoragePo> waybillOutstorageListForUpd = new ArrayList<>();

        for (Long sameId : sameIds) {
            for (WaybillOutstoragePo waybillOutstoragePo : waybillOutstoragePoList) {
                if(waybillOutstoragePo.getWaybillId().equals(sameId))
                    waybillOutstorageListForUpd.add(waybillOutstoragePo);
            }
        }
        for (Long diffId : diffIds) {
            for (WaybillOutstoragePo waybillOutstoragePo : waybillOutstoragePoList) {
                if(waybillOutstoragePo.getWaybillId().equals(diffId))
                    waybillOutstorageListForAdd.add(waybillOutstoragePo);
            }
        }
        if(waybillOutstorageListForUpd.size() > 0)
            waybillOutstorageDao.updBatchWaybillOutstorage(waybillOutstorageListForUpd);
        if(waybillOutstorageListForAdd.size() > 0)
            waybillOutstorageDao.addBatchWayBillOutstorage(waybillOutstorageListForAdd);
    }

    /**
     * 批量更新状态
     * @param waybillIds
     * @return
     */
    @Override
    public Integer updateOutStatusByIds(List<Long> waybillIds, Timestamp newOutTime, Integer outStatus) {
        Integer resultNum = waybillOutstorageDao.updateOutStatusByIds(waybillIds, newOutTime, outStatus);
        return resultNum;
    }

    /**
     * 根据id更新
     * @param waybillOutstoragePo
     * @return
     */
    @Override
    public Integer updById(WaybillOutstoragePo waybillOutstoragePo) throws Exception {
        return waybillOutstorageDao.updById(waybillOutstoragePo);
    }
}