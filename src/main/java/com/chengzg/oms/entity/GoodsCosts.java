package com.chengzg.oms.entity;

import lombok.*;

import java.util.Date;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GoodsCosts {
    private Long id;

    private String orderYear;

    private String goodsCode;

    private Integer totalMarketingCosts;

    private Integer isDel;

    private Date createTime;

    private Date lastModifyTime;

}