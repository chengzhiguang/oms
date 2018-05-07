package com.chengzg.oms.controller.page;

import com.alibaba.fastjson.JSONArray;
import com.chengzg.oms.controller.support.BaseController;
import com.chengzg.oms.controller.support.ReturnResult;
import com.chengzg.oms.exception.ServiceException;
import com.chengzg.oms.utils.ExcelUtil;
import com.chengzg.oms.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * Created by chengzg3 on 2018/5/5.
 */
@Controller
@RequestMapping(value="page/jddata")
public class JdDataController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(JdDataController.class);

    @RequestMapping(value="toDailyManagerPage",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ModelAndView toDailyManagerPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            return new ModelAndView("jddata/DailyManagerPage");
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
    @RequestMapping(value = "/importJdSkuData")
    public ReturnResult importJdSkuData(HttpServletRequest request, HttpServletResponse response) {

        logger.info(" importJdSkuData start  !");

        File excelFile = HttpUtil.getFileByRequest(request);
        if (excelFile == null) {
            return this.errorReturn(100);
        }

        JSONArray excelList = ExcelUtil.importCsv(excelFile);
        logger.info("" + excelList.size());

        return this.successReturn(null);
    }
}
