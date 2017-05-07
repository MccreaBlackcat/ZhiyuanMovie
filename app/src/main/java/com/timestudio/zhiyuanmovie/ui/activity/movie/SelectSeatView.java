package com.timestudio.zhiyuanmovie.ui.activity.movie;

import com.timestudio.zhiyuanmovie.base.BaseMvpView;
import com.timestudio.zhiyuanmovie.bean.Seat;

import java.util.List;

/**
 * Created by strongShen on 2017/5/7.
 */

public interface SelectSeatView extends BaseMvpView {
    void getSeatDateSuccess(List<Seat> seats);
    void getSeatDateFailure();

    SelectSeatView NULL = new SelectSeatView() {
        @Override
        public void getSeatDateSuccess(List<Seat> seats) {

        }

        @Override
        public void getSeatDateFailure() {

        }
    };
}
