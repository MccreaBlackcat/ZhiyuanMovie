package com.timestudio.zhiyuanmovie.ui.activity.movie;

import com.timestudio.zhiyuanmovie.base.BaseMvpView;
import com.timestudio.zhiyuanmovie.bean.Comment;
import com.timestudio.zhiyuanmovie.bean.MovieDetails;

import java.util.List;

/**
 * Created by strongShen on 2017/5/4.
 */

public interface MovieDetailsView extends BaseMvpView {

    /**
     * 获取电影数据成功
     * */
    void getMovieSuccess(MovieDetails details);
    void getMovieFailure();

    /**
     * 获取评论数据成功
     */
    void getCommentSuccess(List<Comment> comments);
    void getCommentFailure();

    MovieDetailsView NULL = new MovieDetailsView() {
        @Override
        public void getMovieSuccess(MovieDetails details) {

        }

        @Override
        public void getMovieFailure() {

        }

        @Override
        public void getCommentSuccess(List<Comment> comments) {

        }

        @Override
        public void getCommentFailure() {

        }
    };
}
