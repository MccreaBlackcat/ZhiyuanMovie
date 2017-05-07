package com.timestudio.zhiyuanmovie.ui.activity.movie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.adapter.MovieShowAdapter;
import com.timestudio.zhiyuanmovie.base.BaseActivity;
import com.timestudio.zhiyuanmovie.bean.MovieShow;
import com.timestudio.zhiyuanmovie.bean.ShowDate;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieShowActivity extends BaseActivity implements MovieShowAdapter.OnBuyClickListener,MovieShowView,View.OnClickListener{


    @Bind(R.id.tv_showDate_1)
    TextView tv_showDate_1;
    @Bind(R.id.tv_showDate_2)
    TextView tv_showDate_2;
    @Bind(R.id.tv_showDate_3)
    TextView tv_showDate_3;
    @Bind(R.id.tv_showDate_1_lines)
    TextView tv_showDate_1_lines;
    @Bind(R.id.tv_showDate_2_lines)
    TextView tv_showDate_2_lines;
    @Bind(R.id.tv_showDate_3_lines)
    TextView tv_showDate_3_lines;
    @Bind(R.id.rv_movieShow)
    RecyclerView rv_movieShow;

    private MovieShowAdapter adapter = new MovieShowAdapter();
    private MovieShowPresenter presenter = new MovieShowPresenter();
    private List<MovieShow> movieShows;
    private List<ShowDate> showDates;
    private String movieName;
    //记录 日期View 是否可以点击
    private boolean isShow1 = false;
    private boolean isShow2 = true;
    private boolean isShow3 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContent());
        movieName = getIntent().getStringExtra("movieName");
        ButterKnife.bind(this);
        presenter.attachView(this);
        presenter.getShowDate();
        initRView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    public void initRView() {
        rv_movieShow.setLayoutManager(new GridLayoutManager(this,1));
        adapter.setListener(this);
        tv_showDate_1.setOnClickListener(this);
        tv_showDate_2.setOnClickListener(this);
        tv_showDate_3.setOnClickListener(this);
    }

    @Override
    protected int setContent() {
        return R.layout.activity_movie_show;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onBuyClick(MovieShow show) {
        Intent intent = new Intent();
        intent.setClass(this, SelectSeatActivity.class);
        intent.putExtra("movieName", show.getMovieName());
        intent.putExtra("hall", show.getShowHall());
        intent.putExtra("showDate", show.getShowDate());
        intent.putExtra("showTime", show.getShowTime());
        startActivity(intent);
    }

    @Override
    public void getShowDataSuccess(List<MovieShow> movieShows) {
        this.movieShows = movieShows;
        if (adapter.getMovieShows() != null) {
            adapter.clearMovieShows();
        }
        adapter.setMovieShows(movieShows);
        rv_movieShow.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getShowDataFailure() {

    }

    @Override
    public void getShowDateSuccess(List<ShowDate> showDates) {
        this.showDates = showDates;
        tv_showDate_1.setText(showDates.get(0).getShowDate());
        tv_showDate_2.setText(showDates.get(1).getShowDate());
        tv_showDate_3.setText(showDates.get(2).getShowDate());
        presenter.getMivieShowData(movieName,tv_showDate_1.getText().toString());
    }

    @Override
    public void getShowDateFailure() {

    }

    @OnClick({R.id.tv_showDate_1,R.id.tv_showDate_2,R.id.tv_showDate_3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_showDate_1:
                if (isShow1) {
                    tv_showDate_1.setTextColor(getResources().getColor(R.color.colorMainRed));
                    tv_showDate_2.setTextColor(getResources().getColor(R.color.mainText));
                    tv_showDate_3.setTextColor(getResources().getColor(R.color.mainText));
                    tv_showDate_1_lines.setVisibility(View.VISIBLE);
                    tv_showDate_2_lines.setVisibility(View.GONE);
                    tv_showDate_3_lines.setVisibility(View.GONE);
                    presenter.getMivieShowData(movieName,tv_showDate_1.getText().toString());
                    isShow1 = false;
                    isShow2 = true;
                    isShow3 = true;
                }

                break;
            case R.id.tv_showDate_2:
                if (isShow2) {
                    tv_showDate_2.setTextColor(getResources().getColor(R.color.colorMainRed));
                    tv_showDate_1.setTextColor(getResources().getColor(R.color.mainText));
                    tv_showDate_3.setTextColor(getResources().getColor(R.color.mainText));
                    tv_showDate_2_lines.setVisibility(View.VISIBLE);
                    tv_showDate_1_lines.setVisibility(View.GONE);
                    tv_showDate_3_lines.setVisibility(View.GONE);
                    presenter.getMivieShowData(movieName,tv_showDate_2.getText().toString());
                    isShow2 = false;
                    isShow1 = true;
                    isShow1 = true;
                }

                break;
            case R.id.tv_showDate_3:
                if (isShow3) {
                    tv_showDate_3.setTextColor(getResources().getColor(R.color.colorMainRed));
                    tv_showDate_2.setTextColor(getResources().getColor(R.color.mainText));
                    tv_showDate_1.setTextColor(getResources().getColor(R.color.mainText));
                    tv_showDate_3_lines.setVisibility(View.VISIBLE);
                    tv_showDate_1_lines.setVisibility(View.GONE);
                    tv_showDate_2_lines.setVisibility(View.GONE);
                    presenter.getMivieShowData(movieName,tv_showDate_3.getText().toString());
                    isShow1 = true;
                    isShow3 = false;
                    isShow2 = true;
                }

                break;
        }
    }
}
