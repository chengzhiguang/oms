package com.chengzg.oms.mapper;

import com.chengzg.oms.entity.SpuInfo;
import com.chengzg.oms.model.req.SearchSpuInfoReq;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpuInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SpuInfo record);

    int insertSelective(SpuInfo record);

    SpuInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SpuInfo record);

    int updateByPrimaryKey(SpuInfo record);

    Integer searchCountByWhere(SearchSpuInfoReq orderInfo);

    List<SpuInfo> searchListByWhere(SearchSpuInfoReq orderInfo);

}