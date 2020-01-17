package com.lwb.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractMyZuulFilter extends ZuulFilter {

    public boolean isErrorPage(){
        RequestContext ctx = RequestContext.getCurrentContext();
        if(null == ctx){
            return false;
        }
        HttpServletRequest request = ctx.getRequest();
        return request != null && "/error".equals(request.getRequestURI());
    }
}