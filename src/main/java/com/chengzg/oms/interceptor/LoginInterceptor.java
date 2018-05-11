package com.chengzg.oms.interceptor;

import com.alibaba.fastjson.JSON;
import com.chengzg.oms.controller.support.ReturnResult;
import com.chengzg.oms.entity.UserInfo;
import com.chengzg.oms.entity.WhiteData;
import com.chengzg.oms.service.UserInfoService;
import com.chengzg.oms.service.WhiteListService;
import com.chengzg.oms.utils.HttpUtil;
import com.chengzg.oms.utils.IpUtils;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author dongh38@ziroom
 * @Date 2016/11/10
 * @Time 16:19
 * @Description
 * @Since 1.0.0
 */
@Component
@ToString
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserInfoService userInfoService;

    private final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String useIp = IpUtils.getIpAddr(request);

        String uri = request.getRequestURI();
        if (uri.toLowerCase().indexOf("login") != -1) {
            return true;
        }

        UserInfo userInfo = HttpUtil.getSessionSysUser(request);
        if (userInfo == null) {
            response.sendRedirect("/page/home/toLoginPage");//address=" + uri + "?" + param
            return false;
        }

        return true;
    }
}
