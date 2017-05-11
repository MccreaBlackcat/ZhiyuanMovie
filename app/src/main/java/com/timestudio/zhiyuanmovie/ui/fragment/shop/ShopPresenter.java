package com.timestudio.zhiyuanmovie.ui.fragment.shop;

import android.support.annotation.NonNull;
import android.util.Log;

import com.timestudio.zhiyuanmovie.base.BasePresenter;
import com.timestudio.zhiyuanmovie.bean.Order;
import com.timestudio.zhiyuanmovie.bean.Shop;
import com.timestudio.zhiyuanmovie.bean.ShopOrder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by strongShen on 2017/4/24.
 */

public class ShopPresenter extends BasePresenter<ShopView> {

    private List<Shop> list = new ArrayList<>();
    private String objectId;
    private String goodsName;
    private String discription;
    private int price;
    private String goodsPhoto;

    /**
     * 提交订单
     */

    public void submitTotal(int totalPrice,final List<ShopOrder> shopDatas,String userId,Shop shop) {
        //先创建一个总的订单，然后根据订单号，创建商品订单，保存订单内容
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderName(shopDatas.get(0).getShopName());
        order.setPhoto(shop.getGoodsPhoto());
        order.setOrderType("shop");
        order.setUsed(false);
        order.setPaid(false);
        order.setRefund(false);
        order.setComment(false);
        order.setTotalPrice(totalPrice);
        order.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    for (int i = 0; i < shopDatas.size(); i++) {
                        ShopOrder shopOrder = shopDatas.get(i);
                        shopOrder.setOrderId(objectId);
                        shopOrder.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                            }
                        });
                    }
                    //返回订单ID，待支付页面使用
                    getView().submitSuccess(objectId);
                    Log.i("bmob","订单保存成功");
                }else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    getView().submitFailure("保存订单失败");
                }
            }
        });
    }

    /**
     * 获取商品，从服务器获取到商品的数据
     * 索要查询的是整个表中的数据，返回的是 JSON 数据，需要自己手动解析
     */
    public void getGoods() {
        BmobQuery bmobQuery = new BmobQuery("shop");
        bmobQuery.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if (e == null) {
//                    Log.i("bmob","查询成功："+jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject object = jsonArray.getJSONObject(i);
                            objectId = object.getString("objectId");
                            goodsName = object.getString("goodsName");
                            discription = object.getString("discription");
                            price = object.getInt("price");
                            JSONObject photoobj = (JSONObject) object.get("goodsPhoto");
                            goodsPhoto =  photoobj.getString("url");
                            //直接使用imageLoader将图片加载出来
//                            Log.i("shop","查询成功："+objectId + "-" + goodsName + "-" + discription + "-" + price + "-" + goodsPhoto);
                            list.add(new Shop(objectId, goodsName, discription, price, goodsPhoto));
                            getView().getGoodsSuccess(list);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {
                    Log.i("bmob", "获取商品失败：" + e.getMessage() + ",code" + e.getErrorCode());
                }
            }
        });
    }


    @NonNull
    @Override
    protected ShopView getNullObject() {
        return null;
    }
}
