package com.timestudio.zhiyuanmovie.ui.activity.user.resetpassword;

import android.support.annotation.NonNull;
import android.util.Log;

import com.timestudio.zhiyuanmovie.base.BasePresenter;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by hasee on 2017/4/22.
 */

public class ResetPasswordPresenter extends BasePresenter<ResetPasswordView> {

    /**
     * 获取验证码
     */

    public void getVerificationCode(String phoneNumber) {
        BmobSMS.requestSMSCode(phoneNumber,"repassword", new QueryListener<Integer>() {

            @Override
            public void done(Integer smsId,BmobException ex) {
                if (ex == null) {//验证码发送成功
                    Log.i("bmob", "短信id：" + smsId);//用于查询本次短信发送详情
                }
            }
        });
    }

    /**
     * 修改密码
     */
    public void onChangePassword(String password,String SMSCode) {
        BmobUser.resetPasswordBySMSCode(SMSCode,password, new UpdateListener() {

            @Override
            public void done(BmobException ex) {
                if(ex==null){
                    Log.i("bmob", "密码重置成功");
                    getView().resetSuccess();
                }else{
                    getView().resetFailure();
                    Log.i("bmob", "重置失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
                }
            }
        });
    }




    @NonNull
    @Override
    protected ResetPasswordView getNullObject() {
        return null;
    }
}
