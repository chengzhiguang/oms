package com.chengzg.oms.common;

/**
 * Created by chengzg3 on 2018/5/8.
 */
public class SysConstant {

    public static final String[] SKU_REPORT_TITLE={
            "SKU名称","总斤数","总成本","总金额","总运费","毛利","毛利/总金额"
    };
    public static final String[] SKU_REPORT_COLUMN={
            "skuName","sumJin","sumCost","orderAmount","sumFreight","grossProfit","grossProfitRateStr"
    };

    public static final String[] SPU_REPORT_TITLE={
            "SPU名称","总斤数","总成本","总金额","总运费","毛利","毛利/总金额"
    };
    public static final String[] SPU_REPORT_COLUMN={
            "spuName","sumJin","sumCost","orderAmount","sumFreight","grossProfit","grossProfitRateStr"
    };

    public static final String[] DAILY_REPORT_TITLE={
            "浏览量","客户数","下单客户数","下单单量","下单金额"
    };
    public static final String[] DAILY_REPORT_COLUMN={
            "pvCount","uvCount","userOrderCount","orderCount","orderAmount"
    };
}
