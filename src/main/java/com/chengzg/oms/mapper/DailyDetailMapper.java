package com.chengzg.oms.mapper;

import com.chengzg.oms.entity.DailyDetail;

public interface DailyDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DailyDetail record);

    int insertSelective(DailyDetail record);

    DailyDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DailyDetail record);

    int updateByPrimaryKey(DailyDetail record);

    DailyDetail selectByCode(String code);

}