package com.timestudio.zhiyuanmovie.ui.activity.movie;

import com.timestudio.zhiyuanmovie.base.BaseMvpView;
import com.timestudio.zhiyuanmovie.bean.MovieShow;
import com.timestudio.zhiyuanmovie.bean.ShowDate;

import java.util.List;

/**
 * Created by strongShen on 2017/5/7.
 */

public interface MovieShowView extends BaseMvpView {

    void getShowDataSuccess(List<MovieShow> movieShows);
    void getShowDataFailure();

    void getShowDateSuccess(List<ShowDate> showDates);
    void getShowDateFailure();

    MovieShowView NULL = new MovieShowView() {
        @Override
        public void getShowDataSuccess(List<MovieShow> movieShows) {

        }

        @Override
        public void getShowDataFailure() {

        }

        @Override
        public void getShowDateSuccess(List<ShowDate> showDates) {

        }

        @Override
        public void getShowDateFailure() {

        }
    };

}
