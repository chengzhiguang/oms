package com.chengzg.oms.controller;

import com.chengzg.oms.controller.support.BaseController;
import com.chengzg.oms.controller.support.ReturnResult;
import com.chengzg.oms.entity.UserInfo;
import com.chengzg.oms.exception.ServiceException;
import com.chengzg.oms.service.UserInfoService;
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

/**
 * Created by czg on 2016-08-26.
 */
@Controller
@RequestMapping(value="page/home")
public class HomeController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserInfoService userInfoService;

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

    /**
     * 跳转查询页面
     * @param request
     * @return
     */
    @RequestMapping(value="toLoginPage",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ModelAndView toLoginPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            return new ModelAndView("home/LoginPage");
        } catch (ServiceException e) {
            logger.error("toLoginPage CommonException异常", e);
            int code =((ServiceException) e).getCode();
            return new ModelAndView("404");
        } catch (Exception e) {
            logger.error("toLoginPage Exception异常", e);
            return new ModelAndView("404");
        }
    }

    /**
     * 查询列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "login", method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ReturnResult login(HttpServletRequest request, HttpServletResponse response) {

        String userCode = HttpUtil.getParameter(request, "userCode", null);
        String userPwd = HttpUtil.getParameter(request, "userPwd", null);
        Asserts.checkNullOrEmpty(userCode, "员工系统号不能为空");
        Asserts.checkNullOrEmpty(userPwd, "员工登录密码不能为空");

        UserInfo userInfo = userInfoService.checkLogin(userCode, userPwd);

        request.getSession().setAttribute("userInfo", userInfo);

        return successReturn(null);
    }

    /**
     * 查询列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "logout", method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ReturnResult logout(HttpServletRequest request, HttpServletResponse response) {

        try {
            request.getSession().invalidate();
            response.sendRedirect("/page/home/toLoginPage");//address=" + uri + "?" + param
        } catch (Exception e) {

        }


        return successReturn(null);
    }
}
