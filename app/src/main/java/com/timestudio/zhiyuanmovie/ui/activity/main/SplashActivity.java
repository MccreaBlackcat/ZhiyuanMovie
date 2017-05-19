package com.timestudio.zhiyuanmovie.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.base.BaseActivity;
import com.timestudio.zhiyuanmovie.bean.Splash;
import com.timestudio.zhiyuanmovie.utils.ImageLoadOptions;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SplashActivity extends BaseActivity {

    @Bind(R.id.iv_splash_photo)
    ImageView iv_splash_photo;
    @Bind(R.id.tv_splash_time)
    TextView tv_splash_time;


    private TimeCount time;
    private TimeCount2 time2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContent());
        initView();
        initListener();
    }

    @Override
    protected int setContent() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initListener() {
        time = new TimeCount(3000,1000);
        time2 = new TimeCount2(2000,1000);
        time2.start();

    }

    private class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            tv_splash_time.setVisibility(View.VISIBLE);
            tv_splash_time.setText("跳过" + l/1000);
        }

        @Override
        public void onFinish() {
            Intent intent = new Intent();
            intent.setClass(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private class TimeCount2 extends CountDownTimer {
        public TimeCount2(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            //先加载完图片，然后开启时间倒计时
            BmobQuery<Splash> query = new BmobQuery<>();
            query.findObjects(new FindListener<Splash>() {
                @Override
                public void done(List<Splash> list, BmobException e) {
                    if (e == null) {
                        ImageLoader.getInstance().displayImage(list.get(0).getSplashPhoto().getUrl(),
                                iv_splash_photo,
                                ImageLoadOptions.build_splash());

                        time.start();
                    }
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
