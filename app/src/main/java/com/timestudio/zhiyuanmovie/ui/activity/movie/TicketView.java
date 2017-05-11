package com.timestudio.zhiyuanmovie.ui.activity.movie;

import com.timestudio.zhiyuanmovie.base.BaseMvpView;
import com.timestudio.zhiyuanmovie.bean.MovieShow;
import com.timestudio.zhiyuanmovie.bean.Ticket;

/**
 * Created by strongShen on 2017/5/8.
 */

public interface TicketView  extends BaseMvpView{

    /**
     * 获取Ticket订单
     */
    void getTicketSuccess(Ticket ticket, MovieShow show);
    void getTicketFailure();

    /**
     * 支付成功
     */
    void onPaySuccess();
    void onPayFailure();

    TicketView NULL = new TicketView() {

        @Override
        public void getTicketSuccess(Ticket ticket, MovieShow show) {

        }

        @Override
        public void getTicketFailure() {

        }

        @Override
        public void onPaySuccess() {

        }

        @Override
        public void onPayFailure() {

        }
    };

}
