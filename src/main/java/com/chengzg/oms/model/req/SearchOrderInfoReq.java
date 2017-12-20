package com.chengzg.oms.model.req;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SearchOrderInfoReq {

    private String orderYear;

    private String orderCode;

    private String orderStatus;

    private Integer pageNum;

    private Integer pageSize;

    private Integer start;

    private Integer rows;
}