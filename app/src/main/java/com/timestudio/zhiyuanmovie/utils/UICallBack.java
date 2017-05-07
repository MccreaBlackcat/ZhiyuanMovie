package com.timestudio.zhiyuanmovie.utils;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by shenGqiang on 2016/11/17.
 * @description  返回到主线程
 */

public abstract class UICallBack implements Callback {

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onFailure(final Call call, final IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onFailureInUi(call,e);
            }
        });
    }

    @Override
    public void onResponse(final Call call, Response response)  {
        try {
            if (!response.isSuccessful()) {
                throw new IOException("error code: " + response.code());
            }
            final String content = response.body().string();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onResponseInUi(call,content);
                }
            });
        } catch (IOException e) {
            onFailure(call, e);
        }

    }

    public abstract void onFailureInUi(Call call, Exception e);
    public abstract void onResponseInUi(Call call, String body);
}
