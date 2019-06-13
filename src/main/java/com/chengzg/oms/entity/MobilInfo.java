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
public class MobilInfo {
    private Long id;

    private String uuid;

    private String imei;

    private String brand;

    private String model;

    private Integer useCount;

    private Integer isDel;
}