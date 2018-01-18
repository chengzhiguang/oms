package com.chengzg.oms.model;

import lombok.*;

/**
 * Created by chengzg3 on 2018/1/15.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GoodsModel {
    private String goodsCode;
    private String goodsName;
}
