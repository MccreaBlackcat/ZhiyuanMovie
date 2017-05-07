package com.timestudio.zhiyuanmovie.ui.activity.movie;

import android.support.annotation.NonNull;
import android.util.Log;

import com.timestudio.zhiyuanmovie.base.BasePresenter;
import com.timestudio.zhiyuanmovie.bean.MovieShow;
import com.timestudio.zhiyuanmovie.bean.ShowDate;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by hasee on 2017/5/7.
 */

public class MovieShowPresenter extends BasePresenter<MovieShowView> {


    /**
     * 从服务器获取放映日期，不直接从本地系统获取
     * */
    public void getShowDate() {
        BmobQuery<ShowDate> bmobQuery = new BmobQuery<ShowDate>();
        bmobQuery.findObjects(new FindListener<ShowDate>() {
            @Override
            public void done(List<ShowDate> list, BmobException e) {
                if (e == null) {
                    getView().getShowDateSuccess(list);
                } else {
                    Log.i("bmob","获取放映日期失败，code：" + e.getErrorCode() + "msg:" + e.toString());
                }
            }
        });
    }

    /***/
    public void getMivieShowData(String movieName, String date) {
        BmobQuery<MovieShow> bmobQuery = new BmobQuery<MovieShow>();
        bmobQuery.addWhereEqualTo("showDate", date);
        bmobQuery.addWhereEqualTo("movieName", movieName);
        bmobQuery.findObjects(new FindListener<MovieShow>() {
            @Override
            public void done(List<MovieShow> list, BmobException e) {
                if (e == null) {
                    getView().getShowDataSuccess(list);
                } else {
                    Log.i("bmob","获取放映信息失败，code：" + e.getErrorCode() + "msg:" + e.toString());
                }
            }
        });
    }


    @NonNull
    @Override
    protected MovieShowView getNullObject() {
        return null;
    }
}
