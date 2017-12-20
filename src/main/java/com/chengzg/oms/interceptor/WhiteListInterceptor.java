package com.chengzg.oms.interceptor;

import com.alibaba.fastjson.JSON;
import com.chengzg.oms.controller.support.ReturnResult;
import com.chengzg.oms.entity.WhiteData;
import com.chengzg.oms.service.WhiteListService;
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
public class WhiteListInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private WhiteListService whiteListService;

    private final Logger LOGGER = LoggerFactory.getLogger(WhiteListInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 白名单开关 默认开启
        if (!whiteListService.isEnabled()) {
            return true;
        }
        String useIp = IpUtils.getIpAddr(request);
        String requestUri = request.getRequestURI();
        if (requestUri.indexOf("whitelist") > -1) {
            LOGGER.info("白名单设置请求，直接通过");
            return true;
        }

        List<WhiteData> whiteList = whiteListService.whiteList();
        if (whiteList == null || whiteList.size() == 0) {
            LOGGER.info("没有配置白名单");
            return true;
        }
        for (WhiteData data : whiteList) {
            if (useIp.equals(data.getWhiteServer())) {
                LOGGER.info("user ip is:{} 在白名单中，允许访问",useIp);
                return true;
            }
        }
        LOGGER.info("user ip is:{} 不在白名单中，不允许访问",useIp);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = response.getWriter();
        ReturnResult result = ReturnResult.failure(102);

        writer.println(JSON.toJSONString(result));
        writer.close();
        return false;
    }
}
