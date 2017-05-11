package com.timestudio.zhiyuanmovie.ui.activity.movie;

import android.support.annotation.NonNull;
import android.util.Log;

import com.timestudio.zhiyuanmovie.base.BasePresenter;
import com.timestudio.zhiyuanmovie.bean.Movie;
import com.timestudio.zhiyuanmovie.bean.MovieShow;
import com.timestudio.zhiyuanmovie.bean.MyUser;
import com.timestudio.zhiyuanmovie.bean.Order;
import com.timestudio.zhiyuanmovie.bean.Seat;
import com.timestudio.zhiyuanmovie.bean.Ticket;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by strongShen on 2017/5/7.
 */

public class SelectSeatPresenter extends BasePresenter<SelectSeatView> {

    private boolean isSold;
    private List<Seat> mSeats = new ArrayList<>();
    String seatsName = "";
    private boolean[] isSeatSole ;
    private MovieShow show;
    private List<String> seats;
    private int totalPrice;
    private String userId;
    private Movie movie;
    /**获取座位信息*/
    public void getSeatData(MovieShow show) {
        BmobQuery<Seat> bmobQuery = new BmobQuery<Seat>();
        bmobQuery.addWhereEqualTo("movieShowId", show.getObjectId());
        bmobQuery.findObjects(new FindListener<Seat>() {
            @Override
            public void done(List<Seat> list, BmobException e) {
                if (e == null) {
                    getView().getSeatDateSuccess(list);
                } else {
                    Log.i("bmob", "获取座位信息失败");
                }
            }
        });
    }

    /**
     * 验证座位是否已经出售
     * */
    public void onVerifiSeatIsSold(MovieShow show, final List<String> seats,final int totalPrice,String userId,Movie movie) {
        this.show = show;
        this.seats = seats;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.movie = movie;
        isSeatSole = new boolean[seats.size()];
        for (int i = 0; i < seats.size(); i++) {
            seatsName += seats.get(i) + " ";
            BmobQuery<Seat> bmobQuery = new BmobQuery<Seat>();
            bmobQuery.addWhereEqualTo("movieShowId", show.getObjectId());
            bmobQuery.addWhereEqualTo("seatName", seats.get(i));
            final int finalI = i;
            bmobQuery.findObjects(new FindListener<Seat>() {
                @Override
                public void done(List<Seat> list, BmobException e) {
                    if (e == null) {
                        mSeats.addAll(list);
                        isSeatSole[finalI] = list.get(0).isSold();
                        Log.i("shen", "验证座位循环--" + finalI);
                        if (finalI == seats.size() - 1) {
                            for (int j = 0; j < isSeatSole.length; j++) {
                                if (isSeatSole[j]) {
                                    isSold = true;
                                    break;
                                }
                            }
                            if (isSold) {
                                //下单失败
                            } else {
                                //保存订单
                                Log.i("shen", "mSeats的长度--" + mSeats.size());
                                onPutOrder();
                            }
                        }
                    } else {
                        Log.i("bmob", "检查座位出错，msg:" + e.toString());
                    }
                }
            });
        }
    }

    /**
     * 提交订单
     * */

    public void onPutOrder() {

        //先锁定座位
        for (int i = 0; i < mSeats.size(); i++) {
            mSeats.get(i).setSold(true);
            mSeats.get(i).update(mSeats.get(i).getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {

                }
            });
            Log.i("shen", mSeats.size() +"锁定座位---" + mSeats.get(i).getSeatName());
        }
        //先保存到总的订单表中
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderName(show.getMovieName());
        order.setOrderType("movie");
        order.setUsed(false);
        order.setPaid(false);
        order.setRefund(false);
        order.setComment(false);
        order.setPhoto(movie.getPhoto().getUrl());
        order.setTotalPrice(totalPrice);
        final String finalSeatsName = seatsName;
        order.save(new SaveListener<String>() {
            @Override
            public void done(final String orderId, BmobException e) {
                if (e == null) {
                    //保存成功后，保存到影票表中
                    Ticket ticket = new Ticket();
                    ticket.setOrderId(orderId);
                    ticket.setMovieShowId(show.getObjectId());
                    ticket.setSeatName(finalSeatsName);
                    ticket.setTotalPrice(totalPrice);
                    ticket.setPrice(show.getPrice());
                    ticket.setAmount(seats.size());
                    ticket.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                getView().onPutOrderSuccess(orderId);
                            } else {
                                Log.i("bmob", "保存Ticket失败，code:" + e.getErrorCode() + "msg:" + e.toString());
                            }
                        }
                    });
                } else {
                    Log.i("bmob", "保存Order失败，code:" + e.getErrorCode() + "msg:" + e.toString());
                }
            }
        });


    }


    @NonNull
    @Override
    protected SelectSeatView getNullObject() {
        return null;
    }
}
