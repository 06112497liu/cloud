package com.lwb.gateway.model;

/**
 * 请求的url，网关等信息
 * @Author wangGang
 * @Description //TODO
 * @Date 2019-04-20 18:03
 **/
public class RequestInfo {
    private Boolean retryable;
    private String headers;
    private String params;
    private String body;
    private String  routeHost;
    private String requestURI;
    private String proxy;
    private boolean outTime;

    public Boolean getRetryable() {
        return retryable;
    }

    public RequestInfo setRetryable(Boolean retryable) {
        this.retryable = retryable;
        return this;
    }

    public String getRouteHost() {
        return routeHost;
    }

    public RequestInfo setRouteHost(String routeHost) {
        this.routeHost = routeHost;
        return this;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public RequestInfo setRequestURI(String requestURI) {
        this.requestURI = requestURI;
        return this;
    }

    public String getProxy() {
        return proxy;
    }

    public RequestInfo setProxy(String proxy) {
        this.proxy = proxy;
        return this;
    }

    public String getHeaders() {
        return headers;
    }

    public RequestInfo setHeaders(String headers) {
        this.headers = headers;
        return this;
    }

    public String getParams() {
        return params;
    }

    public RequestInfo setParams(String params) {
        this.params = params;
        return this;
    }

    public String getBody() {
        return body;
    }

    public RequestInfo setBody(String body) {
        this.body = body;
        return this;
    }

    public boolean isOutTime() {
        return outTime;
    }

    public RequestInfo setOutTime(boolean outTime) {
        this.outTime = outTime;
        return this;
    }
}
