package com.timestudio.zhiyuanmovie.ui.fragment.find;

import com.timestudio.zhiyuanmovie.base.BaseMvpView;
import com.timestudio.zhiyuanmovie.bean.Find;

import java.util.List;

/**
 * Created by strongShen on 2017/4/27.
 */

public interface FindView extends BaseMvpView {
    /**
     * 隐藏下拉刷新视图
    */
    void hideRefresh();

    /**
     * 添加刷新更多的数据
     */
    void addRefreshData(List<Find> data);

    /**
     * 添加更多数据
     */
    void loadMoreData(List<Find> data);

    /**
     * 添加数据失败
     */
    void loadDataError();

    FindView NULL = new FindView() {
        @Override
        public void hideRefresh() {

        }

        @Override
        public void addRefreshData(List<Find> data) {

        }

        @Override
        public void loadMoreData(List<Find> data) {

        }

        @Override
        public void loadDataError() {

        }
    };
}
