package com.cars.config.aop;


import com.cars.util.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 拦截异常
 *
 */
@org.springframework.web.bind.annotation.ControllerAdvice
@ResponseBody
public class ControllerAdvice extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger("controllerLog");

    /**
     * @return request中所有header
     */
    private String getHeaderValue(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaderNames();
        StringBuilder stringBuilder = new StringBuilder();
        if (headers.hasMoreElements()) {
            String headerName = headers.nextElement();
            stringBuilder.append(headerName).append('=').append(request.getHeader(headerName)).append('&');
        }
        return stringBuilder.toString();
    }

    /**
     * 记录日志
     */
    private void logException(HttpServletRequest request) throws IOException {
        logger.error("请求路径：" + request.getServletPath());
        logger.error("请求参数：" + request.getParameterMap().toString());
        logger.error("请求header：" + getHeaderValue(request));
        logger.error("请求body：" + StringUtil.getBodyString(request.getReader()));
    }

}
