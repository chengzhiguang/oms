package com.chengzg.oms.entity;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UseRecord {
    private Long id;

    private String mobilUuid;

    private String imei;

    private String brand;

    private String model;

    private String result;

    private Date createTime;
}