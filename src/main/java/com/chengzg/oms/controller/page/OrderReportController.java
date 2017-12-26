package com.chengzg.oms.controller.page;

import com.alibaba.fastjson.JSONArray;
import com.chengzg.oms.controller.support.BaseController;
import com.chengzg.oms.controller.support.PageResponse;
import com.chengzg.oms.controller.support.ReturnResult;
import com.chengzg.oms.entity.OrderInfo;
import com.chengzg.oms.entity.OrderReport;
import com.chengzg.oms.exception.ServiceException;
import com.chengzg.oms.model.req.SearchOrderInfoReq;
import com.chengzg.oms.model.req.SearchReportReq;
import com.chengzg.oms.service.OrderReportService;
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
import java.util.List;

/**
 * Created by chengzg3 on 2017/12/26.
 */
@Controller
@RequestMapping(value="page/orderreport")
public class OrderReportController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(OrderReportController.class);

    @Autowired
    private OrderReportService orderReportService;

    @RequestMapping(value="toOrderReportPage",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ModelAndView toGoodsManagerPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            return new ModelAndView("orderreport/OrderReportPage");
        } catch (ServiceException e) {
            logger.error("toOrderReportPage CommonException异常", e);
            int code =((ServiceException) e).getCode();
            return new ModelAndView("404");
        } catch (Exception e) {
            logger.error("toOrderReportPage Exception异常", e);
            return new ModelAndView("404");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/generateOrderReport")
    public ReturnResult generateOrderReport(HttpServletRequest request, HttpServletResponse response) {

        logger.info(" importOrderInfo start  !");

        String orderYear = HttpUtil.getParameter(request, "orderYear", null);

        Asserts.checkNullOrEmpty(orderYear, "年份不能为空");
        orderReportService.generateOrderReport(orderYear);

        return this.successReturn(null);
    }

    @RequestMapping(value="toReportPage",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ModelAndView toReportPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            return new ModelAndView("orderreport/ReportPage");
        } catch (ServiceException e) {
            logger.error("toOrderReportPage CommonException异常", e);
            int code =((ServiceException) e).getCode();
            return new ModelAndView("404");
        } catch (Exception e) {
            logger.error("toOrderReportPage Exception异常", e);
            return new ModelAndView("404");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/searchOrder")
    public PageResponse searchOrder(HttpServletRequest request, HttpServletResponse response) {

        logger.info(" importOrderInfo start  !");

        String orderYear = HttpUtil.getParameter(request, "orderYear", null);
        String goodsCode = HttpUtil.getParameter(request, "goodsCode", null);
        Integer pageNum = HttpUtil.getIntegerParameter(request, "page", 1);
        Integer pageSize = HttpUtil.getIntegerParameter(request, "rows", 10);

        Asserts.checkNullOrEmpty(orderYear, "订单年份不能为空");
        SearchReportReq where = SearchReportReq.builder()
                .orderYear(orderYear)
                .goodsCode(goodsCode)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();

        Integer total = orderReportService.searchCountByWhere(where);
        List<OrderReport> list = orderReportService.searchListByWhere(where);

        return this.successPageReturn(pageNum + 1, total, list);
    }
}
