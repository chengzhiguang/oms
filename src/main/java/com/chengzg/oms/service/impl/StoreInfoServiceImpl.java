package com.chengzg.oms.service.impl;

import com.chengzg.oms.entity.DailyInfo;
import com.chengzg.oms.entity.StoreInfo;
import com.chengzg.oms.enums.DailyInfoStatus;
import com.chengzg.oms.mapper.StoreInfoMapper;
import com.chengzg.oms.model.req.SearchStooreInfoReq;
import com.chengzg.oms.service.StoreInfoService;
import com.chengzg.oms.utils.TimeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by chengzg3 on 2018/5/10.
 */
@Service
public class StoreInfoServiceImpl implements StoreInfoService {
    private static Logger logger = LoggerFactory.getLogger(StoreInfoServiceImpl.class);

    @Autowired
    private StoreInfoMapper storeInfoMapper;

    @Override
    public Integer searchCountByWhere(SearchStooreInfoReq req) {

        Integer count = storeInfoMapper.searchCountByWhere(req);
        return count;
    }

    @Override
    public List<StoreInfo> searchListByWhere(SearchStooreInfoReq req) {
        if (req.getPageNum() != null && req.getPageSize() != null) {
            req.setStart((req.getPageNum() - 1) * req.getPageSize());
            req.setRows(req.getPageSize());
        }

        List<StoreInfo> list = storeInfoMapper.searchListByWhere(req);
        for (StoreInfo dailyInfo : list) {
        }
        return list;

    }

    @Override
    public Integer saveStoreInfo(StoreInfo storeInfo) {
        String storeCode = storeInfo.getStoreCode();

        StoreInfo check = storeInfoMapper.getStoreInfoByCode(storeCode);
        if (check == null) {
            storeInfo.setIsDel(0);
            storeInfo.setCreateTime(new Date());
            storeInfoMapper.insertSelective(storeInfo);
        } else {
            storeInfo.setId(check.getId());
            storeInfoMapper.updateByPrimaryKeySelective(storeInfo);
        }
        return 1;
    }

    @Override
    public StoreInfo getStoreInfoByCode(String storeCode) {
        StoreInfo storeInfo = storeInfoMapper.getStoreInfoByCode(storeCode);

        return storeInfo;
    }

    @Override
    public List<StoreInfo> getAllList() {
        SearchStooreInfoReq req = new SearchStooreInfoReq();
        List<StoreInfo> list = storeInfoMapper.searchListByWhere(req);
        for (StoreInfo dailyInfo : list) {
        }
        return list;
    }
}
