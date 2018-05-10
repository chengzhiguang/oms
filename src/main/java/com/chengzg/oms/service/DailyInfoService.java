package com.chengzg.oms.service;

import com.alibaba.fastjson.JSONObject;
import com.chengzg.oms.entity.DailyInfo;
import com.chengzg.oms.entity.StoreInfo;
import com.chengzg.oms.model.req.SearchDailyInfoReq;

import java.util.List;

/**
 * Created by chengzg3 on 2018/5/7.
 */
public interface DailyInfoService {

    List<DailyInfo> searchDailyListByWhere(SearchDailyInfoReq req);

    Integer searchDailyCountByWhere(SearchDailyInfoReq req);

    Integer importDailyInfo(StoreInfo storeInfo, JSONObject dailyObj);

    Integer importDailyDetail(String dailyCode, JSONObject dailyObj);


    DailyInfo getDailyInfoByCode(String code);
}
