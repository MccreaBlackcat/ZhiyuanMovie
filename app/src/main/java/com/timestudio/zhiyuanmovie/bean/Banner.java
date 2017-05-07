package com.timestudio.zhiyuanmovie.bean;


import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by strongShen on 2017/5/3.
 */

public class Banner extends BmobFile {

    private String movieName;
    private BmobFile photo;

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public BmobFile getPhoto() {
        return photo;
    }

    public void setPhoto(BmobFile photo) {
        this.photo = photo;
    }
}
