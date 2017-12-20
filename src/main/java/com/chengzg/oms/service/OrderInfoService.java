package com.chengzg.oms.service;

import com.alibaba.fastjson.JSONArray;
import com.chengzg.oms.entity.OrderInfo;
import com.chengzg.oms.model.req.SearchOrderInfoReq;

import java.util.List;

/**
 * Created by chengzg3 on 2017/12/18.
 */
public interface OrderInfoService {

    Integer importOrderInfo(JSONArray excelList);

    Integer searchCountByWhere(SearchOrderInfoReq orderInfo);

    List<OrderInfo> searchListByWhere(SearchOrderInfoReq orderInfo);
}
