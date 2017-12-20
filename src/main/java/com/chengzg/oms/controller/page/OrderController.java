package com.chengzg.oms.controller.page;

import com.alibaba.fastjson.JSONArray;
import com.chengzg.oms.controller.support.BaseController;
import com.chengzg.oms.controller.support.PageResponse;
import com.chengzg.oms.controller.support.ReturnResult;
import com.chengzg.oms.entity.OrderInfo;
import com.chengzg.oms.exception.ServiceException;
import com.chengzg.oms.model.req.SearchOrderInfoReq;
import com.chengzg.oms.service.OrderInfoService;
import com.chengzg.oms.utils.Asserts;
import com.chengzg.oms.utils.ExcelUtil;
import com.chengzg.oms.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * Created by czg on 2016-08-26.
 */
@Controller
@RequestMapping(value="page/order")
public class OrderController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderInfoService orderInfoService;

    @RequestMapping(value="toImportOrderPage",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ModelAndView toImportOrderPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            return new ModelAndView("order/ImportOrderPage");
        } catch (ServiceException e) {
            logger.error("getSysTime CommonException异常", e);
            int code =((ServiceException) e).getCode();
            return new ModelAndView("404");
        } catch (Exception e) {
            logger.error("getSysTime Exception异常", e);
            return new ModelAndView("404");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/importOrderInfo")
    public ReturnResult importOrderInfo(HttpServletRequest request, HttpServletResponse response) {

        logger.info(" importOrderInfo start  !");

        File excelFile = HttpUtil.getFileByRequest(request);
        if (excelFile == null) {
            return this.errorReturn(100);
        }

        JSONArray excelList = ExcelUtil.importCsv(excelFile);
        logger.info("" + excelList.size());

        orderInfoService.importOrderInfo(excelList);
        return this.successReturn(null);
    }


    @RequestMapping(value="toOrderManagerPage",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ModelAndView toOrderManagerPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            return new ModelAndView("order/OrderManagerPage");
        } catch (ServiceException e) {
            logger.error("toOrderManagerPage CommonException异常", e);
            int code =((ServiceException) e).getCode();
            return new ModelAndView("404");
        } catch (Exception e) {
            logger.error("toOrderManagerPage Exception异常", e);
            return new ModelAndView("404");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/searchOrder")
    public PageResponse searchOrder(HttpServletRequest request, HttpServletResponse response) {

        logger.info(" importOrderInfo start  !");

        String orderYear = HttpUtil.getParameter(request, "orderYear", null);
        String orderStatus = HttpUtil.getParameter(request, "orderStatus", null);
        Integer pageNum = HttpUtil.getIntegerParameter(request, "page", 1);
        Integer pageSize = HttpUtil.getIntegerParameter(request, "rows", 10);

        Asserts.checkNullOrEmpty(orderYear, "订单年份不能为空");
        String orderCode = HttpUtil.getParameter(request, "orderCode", null);
        SearchOrderInfoReq where = SearchOrderInfoReq.builder()
                .orderYear(orderYear)
                .orderCode(orderCode)
                .orderStatus(orderStatus)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();

        Integer total = orderInfoService.searchCountByWhere(where);
        List<OrderInfo> list = orderInfoService.searchListByWhere(where);

        return this.successPageReturn(pageNum + 1, total, list);
    }
}
