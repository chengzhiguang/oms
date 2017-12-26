package com.chengzg.oms.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by chengzg3 on 2017/12/26.
 */
public interface OrderReportService {
    JSONObject generateOrderReport(String orderYear);
}
