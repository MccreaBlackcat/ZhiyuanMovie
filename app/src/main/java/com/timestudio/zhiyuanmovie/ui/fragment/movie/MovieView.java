package com.timestudio.zhiyuanmovie.ui.fragment.movie;

import com.timestudio.zhiyuanmovie.base.BaseMvpView;
import com.timestudio.zhiyuanmovie.bean.Banner;
import com.timestudio.zhiyuanmovie.bean.Movie;

import java.util.List;

/**
 * Created by strongShen on 2017/5/3.
 */

public interface MovieView extends BaseMvpView {

    void getBannerSuccess(List<Banner> banners);

    void getBannerFailure();
    void getMovieFailure();
    void getMovieSuccess(List<Movie> movies);

    MovieView Null = new MovieView() {
        @Override
        public void getBannerSuccess(List<Banner> banners) {

        }

        @Override
        public void getBannerFailure() {

        }

        @Override
        public void getMovieFailure() {

        }

        @Override
        public void getMovieSuccess(List<Movie> movies) {

        }
    };
}
