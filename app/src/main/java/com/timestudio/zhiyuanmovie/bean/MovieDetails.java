package com.timestudio.zhiyuanmovie.bean;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by strongShen on 2017/5/4.
 */

public class MovieDetails extends BmobObject{
    private String actors;  //演员1
    private String directors;  //导演1
    private String country;  //拍摄国家1
    private String genres;  //分类 1
    private String language;  //语言1
    private String plot_simple;  //剧情概要1
    private BmobFile poster;  //海报1
    private String rating;  //得分1
    private String release_date;  //上映时间1
    private String runtime;  //时长1
    private String title;  //影片名称1

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPlot_simple() {
        return plot_simple;
    }

    public void setPlot_simple(String plot_simple) {
        this.plot_simple = plot_simple;
    }

    public BmobFile getPoster() {
        return poster;
    }

    public void setPoster(BmobFile poster) {
        this.poster = poster;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
