package com.timestudio.zhiyuanmovie.ui.activity.movie;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.adapter.MovieDetailsAdapter;
import com.timestudio.zhiyuanmovie.base.BaseActivity;
import com.timestudio.zhiyuanmovie.bean.Comment;
import com.timestudio.zhiyuanmovie.bean.MovieDetails;
import com.timestudio.zhiyuanmovie.bean.MovieShow;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieDetailsActivity extends BaseActivity implements MovieDetailsView,View.OnClickListener{


    @Bind(R.id.movie_details_toolbar)
    Toolbar movie_details_toolbar;
    @Bind(R.id.rv_movie_details)
    RecyclerView rv_movie_details;

    @Bind(R.id.btn_buyTicket)
    Button btn_buyTicket;

    private String movieName;
    private String status;
    private int mDistanceY;
    private List<Comment> mComments;  //评论数据
    private MovieDetails mDetails;  //电影数据
    private MovieDetailsAdapter adapter;
    private MovieDetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContent());
        movieName = getIntent().getStringExtra("movieName");
        status = getIntent().getStringExtra("status");
        adapter = new MovieDetailsAdapter();
        presenter = new MovieDetailsPresenter();
        presenter.attachView(this);
        initRecycleView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);

        movie_details_toolbar.setTitle(movieName);
        setSupportActionBar(movie_details_toolbar);
        getSupportActionBar().setTitle("");
        movie_details_toolbar.setNavigationIcon(R.drawable.back);

    }

    @Override
    protected int setContent() {
        return R.layout.activity_movie_details;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    public void initRecycleView() {
        rv_movie_details.setLayoutManager(new GridLayoutManager(this, 1));
        rv_movie_details.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //滑动的距离
                mDistanceY += dy;
                //toolbar的高度
                int toolbarHeight = movie_details_toolbar.getHeight();
                //当滑动的距离 <= toolbar高度的时候，改变Toolbar背景色的透明度，达到渐变的效果
                if (mDistanceY <= toolbarHeight * 4 ) {
                    float scale = (float) mDistanceY / (toolbarHeight * 4);
                    float alpha = scale * 255;
                    movie_details_toolbar.setBackgroundColor(Color.argb((int) alpha, 128, 0, 0));
                } else {
                    //上述虽然判断了滑动距离与toolbar高度相等的情况，但是实际测试时发现，标题栏的背景色
                    //很少能达到完全不透明的情况，所以这里又判断了滑动距离大于toolbar高度的情况，
                    //将标题栏的颜色设置为完全不透明状态
                    movie_details_toolbar.setBackgroundResource(R.color.colorPrimaryDark);
                }
            }
        });
        presenter.getMovieDetailsData(movieName);
        if (status.equals("待映")) {
            btn_buyTicket.setVisibility(View.GONE);
        }
        btn_buyTicket.setOnClickListener(this);

        movie_details_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    @Override
    public void getMovieSuccess(MovieDetails details) {
        mDetails = details;
        presenter.getCommentData(movieName);
        adapter.setMovieDetails(mDetails);
    }

    @Override
    public void getMovieFailure() {

    }

    @Override
    public void getCommentSuccess(List<Comment> comments) {
        mComments = comments;
        adapter.setmComments(mComments);
        rv_movie_details.setAdapter(adapter);
    }

    @Override
    public void getCommentFailure() {

    }

    @OnClick(R.id.btn_buyTicket)
    public void onClick(View view) {
        //跳转到选场界面
        Intent intent = new Intent();
        intent.setClass(this, MovieShowActivity.class);
        intent.putExtra("movieName", movieName);
        startActivity(intent);
    }
}
