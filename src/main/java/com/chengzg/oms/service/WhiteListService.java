package com.chengzg.oms.service;

import com.chengzg.oms.entity.WhiteData;

import java.util.List;

/**
 * @author dongh38@ziroom
 * @Date 2016/11/10
 * @Time 18:42
 * @Description
 * @Since 1.0.0
 */
public interface WhiteListService {
    boolean isEnabled();
    List<WhiteData> whiteList();

    void clertWhiteList();
    void addWhiteList(String serviceIp);
    void delWhiteList(String serviceIp);
}
