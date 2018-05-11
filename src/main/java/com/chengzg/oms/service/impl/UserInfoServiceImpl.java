package com.chengzg.oms.service.impl;

import com.chengzg.oms.entity.UserInfo;
import com.chengzg.oms.exception.ServiceException;
import com.chengzg.oms.mapper.UserInfoMapper;
import com.chengzg.oms.model.req.SearchUserInfoReq;
import com.chengzg.oms.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chengzg3 on 2018/5/10.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    private static Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public Integer searchCountByWhere(SearchUserInfoReq req) {
        Integer count = userInfoMapper.searchCountByWhere(req);
        return count;
    }

    @Override
    public List<UserInfo> searchListByWhere(SearchUserInfoReq req) {
        if (req.getPageNum() != null && req.getPageSize() != null) {
            req.setStart((req.getPageNum() - 1) * req.getPageSize());
            req.setRows(req.getPageSize());
        }

        List<UserInfo> list = userInfoMapper.searchListByWhere(req);
        for (UserInfo dailyInfo : list) {

        }
        return list;
    }

    @Override
    public UserInfo getUserInfoByCode(String userCode) {
        UserInfo userInfo = userInfoMapper.getUserInfoByCode(userCode);
        return userInfo;
    }

    @Override
    public UserInfo checkLogin(String userCode, String userPwd) {
        UserInfo userInfo = userInfoMapper.getUserInfoByCode(userCode);
        if (userInfo == null) {
            throw new ServiceException(100, "用户名错误");
        }
        if (!userInfo.getUserPwd().equals(userPwd)) {
            throw new ServiceException(100, "密码错误");
        }
        return userInfo;
    }
}
