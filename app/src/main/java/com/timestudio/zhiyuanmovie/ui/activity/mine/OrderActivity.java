package com.timestudio.zhiyuanmovie.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.adapter.OrderAdapter;
import com.timestudio.zhiyuanmovie.base.BaseActivity;
import com.timestudio.zhiyuanmovie.bean.Order;
import com.timestudio.zhiyuanmovie.bean.Ticket;
import com.timestudio.zhiyuanmovie.ui.activity.movie.TicketActivity;
import com.timestudio.zhiyuanmovie.ui.activity.shop.ShopOrderActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderActivity extends BaseActivity implements OrderView{

    @Bind(R.id.lv_order)
    ListView lv_order;
    @Bind(R.id.mine_order_toolbar)
    Toolbar mine_order_toolbar;

    private String orderType;
    private OrderAdapter adapter;
    private OrderPresenter presenter = new OrderPresenter();
    private List<Order> mOrders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContent());
        orderType = getIntent().getStringExtra("orderType");
        ButterKnife.bind(this);
        presenter.attachView(this);
        setSupportActionBar(mine_order_toolbar);
        //isPaid isUsed isComment  isRefund
        switch (orderType) {
            case "order":
                getSupportActionBar().setTitle("总订单");
                break;
            case "isPaid":
                getSupportActionBar().setTitle("待付款");
                break;
            case "isUsed":
                getSupportActionBar().setTitle("待消费");
                break;
            case "isComment":
                getSupportActionBar().setTitle("未评论");
                break;
            case "isRefund":
                getSupportActionBar().setTitle("退款");
                break;
        }

        mine_order_toolbar.setNavigationIcon(R.drawable.back);
        mine_order_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter = new OrderAdapter(this,orderType);
        initData();

    }

    private void initData() {
        //设置listview的Item点击事件
        lv_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                if (mOrders.get(i).getOrderType().equals("movie")) {
                    intent = new Intent();
                    intent.setClass(OrderActivity.this, TicketActivity.class);
                    intent.putExtra("order", mOrders.get(i));
                    startActivity(intent);
                } else if (mOrders.get(i).getOrderType().equals("shop")) {
                    intent = new Intent();
                    intent.setClass(OrderActivity.this, ShopOrderActivity.class);
                    intent.putExtra("orderId", mOrders.get(i).getObjectId());
                    startActivity(intent);
                }
            }
        });

        if (orderType.equals("order")) {
            presenter.getAllOrderData();
        } else {
            presenter.getOrderOnBoolean(orderType);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    protected int setContent() {
        return R.layout.activity_order;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void getAllOrderSuccess(List<Order> orders) {
        mOrders = orders;
        adapter.setOrders(orders);
        lv_order.setAdapter(adapter);
    }

    @Override
    public void getOrderOnBooleanSuccess(List<Order> orders) {
        mOrders = orders;
        adapter.setOrders(orders);
        lv_order.setAdapter(adapter);
    }

    @Override
    public void getOrderFailure() {

    }
}
