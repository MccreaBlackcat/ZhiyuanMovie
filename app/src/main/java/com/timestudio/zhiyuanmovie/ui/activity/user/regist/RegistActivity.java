package com.timestudio.zhiyuanmovie.ui.activity.user.regist;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @description 用户注册界面
 * */
public class RegistActivity extends BaseActivity implements RegistView{


    @Bind(R.id.regist_toolbar)
    Toolbar regist_toolbar;
    @Bind(R.id.et_userName)
    EditText et_userName;
    @Bind(R.id.et_account)
    EditText et_account;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.et_verification)
    EditText et_verification;
    @Bind(R.id.btn_registered)
    Button btn_registered;
    @Bind(R.id.btn_getVerification)
    Button btn_getVerification;

    private RegistPresenter registPresenter = new RegistPresenter();
    private String nickName;
    private String phoneNumber;
    private String password;
    private String SMSCode;
    private TimeCount time;

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(regist_toolbar);
        getSupportActionBar().setTitle("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContent());
        et_userName.addTextChangedListener(textWatcher);
        et_account.addTextChangedListener(textWatcher);
        et_password.addTextChangedListener(textWatcher);
        et_verification.addTextChangedListener(textWatcher);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registPresenter.detachView();
    }

    @Override
    protected int setContent() {
        return R.layout.activity_regist;
    }

    @Override
    protected void initView() {
        registPresenter.attachView(this);
        time = new TimeCount(60000,1000);
    }

    @Override
    protected void initListener() {

    }


    @OnClick({R.id.btn_registered, R.id.btn_getVerification})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_getVerification:
                //获取 验证码
                if (phoneNumber != "") {
                    registPresenter.getVerification(phoneNumber);
                    time.start();
                }
                break;
            case R.id.btn_registered:
                //先验证
                registPresenter.onVerification(nickName, phoneNumber, password, SMSCode);
//                registPresenter.userRegistered(nickName,phoneNumber,password);
                break;
        }

    }

    private void clearEditText() {
        et_account.setText("");
        et_password.setText("");
        et_userName.setText("");
        et_verification.setText("");
        btn_registered.setEnabled(false);
    }

    @Override
    public void registSuccess() {
        Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void registFailure() {
        clearEditText();
    }

    private class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            btn_getVerification.setEnabled(false);
            btn_getVerification.setText(l/1000 + "s后重新获取");
            btn_getVerification.setBackgroundColor(getColor(R.color.colorButtonFalse));
        }

        @Override
        public void onFinish() {
            btn_getVerification.setText("再次获取");
            btn_getVerification.setEnabled(true);
            btn_getVerification.setBackgroundColor(getColor(R.color.colorButtonTrue));
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            nickName = et_userName.getText().toString();
            phoneNumber = et_account.getText().toString();
            password = et_password.getText().toString();
            SMSCode = editable.toString();
            boolean isRegist = !(TextUtils.isEmpty(nickName) || TextUtils.isEmpty(phoneNumber) ||
                    TextUtils.isEmpty(password) || TextUtils.isEmpty(SMSCode));
            if (isRegist) {
                btn_registered.setEnabled(isRegist);
                btn_registered.setBackground(getDrawable(R.drawable.btn_sel_getverification));
            } else {
                btn_registered.setBackgroundResource(R.color.colorButtonFalse);
            }
        }
    };
}
