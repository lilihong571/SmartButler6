package com.llh.smartbutler.entity;

/**
 * 项目名:    SmartButler5
 * 包名:      com.llh.smartbutler.entity
 * 文件名:    CourierData
 * 创建者:    LLH
 * 创建时间:  2019/8/18 16:33
 * 描述:      TODO
 */
public class CourierData {
    //时间，
    private String datetime;
    //状态
    private String remark;
    //城市
    private String zone;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
    //重写toString方法，打印我们的信息的
    //可有可无
    @Override
    public String toString() {
        return "CourierData{" +
                "datetime='" + datetime + '\'' +
                ", remark='" + remark + '\'' +
                ", zone='" + zone + '\'' +
                '}';
    }
}
