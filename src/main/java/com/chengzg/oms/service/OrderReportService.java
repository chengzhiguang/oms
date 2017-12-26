package com.chengzg.oms.service;

import com.alibaba.fastjson.JSONObject;
import com.chengzg.oms.entity.OrderReport;
import com.chengzg.oms.model.req.SearchReportReq;

import java.util.List;

/**
 * Created by chengzg3 on 2017/12/26.
 */
public interface OrderReportService {
    JSONObject generateOrderReport(String orderYear);

    Integer searchCountByWhere(SearchReportReq orderInfo);

    List<OrderReport> searchListByWhere(SearchReportReq orderInfo);
}
