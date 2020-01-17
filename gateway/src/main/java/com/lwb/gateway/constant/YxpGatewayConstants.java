package com.lwb.gateway.constant;

/**
 * @author wg
 * @ 网关相关的常量存放
 */
public interface YxpGatewayConstants {
    Long ADMIN_USER_ID = 1L;

    /**
     * zuul 过滤器中的常量
     */
    interface ZuulFileterKeys {
        String startRequestTime = "startRequestTime";
        long maxFileUploadTime = 30000;

        long errorApiUseTime = 2500;
        long warnApiUseTime = 1500;
        long infoApiUseTime = 1000;
    }

    /**
     * zuul请求Request Error 下的key
     */
    interface ZuulRequestErrorKeys{
        String statusCode = "javax.servlet.error.status_code";
        String message = "javax.servlet.error.message";
        String exception = "javax.servlet.error.exception";

    }

}
