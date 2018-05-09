package com.chengzg.oms.interceptor;

import com.chengzg.oms.utils.IpUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zhanjunwei on 15/7/30.
 */
public class CommonControllerInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(CommonControllerInterceptor.class);


    private String charReader(HttpServletRequest request) throws IOException {
        BufferedReader br = request.getReader();
        String str, wholeStr = "";
        while((str = br.readLine()) != null){
            wholeStr += str;
        }

        System.out.println(wholeStr);
        return wholeStr;
    }


    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     *
     * 如果返回true
     *    执行下一个拦截器,直到所有的拦截器都执行完毕
     *    再执行被拦截的Controller
     *    然后进入拦截器链,
     *    从最后一个拦截器往回执行所有的postHandle()
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String contentType = request.getHeader("Content-Type");
        String methodType = request.getMethod();
        //String paramStr = charReader(request);

        //设置请求唯一值
        String uuid = request.getParameter("uuid");
        if (StringUtils.isBlank(uuid)) {
            uuid = UUID.randomUUID().toString();
        }
        if (!StringUtils.isBlank(uuid)) {
            String oldThreadName = Thread.currentThread().getName();
            StringBuilder tn = new StringBuilder();
            tn.append(oldThreadName).append("_uuid:").append(uuid);
            Thread.currentThread().setName(tn.toString());
        }

        //打印请求日志
        StringBuffer logUrlSb = new StringBuffer(request.getRequestURI());
        logUrlSb.append("?");
        Map<String, String[]> params = request.getParameterMap();
        for (String key : params.keySet()) {
            String[] values = params.get(key);
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                logUrlSb.append(key);
                logUrlSb.append("=");
                logUrlSb.append(value);
                logUrlSb.append("&");
            }
        }

        String useIp = IpUtils.getIpAddr(request);


        logger.info("【{}】" + logUrlSb.toString(),useIp);
        return true;
    }




    /* (non-Javadoc)
    * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle(javax.servlet.http.HttpServletRequest,
    * javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
    *
    *
    */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }




    /**
     * 在DispatcherServlet完全处理完请求后被调用
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     * (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(HttpServletRequest,
     * HttpServletResponse, Object, Exception)
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        logger.info("afterCompletion 请求结束");
        String oldThreadName = Thread.currentThread().getName();
        if ( oldThreadName.indexOf("_uuid:") > -1) {
            Thread.currentThread().setName(oldThreadName.substring(0,
                    oldThreadName.indexOf("_uuid:")));
        }

    }


    private static String getRequestInfo(HttpServletRequest request, boolean requestDetails) {
        StringBuffer sb = new StringBuffer();
        /*sb.append(request.getMethod()).append(" ");
        sb.append(request.getRequestURI());*/
        if (requestDetails) {
            Enumeration<String> e = request.getParameterNames();
            sb.append("{");
            int i = 0;
            while (e.hasMoreElements()) {
                String name = e.nextElement();
                String val = request.getParameter(name);

                if (val != null && !val.isEmpty()) {
                    if (i > 0)
                        sb.append(", ");
                    sb.append(name).append(": ").append(val);

                    i++;
                }
            }
            sb.append("}");
        }

        return sb.toString();
    }


}
