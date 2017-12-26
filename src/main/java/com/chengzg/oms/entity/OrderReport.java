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
public class OrderReport {
    private Long id;

    private String orderYear;

    private String goodsCode;

    private String goodsName;

    private Integer sumCount;

    private Integer unitPrice;

    private Integer sumSettmentAmount;

    private Integer sumLuggageAmount;

    private Integer marketingCosts;

    private Integer sumAmount;

    private BigDecimal sumJin;

    private Integer luggageJin;

    private Integer marketingFeeJin;

    private Integer isDel;

    private Date createTime;

    private Date lastMofyTime;

}