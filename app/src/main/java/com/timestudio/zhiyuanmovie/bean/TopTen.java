package com.timestudio.zhiyuanmovie.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by strongShen on 2017/4/28.
 */

public class TopTen extends BmobObject {

    private String title;
    private String source;
    private String webUrl;
    private BmobFile photo1;
    private BmobFile photo2;
    private BmobFile photo3;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public BmobFile getPhoto1() {
        return photo1;
    }

    public void setPhoto1(BmobFile photo1) {
        this.photo1 = photo1;
    }

    public BmobFile getPhoto2() {
        return photo2;
    }

    public void setPhoto2(BmobFile photo2) {
        this.photo2 = photo2;
    }

    public BmobFile getPhoto3() {
        return photo3;
    }

    public void setPhoto3(BmobFile photo3) {
        this.photo3 = photo3;
    }
}
