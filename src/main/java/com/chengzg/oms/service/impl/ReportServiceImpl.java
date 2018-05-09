package com.chengzg.oms.service.impl;

import com.chengzg.oms.common.SysConstant;
import com.chengzg.oms.entity.DailyDetail;
import com.chengzg.oms.entity.DailyInfo;
import com.chengzg.oms.mapper.DailyDetailMapper;
import com.chengzg.oms.model.SpuReportModel;
import com.chengzg.oms.model.req.SearchSkuReportReq;
import com.chengzg.oms.model.req.SearchSpuReportReq;
import com.chengzg.oms.service.ReportService;
import com.chengzg.oms.utils.TimeUtility;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;

/**
 * Created by chengzg3 on 2018/5/8.
 */
@Service
public class ReportServiceImpl implements ReportService {
    private static Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Autowired
    private DailyDetailMapper dailyDetailMapper;

    @Override
    public List<DailyDetail> getSkuReportListByWhere(SearchSkuReportReq where) {
        if (where.getPageNum() != null && where.getPageSize() != null) {
            where.setStart((where.getPageNum() - 1) * where.getPageSize());
            where.setRows(where.getPageSize());
        }


        List<DailyDetail> list = dailyDetailMapper.getSkuReportListByWhere(where);
        for (DailyDetail item : list) {
            item.setDateStr(TimeUtility.formatTimeStr(item.getDate(), TimeUtility.TIME_FORMAT_YYYY_MM_DD));
            item.setGrossProfitRateStr(item.getGrossProfitRate()+"%");
        }
        return list;
    }

    @Override
    public Integer getSkuReportCountByWhere(SearchSkuReportReq where) {
        Integer count = dailyDetailMapper.getSkuReportCountByWhere(where);
        return count;
    }

    @Override
    public List<SpuReportModel> getSpuReportListByWhere(SearchSpuReportReq where) {
        List<SpuReportModel> list = dailyDetailMapper.getSpuReportListByWhere(where);

        return list;
    }

