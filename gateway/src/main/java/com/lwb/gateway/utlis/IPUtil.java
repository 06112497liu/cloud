package com.lwb.gateway.utlis;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liuweibo
 * @date 2020/1/16
 */
public class IPUtil {

    private static Logger logger = LoggerFactory.getLogger(IPUtil.class);

    /**
     * 获取IP地址
     *
     * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            logger.error("IPUtil ERROR ", e);
        }

//        //使用代理，则获取第一个IP地址
//        if(StringUtils.isEmpty(ip) && ip.length() > 15) {
//			if(ip.indexOf(",") > 0) {
//				ip = ip.substring(0, ip.indexOf(","));
//			}
//		}

        return ip;
    }

    public static List<InetAddress> getLocalAddress(){
        List<InetAddress> addressList = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> inetAddressIterator = ni.getInetAddresses();
                while(inetAddressIterator.hasMoreElements()) {
                    addressList.add(inetAddressIterator.nextElement());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addressList;
    }

    /**
     *
     * @param exceptPhysicalAddress 是否排除物理地址
     * @return
     */
    public static List<String> getLocalIpV4Addresses(boolean exceptPhysicalAddress){
        List<InetAddress> addresses = getLocalAddress();
        return addresses.stream()
            .map(it -> it.getHostAddress())
            .filter(it -> !exceptPhysicalAddress || RegexpUtil.isMathAll(RegexpUtil.RegexpPattern.ipV4,it)).distinct().collect(Collectors.toList());
    }

    /**
     * 获取ipv4地址，不包含物理地址
     * @return
     */
    public static List<String> getLocalIpV4Addresses(){
        return getLocalIpV4Addresses(true);
    }
}
