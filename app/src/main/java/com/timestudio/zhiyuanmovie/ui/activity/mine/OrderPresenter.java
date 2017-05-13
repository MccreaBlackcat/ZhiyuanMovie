package com.timestudio.zhiyuanmovie.ui.activity.mine;

import android.support.annotation.NonNull;
import android.util.Log;

import com.timestudio.zhiyuanmovie.base.BasePresenter;
import com.timestudio.zhiyuanmovie.bean.Order;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by strongShen on 2017/5/10.
 */

public class OrderPresenter extends BasePresenter<OrderView>{


    /**
     * 获取所有订单
     * */
    public void getAllOrderData() {
        BmobQuery<Order> query = new BmobQuery<Order>();
        query.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> list, BmobException e) {
                if (e == null) {
                    getView().getAllOrderSuccess(list);
                } else {
                    Log.i("bmob", "获取订单失败，msg：" + e.toString());
                }
            }
        });
    }
    /**
     * 获取订单,通过类型
     */
    public void getOrderOnType(String orderType) {
        BmobQuery<Order> query = new BmobQuery<Order>();
        query.addWhereEqualTo("",orderType);
        query.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> list, BmobException e) {
                if (e == null) {

                } else {
                    Log.i("bmob", "获取订单失败，msg：" + e.toString());
                }
            }
        });
    }

    /**
     * 通过boolean值获取订单
     * isUsed isPaid isRefund isComment
     * */
    public void getOrderOnBoolean(String orderType) {
        BmobQuery<Order> query = new BmobQuery<Order>();
        if (orderType.equals("isRefund")) {
            query.addWhereEqualTo(orderType,true);
        } else {
            query.addWhereEqualTo(orderType,false);
        }
        if (orderType.equals("isComment")) {
            query.addWhereEqualTo("orderType", "movie");
            query.addWhereEqualTo("isUsed", true);
        }
        if (orderType.equals("isUsed")) {
            query.addWhereEqualTo("isPaid", true);
        }
        query.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> list, BmobException e) {
                if (e == null) {
                    getView().getOrderOnBooleanSuccess(list);
                } else {
                    Log.i("bmob", "获取订单失败，msg：" + e.toString());
                }
            }
        });
    }
    /**
     * 消费订单，更新服务器
     * */
    public void onUsedUpdateOrder(Order order) {
        order.setUsed(true);
        order.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    getView().onUseSuccess();
                } else {
                    Log.i("bmob", "消费失败，msg" + e.toString());
                }

            }
        });

    }

    @NonNull
    @Override
    protected OrderView getNullObject() {
        return null;
    }
}
