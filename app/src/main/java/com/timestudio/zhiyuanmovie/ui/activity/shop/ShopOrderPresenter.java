package com.timestudio.zhiyuanmovie.ui.activity.shop;

import android.support.annotation.NonNull;
import android.util.Log;

import com.timestudio.zhiyuanmovie.base.BasePresenter;
import com.timestudio.zhiyuanmovie.bean.Order;
import com.timestudio.zhiyuanmovie.bean.ShopOrder;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by strongShen on 2017/4/26.
 */

public class ShopOrderPresenter extends BasePresenter<ShopOrderView> {



    /**
     * 支付
     * */
    public void onOrderPay(String orderId,Order order) {
        order.setPaid(true);
        order.update(orderId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    getView().onPaidSuccess();
                    Log.i("bmob", "支付成功!");
                } else {
                    Log.i("bmob","支付失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

    }

    /**获取订单信息*/
    public void getOrderData(String orderId) {
        Log.i("bmob","传递过来的订单objectId：" + orderId);
        BmobQuery<Order> bmobQuery = new BmobQuery<Order>();
        bmobQuery.getObject(orderId, new QueryListener<Order>() {
            @Override
            public void done(Order order, BmobException e) {
                if (e == null) {
                    getView().getOrderSuccess(order);
                } else {
                    Log.i("bmob","获取订单失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
    /**获取订单详细信息*/
    public void getOrderDetails(String orderId) {
        BmobQuery<ShopOrder> query = new BmobQuery<>();
        query.addWhereEqualTo("orderId", orderId);
        query.findObjects(new FindListener<ShopOrder>() {
            @Override
            public void done(List<ShopOrder> list, BmobException e) {
                if (e == null) {
                    getView().getShopOrderSuccess(list);
                } else {
                    Log.i("bmob","获取订单详细信息失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    @NonNull
    @Override
    protected ShopOrderView getNullObject() {
        return null;
    }
}
