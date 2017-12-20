package com.chengzg.oms.repository;

import com.chengzg.oms.entity.OrderInfo;

/**
 * Created by chengzg3 on 2017/12/19.
 */
public interface OrderInfoResp {
    int deleteByPrimaryKey(Long id);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);
}
