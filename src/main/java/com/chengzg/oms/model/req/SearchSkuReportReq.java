package com.chengzg.oms.model.req;

import lombok.*;

import java.util.Date;

/**
 * Created by chengzg3 on 2017/12/25.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SearchSkuReportReq {
    private String code;

    private Date date;

    private Integer pageNum;

    private Integer pageSize;

    private Integer start;

    private Integer rows;
}
