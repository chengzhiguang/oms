package com.chengzg.oms.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chengzg.oms.entity.GoodsInfo;
import com.chengzg.oms.entity.SkuInfo;
import com.chengzg.oms.entity.SpuInfo;
import com.chengzg.oms.entity.WhiteData;
import com.chengzg.oms.mapper.GoodsInfoMapper;
import com.chengzg.oms.mapper.SkuInfoMapper;
import com.chengzg.oms.mapper.SpuInfoMapper;
import com.chengzg.oms.model.req.SearchGoodsInfoReq;
import com.chengzg.oms.model.req.SearchSkuInfoReq;
import com.chengzg.oms.model.req.SearchSpuInfoReq;
import com.chengzg.oms.service.GoodsInfoService;
import com.chengzg.oms.utils.Asserts;
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

    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Autowired
    private SkuInfoMapper skuInfoMapper;

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
    public Integer saveSpuInfo(SpuInfo spuInfo) {
        String spuCode = spuInfo.getSpuCode();
        SpuInfo check = getSpuInfoByCode(spuCode);
        Integer result = 0;
        if (check == null) {
            spuInfo.setIsDel(0);
            spuInfo.setCreateTime(new Date());
            result = spuInfoMapper.insertSelective(spuInfo);
        } else {
            spuInfo.setIsDel(0);
            spuInfo.setId(check.getId());
            spuInfo.setCreateTime(null);
            result = spuInfoMapper.updateByPrimaryKeySelective(spuInfo);
        }
        return result;
    }

    @Override
    public Integer saveSkuInfo(SkuInfo skuInfo) {
        String skuCode = skuInfo.getSkuCode();

        SkuInfo check = getSkuInfoByCode(skuCode);
        Integer result = 0;
        if (check == null) {
            String spuCode = skuInfo.getSpuCode();
            SpuInfo spuInfo = getSpuInfoByCode(spuCode);
            Asserts.checkNullOrEmpty(spuInfo, "SPU信息不存在");
            skuInfo.setSpuName(spuInfo.getSpuName());
            skuInfo.setIsDel(0);
            skuInfo.setCreateTime(new Date());
            result = skuInfoMapper.insertSelective(skuInfo);
        } else {
            skuInfo.setId(check.getId());
            skuInfo.setCreateTime(null);
            result = skuInfoMapper.updateByPrimaryKeySelective(skuInfo);
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
    public SpuInfo getSpuInfoByCode(String spuCode) {
        SearchSpuInfoReq where = SearchSpuInfoReq
                .builder()
                .spuCode(spuCode)
                .build();
        List<SpuInfo> goodsInfoList = spuInfoMapper.searchListByWhere(where);
        if (goodsInfoList == null || goodsInfoList.size() <= 0) {
            return  null;
        }

        return goodsInfoList.get(0);
    }

    @Override
    public SkuInfo getSkuInfoByCode(String skuCode) {
        SearchSkuInfoReq where = SearchSkuInfoReq.builder().skuCode(skuCode).build();
        List<SkuInfo> skuList = skuInfoMapper.searchListByWhere(where);
        if (skuList == null || skuList.size() <= 0) {
            return null;
        }
        return skuList.get(0);
    }

    @Override
    public GoodsInfo getGoodsInfo(String sku) {
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
                            String sku = obj.getGoodsSku();
                            map2.put(sku, obj);
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
        return map.get(sku);
    }

    @Override
    public Integer searchSpuCountByWhere(SearchSpuInfoReq spuInfoReq) {
        Integer count = spuInfoMapper.searchCountByWhere(spuInfoReq);
        return count;
    }

    @Override
    public List<SpuInfo> searchSpuListByWhere(SearchSpuInfoReq spuInfoReq) {
        if (spuInfoReq.getPageNum() != null && spuInfoReq.getPageSize() != null) {
            spuInfoReq.setStart((spuInfoReq.getPageNum() - 1) * spuInfoReq.getPageSize());
            spuInfoReq.setRows(spuInfoReq.getPageSize());
        }
        List<SpuInfo> list = spuInfoMapper.searchListByWhere(spuInfoReq);
        for (SpuInfo item : list) {

        }
        return list;
    }

    @Override
    public Integer searchSkuCountByWhere(SearchSkuInfoReq spuInfoReq) {
        Integer count = skuInfoMapper.searchCountByWhere(spuInfoReq);
        return count;
    }

    @Override
    public List<SkuInfo> searchSkuListByWhere(SearchSkuInfoReq spuInfoReq) {
        if (spuInfoReq.getPageNum() != null && spuInfoReq.getPageSize() != null) {
            spuInfoReq.setStart((spuInfoReq.getPageNum() - 1) * spuInfoReq.getPageSize());
            spuInfoReq.setRows(spuInfoReq.getPageSize());
        }
        List<SkuInfo> list = skuInfoMapper.searchListByWhere(spuInfoReq);
        for (SkuInfo item : list) {

        }
        return list;
    }
}
