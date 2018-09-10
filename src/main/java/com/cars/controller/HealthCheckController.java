package com.cars.controller;

import com.cars.service.HdZgsHealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**健康检查-总公司端的心跳监控功能
 * Created by liuyanchao
 * on 2018/8/17.
 */
@RestController
public class HealthCheckController {
    @Autowired
    private HdZgsHealthService hdZgsHealthService;

    /**
     * 健康检查请求
     * @param response
     */
    @RequestMapping("/healthCheck")
    public void healthCheck(HttpServletResponse response){
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(hdZgsHealthService.getDete());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
