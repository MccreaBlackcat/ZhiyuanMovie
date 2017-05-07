package com.timestudio.zhiyuanmovie.ui.activity.user.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pkmmte.view.CircularImageView;
import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.base.BaseActivity;
import com.timestudio.zhiyuanmovie.ui.activity.user.regist.RegistActivity;
import com.timestudio.zhiyuanmovie.ui.activity.user.resetpassword.ResetPasswordActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements View.OnClickListener,LoginView {

    @Bind(R.id.login_toolbar)
    Toolbar login_toolbar;
    @Bind(R.id.et_account)
    EditText et_account;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.btn_login)
    Button btn_login;
    @Bind(R.id.tv_loginProblem)
    TextView tv_loginProblem;
    @Bind(R.id.tv_regist)
    TextView tv_regist;
    @Bind(R.id.civ_login_share_qq)
    CircularImageView civ_login_share_qq;
    @Bind(R.id.civ_login_share_wx)
    CircularImageView civ_login_share_wx;


    private String phoneNumber;
    private String password;
    private LoginPresenter loginPresenter = new LoginPresenter();

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(login_toolbar);
        getSupportActionBar().setTitle("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContent());
        loginPresenter.attachView(this);
        et_account.addTextChangedListener(textWatcher);
        et_password.addTextChangedListener(textWatcher);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.detachView();
    }

    @Override
    protected int setContent() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.btn_login,R.id.tv_loginProblem,R.id.tv_regist,R.id.civ_login_share_qq,R.id.civ_login_share_wx})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                loginPresenter.userLogin(phoneNumber,password);
                break;
            case R.id.tv_loginProblem:
                Intent intent1 = new Intent();
                intent1.setClass(getBaseContext(), ResetPasswordActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_regist:
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), RegistActivity.class);
                startActivity(intent);
                break;
            case R.id.civ_login_share_qq:
                Toast.makeText(this,"使用QQ登录",Toast.LENGTH_SHORT).show();
                break;
            case R.id.civ_login_share_wx:
                Toast.makeText(this,"使用微信登录",Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public void loginFailed() {
        clearEditText();
    }

    @Override
    public void loginSussed() {
        Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("isLogin", true);
        setResult(0x001,intent);
        finish();
    }

    private void clearEditText() {
        et_password.setText("");
        btn_login.setEnabled(false);
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
            password = editable.toString();
            boolean isLogin = !(TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(password));
            if (isLogin) {
                btn_login.setEnabled(isLogin);
                btn_login.setBackground(getDrawable(R.drawable.btn_sel_getverification));
            } else {
                btn_login.setBackgroundResource(R.color.colorButtonFalse);
            }
        }
    };
}
