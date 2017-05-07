package com.timestudio.zhiyuanmovie.bean;

/**
 * Created by strongShen on 2017/5/4.
 */

public class MovieDetailsResult {

    private int resultcode;
    private String reason;
    private MovieDetails result;

    public int getResultcode() {
        return resultcode;
    }

    public String getReason() {
        return reason;
    }

    public MovieDetails getMovieDetails() {
        return result;
    }
}
