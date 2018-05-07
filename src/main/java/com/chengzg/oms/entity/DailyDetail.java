package com.chengzg.oms.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DailyDetail {
    private Long id;

    private Date date;

    private String code;

    private String dailyCode;

    private String spuCode;

    private String spuName;

    private String skuCode;

    private String skuName;

    private Integer pvCount;

    private Integer uvCount;

    private Integer orderCount;

    private Integer userOrderCount;

    private BigDecimal orderAmount;

    private Integer isDel;

    private Date createTime;
}