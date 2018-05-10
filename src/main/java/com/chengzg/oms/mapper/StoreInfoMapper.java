package com.chengzg.oms.mapper;

import com.chengzg.oms.entity.StoreInfo;
import com.chengzg.oms.model.req.SearchStooreInfoReq;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StoreInfo record);

    int insertSelective(StoreInfo record);

    StoreInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StoreInfo record);

    int updateByPrimaryKey(StoreInfo record);

    Integer searchCountByWhere(SearchStooreInfoReq orderInfo);

    List<StoreInfo> searchListByWhere(SearchStooreInfoReq orderInfo);

    StoreInfo getStoreInfoByCode(String storeCode);
}