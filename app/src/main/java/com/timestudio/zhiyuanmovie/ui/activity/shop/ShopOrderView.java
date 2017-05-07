package com.timestudio.zhiyuanmovie.ui.activity.shop;

import com.timestudio.zhiyuanmovie.base.BaseMvpView;
import com.timestudio.zhiyuanmovie.bean.Order;
import com.timestudio.zhiyuanmovie.bean.ShopOrder;

import java.util.List;

/**
 * Created by hasee on 2017/4/26.
 */

public interface ShopOrderView extends BaseMvpView {

    /**
     * 获取商品订单成功
     * */
    void getShopOrderSuccess(List<ShopOrder> list);
    /**
     * 获取商品订单失败
     * */
    void getShopOrderFailure();

    /**
     * 支付成功
     */
    void onPaidSuccess();
    /**
     * 支付失败
     */
    void onPaidFailure();

    void getOrderSuccess(Order order);

    void getOrderFailure();

    ShopOrderView NULL = new ShopOrderView() {
        @Override
        public void getShopOrderSuccess(List<ShopOrder> list) {

        }

        @Override
        public void getShopOrderFailure() {

        }

        @Override
        public void onPaidSuccess() {

        }

        @Override
        public void onPaidFailure() {

        }

        @Override
        public void getOrderSuccess(Order order) {

        }

        @Override
        public void getOrderFailure() {

        }
    };


}
