package com.chengzg.oms.mapper;

import com.chengzg.oms.entity.UserInfo;
import com.chengzg.oms.model.req.SearchUserInfoReq;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    Integer searchCountByWhere(SearchUserInfoReq req);

    List<UserInfo> searchListByWhere(SearchUserInfoReq req);

    UserInfo getUserInfoByCode(String userCode);
}