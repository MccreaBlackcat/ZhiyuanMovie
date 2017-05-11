package com.timestudio.zhiyuanmovie.ui.activity.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.base.BaseActivity;

public class QuestionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContent());
    }

    @Override
    protected int setContent() {
        return R.layout.activity_question;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }
}
