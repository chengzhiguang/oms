package com.chengzg.oms.service.impl;

import com.chengzg.oms.entity.WhiteData;
import com.chengzg.oms.mapper.WhiteMapper;
import com.chengzg.oms.service.WhiteListService;
import com.chengzg.oms.utils.ConfigLoader;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author dongh38@ziroom
 * @Date 2016/11/10
 * @Time 16:44
 * @Description
 * @Since 1.0.0
 */
@Service
public class WhiteListServiceImpl implements WhiteListService {

    @Autowired
    private WhiteMapper whiteMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(WhiteListServiceImpl.class);

    private static final String WHITE_LIST = "whiteList";

    private Cache<String,List<WhiteData>> whiteDataCache = CacheBuilder
            .newBuilder()
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .maximumSize(100)
            .softValues()
            .build();

    @Override
    public boolean isEnabled() {
        Boolean isEnable = ConfigLoader.getBoolean("whitelist.enable", true);
        return isEnable;
    }

    @Override
    public List<WhiteData> whiteList() {
        List<WhiteData> whiteList = whiteDataCache.getIfPresent(WHITE_LIST);
        if (whiteList == null) {
            whiteList = whiteMapper.whiteList();
            LOGGER.info("开始查找白名单中所有允许访问的ip，允许访问的IP地址为:{}", whiteList);
            whiteDataCache.put(WHITE_LIST, whiteList);
        }
         return whiteList;
    }

    @Override
    public void clertWhiteList() {
        whiteDataCache.invalidate(WHITE_LIST);
    }

    @Override
    public void addWhiteList(String serviceIp) {
        whiteMapper.addWhiteList(WhiteData.builder().whiteServer(serviceIp).build());
    }

    @Override
    public void delWhiteList(String serviceIp) {
        whiteMapper.delWhiteList(serviceIp);
    }
}
