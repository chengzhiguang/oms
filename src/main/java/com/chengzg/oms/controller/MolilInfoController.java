package com.chengzg.oms.controller;

import com.alibaba.fastjson.JSONObject;
import com.chengzg.oms.controller.support.BaseController;
import com.chengzg.oms.controller.support.ReturnResult;
import com.chengzg.oms.entity.WhiteData;
import com.chengzg.oms.service.MobilInfoService;
import com.chengzg.oms.service.WhiteListService;
import com.chengzg.oms.utils.Asserts;
import com.chengzg.oms.utils.HttpUtil;
import com.google.gson.JsonObject;
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
@RequestMapping("api/mobilinfo")
public class MolilInfoController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(MolilInfoController.class);

    @Autowired
    private MobilInfoService mobilInfoService;

    /**
     * 查询列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getMobilInfo", method = {RequestMethod.GET})
    public @ResponseBody
    ReturnResult getMobilInfo(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result =  mobilInfoService.getMobilInfo();
        return successReturn(result);
    }
}