    @Override
    public HSSFWorkbook createSkuReportExcel(DailyInfo dailyInfo, List<DailyDetail> skuList, List<SpuReportModel> spuList) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {


        HSSFWorkbook workbook = new HSSFWorkbook();//创建一个excel文件
        // 构造导出xls样式及数据
        // 设置表头的样式
        HSSFCellStyle sheetStyle = workbook.createCellStyle();//sheet样式
        sheetStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        sheetStyle.setLeftBorderColor(HSSFCellStyle.BORDER_THIN);//左边框
        sheetStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        sheetStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        sheetStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        sheetStyle.setWrapText(true);//自动换行
        sheetStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        sheetStyle.setDataFormat((short) 0);

        HSSFSheet sheet = workbook.createSheet("SKU统计报表");//创建名称为:"收款明细"的sheet页
        //设置冻结列(参数1:冻结列数,参数2:冻结行数,参数3:表示右边区域可见的首列序号，从1开始计算,参数4:表示下边区域可见的首行序号，也是从1开始计算，这里是冻结列，所以为0；
        sheet.createFreezePane(0, 1, 0, 1);
        HSSFRow row = sheet.createRow((short) 0);
        for(int i = 0; i < SysConstant.SKU_REPORT_TITLE.length; i++){
            sheet.setColumnWidth(i, 3500);//设置其它列的宽度
            // sheet.autoSizeColumn((short)i);
        }
        //设置单元格表头
        this.setTitle(row,sheetStyle,SysConstant.SKU_REPORT_TITLE);
        if(skuList != null && skuList.size()>0){
            BigDecimal orderAmount = new BigDecimal(0);
            BigDecimal sumJin = new BigDecimal(0);
            BigDecimal totalCost = new BigDecimal(0);
            BigDecimal sumFreight = new BigDecimal(0);
            BigDecimal grossProfit = new BigDecimal(0);
            BigDecimal  grossProfitRate = new BigDecimal(0);

            int k =1;
            for(Iterator<DailyDetail> it = skuList.iterator(); it.hasNext(); k++){
                DailyDetail detail = it.next();
                orderAmount = orderAmount.add(detail.getOrderAmount());
                sumJin = sumJin.add(detail.getSumJin());
                totalCost = totalCost.add(detail.getSumCost());
                sumFreight = sumFreight.add(detail.getSumFreight());
                grossProfit = grossProfit.add(detail.getGrossProfit());
                row = sheet.createRow(k);
                //填充数据内容
                this.setContent(row,detail,sheetStyle,SysConstant.SKU_REPORT_COLUMN,0);
            }
            row = sheet.createRow(k);
            DailyDetail detail = new DailyDetail();
            grossProfitRate = orderAmount.intValue() == 0 ? new BigDecimal(0) : new BigDecimal(1).subtract(totalCost.add(sumFreight).divide(orderAmount,2,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));

            detail.setSkuName("总计");
            detail.setOrderAmount(orderAmount);
            detail.setSumJin(sumJin);
            detail.setSumCost(totalCost);
            detail.setSumFreight(sumFreight);
            detail.setGrossProfit(grossProfit);
            detail.setGrossProfitRate(grossProfitRate);
            detail.setGrossProfitRateStr(grossProfitRate+"%");
            this.setContent(row,detail,sheetStyle,SysConstant.SKU_REPORT_COLUMN,0);
        }

        HSSFSheet spusheet = workbook.createSheet("SPU统计报表");//创建名称为:"收款明细"的sheet页
        //设置冻结列(参数1:冻结列数,参数2:冻结行数,参数3:表示右边区域可见的首列序号，从1开始计算,参数4:表示下边区域可见的首行序号，也是从1开始计算，这里是冻结列，所以为0；
        spusheet.createFreezePane(0, 1, 0, 1);
        HSSFRow spurow = spusheet.createRow((short) 0);
        for(int i = 0; i < SysConstant.SKU_REPORT_TITLE.length; i++){
            spusheet.setColumnWidth(i, 3500);//设置其它列的宽度
            // spusheet.autoSizeColumn((short)i);
        }
        //设置单元格表头
        this.setTitle(spurow,sheetStyle,SysConstant.SPU_REPORT_TITLE);
        if(spuList != null && spuList.size()>0){
            BigDecimal spuorderAmount = new BigDecimal(0);
            BigDecimal spusumJin = new BigDecimal(0);
            BigDecimal sputotalCost = new BigDecimal(0);
            BigDecimal spusumFreight = new BigDecimal(0);
            BigDecimal spugrossProfit = new BigDecimal(0);
            BigDecimal spugrossProfitRate = new BigDecimal(0);
            int k =1;
            for(Iterator<SpuReportModel> it = spuList.iterator(); it.hasNext(); k++){
                SpuReportModel spudetail = it.next();
                spuorderAmount = spuorderAmount.add(spudetail.getOrderAmount());
                spusumJin = spusumJin.add(spudetail.getSumJin());
                sputotalCost = sputotalCost.add(spudetail.getSumCost());
                spusumFreight = spusumFreight.add(spudetail.getSumFreight());
                spugrossProfit = spugrossProfit.add(spudetail.getGrossProfit());

                BigDecimal grossProfitRate = spudetail.getOrderAmount().intValue() == 0 ? new BigDecimal(0) : new BigDecimal(1).subtract(spudetail.getSumCost().add(spudetail.getSumFreight()).divide(spudetail.getOrderAmount(),2,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
                spudetail.setGrossProfitRate(grossProfitRate);
                spudetail.setGrossProfitRateStr(grossProfitRate+"%");
                spurow = spusheet.createRow(k);
                //填充数据内容
                this.setContent(spurow,spudetail,sheetStyle,SysConstant.SPU_REPORT_COLUMN,0);
            }
            spurow = spusheet.createRow(k);
            SpuReportModel detail = new SpuReportModel();
            detail.setSpuName("总计");
            detail.setOrderAmount(spuorderAmount);
            detail.setSumJin(spusumJin);
            detail.setSumCost(sputotalCost);
            detail.setSumFreight(spusumFreight);
            detail.setGrossProfit(spugrossProfit);
            spugrossProfitRate = spuorderAmount.intValue() == 0 ? new BigDecimal(0) : new BigDecimal(1).subtract(sputotalCost.add(spusumFreight).divide(spuorderAmount,2,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
            detail.setGrossProfitRate(spugrossProfitRate);
            detail.setGrossProfitRateStr(spugrossProfitRate+"%");

            this.setContent(spurow,detail,sheetStyle,SysConstant.SPU_REPORT_COLUMN,0);
        }

        HSSFSheet dailysheet = workbook.createSheet(dailyInfo.getDateStr()+"日期汇总报表");//创建名称为:"收款明细"的sheet页
        dailysheet.createFreezePane(0, 1, 0, 1);
        HSSFRow dailyrow = dailysheet.createRow((short) 0);
        for(int i = 0; i < SysConstant.DAILY_REPORT_TITLE.length; i++){
            spusheet.setColumnWidth(i, 3500);//设置其它列的宽度
            // spusheet.autoSizeColumn((short)i);
        }
        this.setTitle(dailyrow,sheetStyle,SysConstant.DAILY_REPORT_TITLE);
        dailyrow = dailysheet.createRow(1);
        this.setContent(dailyrow,dailyInfo,sheetStyle,SysConstant.DAILY_REPORT_COLUMN,0);


        return workbook;
    }


    private void setContent(HSSFRow row, DailyDetail model, HSSFCellStyle sheetStyle,
                            String[] content, int j) throws NoSuchMethodException, SecurityException,IllegalAccessException, IllegalArgumentException,InvocationTargetException{

        HSSFCell cell;
        for(int i=j,k=0; i < j+content.length;i++,k++){
            try {
                //为第i个单元格填充个数
                cell = row.createCell(i);
                String attributName = content[k];
                String getAttributeValueMethodName = "get" + attributName.substring(0, 1).toUpperCase() + attributName.substring(1);
                Method m = model.getClass().getMethod(getAttributeValueMethodName);
                Object cellValObj = m.invoke(model);
                if (cellValObj == null) {
                    cellValObj = "";
                } else if (cellValObj instanceof BigDecimal) {
                    cell.setCellValue(Double.parseDouble(cellValObj.toString()));
                } else {
                    if (attributName.toLowerCase().contains("time")) {
                        cell.setCellValue(new HSSFRichTextString(
                                substringBeforeLast(cellValObj.toString(), ".")));
                    } else {
                        cell.setCellValue(new HSSFRichTextString(cellValObj
                                .toString()));
                    }
                }
                cell.setCellStyle(sheetStyle);
            } catch (Exception e) {
                logger.error("设置单元格内容:" + content[k] + ",error:" + e.getMessage());
            }
        }

    }

    private void setContent(HSSFRow row, SpuReportModel model, HSSFCellStyle sheetStyle,
                            String[] content, int j) throws NoSuchMethodException, SecurityException,IllegalAccessException, IllegalArgumentException,InvocationTargetException{

        HSSFCell cell;
        for(int i=j,k=0; i < j+content.length;i++,k++){
            try {
                //为第i个单元格填充个数
                cell = row.createCell(i);
                String attributName = content[k];
                String getAttributeValueMethodName = "get" + attributName.substring(0, 1).toUpperCase() + attributName.substring(1);
                Method m = model.getClass().getMethod(getAttributeValueMethodName);
                Object cellValObj = m.invoke(model);
                if (cellValObj == null) {
                    cellValObj = "";
                } else if (cellValObj instanceof BigDecimal) {
                    cell.setCellValue(Double.parseDouble(cellValObj.toString()));
                } else {
                    if (attributName.toLowerCase().contains("time")) {
                        cell.setCellValue(new HSSFRichTextString(
                                substringBeforeLast(cellValObj.toString(), ".")));
                    } else {
                        cell.setCellValue(new HSSFRichTextString(cellValObj
                                .toString()));
                    }
                }
                cell.setCellStyle(sheetStyle);
            } catch (Exception e) {
                logger.error("设置单元格内容:" + content[k] + ",error:" + e.getMessage());
            }
        }

    }

    private void setContent(HSSFRow row, DailyInfo model, HSSFCellStyle sheetStyle,
                            String[] content, int j) throws NoSuchMethodException, SecurityException,IllegalAccessException, IllegalArgumentException,InvocationTargetException{

        HSSFCell cell;
        for(int i=j,k=0; i < j+content.length;i++,k++){
            try {
                //为第i个单元格填充个数
                cell = row.createCell(i);
                String attributName = content[k];
                String getAttributeValueMethodName = "get" + attributName.substring(0, 1).toUpperCase() + attributName.substring(1);
                Method m = model.getClass().getMethod(getAttributeValueMethodName);
                Object cellValObj = m.invoke(model);
                if (cellValObj == null) {
                    cellValObj = "";
                } else if (cellValObj instanceof BigDecimal) {
                    cell.setCellValue(Double.parseDouble(cellValObj.toString()));
                } else {
                    if (attributName.toLowerCase().contains("time")) {
                        cell.setCellValue(new HSSFRichTextString(
                                substringBeforeLast(cellValObj.toString(), ".")));
                    } else {
                        cell.setCellValue(new HSSFRichTextString(cellValObj
                                .toString()));
                    }
                }
                cell.setCellStyle(sheetStyle);
            } catch (Exception e) {
                logger.error("设置单元格内容:" + content[k] + ",error:" + e.getMessage());
            }
        }

    }

    private String substringBeforeLast(String str, String separator) {
        if(StringUtils.isEmpty(str) || StringUtils.isEmpty(separator)){
            return str;
        }
        int pos = str.lastIndexOf(separator);
        if(pos == -1){
            return str;
        }
        return str.substring(0,pos);
    }

    private void setTitle(HSSFRow row, HSSFCellStyle sheetStyle, String[] title) {

        HSSFCell cell;
        for(int i = 0 ; i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(new HSSFRichTextString(title[i]));
            cell.setCellStyle(sheetStyle);
        }

    }
}
