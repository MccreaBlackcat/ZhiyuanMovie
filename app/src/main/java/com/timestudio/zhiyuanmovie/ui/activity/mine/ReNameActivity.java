package com.timestudio.zhiyuanmovie.ui.activity.mine;

import android.support.v7.app.AppCompatActivity;
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

public class ReNameActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.rename_toolbar)
    Toolbar rename_toolbar;
    @Bind(R.id.et_rename)
    EditText et_rename;
    @Bind(R.id.btn_rename_commit)
    Button btn_rename_commit;

    private MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
    private String newName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContent());
        initView();
        initListener();
    }

    @Override
    protected int setContent() {
        return R.layout.activity_re_name;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        et_rename.setHint(myUser.getUsername());
        setSupportActionBar(rename_toolbar);
        getSupportActionBar().setTitle("修改昵称");
        rename_toolbar.setNavigationIcon(R.drawable.back);
        rename_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initListener() {
        et_rename.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                newName = editable.toString();
            }
        });
    }

    @OnClick(R.id.btn_rename_commit)
    public void onClick(View view) {
        if (newName != null) {
            myUser.setUsername(newName);
            myUser.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Toast.makeText(ReNameActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                        finish();
                    } else {

                    }
                }
            });
        }
    }
}
