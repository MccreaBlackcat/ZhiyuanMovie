package com.timestudio.zhiyuanmovie.ui.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.base.BaseActivity;
import com.timestudio.zhiyuanmovie.bean.MyUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class RePassWordActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.repass_toolbar)
    Toolbar repass_toolbar;
    @Bind(R.id.et_old_pass)
    EditText et_old_pass;
    @Bind(R.id.et_new_pass)
    EditText et_new_pass;
    @Bind(R.id.btn_repass_commit)
    Button btn_repass_commit;

    private MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
    private String newPassword;
    private String oldPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContent());
        initView();
        initListener();

    }

    @Override
    protected int setContent() {
        return R.layout.activity_re_pass_word;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        setSupportActionBar(repass_toolbar);
        getSupportActionBar().setTitle("修改密码");

    }

    @Override
    protected void initListener() {
        et_old_pass.addTextChangedListener(textWatcher);
        et_new_pass.addTextChangedListener(textWatcher);
        repass_toolbar.setNavigationIcon(R.drawable.back);
        repass_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick(R.id.btn_repass_commit)
    public void onClick(View view) {
        if (oldPassword != null && newPassword != null) {
            myUser.updateCurrentUserPassword(oldPassword, newPassword, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Toast.makeText(RePassWordActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RePassWordActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                    }
                }

            });
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
            oldPassword = et_old_pass.getText().toString();
            newPassword = et_new_pass.getText().toString();
        }
    };
}
