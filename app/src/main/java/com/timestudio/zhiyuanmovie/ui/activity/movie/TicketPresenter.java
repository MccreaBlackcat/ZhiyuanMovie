package com.timestudio.zhiyuanmovie.ui.activity.movie;

import android.support.annotation.NonNull;
import android.util.Log;

import com.timestudio.zhiyuanmovie.base.BasePresenter;
import com.timestudio.zhiyuanmovie.bean.MovieShow;
import com.timestudio.zhiyuanmovie.bean.Order;
import com.timestudio.zhiyuanmovie.bean.Ticket;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by strongShen on 2017/5/8.
 */

public class TicketPresenter extends BasePresenter<TicketView> {

    /**
     * 获取电影票信息
     * */
    public void getMovieTicket(String orderId) {
        BmobQuery<Ticket> bmobQuery = new BmobQuery<Ticket>();
        bmobQuery.addWhereEqualTo("orderId", orderId);
        bmobQuery.findObjects(new FindListener<Ticket>() {
            @Override
            public void done(final List<Ticket> list, BmobException e) {
                if (e == null) {
                    BmobQuery<MovieShow> query = new BmobQuery<MovieShow>();
                    query.getObject(list.get(0).getMovieShowId(), new QueryListener<MovieShow>() {
                        @Override
                        public void done(MovieShow show, BmobException e) {
                            if (e == null) {
                                getView().getTicketSuccess(list.get(0), show);
                            } else {
                                Log.i("bmob", "获取ticket失败,msg:" + e.toString());
                            }
                        }
                    });
                } else {

                }
            }
        });
    }
    /**
     * 支付
     * */

    public void onTicketPay(final String orderId) {
        BmobQuery<Order> bmobQuery = new BmobQuery<Order>();
        bmobQuery.getObject(orderId, new QueryListener<Order>() {
            @Override
            public void done(Order order, BmobException e) {
                if (e == null) {
                    order.setPaid(true);
                    order.update(orderId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                getView().onPaySuccess();
                            }
                        }
                    });
                }
            }
        });
    }



    @NonNull
    @Override
    protected TicketView getNullObject() {
        return null;
    }
}
