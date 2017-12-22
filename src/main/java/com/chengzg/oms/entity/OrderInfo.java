package com.chengzg.oms.entity;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderInfo {
    private Long id;

    private String orderYear;

    private String orderCode;

    private String goodsCode;

    private String googsName;

    private Integer totalCount;

    private String payType;

    private Date orderTime;

    private String orderTimeStr;

    private Integer jdPrice;

    private Integer orderAmount;

    private Integer settmentMent;

    private Integer accountAmount;

    private Integer shouldAmount;

    private String orderStatus;

    private String billType;

    private String userCode;

    private String userName;

    private String userAddress;

    private String userPhone;

    private String orderRemark;

    private String invoiceType;

    private String invoiceTitle;

    private String invoiceContent;

    private String shopRemark;

    private Integer shopRemarkLevel;

    private Integer luggageAmount;

    private Date comfireTime;

    private String comfireTimeStr;

    private String vatInvoice;

    private String articleNum;

    private Date finishTime;

    private String finishTimeStr;

    private String orderSource;

    private String orderChannel;

    private String deliveryService;

    private Integer chargesAmount;

    private String storeCode;

    private String storeName;

    private String ordinaryInvoiceCode;

    private String shopSkuid;

    private Date createTime;

    private Integer isDel;

    private Date lastModifyTime;

    private String remark;
}