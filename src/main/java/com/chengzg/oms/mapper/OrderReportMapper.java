package com.chengzg.oms.mapper;

import com.chengzg.oms.entity.OrderReport;
import com.chengzg.oms.model.req.SearchReportReq;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderReportMapper {

    int insert(OrderReport record);

    int insertSelective(OrderReport record);

    int updateByPrimaryKeySelective(OrderReport record);

    int updateByPrimaryKey(OrderReport record);

    OrderReport selectByGoodsAndYear(@Param("goodsCode") String goodsCode, @Param("orderYear") String orderyear);

    Integer searchCountByWhere(SearchReportReq orderInfo);

    List<OrderReport> searchListByWhere(SearchReportReq orderInfo);

}