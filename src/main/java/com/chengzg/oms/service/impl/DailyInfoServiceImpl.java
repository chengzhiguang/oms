package com.chengzg.oms.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chengzg.oms.entity.DailyDetail;
import com.chengzg.oms.entity.DailyInfo;
import com.chengzg.oms.entity.SkuInfo;
import com.chengzg.oms.exception.ServiceException;
import com.chengzg.oms.mapper.DailyDetailMapper;
import com.chengzg.oms.mapper.DailyInfoMapper;
import com.chengzg.oms.model.req.SearchDailyInfoReq;
import com.chengzg.oms.service.DailyInfoService;
import com.chengzg.oms.service.GoodsInfoService;
import com.chengzg.oms.utils.Asserts;
import com.chengzg.oms.utils.MD5Util;
import com.chengzg.oms.utils.TimeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by chengzg3 on 2018/5/7.
 */
@Service
public class DailyInfoServiceImpl implements DailyInfoService {
    private static Logger logger = LoggerFactory.getLogger(DailyInfoServiceImpl.class);

    @Autowired
    private DailyInfoMapper dailyInfoMapper;

    @Autowired
    private DailyDetailMapper dailyDetailMapper;

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Override
    public List<DailyInfo> searchDailyListByWhere(SearchDailyInfoReq req) {
        if (req.getPageNum() != null && req.getPageSize() != null) {
            req.setStart((req.getPageNum() - 1) * req.getPageSize());
            req.setRows(req.getPageSize());
        }

        List<DailyInfo> list = dailyInfoMapper.searchDailyListByWhere(req);
        for (DailyInfo dailyInfo : list) {
            dailyInfo.setDateStr(TimeUtility.formatTimeStr(dailyInfo.getDate(), TimeUtility.TIME_FORMAT_YYYY_MM_DD));

        }
        return list;
    }

    @Override
    public Integer searchDailyCountByWhere(SearchDailyInfoReq req) {
        Integer count = dailyInfoMapper.searchDailyCountByWhere(req);
        return count;
    }

    @Override
    public Integer importDailyInfo(JSONObject dailyObj) {
        Asserts.checkNullOrEmpty(dailyObj, "解析excel失败");
        if (!dailyObj.containsKey("sheet0")) {
            throw new ServiceException(100, "解析excel失败");
        }
        JSONArray dailyArray = dailyObj.getJSONArray("sheet0");
        Asserts.checkNullOrEmpty(dailyArray, "解析excel失败");

        for (int i = 0; i < dailyArray.size(); i++) {

            JSONObject obj = dailyArray.getJSONObject(i);

            Date date = TimeUtility.getDateByStr(obj.getString("日期"), TimeUtility.TIME_FORMAT_YYYYMMDD);
            BigDecimal orderAmount = new BigDecimal(obj.getString("下单金额"));
            Integer orderCount = Integer.parseInt(obj.getString("下单单量"));
            Integer pvCount = Integer.parseInt(obj.getString("浏览量"));
            Integer userOrderCount = Integer.parseInt(obj.getString("下单客户数"));
            BigDecimal userPrice  = new BigDecimal(obj.getString("客单价"));
            Integer uvCount = Integer.parseInt(obj.getString("访客数"));
            DailyInfo dailyInfo = DailyInfo
                    .builder()
                    .code(MD5Util.MD5(obj.getString("日期")))
                    .createTime(new Date())
                    .date(date)
                    .isDel(0)
                    .orderAmount(orderAmount)
                    .orderCount(orderCount)
                    .pvCount(pvCount)
                    .status(0)
                    .userOrderCount(userOrderCount)
                    .userPrice(userPrice)
                    .uvCount(uvCount)
                    .build();
            DailyInfo check =  dailyInfoMapper.selectByCode(dailyInfo.getCode());
            if (check == null) {
                dailyInfoMapper.insertSelective(dailyInfo);
            } else {
                dailyInfo.setId(check.getId());
                dailyInfoMapper.updateByPrimaryKeySelective(dailyInfo);
            }
        }

        return 1;
    }

