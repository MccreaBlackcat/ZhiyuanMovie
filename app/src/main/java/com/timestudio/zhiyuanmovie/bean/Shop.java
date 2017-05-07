package com.timestudio.zhiyuanmovie.bean;


import cn.bmob.v3.BmobObject;

/**
 * Created by strongShen on 2017/4/24.
 * 商品类
 */

public class Shop extends BmobObject{


    private String objectId;
    private String goodsName;
    private String discription;
    private int price;
    private String goodsPhoto;

    public Shop(String objectId, String goodsName, String discription, int price, String goodsPhoto) {
        this.objectId = objectId;
        this.goodsName = goodsName;
        this.discription = discription;
        this.price = price;
        this.goodsPhoto = goodsPhoto;
        this.setTableName("shop");
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getGoodsPhoto() {
        return goodsPhoto;
    }

    public void setGoodsPhoto(String goodsPhoto) {
        this.goodsPhoto = goodsPhoto;
    }
}
