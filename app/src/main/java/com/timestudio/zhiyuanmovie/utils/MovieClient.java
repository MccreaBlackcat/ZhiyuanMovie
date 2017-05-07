package com.timestudio.zhiyuanmovie.utils;


import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by shenGqiang on 2016/11/17.
 * @description 单例模式,所有的网络连接请求
 */

public class MovieClient {

    private OkHttpClient okHttpClient;
    private String MOVIE_URL = "http://v.juhe.cn/movie/index";

    private MovieClient() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    private static MovieClient movieClient;

    public static MovieClient getInstance() {
        if (movieClient == null) {
            movieClient = new MovieClient();
        }
        return movieClient;
    }

    /**
     * 获取电影详细数据
     */
    public Call getMovieDetailsData(String movieName)  {
        String movie = null;
        try {
            movie = URLDecoder.decode(movieName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.i("shen", "------------getMovieDetailsData " + movie);
        RequestBody requestBody = new FormBody.Builder()
                .add("key", "906a66002d4cafc3ac3d0f5a944bb26a")
                .add("title", movie)
                .build();
        Request request = new Request.Builder()
                .url(MOVIE_URL)
                .post(requestBody)
                .build();
        return okHttpClient.newCall(request);
    }


}