    @Override
    public Integer importDailyDetail(String dailyCode, JSONObject dailyObj) {
        Asserts.checkNullOrEmpty(dailyObj, "解析excel失败");
        if (!dailyObj.containsKey("sheet0")) {
            throw new ServiceException(100, "解析excel失败");
        }
        JSONArray dailyArray = dailyObj.getJSONArray("sheet0");
        Asserts.checkNullOrEmpty(dailyArray, "解析excel失败");

        DailyInfo dailyInfo = dailyInfoMapper.selectByCode(dailyCode);
        Asserts.checkNullOrEmpty(dailyInfo, "没有找到汇总信息,请确认");
        if (dailyInfo.getStatus() != 0) {
            throw new ServiceException(100, "只有状态为未导入的才能导入明细");
        }
        List<DailyDetail> dailyDetailList = new ArrayList<>();

        for (int i = 0; i < dailyArray.size(); i++) {
            JSONObject obj = dailyArray.getJSONObject(i);
            Integer pvCount = Integer.parseInt(obj.getString("浏览量").replace(",",""));
            Integer uvCount = Integer.parseInt(obj.getString("访客数").replace(",",""));
            BigDecimal orderAmount = new BigDecimal(obj.getString("下单金额").replace(",",""));

            if (i == 0) {
                dailyInfo.setPvCount(pvCount);
                dailyInfo.setUvCount(uvCount);
                dailyInfo.setOrderAmount(orderAmount);
                dailyInfo.setStatus(1);
                continue;
            }
            String skuCode = obj.getString("商品ID");
            String skuName = obj.getString("商品名称");

            SkuInfo skuInfo = goodsInfoService.getSkuInfoByCode(skuCode);
            Asserts.checkNullOrEmpty(skuInfo, "商品【"+skuName+"】不存在,请确认");

            String detailCode = MD5Util.MD5(TimeUtility.formatTimeStr(dailyInfo.getDate(), TimeUtility.TIME_FORMAT_YYYYMMDD)+skuCode);

            Integer userOrderCount = Integer.parseInt(obj.getString("下单客户数").replace(",",""));
            Integer orderCount = Integer.parseInt(obj.getString("下单单量").replace(",",""));;
            DailyDetail dailyDetail = new DailyDetail();
            dailyDetail.setOrderAmount(orderAmount);
            dailyDetail.setUvCount(uvCount);
            dailyDetail.setPvCount(pvCount);
            dailyDetail.setCode(detailCode);
            dailyDetail.setDailyCode(dailyInfo.getCode());
            dailyDetail.setDate(dailyInfo.getDate());
            dailyDetail.setOrderAmount(orderAmount);
            dailyDetail.setCreateTime(new Date());
            dailyDetail.setIsDel(0);
            dailyDetail.setOrderCount(orderCount);
            dailyDetail.setUserOrderCount(userOrderCount);
            dailyDetail.setSkuCode(skuCode);
            dailyDetail.setSkuName(skuInfo.getSkuName());
            dailyDetail.setSpuCode(skuInfo.getSpuCode());
            dailyDetail.setSpuName(skuInfo.getSpuName());
            dailyDetailList.add(dailyDetail);

        }

        dailyInfoMapper.updateByPrimaryKeySelective(dailyInfo);

        for (DailyDetail detail : dailyDetailList) {
            DailyDetail check = dailyDetailMapper.selectByCode(detail.getCode());
            if (check == null) {
                detail.setIsDel(0);
                detail.setCreateTime(new Date());
                dailyDetailMapper.insertSelective(detail);
            } else {
                detail.setId(check.getId());
                dailyDetailMapper.updateByPrimaryKeySelective(detail);
            }
        }

        return 1;
    }

    @Override
    public DailyInfo getDailyInfoByCode(String code) {
        DailyInfo dailyInfo =  dailyInfoMapper.selectByCode(code);
        dailyInfo.setDateStr(TimeUtility.formatTimeStr(dailyInfo.getDate(), TimeUtility.TIME_FORMAT_YYYY_MM_DD));
        return dailyInfo;
    }
}
