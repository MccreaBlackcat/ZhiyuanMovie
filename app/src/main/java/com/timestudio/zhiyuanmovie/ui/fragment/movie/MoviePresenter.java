package com.timestudio.zhiyuanmovie.ui.fragment.movie;

import android.support.annotation.NonNull;
import android.util.Log;

import com.timestudio.zhiyuanmovie.base.BasePresenter;
import com.timestudio.zhiyuanmovie.bean.Banner;
import com.timestudio.zhiyuanmovie.bean.Movie;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by strongShen on 2017/5/3.
 */

public class MoviePresenter extends BasePresenter<MovieView> {

    /**
     * 获取 Banner 数据
     * */
    public void getBannerData() {
        BmobQuery<Banner> bannerBmobQuery = new BmobQuery<Banner>();
        bannerBmobQuery.findObjects(new FindListener<Banner>() {
            @Override
            public void done(List<Banner> list, BmobException e) {
                if (e == null) {
                    getView().getBannerSuccess(list);
                } else {
                    Log.i("bmob", "获取 Banner 失败：" + e.getMessage() + ",code：" + e.getErrorCode());
                }

            }
        });
    }

    /**
     * 获取 Movie(热映) 数据
     * */
    public void getHotMovieData() {
        BmobQuery<Movie> movieBmobQuery = new BmobQuery<Movie>();
        movieBmobQuery.addWhereEqualTo("status","热映");
        movieBmobQuery.findObjects(new FindListener<Movie>() {
            @Override
            public void done(List<Movie> list, BmobException e) {
                if (e == null) {
                    getView().getMovieSuccess(list);
                } else {
                    Log.i("bmob", "获取 Movie(热映) 失败：" + e.getMessage() + ",code：" + e.getErrorCode());
                }

            }
        });
    }

    /**
     * 获取 Movie(待映) 数据
     * */
    public void getWaitMovieData() {
        BmobQuery<Movie> movieBmobQuery = new BmobQuery<Movie>();
        movieBmobQuery.addWhereEqualTo("status","待映");
        movieBmobQuery.findObjects(new FindListener<Movie>() {
            @Override
            public void done(List<Movie> list, BmobException e) {
                if (e == null) {
                    getView().getMovieSuccess(list);
                } else {
                    Log.i("bmob", "获取 Movie(热映) 失败：" + e.getMessage() + ",code：" + e.getErrorCode());
                }

            }
        });
    }




    @NonNull
    @Override
    protected MovieView getNullObject() {
        return null;
    }
}
