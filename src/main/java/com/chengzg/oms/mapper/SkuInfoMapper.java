package com.chengzg.oms.mapper;

import com.chengzg.oms.entity.SkuInfo;
import com.chengzg.oms.entity.SpuInfo;
import com.chengzg.oms.model.req.SearchSkuInfoReq;
import com.chengzg.oms.model.req.SearchSpuInfoReq;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkuInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SkuInfo record);

    int insertSelective(SkuInfo record);

    SkuInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SkuInfo record);

    int updateByPrimaryKey(SkuInfo record);


    Integer searchCountByWhere(SearchSkuInfoReq orderInfo);

    List<SkuInfo> searchListByWhere(SearchSkuInfoReq orderInfo);
}