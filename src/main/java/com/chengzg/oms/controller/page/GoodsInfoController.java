package com.chengzg.oms.controller.page;

import com.alibaba.fastjson.JSONArray;
import com.chengzg.oms.controller.support.BaseController;
import com.chengzg.oms.controller.support.PageResponse;
import com.chengzg.oms.controller.support.ReturnResult;
import com.chengzg.oms.entity.GoodsInfo;
import com.chengzg.oms.entity.OrderInfo;
import com.chengzg.oms.entity.SkuInfo;
import com.chengzg.oms.entity.SpuInfo;
import com.chengzg.oms.exception.ServiceException;
import com.chengzg.oms.model.req.SearchGoodsInfoReq;
import com.chengzg.oms.model.req.SearchOrderInfoReq;
import com.chengzg.oms.model.req.SearchSkuInfoReq;
import com.chengzg.oms.model.req.SearchSpuInfoReq;
import com.chengzg.oms.service.GoodsInfoService;
import com.chengzg.oms.service.OrderInfoService;
import com.chengzg.oms.utils.Asserts;
import com.chengzg.oms.utils.ExcelUtil;
import com.chengzg.oms.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by czg on 2016-08-26.
 */
@Controller
@RequestMapping(value="page/goods")
public class GoodsInfoController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(GoodsInfoController.class);

    @Autowired
    private GoodsInfoService goodsInfoService;

    @RequestMapping(value="toSpuManagerPage",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ModelAndView toGoodsManagerPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            return new ModelAndView("goods/SpuManagerPage");
        } catch (ServiceException e) {
            logger.error("toGoodsManagerPage CommonException异常", e);
            int code =((ServiceException) e).getCode();
            return new ModelAndView("404");
        } catch (Exception e) {
            logger.error("toGoodsManagerPage Exception异常", e);
            return new ModelAndView("404");
        }
    }

    @RequestMapping(value="toSkuManagerPage",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ModelAndView toSkuManagerPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            return new ModelAndView("goods/SkuManagerPage");
        } catch (ServiceException e) {
            logger.error("toGoodsManagerPage CommonException异常", e);
            int code =((ServiceException) e).getCode();
            return new ModelAndView("404");
        } catch (Exception e) {
            logger.error("toGoodsManagerPage Exception异常", e);
            return new ModelAndView("404");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/searchSkuList")
    public PageResponse searchSkuList(HttpServletRequest request, HttpServletResponse response) {
        Integer pageNum = HttpUtil.getIntegerParameter(request, "page", 1);
        Integer pageSize = HttpUtil.getIntegerParameter(request, "rows", 10);

        String skuName = HttpUtil.getParameter(request, "skuName", null);

        SearchSkuInfoReq where = SearchSkuInfoReq.builder()
                .skuName(skuName)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();

        Integer total = goodsInfoService.searchSkuCountByWhere(where);
        List<SkuInfo> list = goodsInfoService.searchSkuListByWhere(where);

        return this.successPageReturn(pageNum + 1, total, list);

    }


    @ResponseBody
    @RequestMapping(value = "/searchSpuList")
    public PageResponse searchSpuList(HttpServletRequest request, HttpServletResponse response) {
        Integer pageNum = HttpUtil.getIntegerParameter(request, "page", 1);
        Integer pageSize = HttpUtil.getIntegerParameter(request, "rows", 10);

        String spuName = HttpUtil.getParameter(request, "spuName", null);

        SearchSpuInfoReq where = SearchSpuInfoReq.builder()
                .spuName(spuName)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();

        Integer total = goodsInfoService.searchSpuCountByWhere(where);
        List<SpuInfo> list = goodsInfoService.searchSpuListByWhere(where);

        return this.successPageReturn(pageNum + 1, total, list);
    }


    @RequestMapping(value="toAddGoodsInfoPage",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ModelAndView toAddGoodsInfoPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            return new ModelAndView("goods/AddGoodsInfoPage");
        } catch (ServiceException e) {
            logger.error("toAddGoodsInfoPage CommonException异常", e);
            int code =((ServiceException) e).getCode();
            return new ModelAndView("404");
        } catch (Exception e) {
            logger.error("toAddGoodsInfoPage Exception异常", e);
            return new ModelAndView("404");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveGoodsInfo")
    public ReturnResult saveGoodsInfo(HttpServletRequest request, HttpServletResponse response) {

        logger.info(" saveGoodsInfo start  !");
        String goodsCode = HttpUtil.getParameter(request, "goodsCode", null);
        String goodsSku = HttpUtil.getParameter(request, "goodsSku", null);
        String goodsName = HttpUtil.getParameter(request, "goodsName", null);
        Integer jdPrice = HttpUtil.getIntegerParameter(request, "jdPrice", null);
        Integer marketPrice = HttpUtil.getIntegerParameter(request, "marketPrice", null);
        Integer goodsWeight = HttpUtil.getIntegerParameter(request, "goodsWeight", null);
        Integer discount = HttpUtil.getIntegerParameter(request, "discount", null);
        Integer marketingCosts = HttpUtil.getIntegerParameter(request, "marketingCosts", null);

        GoodsInfo goodsInfo = GoodsInfo
                .builder()
                .goodsCode(goodsCode)
                .goodsSku(goodsSku)
                .goodsName(goodsName)
                .jdPrice(jdPrice)
                .marketPrice(marketPrice)
                .goodsWeight(goodsWeight)
                .discount(discount)
                .marketingCosts(marketingCosts)
                .build();

        goodsInfoService.saveGoodsInfo(goodsInfo);
        return this.successReturn(null);
    }

    @RequestMapping(value="toMofyGoodsInfoPage",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ModelAndView toMofyGoodsInfoPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            String goodsSku = HttpUtil.getParameter(request, "goodsSku", null);
            Asserts.checkNullOrEmpty(goodsSku, "商品编号不能为空");

            GoodsInfo goodsInfo = goodsInfoService.getGoodsInfoByCode(goodsSku);
            Asserts.checkNullOrEmpty(goodsInfo, "商品为空");
            request.setAttribute("goodsInfo", goodsInfo);

            return new ModelAndView("goods/MofyGoodsInfoPage");
        } catch (ServiceException e) {
            logger.error("toMofyGoodsInfoPage CommonException异常", e);
            int code =((ServiceException) e).getCode();
            return new ModelAndView("404");
        } catch (Exception e) {
            logger.error("toMofyGoodsInfoPage Exception异常", e);
            return new ModelAndView("404");
        }
    }
}
