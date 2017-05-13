package com.timestudio.zhiyuanmovie.ui.activity.mine;

import com.timestudio.zhiyuanmovie.base.BaseMvpView;

/**
 * Created by strongShen on 2017/5/13.
 */

public interface CommentView extends BaseMvpView {
    /**
     * 发表成功
     * */
    void onCommentSuccess();
    /**
     * 发表失败
     * */
    void onCommentFailure();

    CommentView NULL = new CommentView() {
        @Override
        public void onCommentSuccess() {

        }

        @Override
        public void onCommentFailure() {

        }
    };

}
