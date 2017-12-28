package com.chengzg.oms.service;

import com.chengzg.oms.entity.GoodsInfo;
import com.chengzg.oms.model.req.SearchGoodsInfoReq;

import java.util.List;

/**
 * Created by chengzg3 on 2017/12/25.
 */
public interface GoodsInfoService {
    Integer saveGoodsInfo(GoodsInfo goodsInfo);

    Integer mofyGoodsInfoByCode(GoodsInfo goodsInfo);

    Integer searchCountByWhere(SearchGoodsInfoReq orderInfo);

    List<GoodsInfo> searchListByWhere(SearchGoodsInfoReq orderInfo);

    GoodsInfo getGoodsInfoByCode(String goodsSku);

    GoodsInfo getGoodsInfo(String sku);

}
