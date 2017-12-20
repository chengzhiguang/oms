package com.chengzg.oms.controller.support;

import com.chengzg.oms.exception.ServiceException;
import com.chengzg.oms.utils.ErrorMessageLoader;
import com.chengzg.oms.utils.IpUtils;
import com.chengzg.oms.utils.JsonMapper;
import com.dianping.cat.Cat;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.google.common.collect.Maps;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author dongh38@ziroom
 * @Date 16/10/23
 * @Time 下午9:00
 */
@ControllerAdvice
public class RestfulExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ServiceException.class})
    public final ResponseEntity<ReturnResult> handleServiceException(ServiceException ex,
                                                                     HttpServletRequest request) {
        // 注入servletRequest，用于出错时打印请求URL与来源地址
        logError(ex, request);
        Cat.logError("财务系统业务异常",ex);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE));
        ReturnResult result = ReturnResult.failure(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public final ResponseEntity<ReturnResult> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        logError(ex,request);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE));
        ReturnResult result = ReturnResult.failure(101, ErrorMessageLoader.getString(101) + "  " + ex.getMessage());
        return new ResponseEntity<>(result,headers, HttpStatus.OK);
    }

    @ExceptionHandler(value = {Exception.class})
    public final ResponseEntity<ReturnResult> handleGeneralException(Exception ex, HttpServletRequest request) {
        logError(ex, request);

        Cat.logError("财务系统异常",ex);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE));
        ReturnResult result = ReturnResult.failure(100, ErrorMessageLoader.getString(100));
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }

    @ExceptionHandler(value = {InvalidFormatException.class})
    public final ResponseEntity<ReturnResult> handleGeneralHttpMessageNotReadableException(InvalidFormatException ex, HttpServletRequest request) {
        logError(ex, request);

        Cat.logError("财务系统异常",ex);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE));
        ReturnResult result = ReturnResult.failure(103, ErrorMessageLoader.getString(103));
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }



    /**
     * 重载ResponseEntityExceptionHandler的方法，加入日志
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {

        logError(ex);

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, WebRequest.SCOPE_REQUEST);
        }

        ReturnResult result = null;

        if (ex.getClass() == HttpMessageNotReadableException.class) {
            result = ReturnResult.failure(103, ErrorMessageLoader.getString(103));
        } else {
            result = ReturnResult.failure(100, ErrorMessageLoader.getString(100));
        }
        return new ResponseEntity<Object>(result, headers, HttpStatus.OK);

    }

    public String connInfo(Exception ex, HttpServletRequest request) {
        Map<String, String> map = Maps.newHashMap();
        map.put("message", ex.getMessage());
        map.put("from", request.getRemoteAddr());
        String queryString = request.getQueryString();
        map.put("path", queryString != null ?
                (request.getRequestURI() + "?" + queryString) :
                request.getRequestURI());

        return JsonMapper.nonDefaultMapper().toJson(map);
    }

    public void logError(Exception ex) {
        Map<String, String> map = Maps.newHashMap();
        map.put("message", ex.getMessage());
        logger.error(JsonMapper.nonDefaultMapper().toJson(map), ex);
    }

    public void logError(Exception ex, HttpServletRequest request) {
        Map<String, String> map = Maps.newHashMap();
        map.put("message", ex.getMessage());
        map.put("from", IpUtils.getIpAddr(request));
        String queryString = request.getQueryString();
        map.put("path", queryString != null ?
                (request.getRequestURI() + "?" + queryString) :
                request.getRequestURI());

        logger.error(JsonMapper.nonDefaultMapper().toJson(map), ex);
    }
}
