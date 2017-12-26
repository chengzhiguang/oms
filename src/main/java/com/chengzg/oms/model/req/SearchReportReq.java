package com.chengzg.oms.model.req;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SearchReportReq {

    private String orderYear;

    private String goodsCode;

    private Integer pageNum;

    private Integer pageSize;

    private Integer start;

    private Integer rows;
}