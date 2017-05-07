package com.timestudio.zhiyuanmovie.ui.activity.find;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.base.BaseActivity;
import com.timestudio.zhiyuanmovie.utils.ConnectUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BookingActivity extends BaseActivity {

    @Bind(R.id.wv_find_booking)
    WebView wv_find_booking;
    @Bind(R.id.pb_find_booking)
    ProgressBar pb_find_booking;

    private WebSettings settings;
    private String webUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContent());
        ButterKnife.bind(this);
        webUrl = getIntent().getStringExtra("webUrl");
        initWebData();
    }

    @Override
    protected int setContent() {
        return R.layout.activity_booking;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    private void initWebData() {
        if (!ConnectUtil.isNetworkAvailable(this)) {

        } else {
            wv_find_booking.loadUrl(webUrl);
            settings = wv_find_booking.getSettings();
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            wv_find_booking.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            wv_find_booking.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if (newProgress == 100) {
                        pb_find_booking.setVisibility(View.GONE);
                    } else {
                        if (View.INVISIBLE == pb_find_booking.getVisibility()) {
                            pb_find_booking.setVisibility(View.VISIBLE);

                        }
                        pb_find_booking.setProgress(newProgress);
                    }
                    super.onProgressChanged(view, newProgress);
                }
            });
        }
    }
}
