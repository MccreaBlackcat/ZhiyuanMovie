package com.timestudio.zhiyuanmovie.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by strongShen on 2017/5/6.
 */

public class MovieShow extends BmobObject {
    private String movieName;
    private String language;
    private String showTime;
    private String endTime;
    private String showDate;
    private String showHall;
    private int price;

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getShowHall() {
        return showHall;
    }

    public void setShowHall(String showHall) {
        this.showHall = showHall;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
