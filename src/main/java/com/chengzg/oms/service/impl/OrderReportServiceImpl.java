package com.chengzg.oms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chengzg.oms.entity.GoodsInfo;
import com.chengzg.oms.entity.OrderReport;
import com.chengzg.oms.exception.ServiceException;
import com.chengzg.oms.mapper.OrderInfoMapper;
import com.chengzg.oms.mapper.OrderReportMapper;
import com.chengzg.oms.model.OrderInfoStatistics;
import com.chengzg.oms.model.req.SearchReportReq;
import com.chengzg.oms.service.GoodsInfoService;
import com.chengzg.oms.service.OrderReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chengzg3 on 2017/12/26.
 */
@Service
public class OrderReportServiceImpl implements OrderReportService {
    private static Logger logger = LoggerFactory.getLogger(OrderReportServiceImpl.class);

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private OrderReportMapper orderReportMapper;

    @Override
    @Transactional
    public JSONObject generateOrderReport(String orderYear) {
        List<OrderInfoStatistics> statisticsList =orderInfoMapper.getStatisticsListByYear(orderYear);

        if (statisticsList == null || statisticsList.size() <= 0) {
            throw new ServiceException(100, "年份：【" +  orderYear + "】没有订单信息");
        }

        List<OrderReport> reportList = new ArrayList<OrderReport>();

        for (OrderInfoStatistics statistics : statisticsList) {
            String goodsSku = statistics.getGoodsCode();
            Integer sumCount = statistics.getSumCount();//总单量
            //Integer sumAmount = statistics.getSumAmount();//总销售量
            Integer jdPrice = statistics.getSumSettmentAmount()/sumCount;//每单客单价
            Integer sumSettmentAmount = statistics.getSumSettmentAmount();//总结算金额
            Integer sumLuggageAmount = statistics.getSumLuggageAmount();//总运费
            GoodsInfo goodsInfo = goodsInfoService.getGoodsInfo(goodsSku);
            if (goodsInfo == null) {
                goodsInfo = GoodsInfo
                        .builder()
                        .goodsWeight(1)
                        .marketingCosts(1)
                        .goodsSku(goodsSku)
                        .goodsName("没有商品")
                        .build();
                //continue;
                //throw new ServiceException(100, "商品编码【" + goodsSku + "】没有找到商品信息");
            }
            Integer goodsWeight = goodsInfo.getGoodsWeight();//商品重量g
            Integer marketingCosts = goodsInfo.getMarketingCosts();//总营销费用


            //总销售额
            Integer sumAmount = sumCount *jdPrice;

            //总斤数
            BigDecimal sumJin = new BigDecimal(sumCount).multiply(new BigDecimal(goodsWeight)).divide(new BigDecimal(500), 2, RoundingMode.HALF_UP);
            if (sumJin.intValue() <= 0) {
                sumJin = new BigDecimal(1);
            }
            //平均每斤运费
            BigDecimal luggageJin = new BigDecimal(sumLuggageAmount).divide(sumJin, 2, RoundingMode.CEILING.HALF_UP);
            //每斤营销费用
            BigDecimal marketingFeeJin = new BigDecimal(marketingCosts).divide(sumJin, 2, RoundingMode.CEILING.HALF_UP);

            OrderReport report = OrderReport
                    .builder()
                    .createTime(new Date())
                    .goodsCode(goodsInfo.getGoodsSku())
                    .goodsName(goodsInfo.getGoodsName())
                    .isDel(0)
                    .lastMofyTime(new Date())
                    .luggageJin(luggageJin.intValue())
                    .marketingCosts(marketingCosts)
                    .marketingFeeJin(marketingFeeJin.intValue())
                    .orderYear(orderYear)
                    .sumAmount(sumAmount)
                    .sumCount(sumCount)
                    .sumJin(sumJin)
                    .sumLuggageAmount(sumLuggageAmount)
                    .sumSettmentAmount(sumSettmentAmount)
                    .unitPrice(jdPrice)
                    .build();
            reportList.add(report);
            logger.info("年份:【{}】  商品【{}】,总销售额：{},总斤数：{},每斤运费：{},每斤营销费：{}", orderYear,goodsInfo.getGoodsName(),sumAmount,sumJin,luggageJin,marketingFeeJin);
        }
        insertReportBath(reportList);
        return null;
    }

    private Integer insertReportBath(List<OrderReport> reportList) {
        for (OrderReport report : reportList) {
            OrderReport check = orderReportMapper.selectByGoodsAndYear(report.getGoodsCode(), report.getOrderYear());
            if (check != null) {
                report.setId(check.getId());
                report.setLastMofyTime(new Date());
                orderReportMapper.updateByPrimaryKeySelective(report);
            } else {
                report.setCreateTime(new Date());
                report.setLastMofyTime(new Date());
                report.setIsDel(0);
                orderReportMapper.insertSelective(report);
            }
        }
        return  1;
    }

    @Override
    public Integer searchCountByWhere(SearchReportReq orderInfo) {
        Integer count = orderReportMapper.searchCountByWhere(orderInfo);
        return count;
    }

    @Override
    public List<OrderReport> searchListByWhere(SearchReportReq orderInfo) {
        if (orderInfo.getPageNum() != null && orderInfo.getPageSize() != null) {
            orderInfo.setStart((orderInfo.getPageNum() - 1) * orderInfo.getPageSize());
            orderInfo.setRows(orderInfo.getPageSize());
        }
        List<OrderReport> list = orderReportMapper.searchListByWhere(orderInfo);
        return list;
    }
}
