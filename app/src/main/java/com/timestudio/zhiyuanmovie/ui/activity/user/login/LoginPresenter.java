package com.timestudio.zhiyuanmovie.ui.activity.user.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.timestudio.zhiyuanmovie.base.BasePresenter;
import com.timestudio.zhiyuanmovie.bean.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by strongShen on 2017/4/20.
 */

public class LoginPresenter extends BasePresenter<LoginView> {



    private String username;
    private String phonenumber;
    private String password;
    private String vipIntegral;
    private MyUser myMyUser;

    /**
     * 用户登录
     */
    public void userLogin(String phongNumber, String password) {
        MyUser muser = new MyUser();
        //手机号登录
        muser.loginByAccount(phongNumber, password, new LogInListener<MyUser>() {

            @Override
            public void done(MyUser myUser, BmobException e) {
                if (myUser != null) {
                    Log.i("bmob", "用户登陆成功" + myUser.getUsername());
                    myMyUser = myUser;
                    getView().loginSussed();
                } else {
                    Log.i("bmob", "用户登陆失败！errorCode: "+ e.getErrorCode()+" meg: " + e.getLocalizedMessage());
                    getView().loginFailed();
                }
            }
        });
    }






    @NonNull
    @Override
    protected LoginView getNullObject() {
        return null;
    }
}
