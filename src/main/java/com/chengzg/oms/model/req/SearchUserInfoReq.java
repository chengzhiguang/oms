package com.chengzg.oms.model.req;

import lombok.*;

/**
 * Created by chengzg3 on 2018/5/10.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SearchUserInfoReq {
    private String userCode;

    private String userName;

    private String userType;

    private Integer pageNum;

    private Integer pageSize;

    private Integer start;

    private Integer rows;
}
