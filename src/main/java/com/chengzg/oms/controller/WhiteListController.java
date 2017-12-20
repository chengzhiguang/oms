package com.chengzg.oms.controller;

import com.chengzg.oms.controller.support.BaseController;
import com.chengzg.oms.controller.support.ReturnResult;
import com.chengzg.oms.entity.WhiteData;
import com.chengzg.oms.service.WhiteListService;
import com.chengzg.oms.utils.Asserts;
import com.chengzg.oms.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by czg on 2017/1/13.
 */
@Controller
@RequestMapping("api/whitelist")
public class WhiteListController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(WhiteListController.class);

    @Autowired
    private WhiteListService whiteListService;

    /**
     * 查询列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getWhiteList", method = {RequestMethod.GET})
    public @ResponseBody
    ReturnResult getWhiteList(HttpServletRequest request, HttpServletResponse response) {
        List<WhiteData> list =  whiteListService.whiteList();
        return successReturn(list);
    }

    /**
     * 查询列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "clertWhiteList", method = {RequestMethod.GET})
    public @ResponseBody
    ReturnResult clertWhiteList(HttpServletRequest request, HttpServletResponse response) {
        whiteListService.clertWhiteList();
        return successReturn(null);
    }

    /**
     * 查询列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "addWhiteList", method = {RequestMethod.GET})
    public @ResponseBody
    ReturnResult addWhiteList(HttpServletRequest request, HttpServletResponse response) {
        String serviceIp = HttpUtil.getParameter(request, "serviceIp", null);
        Asserts.checkNullOrEmpty(serviceIp, "服务器ip为空");
        whiteListService.addWhiteList(serviceIp);
        return successReturn(null);
    }

    /**
     * 查询列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "delWhiteList", method = {RequestMethod.GET})
    public @ResponseBody
    ReturnResult delWhiteList(HttpServletRequest request, HttpServletResponse response) {
        String serviceIp = HttpUtil.getParameter(request, "serviceIp", null);
        Asserts.checkNullOrEmpty(serviceIp, "服务器ip为空");
        whiteListService.delWhiteList(serviceIp);
        return successReturn(null);
    }
}
