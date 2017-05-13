package com.timestudio.zhiyuanmovie.ui.activity.mine;

import android.support.annotation.NonNull;
import android.util.Log;

import com.timestudio.zhiyuanmovie.base.BasePresenter;
import com.timestudio.zhiyuanmovie.bean.Comment;
import com.timestudio.zhiyuanmovie.bean.MyUser;
import com.timestudio.zhiyuanmovie.bean.Order;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by strongShen on 2017/5/13.
 */

public class CommentPresenter extends BasePresenter<CommentView> {

    /**
     * 发表评论
     * */
    public void onPutComment(String comment, MyUser myUser, final Order mOrder) {
        Comment comment1 = new Comment();
        comment1.setContent(comment);
        comment1.setMovieName(mOrder.getOrderName());
        comment1.setUserId(myUser.getObjectId());
        comment1.setUserName(myUser.getUsername());
        comment1.setPhotoUrl(mOrder.getPhoto());
        comment1.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    mOrder.setComment(true);
                    mOrder.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                getView().onCommentSuccess();
                            } else {
                                Log.i("bmob", "修改订单失败，msg：" + e.toString());
                            }
                        }
                    });
                } else {
                    Log.i("bmob", "发表评论失败，msg：" + e.toString());
                }
            }
        });
    }

    @NonNull
    @Override
    protected CommentView getNullObject() {
        return null;
    }
}
