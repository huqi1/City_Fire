package com.hq.modules.operate.entity;

import java.io.Serializable;

public class LocationStatistics implements Serializable {

    private String district;

    private String num;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
