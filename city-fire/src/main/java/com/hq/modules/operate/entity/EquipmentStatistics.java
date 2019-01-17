package com.hq.modules.operate.entity;

import java.io.Serializable;

public class EquipmentStatistics implements Serializable {
    //小区
    private String community;

    //时间
    private String date;

    //新增设备数量
    private String addEqunum;

    //设备总数量
    private  String sumEqunum;

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddEqunum() {
        return addEqunum;
    }

    public void setAddEqunum(String addEqunum) {
        this.addEqunum = addEqunum;
    }

    public String getSumEqunum() {
        return sumEqunum;
    }

    public void setSumEqunum(String sumEqunum) {
        this.sumEqunum = sumEqunum;
    }
}
