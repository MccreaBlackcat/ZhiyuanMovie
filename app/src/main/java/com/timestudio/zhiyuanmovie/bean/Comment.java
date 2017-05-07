package com.timestudio.zhiyuanmovie.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by strongShen on 2017/5/4.
 * 评论类
 */

public class Comment extends BmobObject{

    private String userName;
    private String movieName;
    private String content; //评论内容
    private String photoUrl; //用户头像Url
    private String userId; //用户ID

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
