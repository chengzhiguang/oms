package com.chengzg.oms.model;

import lombok.*;

/**
 * Created by chengzg3 on 2017/12/26.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderInfoStatistics {
    private String goodsCode;
    private Integer sumCount;
    private Integer sumAmount;
    private Integer sumLuggageAmount;
    private Integer sumSettmentAmount;
}
