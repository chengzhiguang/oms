package com.chengzg.oms.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chengzg.oms.entity.DailyInfo;
import com.chengzg.oms.exception.ServiceException;
import com.chengzg.oms.mapper.DailyInfoMapper;
import com.chengzg.oms.model.req.SearchDailyInfoReq;
import com.chengzg.oms.service.DailyInfoService;
import com.chengzg.oms.utils.Asserts;
import com.chengzg.oms.utils.MD5Util;
import com.chengzg.oms.utils.TimeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Override
    public List<DailyInfo> searchDailyListByWhere(SearchDailyInfoReq req) {
        if (req.getPageNum() != null && req.getPageSize() != null) {
            req.setStart((req.getPageNum() - 1) * req.getPageSize());
            req.setRows(req.getPageSize());
        }

        List<DailyInfo> list = dailyInfoMapper.searchDailyListByWhere(req);

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
    public Integer importDailyDetail(JSONObject dailyObj) {
        return null;
    }

    @Override
    public DailyInfo getDailyInfoByCode(String code) {
        DailyInfo dailyInfo =  dailyInfoMapper.selectByCode(code);
        dailyInfo.setDateStr(TimeUtility.formatTimeStr(dailyInfo.getDate(), TimeUtility.TIME_FORMAT_YYYY_MM_DD));
        return dailyInfo;
    }
}
