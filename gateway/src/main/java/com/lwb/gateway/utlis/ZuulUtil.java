package com.lwb.gateway.utlis;

import cn.hutool.core.util.ReflectUtil;
import com.lwb.gateway.constant.YxpGatewayConstants;
import com.lwb.gateway.model.ResponseTypeData;
import com.netflix.zuul.context.RequestContext;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.springframework.cloud.netflix.ribbon.RibbonHttpResponse;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @Author wangGang
 * @Description //TODO
 * @Date 2019-04-18 10:04
 **/
public class ZuulUtil {
        private  static  String[] txtContentTypes = new String[]{"json","text","plain","xml","html"};
        /**
         * 为空的属性不设置
         * @param request
         * @param statusCode
         * @param errorMsg
         * @param throwable
         */
        public static void setErrorMsg(HttpServletRequest request, Integer statusCode, String errorMsg, Throwable throwable){
            if(statusCode != null) {
                request.setAttribute(YxpGatewayConstants.ZuulRequestErrorKeys.statusCode, statusCode);
            }
            if(errorMsg != null) {
                request.setAttribute(YxpGatewayConstants.ZuulRequestErrorKeys.message, errorMsg);
            }
            if(throwable != null) {
                request.setAttribute(YxpGatewayConstants.ZuulRequestErrorKeys.exception, throwable);
            }
        }

        public static Integer getStatusCode(HttpServletRequest request){
            return (Integer) request.getAttribute(YxpGatewayConstants.ZuulRequestErrorKeys.statusCode);
        }
        public static String getErrorMsg(HttpServletRequest request){
            return (String) request.getAttribute(YxpGatewayConstants.ZuulRequestErrorKeys.message);
        }
        public static Throwable getException(HttpServletRequest request){
            return (Throwable) request.getAttribute(YxpGatewayConstants.ZuulRequestErrorKeys.exception);
        }

        public static  Throwable getThrowable(RequestContext ctx){
            Throwable throwable = RequestContext.getCurrentContext().getThrowable();
            if(throwable == null) {
                throwable = (Throwable) ctx.get("failed.filter");
            }
            if(throwable == null){
                throwable = ZuulUtil.getException(ctx.getRequest());
            }
            return throwable;
        }
        /**
         * 判断contentType的类型是否是文本
         * @param contentType
         * @return
         */
        public static boolean isTextContentType(String contentType){
            if(StringUtils.isEmpty(contentType)){
                return false;
            }
            String typeRe = Arrays.stream(txtContentTypes).filter(it -> contentType.indexOf(it) >= 0).findAny().orElse(null);
            if (typeRe != null) {
                return true;
            }
            return false;
        }

        /**
         * 是否是一个文本响应
         * @param ctx
         * @return
         */
        public static ResponseTypeData getResponseTypeData(RequestContext ctx){
            boolean isText = false;
            String unKnowContentType = "unKnowContentType";
            String contentType = unKnowContentType;
            Object resp = ctx.get("zuulResponse");
            if (resp != null) {
                if (resp instanceof RibbonHttpResponse) {
                    MediaType mediaType = ((RibbonHttpResponse) ctx.get("zuulResponse")).getHeaders().getContentType();
                    isText = mediaType != null && isTextContentType(mediaType.getSubtype());
                    if(mediaType != null) {
                        contentType = mediaType.toString();
                    }
                } else {
                    HttpResponse httpResponse = (HttpResponse) ReflectUtil.getFieldValue(resp, "original");
                    if (httpResponse != null) {
                        Header zuulResponseContentType = httpResponse.getFirstHeader("Content-Type");
                        if (null != zuulResponseContentType) {
                            String type = zuulResponseContentType.getValue();
                            isText = isTextContentType(type);
                            contentType = zuulResponseContentType.getValue();
                        }
                    }
                }
            }
            return new ResponseTypeData(isText,contentType);
        }

        /**
         * 直接转发到内部链接，不转发给后端服务器
         * @param ctx
         * @param url
         * @throws ServletException
         * @throws IOException
         */
        public static void dispatcher(RequestContext ctx, String url) throws ServletException, IOException {
            HttpServletRequest request = ctx.getRequest();
            ctx.setSendZuulResponse(false);
            HttpServletResponse response = ctx.getResponse();
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            if (dispatcher != null) {
                //ctx.set(SEND_ERROR_FILTER_RAN, true);
                if (!ctx.getResponse().isCommitted()) {
                    //ctx.setResponseStatusCode(exception.nStatusCode);
                    dispatcher.forward(request, ctx.getResponse());
                }
            }
        }



}
