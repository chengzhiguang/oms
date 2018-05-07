package com.chengzg.oms.controller.page;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chengzg.oms.controller.support.BaseController;
import com.chengzg.oms.controller.support.PageResponse;
import com.chengzg.oms.controller.support.ReturnResult;
import com.chengzg.oms.entity.DailyInfo;
import com.chengzg.oms.entity.SkuInfo;
import com.chengzg.oms.exception.ServiceException;
import com.chengzg.oms.model.req.SearchDailyInfoReq;
import com.chengzg.oms.model.req.SearchSkuInfoReq;
import com.chengzg.oms.service.DailyInfoService;
import com.chengzg.oms.utils.*;
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
import java.sql.Time;
import java.util.List;
import java.util.Map;

/**
 * Created by chengzg3 on 2018/5/7.
 */
@Controller
@RequestMapping(value="page/dailyinfo")
public class DailyInfoController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(DailyInfoController.class);

    @Autowired
    private DailyInfoService dailyInfoService;

    @RequestMapping(value="toDailyManagerPage",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ModelAndView toDailyManagerPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            return new ModelAndView("dailyinfo/DailyManagerPage");
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
    @RequestMapping(value = "/searchDailyInfoList")
    public PageResponse searchDailyInfoList(HttpServletRequest request, HttpServletResponse response) {
        Integer pageNum = HttpUtil.getIntegerParameter(request, "page", 1);
        Integer pageSize = HttpUtil.getIntegerParameter(request, "rows", 10);

        String date = HttpUtil.getParameter(request, "date", null);

        SearchDailyInfoReq where = SearchDailyInfoReq.builder()
                .date(StrUtils.isNullOrBlank(date) ? null : TimeUtility.getDateByStr(date, TimeUtility.TIME_FORMAT_YYYY_MM_DD))
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();

        Integer total = dailyInfoService.searchDailyCountByWhere(where);
        List<DailyInfo> list = dailyInfoService.searchDailyListByWhere(where);

        return this.successPageReturn(pageNum + 1, total, list);

    }

    @RequestMapping(value="toImportDailyInfoPage",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ModelAndView toImportDailyInfoPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            return new ModelAndView("dailyinfo/ImportDailyInfoPage");
        } catch (ServiceException e) {
            logger.error("toImportDailyInfoPage CommonException异常", e);
            int code =((ServiceException) e).getCode();
            return new ModelAndView("404");
        } catch (Exception e) {
            logger.error("toImportDailyInfoPage Exception异常", e);
            return new ModelAndView("404");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/importDailyInfo")
    public ReturnResult importDailyInfo(HttpServletRequest request, HttpServletResponse response) {

        logger.info(" importDailyInfo start  !");

        File excelFile = HttpUtil.getFileByRequest(request);
        if (excelFile == null) {
            return this.errorReturn(100);
        }

        JSONObject excelObj = ExcelUtil.importExcel(excelFile.getPath(), 1);
        logger.info("" + excelObj.size());

        dailyInfoService.importDailyInfo(excelObj);
        return this.successReturn(null);
    }


    @RequestMapping(value="toImportDailyDetailPage",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ModelAndView toImportDailyDetailPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            String dailyCode = HttpUtil.getParameter(request, "dailyCode", null);
            Asserts.checkNullOrEmpty(dailyCode, "汇总信息不存在");
            DailyInfo dailyInfo = dailyInfoService.getDailyInfoByCode(dailyCode);
            Asserts.checkNullOrEmpty(dailyInfo, "汇总信息不存在");
            request.setAttribute("dailyInfo", dailyInfo);

            return new ModelAndView("dailyinfo/ImportDailyDetailPage");
        } catch (ServiceException e) {
            logger.error("toImportDailyDetailPage CommonException异常", e);
            int code =((ServiceException) e).getCode();
            return new ModelAndView("404");
        } catch (Exception e) {
            logger.error("toImportDailyDetailPage Exception异常", e);
            return new ModelAndView("404");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/importDailyDetail")
    public ReturnResult importDailyDetail(HttpServletRequest request, HttpServletResponse response) {

        logger.info(" importDailyDetail start  !");

        File excelFile = HttpUtil.getFileByRequest(request);
        if (excelFile == null) {
            return this.errorReturn(100);
        }

        JSONObject excelObj = ExcelUtil.importExcel(excelFile.getPath(), 1);
        logger.info("" + excelObj.size());

        dailyInfoService.importDailyInfo(excelObj);
        return this.successReturn(null);
    }

}
