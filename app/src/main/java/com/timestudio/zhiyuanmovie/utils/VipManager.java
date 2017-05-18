package com.timestudio.zhiyuanmovie.utils;

import android.util.Log;

/**
 * Created by strongShen on 2017/5/15.
 */

public class VipManager  {

    private  String VIP_1 = "V1 黄铜会员";
    private  String VIP_2 = "V2 白银会员";
    private  String VIP_3 = "V3 黄金会员";
    private  String VIP_4 = "V4 白金会员";
    private  String VIP_5 = "V5 钻石会员";

    private static VipManager vipManager;

    private VipManager() {
    }

    public static VipManager getInstance(){
        if (vipManager == null) {
            vipManager = new VipManager();
        }
        return vipManager;
    }

    /**
     * 根据会员积分，获取到会员名称
     * */
    public String getVipName(int i) {
        String str = "";
        if (100 > i && i >= 0) {
            str = VIP_1;
        } else if (200 > i && i >= 100) {
            str = VIP_2;
        }else if (500 > i && i >= 200) {
            str = VIP_3;
        }else if (1000 > i && i >= 500) {
            str = VIP_4;
        }else if (2000 > i && i >= 1000) {
            str = VIP_5;
        }
        return str;
    }
    /**
     * 根据会员积分，获取到会员打折力度
     * */
    public double getDiscount(int i) {
        double discount = 1;
        if (100 > i && i >= 0) {
            discount = 1;
        } else if (200 > i && i >= 100) {
            discount = 0.98;
        }else if (500 > i && i >= 200) {
            discount = 0.95;
        }else if (1000 > i && i >= 500) {
            discount = 0.9;
        }else if (2000 > i && i >= 1000) {
            discount = 0.88;
        }
        return discount;
    }

    public String getVip1() {
        return VIP_1;
    }

    public String getVip2() {
        return VIP_2;
    }

    public String getVip3() {
        return VIP_3;
    }

    public String getVip4() {
        return VIP_4;
    }

    public String getVip5() {
        return VIP_5;
    }
}
