package com.timestudio.zhiyuanmovie.ui.activity.movie;

import com.timestudio.zhiyuanmovie.base.BaseMvpView;
import com.timestudio.zhiyuanmovie.bean.Seat;

import java.util.List;

/**
 * Created by strongShen on 2017/5/7.
 */

public interface SelectSeatView extends BaseMvpView {
    /**获取座位数据*/
    void getSeatDateSuccess(List<Seat> seats);
    void getSeatDateFailure();

    /**
     * 提交订单
     */
    void onPutOrderSuccess(String orderId);
    void onPutOrderFailure();

    SelectSeatView NULL = new SelectSeatView() {
        @Override
        public void getSeatDateSuccess(List<Seat> seats) {

        }

        @Override
        public void getSeatDateFailure() {

        }

        @Override
        public void onPutOrderSuccess(String orderId) {

        }

        @Override
        public void onPutOrderFailure() {

        }
    };
}
