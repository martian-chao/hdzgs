package com.cars.service;

import com.cars.dao.HdZgsHealthDao;
import com.cars.dao.SysSmsSendDao;
import com.cars.model.HdJkjcModel;
import com.cars.model.SmRequestDto;
import com.cars.model.SysSmsSend;
import com.cars.util.http.HttpUtil;
import com.cars.util.json.JsonResult;
import com.cars.util.json.JsonUtil;
import com.cars.util.log.LogUtil;
import com.cars.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private SysSmsSendDao sysSmsSendDao;
    @Value("${smsUrl}")
    private String smsUrl;
    private static int flag=0;

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
                    flag=0;//重置为0
                    LogUtil.infoHttp("心跳检查路局："+hdJkjcModel.getLjmc()+",正常！");
                    //根据路局代码更新监控表
                    hdJkjcModel.setFlag("1");
                    hdZgsHealthDao.updateByLjdm(hdJkjcModel);
                    //入健康检查历史表
                    hdZgsHealthDao.addJkjcLs(hdJkjcModel);
                }else {//不通flag置为0
                    flag++;
                    LogUtil.infoHttp("心跳检查路局："+hdJkjcModel.getLjmc()+",异常！");
                    hdJkjcModel.setFlag("0");
                    hdZgsHealthDao.updateByLjdm(hdJkjcModel);
                    //入健康检查历史表
                    hdZgsHealthDao.addJkjcLs(hdJkjcModel);
                    if (flag<4){//前三次发送短信
                        //发送短信
                        smsSend(hdJkjcModel);

                    }
                }
            }
        }
    }

    /**
     * 发送短信方法
     * @param hdJkjcModel
     */
    public void smsSend(HdJkjcModel hdJkjcModel){
        {//文本不为空发送短信
            //短信内容
            String smContent=hdJkjcModel.getLjmc()+"货调数据上报系统发生异常，请尽快修复";
            //获取联系电话
            if (StringUtil.isNotNullOrEmpty(hdJkjcModel.getMobile()) && StringUtil.isNotNullOrEmpty(hdJkjcModel.getName())) {
                //先将记录入库，发送成功后，根据结果更新状态
                SmRequestDto smRequestDto = new SmRequestDto();
                smRequestDto.setMobile(hdJkjcModel.getMobile());
                smRequestDto.setText(smContent);
                smRequestDto.setSendType(2);
                //短信记录
                SysSmsSend sysSmsSend = new SysSmsSend();
                sysSmsSend.setSendMobile(hdJkjcModel.getMobile());
                sysSmsSend.setSendName(hdJkjcModel.getName());
                sysSmsSend.setSendContent(smContent);
                sysSmsSend.setSendLjdm(hdJkjcModel.getLjdm());
                sysSmsSend.setSendLjmc(hdJkjcModel.getLjmc());
                sysSmsSend.setSendStatus(0);
                try {
                    Integer id = sysSmsSendDao.addSysSmsSend(sysSmsSend);
                    String result = HttpUtil.postJson(smsUrl, JsonUtil.toStr(smRequestDto));
                    if (StringUtil.isNotNullOrEmpty(result)) {
                        JsonResult jsonResult = JsonUtil.toObject(result, JsonResult.class);
                        if (jsonResult.isSuccess()) {
                            sysSmsSendDao.updateStatus(id);
                        }
                    }
                } catch (Exception e) {
                    LogUtil.error(hdJkjcModel.getName() + "-" + hdJkjcModel.getMobile() + " 短信发送异常");
                }
            }
        }
    }

}
