package com.cars.model;

/**短信发送内容
 * Created by liuyanchao
 * on 2018/9/7.
 */
public class SmRequestDto {
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 短信内容
     */
    private String text;
    /**
     * 发送类型
     */
    private Integer sendType;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    @Override
    public String toString() {
        return "SmRequestDto{" +
                "mobile='" + mobile + '\'' +
                ", text='" + text + '\'' +
                ", sendType=" + sendType +
                '}';
    }
}
