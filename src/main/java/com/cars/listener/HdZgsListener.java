package com.cars.listener;

import com.cars.model.HdLcrqModel;
import com.cars.model.HdModel;
import com.cars.service.HdZgsService;
import com.cars.util.date.DateUtil;
import com.cars.util.json.JsonUtil;
import com.cars.util.log.LogUtil;
import com.cars.util.queue.QueueName;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 货调mq数据接收端
 * Created by liuyanchao
 * on 2018/8/13.
 */
@Component
public class HdZgsListener {
    @Value("#{hdZgsService}")
    private HdZgsService hdZgsService;
    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 入库-18路局货调数据
     *
     * @param str
     */
    @RabbitListener(queues = QueueName.HD_HDHZ_QUEUE)
    @RabbitHandler
    public void saveHd(String str) {
//        String str = null;
        try {
//            str = new String(message, "UTF-8");
            JavaType javaType = JsonUtil.getCollectionType(ArrayList.class, HdModel.class);
            List<HdModel> list = (List<HdModel>) objectMapper.readValue(str, javaType);
            if (list!=null && list.size()>0){
                LogUtil.infoHttp("路局:"+list.get(0).getLjdm()+",汇总数据入库开始时间："+DateUtil.getSystemTime());
            }
            for (HdModel hdModel : list) {
                // zcrq，lwdw，sequence 三个字段不在入库表中插入
                boolean flag = hdZgsService.isExist(hdModel.getZcrq(), hdModel.getLwdw(), hdModel.getSeqnum());
                if (flag) {//存在更新
                    //系统的明天日期
                    String tomorrow = DateUtil.getOperaDate(DateUtil.getShortSystemDate(), 1, "yyyyMMdd");
                    int num = hdModel.getZcrq().compareTo(tomorrow);
                    if (num>0){//大于次日更新字段
                        hdZgsService.updateHdHz(hdModel);
                    }else {//小于等于次日更新字段
                        hdZgsService.updateHdHz2(hdModel);
                    }
                }else {//不存在插入
                    hdZgsService.saveHdHz(hdModel);
                }
            }
            if (list!=null && list.size()>0){
                LogUtil.infoHttp("路局:"+list.get(0).getLjdm()+",汇总数据入库结束时间："+DateUtil.getSystemTime());
            }else {
                LogUtil.infoHttp("汇总数据为空。");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error("货调数据汇总入库错误");
            LogUtil.error(str);
            LogUtil.error(e.getMessage());
        }
    }

    /**
     * 落成日期
     * @param message
     */
    @RabbitListener(queues = QueueName.HD_LCRQHZ_QUEUE)
    @RabbitHandler
    public void saveHdLcrqhz(byte[] message){
        String str = null;
        try {
            str= new String(message,"UTF-8");
            HdLcrqModel hdLcrqModel = JsonUtil.toObject(str, HdLcrqModel.class);
            if (hdLcrqModel!=null){
                LogUtil.infoHttp("路局:"+hdLcrqModel.getLjdm()+",落成日期数据入库开始时间："+DateUtil.getSystemTime());
                //ljdm和lcrq
                boolean flag = hdZgsService.isExistLcrq(hdLcrqModel.getLjdm(),hdLcrqModel.getLcrq());
                if (!flag){//不存在插入
                    hdZgsService.saveHdLcrq(hdLcrqModel);
                }
                LogUtil.infoHttp("路局:"+hdLcrqModel.getLjdm()+",落成日期数据入库结束时间："+DateUtil.getSystemTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error("货调落成日期入库错误");
            LogUtil.error(str);
            LogUtil.error(e.getMessage());
        }

    }

}
