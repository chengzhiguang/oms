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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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


    @RequestMapping(value="toAddSpuInfoPage",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ModelAndView toAddSpuInfoPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            String spuCode = UUID.randomUUID().toString().replace("-", "");
            request.setAttribute("spuCode", spuCode);
            return new ModelAndView("goods/AddSpuInfoPage");
        } catch (ServiceException e) {
            logger.error("toAddSpuInfoPage CommonException异常", e);
            int code =((ServiceException) e).getCode();
            return new ModelAndView("404");
        } catch (Exception e) {
            logger.error("toAddSpuInfoPage Exception异常", e);
            return new ModelAndView("404");
        }
    }

    @RequestMapping(value="toAddSkuInfoPage",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ModelAndView toAddSkuInfoPage(HttpServletRequest request, HttpServletResponse response) {
        try {


            SearchSpuInfoReq where = SearchSpuInfoReq.builder()
                    .build();

            List<SpuInfo> spuList = goodsInfoService.searchSpuListByWhere(where);

            request.setAttribute("spuList", spuList);
            return new ModelAndView("goods/AddSkuInfoPage");
        } catch (ServiceException e) {
            logger.error("toAddSkuInfoPage CommonException异常", e);
            int code =((ServiceException) e).getCode();
            return new ModelAndView("404");
        } catch (Exception e) {
            logger.error("toAddSkuInfoPage Exception异常", e);
            return new ModelAndView("404");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveSpuInfo")
    public ReturnResult saveSpuInfo(HttpServletRequest request, HttpServletResponse response) {

        logger.info(" saveGoodsInfo start  !");
        String spuCode = HttpUtil.getParameter(request, "spuCode", null);
        String spuName = HttpUtil.getParameter(request, "spuName", null);
        BigDecimal spuCost = HttpUtil.getBigDecimal(request, "spuCost");

        SpuInfo goodsInfo = SpuInfo
                .builder()
                .spuCode(spuCode)
                .spuName(spuName)
                .spuCost(spuCost)
                .build();

        goodsInfoService.saveSpuInfo(goodsInfo);
        return this.successReturn(null);
    }

    @ResponseBody
    @RequestMapping(value = "/saveSkuInfo")
    public ReturnResult saveSkuInfo(HttpServletRequest request, HttpServletResponse response) {

        logger.info(" saveGoodsInfo start  !");
        String spuCode = HttpUtil.getParameter(request, "spuCode", null);
        String skuCode = HttpUtil.getParameter(request, "skuCode", null);
        String skuName = HttpUtil.getParameter(request, "skuName", null);
        Integer skuWeight = HttpUtil.getIntegerParameter(request, "skuWeight", null);

        SkuInfo goodsInfo = SkuInfo
                .builder()
                .spuCode(spuCode)
                .skuCode(skuCode)
                .skuName(skuName)
                .skuWeight(skuWeight)
                .build();

        goodsInfoService.saveSkuInfo(goodsInfo);
        return this.successReturn(null);
    }

    @RequestMapping(value="toMofySpuInfoPage",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ModelAndView toMofySpuInfoPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            String spuCode = HttpUtil.getParameter(request, "spuCode", null);
            Asserts.checkNullOrEmpty(spuCode, "商品编号不能为空");

            SpuInfo spuInfo = goodsInfoService.getSpuInfoByCode(spuCode);
            Asserts.checkNullOrEmpty(spuInfo, "商品spu为空");
            request.setAttribute("spuInfo", spuInfo);

            return new ModelAndView("goods/MofySpuInfoPage");
        } catch (ServiceException e) {
            logger.error("toMofySpuInfoPage CommonException异常", e);
            int code =((ServiceException) e).getCode();
            return new ModelAndView("404");
        } catch (Exception e) {
            logger.error("toMofySpuInfoPage Exception异常", e);
            return new ModelAndView("404");
        }
    }

    @RequestMapping(value="toMofySkuInfoPage",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ModelAndView toMofySkuInfoPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            String skuCode = HttpUtil.getParameter(request, "skuCode", null);
            Asserts.checkNullOrEmpty(skuCode, "商品编号不能为空");

            SkuInfo skuInfo = goodsInfoService.getSkuInfoByCode(skuCode);
            Asserts.checkNullOrEmpty(skuInfo, "商品sku为空");
            request.setAttribute("skuInfo", skuInfo);

            SearchSpuInfoReq where = SearchSpuInfoReq.builder()
                    .build();
            List<SpuInfo> spuList = goodsInfoService.searchSpuListByWhere(where);

            request.setAttribute("spuList", spuList);

            return new ModelAndView("goods/MofySkuInfoPage");
        } catch (ServiceException e) {
            logger.error("toMofySpuInfoPage CommonException异常", e);
            int code =((ServiceException) e).getCode();
            return new ModelAndView("404");
        } catch (Exception e) {
            logger.error("toMofySpuInfoPage Exception异常", e);
            return new ModelAndView("404");
        }
    }
}
