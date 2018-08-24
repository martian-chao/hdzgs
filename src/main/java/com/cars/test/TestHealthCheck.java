package com.cars.test;

import com.cars.util.http.HttpUtil;
import org.junit.Test;

/**
 * Created by liuyanchao
 * on 2018/8/17.
 */
public class TestHealthCheck {
    @Test
    public void testHealthCheck(){
        int num =0;
        String str = HttpUtil.httpGet("http://localhost:8089/hdlj/healthCheck");
        if (str!=null){
//            num = DateUtil.dateRegex(str);
        }
        System.out.println("返回值："+str+","+num);

    }
}
