package com.cars.model;

/**货调落成日期
 * Created by liuyanchao
 * on 2018/8/14.
 */
public class HdLcrqModel {
    private String ljdm;//路局代码
    private String lcrq;//落成日期

    public String getLjdm() {
        return ljdm;
    }

    public void setLjdm(String ljdm) {
        this.ljdm = ljdm;
    }

    public String getLcrq() {
        return lcrq;
    }

    public void setLcrq(String lcrq) {
        this.lcrq = lcrq;
    }

    @Override
    public String toString() {
        return "HdLcrqModel{" +
                "ljdm='" + ljdm + '\'' +
                ", lcrq='" + lcrq + '\'' +
                '}';
    }
}
