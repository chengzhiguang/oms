package com.chengzg.oms.service;

import com.chengzg.oms.entity.GoodsInfo;
import com.chengzg.oms.entity.SkuInfo;
import com.chengzg.oms.entity.SpuInfo;
import com.chengzg.oms.model.req.SearchGoodsInfoReq;
import com.chengzg.oms.model.req.SearchSkuInfoReq;
import com.chengzg.oms.model.req.SearchSpuInfoReq;

import java.util.List;

/**
 * Created by chengzg3 on 2017/12/25.
 */
public interface GoodsInfoService {
    Integer saveSpuInfo(SpuInfo spuInfo);

    Integer saveSkuInfo(SkuInfo skuInfo);

    Integer mofyGoodsInfoByCode(GoodsInfo goodsInfo);

    Integer searchCountByWhere(SearchGoodsInfoReq orderInfo);

    List<GoodsInfo> searchListByWhere(SearchGoodsInfoReq orderInfo);

    SpuInfo getSpuInfoByCode(String spuCode);

    SkuInfo getSkuInfoByCode(String skuCode);


    GoodsInfo getGoodsInfo(String sku);


    Integer searchSpuCountByWhere(SearchSpuInfoReq spuInfoReq);

    List<SpuInfo> searchSpuListByWhere(SearchSpuInfoReq spuInfoReq);

    Integer searchSkuCountByWhere(SearchSkuInfoReq spuInfoReq);

    List<SkuInfo> searchSkuListByWhere(SearchSkuInfoReq spuInfoReq);

}
