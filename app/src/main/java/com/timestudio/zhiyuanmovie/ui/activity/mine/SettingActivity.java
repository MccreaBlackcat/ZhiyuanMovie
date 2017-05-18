package com.timestudio.zhiyuanmovie.ui.activity.mine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.base.BaseActivity;
import com.timestudio.zhiyuanmovie.bean.MyUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class SettingActivity extends BaseActivity implements View.OnClickListener{


    @Bind(R.id.ll_rename)
    LinearLayout ll_rename;
    @Bind(R.id.ll_repass)
    LinearLayout ll_repass;
    @Bind(R.id.tv_setting_time)
    TextView tv_setting_time;
    @Bind(R.id.btn_login_out)
    Button btn_login_out;
    @Bind(R.id.setting_toolbar)
    Toolbar setting_toolbar;

    private MyUser myUser = BmobUser.getCurrentUser(MyUser.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContent());
        initView();
    }

    @Override
    protected int setContent() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        setSupportActionBar(setting_toolbar);
        getSupportActionBar().setTitle("设置");
        setting_toolbar.setNavigationIcon(R.drawable.back);
        setting_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_setting_time.setText(myUser.getCreatedAt() + "");

    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.ll_rename,R.id.ll_repass,R.id.btn_login_out})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_rename:
                //跳转到修改昵称界面
                intent = new Intent();
                intent.setClass(this, ReNameActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_repass:
                //跳转到修改密码界面
                intent = new Intent();
                intent.setClass(this, RePassWordActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login_out:
                //用户登出
                myUser.logOut();
                setResult(0x002);
                finish();
                break;
        }
    }
}
