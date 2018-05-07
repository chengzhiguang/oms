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
public class DailyInfo {
    private Long id;

    private Date date;

    private String code;

    private Integer pvCount;

    private Integer uvCount;

    private Integer userOrderCount;

    private Integer orderCount;

    private BigDecimal orderAmount;

    private BigDecimal userPrice;

    private Integer status;

    private Integer isDel;

    private Date createTime;
}