package com.timestudio.zhiyuanmovie.bean;


import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by strongShen on 2017/4/20.
 * 用户类
 */

public class MyUser extends BmobUser {

    private int vipIntegral; //会员积分
    private BmobFile photo; //用户头像


    public BmobFile getPhoto() {
        return photo;
    }

    public void setPhoto(BmobFile photo) {
        this.photo = photo;
    }

    public int getVipIntegral() {
        return vipIntegral;
    }

    public void setVipIntegral(int vipIntegral) {
        this.vipIntegral = vipIntegral;
    }
}
