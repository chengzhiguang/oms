package com.chengzg.oms.controller;

import com.chengzg.oms.controller.support.BaseController;
import com.chengzg.oms.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by czg on 2016-08-26.
 */
@Controller
@RequestMapping(value="page/home")
public class HomeController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * 跳转查询页面
     * @param request
     * @return
     */
    @RequestMapping(value="toHomePage",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ModelAndView toHomePage(HttpServletRequest request, HttpServletResponse response) {
        try {
            return new ModelAndView("home/HomePage");
        } catch (ServiceException e) {
            logger.error("getSysTime CommonException异常", e);
            int code =((ServiceException) e).getCode();
            return new ModelAndView("404");
        } catch (Exception e) {
            logger.error("getSysTime Exception异常", e);
            return new ModelAndView("404");
        }
    }
}
