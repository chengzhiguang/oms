package com.chengzg.oms.mapper;

import com.chengzg.oms.entity.OrderInfo;
import com.chengzg.oms.model.req.SearchOrderInfoReq;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);

    Integer searchCountByWhere(SearchOrderInfoReq orderInfo);

    List<OrderInfo> searchListByWhere(SearchOrderInfoReq orderInfo);
}