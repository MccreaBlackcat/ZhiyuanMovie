package com.timestudio.zhiyuanmovie.ui.fragment.mine;

import com.timestudio.zhiyuanmovie.base.BaseMvpView;
import com.timestudio.zhiyuanmovie.bean.Order;

import java.util.List;

/**
 * Created by strongShen on 2017/4/26.
 */

public interface MineView extends BaseMvpView {


    void getOrderSuccess(List<Order> orders);
    void getOrderFailure();

    MineView NULL = new MineView() {

        @Override
        public void getOrderSuccess(List<Order> orders) {

        }

        @Override
        public void getOrderFailure() {

        }
    };
}
