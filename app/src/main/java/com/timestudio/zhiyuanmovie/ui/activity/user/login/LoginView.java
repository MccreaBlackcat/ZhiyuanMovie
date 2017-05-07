package com.timestudio.zhiyuanmovie.ui.activity.user.login;

import com.timestudio.zhiyuanmovie.base.BaseMvpView;

/**
 * Created by StrongShen on 2017/4/20.
 */

public interface LoginView extends BaseMvpView {

    /*登录失败*/
    void loginFailed();
    /*登录成功*/
    void loginSussed();

    LoginView NULL = new LoginView() {
        @Override
        public void loginFailed() {

        }

        @Override
        public void loginSussed() {

        }
    };
}
