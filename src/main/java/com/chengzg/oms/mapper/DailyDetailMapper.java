package com.chengzg.oms.mapper;

import com.chengzg.oms.entity.DailyDetail;
import com.chengzg.oms.model.SpuReportModel;
import com.chengzg.oms.model.req.SearchSkuReportReq;
import com.chengzg.oms.model.req.SearchSpuReportReq;

import java.util.List;

public interface DailyDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DailyDetail record);

    int insertSelective(DailyDetail record);

    DailyDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DailyDetail record);

    int updateByPrimaryKey(DailyDetail record);

    DailyDetail selectByCode(String code);

    List<DailyDetail> getSkuReportListByWhere(SearchSkuReportReq where);
    Integer getSkuReportCountByWhere(SearchSkuReportReq where);

    List<SpuReportModel> getSpuReportListByWhere(SearchSpuReportReq where);


}