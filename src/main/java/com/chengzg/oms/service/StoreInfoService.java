package com.chengzg.oms.service;

import com.chengzg.oms.entity.StoreInfo;
import com.chengzg.oms.model.req.SearchStooreInfoReq;

import java.util.List;

/**
 * Created by chengzg3 on 2018/5/10.
 */
public interface StoreInfoService {

    Integer searchCountByWhere(SearchStooreInfoReq orderInfo);

    List<StoreInfo> searchListByWhere(SearchStooreInfoReq orderInfo);

    Integer saveStoreInfo(StoreInfo storeInfo);

    StoreInfo getStoreInfoByCode(String storeCodoe);

    List<StoreInfo> getAllList();

}
