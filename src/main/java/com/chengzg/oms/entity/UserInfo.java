package com.chengzg.oms.entity;

import lombok.*;

import java.util.Date;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserInfo {
    private Long id;

    private String userCode;

    private String userName;

    private String userPwd;

    private String userType;

    private Integer isDel;

    private Date createTime;

    private Date lastModifyTime;
}