package com.chengzg.oms.mapper;

import com.chengzg.oms.entity.GoodsCosts;

public interface GoodsCostsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsCosts record);

    int insertSelective(GoodsCosts record);

    GoodsCosts selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsCosts record);

    int updateByPrimaryKey(GoodsCosts record);
}