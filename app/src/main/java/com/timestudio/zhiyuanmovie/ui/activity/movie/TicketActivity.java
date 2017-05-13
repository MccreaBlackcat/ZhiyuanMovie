package com.timestudio.zhiyuanmovie.ui.activity.movie;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.base.BaseActivity;
import com.timestudio.zhiyuanmovie.bean.MovieShow;
import com.timestudio.zhiyuanmovie.bean.Order;
import com.timestudio.zhiyuanmovie.bean.Ticket;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TicketActivity extends BaseActivity implements TicketView{

    @Bind(R.id.tv_ticket_movieName)
    TextView tv_ticket_movieName;
    @Bind(R.id.tv_ticket_showDate)
    TextView tv_ticket_showDate;
    @Bind(R.id.tv_ticket_movieHall)
    TextView tv_ticket_movieHall;
    @Bind(R.id.tv_ticket_seat)
    TextView tv_ticket_seat;
    @Bind(R.id.tv_ticket_number)
    TextView tv_ticket_number;
    @Bind(R.id.tv_ticket_price)
    TextView tv_ticket_price;
    @Bind(R.id.tv_ticket_totalPrice)
    TextView tv_ticket_totalPrice;
    @Bind(R.id.tv_ticket_time)
    TextView tv_ticket_time;
    @Bind(R.id.tv_ticket_id)
    TextView tv_ticket_id;
    @Bind(R.id.btn_buyTicketSure)
    Button btn_buyTicketSure;
    @Bind(R.id.ticket_toolbar)
    Toolbar ticket_toolbar;

    private TicketPresenter presenter = new TicketPresenter();
    private String orderId;
    private Order mOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContent());
        ButterKnife.bind(this);
        setSupportActionBar(ticket_toolbar);
        getSupportActionBar().setTitle("影院");
        mOrder = (Order) getIntent().getSerializableExtra("order");
        if (mOrder != null) {
            orderId = mOrder.getObjectId();
        } else {
            orderId = getIntent().getStringExtra("orderId");
        }
        presenter.attachView(this);
        presenter.getMovieTicket(orderId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    protected int setContent() {
        return R.layout.activity_ticket;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void getTicketSuccess(Ticket ticket, MovieShow show) {
        //更新UI
        tv_ticket_movieName.setText(show.getMovieName());
        tv_ticket_showDate.setText(show.getShowDate() + " " + show.getShowTime() + "-" + show.getEndTime() + "("+show.getLanguage()+")");
        tv_ticket_movieHall.setText(show.getShowHall());
        tv_ticket_seat.setText(ticket.getSeatName());
        tv_ticket_price.setText("单价：" + ticket.getPrice() + " 元");
        tv_ticket_number.setText("数量：" + ticket.getAmount() + " 张");
        tv_ticket_totalPrice.setText("总额：" + ticket.getTotalPrice() + " 元");
        tv_ticket_time.setText("下单时间：" + ticket.getCreatedAt());
        tv_ticket_id.setText("订单号：" + orderId);
        if (mOrder != null && mOrder.isPaid() == true) {
            btn_buyTicketSure.setVisibility(View.GONE);
        } else {
            btn_buyTicketSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //支付
                    presenter.onTicketPay(orderId);
                }
            });
        }
        
    }

    @Override
    public void getTicketFailure() {

    }

    @Override
    public void onPaySuccess() {
        Toast.makeText(this,"支付完成",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onPayFailure() {

    }
}
