package com.chengzg.oms.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chengzg.oms.entity.OrderInfo;
import com.chengzg.oms.mapper.OrderInfoMapper;
import com.chengzg.oms.model.req.SearchOrderInfoReq;
import com.chengzg.oms.repository.OrderInfoResp;
import com.chengzg.oms.service.OrderInfoService;
import com.chengzg.oms.utils.StrUtils;
import com.chengzg.oms.utils.TimeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by chengzg3 on 2017/12/18.
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    private static Logger logger = LoggerFactory.getLogger(OrderInfoServiceImpl.class);

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderInfoResp orderInfoResp;

    @Override
    public Integer importOrderInfo(JSONArray excelList) {
        if (excelList == null || excelList.size() <= 0) {
            return  -1;
        }
        for (int i = 0; i < excelList.size(); i++) {
            JSONObject orderObj = excelList.getJSONObject(i);
            OrderInfo orderInfo = getOrderInfoByExcel(orderObj);
            if (orderInfo == null) {
                logger.info("importOrderInfo 解析数据异常 orderObj：{}", orderObj.toJSONString());
                continue;
            }
            if (orderInfo.getLuggageAmount() == null || orderInfo.getLuggageAmount() < 0) {
                logger.info("importOrderInfo 运费金额异常");
                continue;
            }
            orderInfoResp.insertSelective(orderInfo);
        }
        return 1;
    }

    @Override
    public Integer searchCountByWhere(SearchOrderInfoReq orderInfo) {
        Integer count = orderInfoMapper.searchCountByWhere(orderInfo);
        return count;
    }

    @Override
    public List<OrderInfo> searchListByWhere(SearchOrderInfoReq orderInfo) {
        if (orderInfo.getPageNum() != null && orderInfo.getPageSize() != null) {
            orderInfo.setStart((orderInfo.getPageNum() - 1) * orderInfo.getPageSize());
            orderInfo.setRows(orderInfo.getPageSize());
        }
        List<OrderInfo> list = orderInfoMapper.searchListByWhere(orderInfo);
        return list;
    }

    private OrderInfo getOrderInfoByExcel(JSONObject orderObj) {
        String orderTimeStr = orderObj.getString("下单时间");
        boolean check = StrUtils.isNullOrBlank(orderTimeStr);
        Date orderDate = check ? null : TimeUtility.getDateByStr(orderTimeStr.substring(0,orderTimeStr.indexOf(" ")), TimeUtility.TIME_FORMAT_YYYY_MM_DD);

        if (orderDate == null) {
            logger.info("下单时间：{}", orderTimeStr);
            return null;
        }

        int hashCode = orderTimeStr.hashCode();
        String orderYear = "2017";
        if (hashCode % 5 == 0) {
            orderYear = "2018";
        } else if (hashCode % 5 == 1) {
            orderYear = "2019";
        } else if (hashCode % 5 == 2) {
            orderYear = "2020";
        } else if (hashCode % 5 == 3) {
            orderYear = "2016";
        } else if (hashCode % 5 == 4) {
            orderYear = "2015";
        }
        OrderInfo orderInfo = OrderInfo
                .builder()
                .articleNum(orderObj.getString("货号"))
                .billType(orderObj.getString("订单类型"))
                .accountAmount(StrUtils.isNullOrBlank(orderObj.getString("余额支付")) ? -1 : new BigDecimal(orderObj.getString("余额支付")).multiply(new BigDecimal(100)).intValue())
                .chargesAmount(StrUtils.isNullOrBlank(orderObj.getString("服务费")) ? -1 : new BigDecimal(orderObj.getString("余额支付")).multiply(new BigDecimal(100)).intValue())
                .comfireTime(StrUtils.isNullOrBlank(orderObj.getString("付款确认时间")) ? null : TimeUtility.getDateByStr(orderObj.getString("付款确认时间").substring(0,orderObj.getString("付款确认时间").indexOf(" ")), TimeUtility.TIME_FORMAT_YYYY_MM_DD))
                .createTime(new Date())
                .deliveryService(orderObj.getString("送装服务"))
                .finishTime(StrUtils.isNullOrBlank(orderObj.getString("订单完成时间")) ? null : TimeUtility.getDateByStr(orderObj.getString("订单完成时间").substring(0,orderObj.getString("订单完成时间").indexOf(" ")), TimeUtility.TIME_FORMAT_YYYY_MM_DD))
                .goodsCode(orderObj.getString("商品ID"))
                .googsName(orderObj.getString("商品名称"))
                .invoiceContent(orderObj.getString("发票内容"))
                .invoiceTitle(orderObj.getString("发票抬头"))
                .invoiceType(orderObj.getString("发票类型"))
                .isDel(0)
                .jdPrice(StrUtils.isNullOrBlank(orderObj.getString("京东价")) ? -1 : new BigDecimal(orderObj.getString("京东价")).multiply(new BigDecimal(100)).intValue())
                .lastModifyTime(new Date())
                .luggageAmount(StrUtils.isNullOrBlank(orderObj.getString("运费金额")) ? -1 : new BigDecimal(orderObj.getString("运费金额")).multiply(new BigDecimal(100)).intValue())
                .orderAmount(StrUtils.isNullOrBlank(orderObj.getString("订单金额")) ? -1 : new BigDecimal(orderObj.getString("订单金额")).multiply(new BigDecimal(100)).intValue())
                .orderChannel(orderObj.getString("订单渠道"))
                .orderCode(orderObj.getString("订单号"))
                .orderRemark(orderObj.getString("订单备注"))
                .orderSource(orderObj.getString("订单来源"))
                .orderStatus(orderObj.getString("订单状态"))
                .orderTime(orderDate)
                .orderYear(orderYear)
                .ordinaryInvoiceCode(null)
                .payType(orderObj.getString("订单类型"))
                .remark("")
                .settmentMent(StrUtils.isNullOrBlank(orderObj.getString("结算金额")) ? -1 : new BigDecimal(orderObj.getString("结算金额")).multiply(new BigDecimal(100)).intValue())
                .shopRemark(orderObj.getString("商家备注"))
                .shopRemarkLevel(StrUtils.isNullOrBlank(orderObj.getString("商家备注等级（等级1-5为由高到低）")) ? null : Integer.valueOf(orderObj.getString("商家备注等级（等级1-5为由高到低）")))
                .shopSkuid(null)
                .shouldAmount(StrUtils.isNullOrBlank(orderObj.getString("应付金额")) ? -1 : new BigDecimal(orderObj.getString("应付金额")).multiply(new BigDecimal(100)).intValue())
                .storeCode(orderObj.getString("仓库id"))
                .storeName(orderObj.getString("仓库名称"))
                .totalCount(StrUtils.isNullOrBlank(orderObj.getString("订购数量")) ? -1 : Integer.valueOf(orderObj.getString("订购数量")))
                .userAddress(orderObj.getString("客户地址"))
                .userCode(orderObj.getString("下单帐号"))
                .userName(orderObj.getString("客户姓名"))
                .userPhone(orderObj.getString("联系电话"))
                .vatInvoice(orderObj.getString("增值税发票"))
                .build();
        return orderInfo;
    }
}
