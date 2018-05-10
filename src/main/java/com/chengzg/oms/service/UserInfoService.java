package com.chengzg.oms.service;

import com.chengzg.oms.entity.UserInfo;
import com.chengzg.oms.model.req.SearchUserInfoReq;

import java.util.List;

/**
 * Created by chengzg3 on 2018/5/10.
 */
public interface UserInfoService {
    Integer searchCountByWhere(SearchUserInfoReq req);

    List<UserInfo> searchListByWhere(SearchUserInfoReq req);

    UserInfo getUserInfoByCode(String userCode);
}
