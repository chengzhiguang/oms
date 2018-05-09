package com.chengzg.oms.service;

import com.chengzg.oms.entity.DailyDetail;
import com.chengzg.oms.entity.DailyInfo;
import com.chengzg.oms.model.SpuReportModel;
import com.chengzg.oms.model.req.SearchSkuReportReq;
import com.chengzg.oms.model.req.SearchSpuInfoReq;
import com.chengzg.oms.model.req.SearchSpuReportReq;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by chengzg3 on 2018/5/8.
 */
public interface ReportService {
    List<DailyDetail> getSkuReportListByWhere(SearchSkuReportReq where);
    Integer getSkuReportCountByWhere(SearchSkuReportReq where);

    List<SpuReportModel> getSpuReportListByWhere(SearchSpuReportReq where);


    HSSFWorkbook createSkuReportExcel(DailyInfo dailyInfo, List<DailyDetail> skuList,List<SpuReportModel> spuList)
            throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException;
}
