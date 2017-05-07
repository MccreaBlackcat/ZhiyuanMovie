package com.timestudio.zhiyuanmovie.ui.activity.find;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.base.BaseActivity;
import com.timestudio.zhiyuanmovie.utils.ConnectUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FindWebActivity extends BaseActivity {

    @Bind(R.id.web_toolbar)
    Toolbar web_toolbar;
    @Bind(R.id.pb_find_details)
    ProgressBar pb_find_details;
    @Bind(R.id.wv_find_details)
    WebView wv_find_details;

    private String findUrl;
    private WebSettings settings;

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(web_toolbar);
        getSupportActionBar().setTitle("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContent());
        findUrl = getIntent().getStringExtra("webUrl");
        initWebData();
    }

    @Override
    protected int setContent() {
        return R.layout.activity_find_web;
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
            wv_find_details.loadUrl(findUrl);
            settings = wv_find_details.getSettings();
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            wv_find_details.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            wv_find_details.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if (newProgress == 100) {
                        pb_find_details.setVisibility(View.GONE);
                    } else {
                        if (View.INVISIBLE == pb_find_details.getVisibility()) {
                            pb_find_details.setVisibility(View.VISIBLE);

                        }
                        pb_find_details.setProgress(newProgress);
                    }
                    super.onProgressChanged(view, newProgress);
                }
            });
        }
    }
}
