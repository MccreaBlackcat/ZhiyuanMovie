package com.timestudio.zhiyuanmovie.ui.activity.movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.adapter.SelectSeatAdapter;
import com.timestudio.zhiyuanmovie.base.BaseActivity;
import com.timestudio.zhiyuanmovie.bean.Movie;
import com.timestudio.zhiyuanmovie.bean.MovieShow;
import com.timestudio.zhiyuanmovie.bean.MyUser;
import com.timestudio.zhiyuanmovie.bean.Seat;
import com.timestudio.zhiyuanmovie.widget.HorizontalListView;
import com.timestudio.zhiyuanmovie.widget.SeatTable;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class SelectSeatActivity extends BaseActivity implements SelectSeatView, View.OnClickListener{

    @Bind(R.id.movie_seat_toolbar)
    Toolbar movie_seat_toolbar;

    @Bind(R.id.st_selectSeat)
    SeatTable st_selectSeat;
    @Bind(R.id.tv_seat_allMoney)
    TextView tv_seat_allMoney;
    @Bind(R.id.tv_seat_money)
    TextView tv_seat_money;
    @Bind(R.id.lv_Selectseat)
    HorizontalListView lv_Selectseat;
    @Bind(R.id.btn_seat_buy)
    Button btn_seat_buy;
    @Bind(R.id.tv_movie_info)
    TextView tv_movie_info;

    private MovieShow movieShow;
    private Movie movie;
    private SelectSeatPresenter presenter = new SelectSeatPresenter();
    private List<Seat> mSeats;
    private List<String> seatName = new ArrayList<String>();
    Boolean seatSold[][] = new Boolean[3][4];
    private SelectSeatAdapter adapter;
    private int allMoney;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(setContent());
        movieShow = (MovieShow) getIntent().getSerializableExtra("show");
        movie = (Movie) getIntent().getSerializableExtra("movie");
        ButterKnife.bind(this);
        setSupportActionBar(movie_seat_toolbar);
        getSupportActionBar().setTitle("");
        adapter = new SelectSeatAdapter(this);
        presenter.attachView(this);
        presenter.getSeatData(movieShow);
        movie_seat_toolbar.setNavigationIcon(R.drawable.back);
        getSupportActionBar().setTitle(movieShow.getMovieName());
        tv_movie_info.setText(movieShow.getShowDate() + " " + movieShow.getShowTime() + " " + movieShow.getLanguage());
        lv_Selectseat.setAdapter(adapter);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
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

    public void initSeatTable() {

        st_selectSeat.setScreenName(movieShow.getShowHall() + "荧幕");
        st_selectSeat.setMaxSelected(4);
        st_selectSeat.setSeatChecker(new SeatTable.SeatChecker() {
            @Override
            public boolean isValidSeat(int row, int column) {
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                return seatSold[row][column];
            }

            @Override
            public void checked(int row, int column) {
                seatName.add((row + 1) + "排" + (column + 1) + "座");
                Log.i("shen", "座位长度---" + seatName.size());
                allMoney += movieShow.getPrice();
                count += 1;
                tv_seat_allMoney.setText(allMoney + " 元");
                tv_seat_money.setText(movieShow.getPrice() + " 元 x" + count + " 张");
                adapter.setSeats(seatName);
                adapter.notifyDataSetChanged();

                if (count > 0) {
                    btn_seat_buy.setEnabled(true);
                    btn_seat_buy.setBackgroundResource(R.drawable.btn_sel_getverification);
                }
            }

            @Override
            public void unCheck(int row, int column) {
                String str = (row + 1) + "排" + (column + 1) + "座";
                allMoney -= movieShow.getPrice();
                count -= 1;
                tv_seat_allMoney.setText(allMoney + " 元");
                tv_seat_money.setText(movieShow.getPrice() + " 元 x" + count + " 张");
                for (int i = 0; i < seatName.size(); i++) {
                    if (seatName.get(i).equals(str)) {
                        seatName.remove(i);
                        break;
                    }
                }
                adapter.setSeats(seatName);
                adapter.notifyDataSetChanged();
                if (count == 0) {
                    btn_seat_buy.setEnabled(false);
                    btn_seat_buy.setBackgroundResource(R.color.colorButtonFalse);
                }
                Log.i("shen", "座位长度---" + seatName.size());
            }

        });
        st_selectSeat.setData(3,4);

    }

    @Override
    public void getSeatDateSuccess(List<Seat> seats) {
        mSeats = seats;
        for (int i = 0; i < seatSold.length; i++) {
            for (int j = 0; j < seatSold[i].length; j++) {
                seatSold[i][j] = mSeats.get(seatSold[i].length * i + j).isSold();
            }
        }
        initSeatTable();
        movie_seat_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void getSeatDateFailure() {

    }

    @Override
    public void onPutOrderSuccess(String orderId) {
        //跳转到支付界面
        Intent intent = new Intent();
        intent.putExtra("orderId", orderId);
        intent.setClass(this, TicketActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onPutOrderFailure() {

    }

    @OnClick(R.id.btn_seat_buy)
    public void onClick(View view) {
        //确认提交订单
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        if (myUser != null) {
            presenter.onVerifiSeatIsSold(movieShow,seatName,allMoney,myUser.getObjectId(),movie);
        }

    }
}
