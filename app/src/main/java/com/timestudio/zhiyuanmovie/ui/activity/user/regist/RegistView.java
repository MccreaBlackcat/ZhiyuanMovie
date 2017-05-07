package com.timestudio.zhiyuanmovie.ui.activity.user.regist;

import com.timestudio.zhiyuanmovie.base.BaseMvpView;

/**
 * Created by strongShen on 2017/4/20.
 */

public interface RegistView extends BaseMvpView {

    /**
     * 注册成功
     */
    void registSuccess();

    /**
     * 注册失败
     */
    void registFailure();

    RegistView NULL = new RegistView() {
        @Override
        public void registSuccess() {

        }

        @Override
        public void registFailure() {

        }
    };
}
