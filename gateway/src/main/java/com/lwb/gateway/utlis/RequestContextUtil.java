package com.lwb.gateway.utlis;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSON;
import com.lwb.gateway.model.RequestInfo;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.ribbon.RibbonHttpResponse;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author wg
 * 请求的上下文工具类
 */
public class RequestContextUtil {
    private static final Logger logger = LoggerFactory.getLogger(RequestContextUtil.class);

    private static final String NO_HOST = "no available host";

    public interface Keys{
        String routeHost = "routeHost" ;
        String requestURI = "requestURI" ;
        String proxy = "proxy";
        String zuulResponse = "zuulResponse";
        String retryable = "retryable";
    }

    /**
     *
     * @param requestContext
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getValue(RequestContext requestContext,String key){
        return (T) requestContext.get(key);
    }




    public static String getRequestProxyHost(RequestContext requestContext){
        if(!requestContext.sendZuulResponse()){
            return "zuul";
        }
        URL host = getValue(requestContext, RequestContextUtil.Keys.routeHost);
        String hostValue = NO_HOST;
        if(host != null){
            hostValue = host.getAuthority();
        }else {
            //如果没有直接配置url访问，而是微服务的方式，则尝试再次获取
            RibbonHttpResponse ribbonHttpResponse = getValue(requestContext, RequestContextUtil.Keys.zuulResponse);
            if(null != ribbonHttpResponse){
                try {
                    Object resp = ReflectUtil.getFieldValue(ribbonHttpResponse, "response");
                    URI respURI = (URI) ReflectUtil.getFieldValue(resp, "requestedURI");
                    hostValue = respURI.getAuthority();
                }catch (Exception e){
                    logger.info("get request server host fail",e);
                }
            }
        }
        return hostValue;
    }

    public static RequestInfo getRequestInfo(RequestContext requestContext, Throwable throwable,boolean withParams){
        try {
            if(requestContext.size() > 0) {
                RequestInfo requestInfo = new RequestInfo();
                String proxy = getValue(requestContext, RequestContextUtil.Keys.proxy);
                String host = getRequestProxyHost(requestContext);
                String url = getValue(requestContext, RequestContextUtil.Keys.requestURI);
                boolean outTime = false;
                java.net.SocketTimeoutException timeoutException = ThrowableUtils.getThrowable(throwable,java.net.SocketTimeoutException.class);
                if (timeoutException != null) {
                    outTime = true;
                }
                if(!requestContext.sendZuulResponse()){
                    proxy = "zuul";
                }
                HttpServletRequest request = requestContext.getRequest();
                if(withParams) {
                    requestInfo.setHeaders(getHeaders(request));
                    requestInfo.setParams(getParams(request));
                    requestInfo.setBody(getJSONBodyValue(request));
                }
                requestInfo.setOutTime(outTime);
                requestInfo.setProxy(proxy);
                requestInfo.setRequestURI(url);
                requestInfo.setRouteHost(host);
                requestInfo.setRetryable(getValue(requestContext, Keys.retryable));
                return requestInfo;
            }

        }catch (Throwable e){
            logger.error("加入监控信息错误:" + e);
        }
        return null;
    }

    public static String getHeaders(HttpServletRequest request){
        Map<String,String> headerMap = new java.util.HashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        if(enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            headerMap.put(name,request.getHeader(name));
        }
        return JSON.toJSONString(headerMap);
    }

    public static String getParams(HttpServletRequest request){
        return StringBuilderUtil.append("params:",JSON.toJSON(request.getParameterMap()),"\tbody:",getJSONBodyValue(request));
    }

    public static String getJSONBodyValue(HttpServletRequest request){
        if(request.getContentType() != null && request.getContentType().startsWith(MediaType.APPLICATION_JSON_VALUE)){
            String field = "contentData";
            try {
                byte[] body = (byte[]) ReflectUtil.getFieldValue(request, field);
                if(body == null){
                    return "";
                }
                String bodyStr = new String(body,"utf-8");
                return bodyStr;
            }catch (Exception e){
                logger.error("解析json请求体错误",e);
            }
        }
        return "";
    }
}
