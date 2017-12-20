package com.chengzg.oms.repository.impl;

import com.chengzg.oms.entity.OrderInfo;
import com.chengzg.oms.mapper.OrderInfoMapper;
import com.chengzg.oms.repository.OrderInfoResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by chengzg3 on 2017/12/19.
 */
@Component
@Transactional
public class OrderInfoRespImpl implements OrderInfoResp {
    private static Logger logger = LoggerFactory.getLogger(OrderInfoRespImpl.class);

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return orderInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(OrderInfo record) {
        return orderInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(OrderInfo record) {
        return orderInfoMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(OrderInfo record) {
        return orderInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OrderInfo record) {
        return orderInfoMapper.updateByPrimaryKey(record);
    }
}
