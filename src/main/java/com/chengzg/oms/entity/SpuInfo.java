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
public class SpuInfo {
    private Long id;

    private String storeCode;

    private String storeName;

    private String spuCode;

    private String spuName;

    private BigDecimal spuCost;

    private Integer isDel;

    private Date createTime;
}