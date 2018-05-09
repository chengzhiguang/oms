package com.chengzg.oms.model;

import lombok.*;

import java.math.BigDecimal;

/**
 * Created by chengzg3 on 2018/5/9.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SpuReportModel {
    private String spuCode;
    private String spuName;
    private BigDecimal sumJin;
    private BigDecimal sumCost;
    private BigDecimal orderAmount;
    private BigDecimal sumFreight;
    private BigDecimal grossProfit;
    private BigDecimal grossProfitRate;
    private String grossProfitRateStr;
}
