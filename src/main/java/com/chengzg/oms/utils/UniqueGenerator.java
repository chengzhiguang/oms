package com.chengzg.oms.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xiaoyg
 * @Title: UniqueGenerator
 * @ProjectName scene
 * @Description: 唯一标识生成器
 * @date 2018/9/414:08
 */

public class UniqueGenerator {

    private static Logger logger = LoggerFactory.getLogger(Utility.class);

    private static final int ORDER_DEFAULT_LENGTH = 15;

    public static String order() {
        return order(ORDER_DEFAULT_LENGTH);
    }

    //获取一个指定长度的订单号
    private static String order(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }


    /**
     * created by Lemazing
     * 生成场景模板id
     */
    public static String getSceneModel(String type) {
        try {
            String sign = UniqueGenerator.order();
            return type.toUpperCase() +"_"+ sign.toUpperCase();
        } catch (Exception e) {
            logger.info("getOnlySign 异常" + e.getMessage());
        }
        return null;
    }

    /**
     * 获取一个场景唯一标识
     *
     * @return
     */
    public static String getRScene() {
        try {
            String sign = UniqueGenerator.order();
            logger.info("getOnlySign 得到的标示：" + sign.toUpperCase());
            return "RSCENE_" + sign.toUpperCase();
        } catch (Exception e) {
            logger.info("getOnlySign 异常" + e.getMessage());
        }
        return null;
    }

    /**
     * 获取一个场景环节唯一标识
     *
     * @return
     */
    public static String getLScene() {
        try {
            String sign = UniqueGenerator.order();
            logger.info("getOnlySign 得到的标示：" + sign.toUpperCase());
            return "RLink_" + sign.toUpperCase();
        } catch (Exception e) {
            logger.info("getOnlySign 异常" + e.getMessage());
        }
        return null;
    }

    /**
     * 获取一个场景动作唯一标识
     *
     * @return
     */
    public static String getLAction() {
        try {
            String sign = UniqueGenerator.order();
            logger.info("getOnlySign 得到的标示：" + sign.toUpperCase());
            return "RAction_" + sign.toUpperCase();
        } catch (Exception e) {
            logger.info("getOnlySign 异常" + e.getMessage());
        }
        return null;
    }

    /**
     * 获取一个场景分时唯一标识
     *
     * @return
     */
    public static String getLTimeAction() {
        try {
            String sign = UniqueGenerator.order();
            logger.info("getOnlySign 得到的标示：" + sign.toUpperCase());
            return "RTimeAction_" + sign.toUpperCase();
        } catch (Exception e) {
            logger.info("getOnlySign 异常" + e.getMessage());
        }
        return null;
    }


    /**
     * 获取一个场景分时唯一标识
     *
     * @return
     */
    public static String getOrderId(int length) {
        try {
            String sign = UniqueGenerator.order(length);
            logger.info("getOnlySign 得到的标示：" + sign.toUpperCase());
            return sign;
        } catch (Exception e) {
            logger.info("getOnlySign 异常" + e.getMessage());
        }
        return null;
    }


}
