package com.timestudio.zhiyuanmovie.ui.activity.movie;

import android.support.annotation.NonNull;
import android.util.Log;

import com.timestudio.zhiyuanmovie.base.BasePresenter;
import com.timestudio.zhiyuanmovie.bean.Seat;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by strongShen on 2017/5/7.
 */

public class SelectSeatPresenter extends BasePresenter<SelectSeatView> {


    public void getSeatData(String movieName, String showTime, String showDate, String hall) {
        BmobQuery<Seat> bmobQuery = new BmobQuery<Seat>();
        bmobQuery.addWhereEqualTo("movieName", movieName);
        bmobQuery.addWhereEqualTo("showTime", showTime);
        bmobQuery.addWhereEqualTo("showDate", showDate);
        bmobQuery.addWhereEqualTo("hall", hall);
        bmobQuery.findObjects(new FindListener<Seat>() {
            @Override
            public void done(List<Seat> list, BmobException e) {
                if (e == null) {

                } else {
                    Log.i("bmob", "获取座位信息失败");
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
