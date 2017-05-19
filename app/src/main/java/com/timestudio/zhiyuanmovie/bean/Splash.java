package com.timestudio.zhiyuanmovie.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by strongShen on 2017/5/19.
 */

public class Splash extends BmobObject {
    private BmobFile splashPhoto;

    public BmobFile getSplashPhoto() {
        return splashPhoto;
    }

    public void setSplashPhoto(BmobFile splashPhoto) {
        this.splashPhoto = splashPhoto;
    }
}
