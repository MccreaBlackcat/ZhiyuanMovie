package com.timestudio.zhiyuanmovie.ui.activity.movie;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.base.BaseActivity;
import com.timestudio.zhiyuanmovie.bean.Seat;
import com.timestudio.zhiyuanmovie.widget.SeatTable;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectSeatActivity extends BaseActivity implements SelectSeatView{

    @Bind(R.id.movie_seat_toolbar)
    Toolbar movie_seat_toolbar;

    @Bind(R.id.st_selectSeat)
    SeatTable st_selectSeat;
    @Bind(R.id.tv_seat_allMoney)
    TextView tv_seat_allMoney;
    @Bind(R.id.tv_seat_money)
    TextView tv_seat_money;
    @Bind(R.id.lv_Selectseat)
    ListView lv_Selectseat;
    @Bind(R.id.btn_seat_buy)
    Button btn_seat_buy;

    private String movieName;
    private String showTime;
    private String hall;
    private String showDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(setContent());
        movieName = getIntent().getStringExtra("movieName");
        showTime = getIntent().getStringExtra("showTime");
        hall = getIntent().getStringExtra("hall");
        showDate = getIntent().getStringExtra("showDate");
        ButterKnife.bind(this);
        setSupportActionBar(movie_seat_toolbar);
        getSupportActionBar().setTitle("");
    }

    @Override
    protected int setContent() {
        return R.layout.activity_select_seat;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void getSeatDateSuccess(List<Seat> seats) {

    }

    @Override
    public void getSeatDateFailure() {

    }
}
