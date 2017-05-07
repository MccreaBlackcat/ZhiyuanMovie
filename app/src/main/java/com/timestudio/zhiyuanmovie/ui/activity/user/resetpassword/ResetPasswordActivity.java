package com.timestudio.zhiyuanmovie.ui.activity.user.resetpassword;

import android.os.CountDownTimer;
import android.os.Bundle;
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

public class ResetPasswordActivity extends BaseActivity implements View.OnClickListener,ResetPasswordView {

    @Bind(R.id.reset_toolbar)
    Toolbar reset_toolbar;
    @Bind(R.id.et_account)
    EditText et_account;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.et_verification)
    EditText et_verification;
    @Bind(R.id.btn_confirm)
    Button btn_confirm;
    @Bind(R.id.btn_getVerification)
    Button btn_getVerification;


    private ResetPasswordPresenter presenter = new ResetPasswordPresenter();
    private String phoneNumber;
    private String passWord;
    private String verificationCode;
    private TimeCount time;

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(reset_toolbar);
        getSupportActionBar().setTitle("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContent());
        presenter.attachView(this);
        et_account.addTextChangedListener(textWatcher);
        et_password.addTextChangedListener(textWatcher);
        et_verification.addTextChangedListener(textWatcher);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    protected int setContent() {
        return R.layout.activity_reset_password;
    }

    @Override
    protected void initView() {
        time = new ResetPasswordActivity.TimeCount(60000,1000);
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.btn_getVerification, R.id.btn_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:

                presenter.onChangePassword(passWord,verificationCode);
                break;

            case R.id.btn_getVerification:
                //获取 验证码
                if (phoneNumber != "") {
                    presenter.getVerificationCode(phoneNumber);
                    time.start();
                }

                break;
        }
    }


    @Override
    public void resetSuccess() {
        Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void resetFailure() {
        Toast.makeText(this, "修改失败！", Toast.LENGTH_SHORT).show();
        clearEditText();
    }

    private void clearEditText() {
        et_account.setText("");
        et_password.setText("");
        et_verification.setText("");
        btn_confirm.setEnabled(false);
    }


    /**
     * 获取验证码倒计时
     * */
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

    public TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            phoneNumber = et_account.getText().toString();
            passWord = et_password.getText().toString();
            verificationCode = editable.toString();
            boolean isReset = !(TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(passWord));
            if (isReset) {
                btn_confirm.setEnabled(isReset);
                btn_confirm.setBackground(getDrawable(R.drawable.btn_sel_getverification));
            } else {
                btn_confirm.setBackgroundResource(R.color.colorButtonFalse);
            }
        }
    };
}
