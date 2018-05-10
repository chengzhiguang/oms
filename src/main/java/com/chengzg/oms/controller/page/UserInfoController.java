package com.chengzg.oms.controller.page;

import com.chengzg.oms.controller.support.BaseController;
import com.chengzg.oms.controller.support.PageResponse;
import com.chengzg.oms.entity.SkuInfo;
import com.chengzg.oms.entity.StoreInfo;
import com.chengzg.oms.entity.UserInfo;
import com.chengzg.oms.exception.ServiceException;
import com.chengzg.oms.model.req.SearchSkuInfoReq;
import com.chengzg.oms.model.req.SearchUserInfoReq;
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
import java.util.List;

/**
 * Created by chengzg3 on 2018/5/10.
 */
@Controller
@RequestMapping(value = "page/user")
public class UserInfoController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value="toUserManagerPage",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ModelAndView toUserManagerPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            return new ModelAndView("user/UserManagerPage");
        } catch (ServiceException e) {
            logger.error("toUserManagerPage CommonException异常", e);
            int code =((ServiceException) e).getCode();
            return new ModelAndView("404");
        } catch (Exception e) {
            logger.error("toUserManagerPage Exception异常", e);
            return new ModelAndView("404");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/searchUserList")
    public PageResponse searchUserList(HttpServletRequest request, HttpServletResponse response) {
        Integer pageNum = HttpUtil.getIntegerParameter(request, "page", 1);
        Integer pageSize = HttpUtil.getIntegerParameter(request, "rows", 10);

        String userName = HttpUtil.getParameter(request, "userName", null);

        SearchUserInfoReq where = SearchUserInfoReq.builder()
                .userName(userName)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();

        Integer total = userInfoService.searchCountByWhere(where);
        List<UserInfo> list = userInfoService.searchListByWhere(where);

        return this.successPageReturn(pageNum + 1, total, list);

    }
}
