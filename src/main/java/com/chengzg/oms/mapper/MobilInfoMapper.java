package com.chengzg.oms.mapper;

import com.chengzg.oms.entity.DailyInfo;
import com.chengzg.oms.entity.MobilInfo;
import com.chengzg.oms.model.req.SearchDailyInfoReq;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MobilInfoMapper {
    MobilInfo selectByPrimaryKey(Long id);

    MobilInfo getOne();

    int updateByPrimaryKeySelective(MobilInfo mobilInfo);
}