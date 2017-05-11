package com.timestudio.zhiyuanmovie.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by strongShen on 2017/5/7.
 */

public class Seat extends BmobObject implements Serializable{

    private String seatName;
    private boolean isSold;


    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }
}
