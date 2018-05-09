package com.chengzg.oms.service;

import com.chengzg.oms.entity.DailyDetail;
import com.chengzg.oms.model.req.SearchSkuReportReq;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by chengzg3 on 2018/5/8.
 */
public interface ReportService {
    List<DailyDetail> getSkuReportListByWhere(SearchSkuReportReq where);
    Integer getSkuReportCountByWhere(SearchSkuReportReq where);


    HSSFWorkbook createSkuReportExcel(List<DailyDetail> fcReceiptOutExportList)
            throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException;
}
