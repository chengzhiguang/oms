package com.chengzg.oms.model.req;

import lombok.*;

/**
 * Created by chengzg3 on 2017/12/25.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SearchSkuInfoReq {
    private String skuName;

    private Integer pageNum;

    private Integer pageSize;

    private Integer start;

    private Integer rows;
}