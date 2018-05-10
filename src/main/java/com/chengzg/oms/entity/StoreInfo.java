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
public class StoreInfo {
    private Long id;

    private String storeCode;

    private String storeName;

    private BigDecimal storeFreight;

    private Integer isDel;

    private Date createTime;

}