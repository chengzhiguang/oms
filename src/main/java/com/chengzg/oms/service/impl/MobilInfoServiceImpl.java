package com.chengzg.oms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chengzg.oms.entity.MobilInfo;
import com.chengzg.oms.entity.UseRecord;
import com.chengzg.oms.enums.Networktor;
import com.chengzg.oms.exception.ServiceException;
import com.chengzg.oms.mapper.MobilInfoMapper;
import com.chengzg.oms.mapper.UseRecordMapper;
import com.chengzg.oms.service.MobilInfoService;
import com.chengzg.oms.utils.UniqueGenerator;
import com.chengzg.oms.utils.securitycode.SecurityCode;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class MobilInfoServiceImpl implements MobilInfoService {
    private static Logger logger = LoggerFactory.getLogger(MobilInfoServiceImpl.class);

    @Autowired
    private MobilInfoMapper mobilInfoMapper;

    @Autowired
    private UseRecordMapper useRecordMapper;

    @Override
    public JSONObject getMobilInfo() {
        MobilInfo mobilInfo = mobilInfoMapper.getOne();
        if (null == mobilInfo) {
            throw new ServiceException(100, "没有可用的信息");
        }

        Networktor networktor = Networktor.getRandomOne();

        JSONObject result = new JSONObject();
        result.put("imei", mobilInfo.getImei()+ SecurityCode.getSecurityCode(9, SecurityCode.SecurityCodeLevel.Simple,true));
        result.put("brand", mobilInfo.getBrand());
        result.put("model", mobilInfo.getModel());
        result.put("networktor", networktor.getCode());
        result.put("carrier", networktor.getName());
        result.put("name", UniqueGenerator.getOrderId(6));
        result.put("id", UniqueGenerator.getOrderId(6).toUpperCase());
        result.put("androidVer", ssss());
        result.put("phoneNumber", "138" + SecurityCode.getSecurityCode(8, SecurityCode.SecurityCodeLevel.Simple,true));
        result.put("lat", "30.2425140000");
        result.put("log", "120.1404220000");

        mobilInfo.setUseCount(mobilInfo.getUseCount() + 1);
        mobilInfoMapper.updateByPrimaryKeySelective(mobilInfo);
        UseRecord useRecord = new UseRecord();
        useRecord.setBrand(mobilInfo.getBrand());
        useRecord.setCreateTime(new Date());
        useRecord.setImei(mobilInfo.getImei());
        useRecord.setMobilUuid(mobilInfo.getUuid());
        useRecord.setModel(mobilInfo.getModel());
        useRecord.setResult(JSON.toJSONString(result));
        useRecordMapper.insert(useRecord);
        return result;
    }

    private String ssss() {
        StringBuffer sss = new StringBuffer();
        int radom = (int)(Math.random()*3)+6;
        sss.append(radom);
        sss.append(".1");
        return sss.toString();
    }
}
