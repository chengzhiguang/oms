package com.chengzg.oms.mapper;

import com.chengzg.oms.entity.DailyInfo;
import com.chengzg.oms.entity.MobilInfo;
import com.chengzg.oms.entity.UseRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface UseRecordMapper {
    int insert(UseRecord record);

    UseRecord selectByPrimaryKey(Long id);
}