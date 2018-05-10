package com.chengzg.oms.controller.page;

import com.chengzg.oms.controller.support.BaseController;
import com.chengzg.oms.controller.support.PageResponse;
import com.chengzg.oms.controller.support.ReturnResult;
import com.chengzg.oms.entity.SkuInfo;
import com.chengzg.oms.entity.SpuInfo;
import com.chengzg.oms.entity.StoreInfo;
import com.chengzg.oms.exception.ServiceException;
import com.chengzg.oms.model.req.SearchSkuInfoReq;
import com.chengzg.oms.model.req.SearchStooreInfoReq;
import com.chengzg.oms.service.StoreInfoService;
import com.chengzg.oms.utils.Asserts;
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
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Created by chengzg3 on 2018/5/10.
 */
@Controller
@RequestMapping(value = "page/storeinfo")
public class StoreInfoController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(StoreInfoController.class);

    @Autowired
    private StoreInfoService storeInfoService;

    @RequestMapping(value="toStoreManagerPage",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ModelAndView toStoreManagerPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            return new ModelAndView("storeinfo/StoreManagerPage");
        } catch (ServiceException e) {
            logger.error("toStoreManagerPage CommonException异常", e);
            int code =((ServiceException) e).getCode();
            return new ModelAndView("404");
        } catch (Exception e) {
            logger.error("toStoreManagerPage Exception异常", e);
            return new ModelAndView("404");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/searchStoreList")
    public PageResponse searchStoreList(HttpServletRequest request, HttpServletResponse response) {
        Integer pageNum = HttpUtil.getIntegerParameter(request, "page", 1);
        Integer pageSize = HttpUtil.getIntegerParameter(request, "rows", 10);

        String storeName = HttpUtil.getParameter(request, "storeName", null);

        SearchStooreInfoReq where = SearchStooreInfoReq.builder()
                .storeName(storeName)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();

        Integer total = storeInfoService.searchCountByWhere(where);
        List<StoreInfo> list = storeInfoService.searchListByWhere(where);

        return this.successPageReturn(pageNum + 1, total, list);

    }

    @RequestMapping(value="toAddStoreInfoPage",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ModelAndView toAddSpuInfoPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            String storeCode = UUID.randomUUID().toString().replace("-", "");
            request.setAttribute("storeCode", storeCode);
            return new ModelAndView("storeinfo/AddStoreInfoPage");
        } catch (ServiceException e) {
            logger.error("toAddStoreInfoPage CommonException异常", e);
            int code =((ServiceException) e).getCode();
            return new ModelAndView("404");
        } catch (Exception e) {
            logger.error("toAddStoreInfoPage Exception异常", e);
            return new ModelAndView("404");
        }
    }


    @RequestMapping(value="toMofyStoreInfoPage",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ModelAndView toMofyStoreInfoPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            String storeCode = HttpUtil.getParameter(request, "storeCode", null);
            Asserts.checkNullOrEmpty(storeCode, "商铺编号不能为空");

            StoreInfo storeInfo = storeInfoService.getStoreInfoByCode(storeCode);
            Asserts.checkNullOrEmpty(storeInfo, "商铺为空");
            request.setAttribute("storeInfo", storeInfo);

            return new ModelAndView("storeinfo/MofyStoreInfoPage");
        } catch (ServiceException e) {
            logger.error("toMofySpuInfoPage CommonException异常", e);
            int code =((ServiceException) e).getCode();
            return new ModelAndView("404");
        } catch (Exception e) {
            logger.error("toMofySpuInfoPage Exception异常", e);
            return new ModelAndView("404");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveStoreInfo")
    public ReturnResult saveStoreInfo(HttpServletRequest request, HttpServletResponse response) {

        logger.info(" saveStoreInfo start  !");
        String storeCode = HttpUtil.getParameter(request, "storeCode", null);
        String storeName = HttpUtil.getParameter(request, "storeName", null);
        BigDecimal storeFreight = HttpUtil.getBigDecimal(request, "storeFreight");

        StoreInfo goodsInfo = StoreInfo
                .builder()
                .storeCode(storeCode)
                .storeName(storeName)
                .storeFreight(storeFreight)
                .build();

        storeInfoService.saveStoreInfo(goodsInfo);
        return this.successReturn(null);
    }

}
