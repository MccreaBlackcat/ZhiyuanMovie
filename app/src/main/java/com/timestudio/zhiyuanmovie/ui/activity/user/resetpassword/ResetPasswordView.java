package com.timestudio.zhiyuanmovie.ui.activity.user.resetpassword;

import com.timestudio.zhiyuanmovie.base.BaseMvpView;

/**
 * Created by strongShen on 2017/4/22.
 */

public interface ResetPasswordView extends BaseMvpView {

    /**
     * 修改成功
     * */
    void resetSuccess();

    /**
     * 修改失败
     * */
    void resetFailure();

    ResetPasswordView NULL = new ResetPasswordView() {
        @Override
        public void resetSuccess() {

        }

        @Override
        public void resetFailure() {

        }
    };

}
