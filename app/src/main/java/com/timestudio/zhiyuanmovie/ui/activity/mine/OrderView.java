package com.timestudio.zhiyuanmovie.ui.activity.mine;

import com.timestudio.zhiyuanmovie.base.BaseMvpView;
import com.timestudio.zhiyuanmovie.bean.Order;

import java.util.List;

/**
 * Created by strongShen on 2017/5/10.
 */

public interface OrderView  extends BaseMvpView{

    /**
     * 获取所有订单成功
     * */
    void getAllOrderSuccess(List<Order> orders);

    /**
     * 获取特定条件订单成功
     */
    void getOrderOnBooleanSuccess(List<Order> orders);
    /**
     * 获取订单失败
     * */
    void getOrderFailure();

    /**
     * 消费成功
     */
    void onUseSuccess();

    /**
     * 删除订单成功*/
    void onDeletedSuccess();


    OrderView NULL = new OrderView() {
        @Override
        public void getAllOrderSuccess(List<Order> orders) {

        }

        @Override
        public void getOrderOnBooleanSuccess(List<Order> orders) {

        }

        @Override
        public void getOrderFailure() {

        }

        @Override
        public void onUseSuccess() {

        }

        @Override
        public void onDeletedSuccess() {

        }
    };

}
