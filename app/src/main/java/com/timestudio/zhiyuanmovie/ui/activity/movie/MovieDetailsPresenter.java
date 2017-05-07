package com.timestudio.zhiyuanmovie.ui.activity.movie;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.timestudio.zhiyuanmovie.base.BasePresenter;
import com.timestudio.zhiyuanmovie.bean.Comment;
import com.timestudio.zhiyuanmovie.bean.MovieDetails;
import com.timestudio.zhiyuanmovie.bean.MovieDetailsResult;
import com.timestudio.zhiyuanmovie.utils.MovieClient;
import com.timestudio.zhiyuanmovie.utils.UICallBack;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by hasee on 2017/5/4.
 */

public class MovieDetailsPresenter extends BasePresenter<MovieDetailsView> {


    /**
     * 获取电影数据
     */


    public void getMovieDetailsData(String movieName) {
        BmobQuery<MovieDetails> query = new BmobQuery<MovieDetails>();
        query.addWhereEqualTo("title", movieName);
        query.findObjects(new FindListener<MovieDetails>() {
            @Override
            public void done(List<MovieDetails> list, BmobException e) {
                if (e == null) {
                    getView().getMovieSuccess(list.get(0));
                } else {
                    Log.i("bmob", "获取影片内容失败，code:" + e.getErrorCode() + ",msg:" + e.toString());
                }
            }
        });
    }


    /**
     * 获取评论数据
     * */

    public void getCommentData(String movieName) {
        BmobQuery<Comment> bmobQuery = new BmobQuery<Comment>();
        bmobQuery.addWhereEqualTo("movieName", movieName);
        bmobQuery.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if (e == null) {
                    getView().getCommentSuccess(list);
                } else {
                    Log.i("bmob", "获取评论失败，code:" + e.getErrorCode() + ",msg:" + e.toString());
                }

            }
        });
    }



    @NonNull
    @Override
    protected MovieDetailsView getNullObject() {
        return null;
    }
}
