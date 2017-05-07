package com.timestudio.zhiyuanmovie.ui.activity.find;

import com.timestudio.zhiyuanmovie.base.BaseMvpView;
import com.timestudio.zhiyuanmovie.bean.TopTen;

import java.util.List;

/**
 * Created by hasee on 2017/4/28.
 */

public interface TopTenView extends BaseMvpView {

    /**
     * 获取数据成功
     * */
    void getTopTenDataSuccess(List<TopTen> topTens);
    /**
     * 获取数据失败
     * */
    void getTopTenDataFailure();

    TopTenView NULL = new TopTenView() {
        @Override
        public void getTopTenDataSuccess(List<TopTen> topTens) {

        }

        @Override
        public void getTopTenDataFailure() {

        }
    };

}
