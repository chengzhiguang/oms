package com.chengzg.oms.entity;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GoodsInfo {
    private Long id;

    private String goodsSku;

    private String goodsCode;

    private String goodsName;

    private Integer jdPrice;

    private Integer marketPrice;

    private Integer discount;

    private Integer goodsWeight;

    private Integer marketingCosts;

    private Date createTime;

    private Integer isDel;

}