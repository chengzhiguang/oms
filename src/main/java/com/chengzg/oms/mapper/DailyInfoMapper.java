package com.chengzg.oms.mapper;

import com.chengzg.oms.entity.DailyInfo;
import com.chengzg.oms.model.req.SearchDailyInfoReq;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DailyInfo record);

    int insertSelective(DailyInfo record);

    DailyInfo selectByPrimaryKey(Long id);

    DailyInfo selectByCode(String code);

    int updateByPrimaryKeySelective(DailyInfo record);

    int updateByPrimaryKey(DailyInfo record);

    List<DailyInfo> searchDailyListByWhere(SearchDailyInfoReq req);

    Integer searchDailyCountByWhere(SearchDailyInfoReq req);
}