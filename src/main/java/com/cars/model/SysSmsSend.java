package com.cars.model;

import java.util.Date;

/**发送短信记录
 * Created by liuyanchao
 * on 2018/9/7.
 */
public class SysSmsSend {
    private String id;// 序号
    private String sendName;//收信人姓名
    private String sendMobile;//收信人手机号
    private String sendLjdm;//收信路局代码 拼音码
    private String sendLjmc;//收信路局代码 名称
    private Date sendDate;//发送时间
    private String sendContent;//发送内容
    private Integer sendStatus;//发送状态 0失败 1 成功

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getSendMobile() {
        return sendMobile;
    }

    public void setSendMobile(String sendMobile) {
        this.sendMobile = sendMobile;
    }

    public String getSendLjdm() {
        return sendLjdm;
    }

    public void setSendLjdm(String sendLjdm) {
        this.sendLjdm = sendLjdm;
    }

    public String getSendLjmc() {
        return sendLjmc;
    }

    public void setSendLjmc(String sendLjmc) {
        this.sendLjmc = sendLjmc;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    @Override
    public String toString() {
        return "SysSmsSend{" +
                "id='" + id + '\'' +
                ", sendName='" + sendName + '\'' +
                ", sendMobile='" + sendMobile + '\'' +
                ", sendLjdm='" + sendLjdm + '\'' +
                ", sendLjmc='" + sendLjmc + '\'' +
                ", sendDate=" + sendDate +
                ", sendContent='" + sendContent + '\'' +
                ", sendStatus=" + sendStatus +
                '}';
    }
}
