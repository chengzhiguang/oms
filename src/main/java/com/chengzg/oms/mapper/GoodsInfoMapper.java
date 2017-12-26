package com.chengzg.oms.mapper;

import com.chengzg.oms.entity.GoodsInfo;
import com.chengzg.oms.model.req.SearchGoodsInfoReq;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsInfo record);

    int insertSelective(GoodsInfo record);

    GoodsInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsInfo record);

    int updateByPrimaryKey(GoodsInfo record);

    int updateByCodeSelective(GoodsInfo record);

    Integer searchCountByWhere(SearchGoodsInfoReq orderInfo);

    List<GoodsInfo> searchListByWhere(SearchGoodsInfoReq orderInfo);
}