package com.cars.controller;

import com.cars.util.log.LogUtil;
import com.cars.util.string.StringUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**获取请求的ip
 * Created by liuyanchao
 * on 2018/8/24.
 */
@RestController
public class IPController {
    @RequestMapping("/getIp")
    @Async
    public String getIp(HttpServletRequest request){
        String ip = StringUtil.getIp(request);
        LogUtil.infoHttp("请求地址ip为："+ip);
        return ip;
    }

}
