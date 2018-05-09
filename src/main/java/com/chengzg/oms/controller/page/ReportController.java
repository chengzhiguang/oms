package com.chengzg.oms.controller.page;

import com.chengzg.oms.controller.support.BaseController;
import com.chengzg.oms.controller.support.PageResponse;
import com.chengzg.oms.controller.support.ReturnResult;
import com.chengzg.oms.entity.DailyDetail;
import com.chengzg.oms.entity.DailyInfo;
import com.chengzg.oms.exception.ServiceException;
import com.chengzg.oms.model.SpuReportModel;
import com.chengzg.oms.model.req.SearchDailyInfoReq;
import com.chengzg.oms.model.req.SearchSkuReportReq;
import com.chengzg.oms.model.req.SearchSpuReportReq;
import com.chengzg.oms.service.DailyInfoService;
import com.chengzg.oms.service.ReportService;
import com.chengzg.oms.utils.HttpUtil;
import com.chengzg.oms.utils.MD5Util;
import com.chengzg.oms.utils.StrUtils;
import com.chengzg.oms.utils.TimeUtility;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chengzg3 on 2018/5/8.
 */
@Controller
@RequestMapping(value = "page/report")
public class ReportController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private ReportService reportService;

    @Autowired
    private DailyInfoService dailyInfoService;

    @RequestMapping(value="toSkuReportPage",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ModelAndView toDailyManagerPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            return new ModelAndView("report/SkuReportPage");
        } catch (ServiceException e) {
            logger.error("toSkuReportPage CommonException异常", e);
            int code =((ServiceException) e).getCode();
            return new ModelAndView("404");
        } catch (Exception e) {
            logger.error("toSkuReportPage Exception异常", e);
            return new ModelAndView("404");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/searchSkuReportList")
    public PageResponse searchSkuReportList(HttpServletRequest request, HttpServletResponse response) {
        Integer pageNum = HttpUtil.getIntegerParameter(request, "page", 1);
        Integer pageSize = HttpUtil.getIntegerParameter(request, "rows", 10);

        String date = HttpUtil.getParameter(request, "date", null);
        if (StrUtils.isNullOrBlank(date)) {
            return this.successPageReturn(pageNum + 1, 0, new ArrayList());
        }

        SearchSkuReportReq where = SearchSkuReportReq.builder()
                .date(StrUtils.isNullOrBlank(date) ? null : TimeUtility.getDateByStr(date, TimeUtility.TIME_FORMAT_YYYY_MM_DD))
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();

        Integer total = reportService.getSkuReportCountByWhere(where);
        List<DailyDetail> list = reportService.getSkuReportListByWhere(where);

        return this.successPageReturn(pageNum + 1, total, list);

    }


    @ResponseBody
    @RequestMapping(value = "/exportSkuReport")
    public ReturnResult exportSkuReport(HttpServletRequest request, HttpServletResponse response) {
        Integer pageNum = HttpUtil.getIntegerParameter(request, "page", 1);
        Integer pageSize = HttpUtil.getIntegerParameter(request, "rows", 10);

        String date = HttpUtil.getParameter(request, "date", null);
        if (StrUtils.isNullOrBlank(date)) {
            return this.errorReturn(100, "下载错误");
        }
        SearchSkuReportReq where = SearchSkuReportReq.builder()
                .date(StrUtils.isNullOrBlank(date) ? null : TimeUtility.getDateByStr(date, TimeUtility.TIME_FORMAT_YYYY_MM_DD))
                .build();

        String dailyCode = MD5Util.MD5(TimeUtility.formatTimeStr(TimeUtility.getDateByStr(date, TimeUtility.TIME_FORMAT_YYYY_MM_DD), TimeUtility.TIME_FORMAT_YYYYMMDD));

        DailyInfo dailyInfo = dailyInfoService.getDailyInfoByCode(dailyCode);
        if (dailyInfo == null) {
            return this.errorReturn(100, "没有找到汇总信息不能下载");
        }
        if (dailyInfo.getStatus() != 1) {
            return this.errorReturn(100, "汇总信息没有导入明细不能下载");
        }


        List<DailyDetail> skuList = reportService.getSkuReportListByWhere(where);

        SearchSpuReportReq spuWhere = SearchSpuReportReq.builder()
                .date(StrUtils.isNullOrBlank(date) ? null : TimeUtility.getDateByStr(date, TimeUtility.TIME_FORMAT_YYYY_MM_DD))
                .build();

        List<SpuReportModel> spuList = reportService.getSpuReportListByWhere(spuWhere);

        try {
            HSSFWorkbook workbook = reportService.createSkuReportExcel(dailyInfo, skuList, spuList);
            response.reset();//清空输出流
            response.setContentType("application/vnd.ms-excel");//设置响应数据格式
            String fileName ="商品统计报表-"+TimeUtility.formatTimeStr(new Date(), TimeUtility.TIME_FORMAT_YYYYMMDDHHMMss) +".xls";//下载文件名
            response.setHeader("Content-Disposition", "attachment;fileName="+ new String( fileName.getBytes("gb2312"), "ISO8859-1" ) +"");//通知浏览器下载格式,解决下载文件名中文乱码问题
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
            return successReturn(null);
        } catch (Exception e) {
            return errorReturn(100, e.getMessage());
        }
    }
}
