package com.cars.config.bean;

import com.cars.config.aop.ControllerAspect;
import com.cars.config.aop.DaoExceptionAspect;
import com.cars.config.aop.ServiceExceptionAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**配置
 * Created by liuyanchao
 * on 2018/8/17.
 */
@Configuration
public class HdzgsConfig {
    /**
     * 拦截service异常
     * @return
     */
    @Bean
    public ServiceExceptionAspect serviceExceptionAspect(){
        return new ServiceExceptionAspect();
    }

    /**
     * 拦截dao异常
     * @return
     */
    @Bean
    public DaoExceptionAspect daoExceptionAspect(){
        return new DaoExceptionAspect();
    }

    /**
     * 拦截controller
     * @return
     */
    @Bean
    public ControllerAspect controllerAspect(){
        return new ControllerAspect();
    }

}
