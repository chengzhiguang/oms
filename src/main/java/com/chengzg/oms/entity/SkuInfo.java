package com.chengzg.oms.entity;

import lombok.*;

import java.util.Date;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SkuInfo {
    private Long id;

    private String skuCode;

    private String skuName;

    private String spuCode;

    private String spuName;

    private Integer skuWeight;

    private Integer isDel;

    private Date createTime;

    private Date lastModifyTime;
}