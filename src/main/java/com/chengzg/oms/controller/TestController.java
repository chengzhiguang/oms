package com.chengzg.oms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chengzg.oms.controller.support.BaseController;
import com.chengzg.oms.controller.support.ReturnResult;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by czg on 2017/1/13.
 */
@Controller
@RequestMapping("api/test")
public class TestController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private WhiteListService whiteListService;

    /**
     * 查询列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "sendTestRabbitMq", method = {RequestMethod.GET})
    public @ResponseBody
    ReturnResult sendTestRabbitMq(HttpServletRequest request, HttpServletResponse response) {

        String queueKey = HttpUtil.getParameter(request, "queueKey", null);
        Asserts.checkNullOrEmpty(queueKey, "对接Key不能为空");

        return successReturn(null);
    }

    @RequestMapping(value = "addTestEleasticJob", method = {RequestMethod.GET})
    public @ResponseBody
    ReturnResult addTestEleasticJob(HttpServletRequest request, HttpServletResponse response) {

        JSONObject bu = new JSONObject();
        bu.put("code", "11111");
        bu.put("name", "北京自如");

        return successReturn(null);
    }

}
