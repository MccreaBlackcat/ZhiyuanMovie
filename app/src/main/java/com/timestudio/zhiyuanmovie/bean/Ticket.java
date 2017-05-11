package com.timestudio.zhiyuanmovie.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by strongShen on 2017/5/8.
 */

public class Ticket extends BmobObject {
    private String movieShowId;
    private String seatName;
    private int price;
    private int totalPrice;
    private int amount;
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMovieShowId() {
        return movieShowId;
    }

    public void setMovieShowId(String movieShowId) {
        this.movieShowId = movieShowId;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
