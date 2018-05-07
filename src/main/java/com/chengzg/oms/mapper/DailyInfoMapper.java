package com.chengzg.oms.mapper;

import com.chengzg.oms.entity.DailyInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DailyInfo record);

    int insertSelective(DailyInfo record);

    DailyInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DailyInfo record);

    int updateByPrimaryKey(DailyInfo record);
}