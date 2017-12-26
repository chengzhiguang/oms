package com.chengzg.oms.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chengzg.oms.entity.GoodsInfo;
import com.chengzg.oms.entity.WhiteData;
import com.chengzg.oms.mapper.GoodsInfoMapper;
import com.chengzg.oms.model.req.SearchGoodsInfoReq;
import com.chengzg.oms.service.GoodsInfoService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by chengzg3 on 2017/12/25.
 */
@Service
public class GoodsInfoServiceImpl implements GoodsInfoService {
    private static Logger logger = LoggerFactory.getLogger(GoodsInfoServiceImpl.class);

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    private Cache<String,List> goodsInfoCache = CacheBuilder
            .newBuilder()
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .maximumSize(100)
            .softValues()
            .build();

    private static Cache<String, Map<String,GoodsInfo>> singleDataCache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .maximumSize(100)
            .softValues()
            .build();

    private static String allGoodsKey = "allGoodsKey";

    @Override
    public Integer saveGoodsInfo(GoodsInfo goodsInfo) {
        String goodsCode = goodsInfo.getGoodsCode();
        GoodsInfo check = getGoodsInfoByCode(goodsCode);
        Integer result = 0;
        if (check == null) {
            goodsInfo.setIsDel(0);
            goodsInfo.setCreateTime(new Date());
            result = goodsInfoMapper.insertSelective(goodsInfo);
        } else {
            goodsInfo.setIsDel(0);
            goodsInfo.setId(check.getId());
            goodsInfo.setCreateTime(null);
            result = goodsInfoMapper.updateByPrimaryKeySelective(goodsInfo);
        }
        return result;
    }

    @Override
    public Integer mofyGoodsInfoByCode(GoodsInfo goodsInfo) {
        Integer result = goodsInfoMapper.updateByCodeSelective(goodsInfo);
        return result;
    }

    @Override
    public Integer searchCountByWhere(SearchGoodsInfoReq orderInfo) {
        Integer count = goodsInfoMapper.searchCountByWhere(orderInfo);
        return count;
    }

    @Override
    public List<GoodsInfo> searchListByWhere(SearchGoodsInfoReq orderInfo) {
        if (orderInfo.getPageNum() != null && orderInfo.getPageSize() != null) {
            orderInfo.setStart((orderInfo.getPageNum() - 1) * orderInfo.getPageSize());
            orderInfo.setRows(orderInfo.getPageSize());
        }
        List<GoodsInfo> list = goodsInfoMapper.searchListByWhere(orderInfo);
        for (GoodsInfo item : list) {

        }
        return list;
    }

    @Override
    public GoodsInfo getGoodsInfoByCode(String code) {
        SearchGoodsInfoReq where = SearchGoodsInfoReq
                .builder()
                .goodsCode(code)
                .build();
        List<GoodsInfo> goodsInfoList = goodsInfoMapper.searchListByWhere(where);
        if (goodsInfoList == null || goodsInfoList.size() <= 0) {
            return  null;
        }

        return goodsInfoList.get(0);
    }

    @Override
    public GoodsInfo getGoodsInfo(String code) {
        Map<String, GoodsInfo> map = null;
        try {
            map = singleDataCache.get(allGoodsKey, new Callable<Map<String, GoodsInfo>>() {
                @Override
                public Map<String, GoodsInfo> call() throws Exception {
                    SearchGoodsInfoReq where = SearchGoodsInfoReq.builder().build();
                    List<GoodsInfo> list = goodsInfoMapper.searchListByWhere(where);
                    if(list != null) {
                        Map<String, GoodsInfo> map2 = new HashMap();
                        for(int i = 0; i < list.size(); i++) {
                            GoodsInfo obj = list.get(i);
                            String code = obj.getGoodsCode();
                            map2.put(code, obj);
                        }
                        return map2;
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            logger.error("LocalCacheManager getBuByCode error:" + e);
        }
        if (map == null) {
            return null;
        }
        return map.get(code);
    }
}