package com.chengzg.oms.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author dongh38@ziroom
 * @Date 2016/10/28
 * @Time 16:46
 * @Description
 * @Since 1.0.0
 */
public class IpUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpUtils.class);

    /**
     *  获取本机第一个可用IP
     * @return
     */
    public static String hostIP() {
        String hostAddress = null;
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface intf = en.nextElement();
                Enumeration<InetAddress> inetAddresses = intf.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress address = inetAddresses.nextElement();
                    if (!address.isLoopbackAddress() && !address.isLinkLocalAddress()
                            && address.isSiteLocalAddress()
                            && address.getHostAddress().indexOf("10") >= 0) {
                        hostAddress = address.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            LOGGER.error("寻找本机IP时出错",e);
        }
        return hostAddress;
    }

    /**
     *  判断当前IP是否是允许调用
     * @param key
     * @return
     */
    public static boolean isOnlineIP(String key) {
        boolean canUse = false;
        String hostIP = hostIP();
        LOGGER.info("当前IP为:{}",hostIP);
        //获取要轮询的ip
        String[] ips = ConfigLoader.getString(key, "").split(",");
        for (String ip : ips) {
            if (ip.equals(hostIP)) {
                canUse = true;
                break;
            }
        }
        if (!canUse) {
            LOGGER.info("您的ip地址:{} 不是系统配置的。无法进行支付回调轮询，如果想本地测试,请修改/resources/project.properties里的 [{}]键值",hostIP,key);
        }
        return canUse;
    }


    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
