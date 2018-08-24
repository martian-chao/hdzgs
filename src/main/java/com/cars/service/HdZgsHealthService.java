package com.cars.service;

import com.cars.dao.HdZgsHealthDao;
import com.cars.model.HdJkjcModel;
import com.cars.util.http.HttpUtil;
import com.cars.util.log.LogUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**货调总公司 健康检查
 * Created by liuyanchao
 * on 2018/8/17.
 */
@Service
public class HdZgsHealthService {
    @Value("#{hdZgsHealthDao}")
    private HdZgsHealthDao hdZgsHealthDao;

    /**
     * 发送健康检查请求并更新健康
     * 10分钟进行心跳监控一次
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void hdzgshealth(){
        //获取每个路局的地址
        List<HdJkjcModel> list = hdZgsHealthDao.listLjJkjc();
        LogUtil.infoHttp("开始心跳检查.....");
        if (list!=null && list.size()>0){
            for (HdJkjcModel hdJkjcModel : list) {
                String ljdm = HttpUtil.httpGet(hdJkjcModel.getLjurl());
                if (ljdm!=null && !ljdm.equals("")){
                    LogUtil.infoHttp("心跳检查路局："+hdJkjcModel.getLjmc()+",正常！");
                    //根据路局代码更新监控表
                    hdJkjcModel.setFlag("1");
                    hdZgsHealthDao.updateByLjdm(hdJkjcModel);
                }else {//不通flag置为0
                    LogUtil.infoHttp("心跳检查路局："+hdJkjcModel.getLjmc()+",异常！");
                    hdJkjcModel.setFlag("0");
                    hdZgsHealthDao.updateByLjdm(hdJkjcModel);
                }
            }
        }
    }

}
