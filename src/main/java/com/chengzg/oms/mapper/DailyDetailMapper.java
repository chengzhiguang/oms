package com.chengzg.oms.mapper;

import com.chengzg.oms.entity.DailyDetail;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DailyDetail record);

    int insertSelective(DailyDetail record);

    DailyDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DailyDetail record);

    int updateByPrimaryKey(DailyDetail record);
}