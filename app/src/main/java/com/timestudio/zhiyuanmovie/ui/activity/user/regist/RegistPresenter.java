package com.timestudio.zhiyuanmovie.ui.activity.user.regist;

import android.support.annotation.NonNull;
import android.util.Log;

import com.timestudio.zhiyuanmovie.base.BasePresenter;
import com.timestudio.zhiyuanmovie.bean.MyUser;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by strongShen on 2017/4/20.
 */

public class RegistPresenter extends BasePresenter<RegistView> {



    /**
     * 验证从服务器获取到的验证码
     */
    public void getVerification(String phoneNumber) {
        //点击获取验证码，获取服务器验证码
        BmobSMS.requestSMSCode(phoneNumber, "zhiyuan", new QueryListener<Integer>() {
            @Override
            public void done(Integer smsId, BmobException e) {
                if(e==null){//验证码发送成功
                    Log.i("bmob", "短信id："+ smsId);//用于查询本次短信发送详情
                }
            }
        });
    }

    /**
     * 验证
     */
    public void onVerification(final String nickname, final String phoneNumber, final String password, String smsCode) {
        //注册之前先验证验证码，然后才能向服务器提交数据
        BmobSMS.verifySmsCode(phoneNumber, smsCode, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {//短信验证码已验证成功
                    Log.i("bmob", "验证通过");
                    userRegistered(nickname,phoneNumber,password);
                } else {
                    Log.i("bmob", "验证失败：code =" + e.getErrorCode() + ",msg = " + e.getLocalizedMessage());
                }
            }
        });
    }

    /**
     * 验证完毕，注册号码，向服务器添加数据
     */
    public void userRegistered(final String nickname, String phoneNumber, String password) {
        MyUser bmobuser = new MyUser();
        bmobuser.setUsername(nickname);
        bmobuser.setMobilePhoneNumber(phoneNumber);
        bmobuser.setPassword(password);
        bmobuser.setVipIntegral(0);
        bmobuser.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if(e==null){
                    Log.i("bmob", "添加信息成功" + myUser.getUsername());
                    getView().registSuccess();
                }else{
                    Log.i("bmob", "添加信息失败：code:" + e.getErrorCode() + "msg" + e.getLocalizedMessage());
                    getView().registFailure();
                }
            }
        });
    }


    @NonNull
    @Override
    protected RegistView getNullObject() {
        return null;
    }
}
