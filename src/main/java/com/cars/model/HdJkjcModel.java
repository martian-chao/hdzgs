package com.cars.model;

/**健康检查model
 * Created by liuyanchao
 * on 2018/8/17.
 */
public class HdJkjcModel {
    private String ljdm;//路局代码
    private String ljmc;//路局名称
    private String ljurl;//路局地址
    private String flag;//标识 0异常，1正常
    private String name;//维护人姓名
    private String mobile;//维护人电话

    public String getLjdm() {
        return ljdm;
    }

    public void setLjdm(String ljdm) {
        this.ljdm = ljdm;
    }

    public String getLjmc() {
        return ljmc;
    }

    public void setLjmc(String ljmc) {
        this.ljmc = ljmc;
    }

    public String getLjurl() {
        return ljurl;
    }

    public void setLjurl(String ljurl) {
        this.ljurl = ljurl;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "HdJkjcModel{" +
                "ljdm='" + ljdm + '\'' +
                ", ljmc='" + ljmc + '\'' +
                ", ljurl='" + ljurl + '\'' +
                ", flag='" + flag + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
