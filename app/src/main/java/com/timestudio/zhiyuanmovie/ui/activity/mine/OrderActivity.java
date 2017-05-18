package com.timestudio.zhiyuanmovie.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.adapter.OrderAdapter;
import com.timestudio.zhiyuanmovie.base.BaseActivity;
import com.timestudio.zhiyuanmovie.bean.Order;
import com.timestudio.zhiyuanmovie.ui.activity.movie.TicketActivity;
import com.timestudio.zhiyuanmovie.ui.activity.shop.ShopOrderActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderActivity extends BaseActivity implements OrderView,OrderAdapter.OnClickListener,View.OnClickListener {

    @Bind(R.id.lv_order)
    ListView lv_order;
    @Bind(R.id.mine_order_toolbar)
    Toolbar mine_order_toolbar;
    @Bind(R.id.ll_order_deleted)
    LinearLayout ll_order_deleted;
    @Bind(R.id.tv_order_allSelect)
    TextView tv_order_allSelect;
    @Bind(R.id.tv_order_deleted)
    TextView tv_order_deleted;
    @Bind(R.id.tv_deleted_cancel)
    TextView tv_deleted_cancel;

    private String orderType;
    private OrderAdapter adapter;
    private OrderPresenter presenter = new OrderPresenter();
    private List<Order> mOrders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContent());

        initView();
        initListener();
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
    }

    @Override
    protected void initListener() {
        adapter.setListener(this);
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

    @Override
    public void onUseSuccess() {
        Toast.makeText(this,"消费成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeletedSuccess() {
        Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBtnClick(int position, String orderType) {
        Intent intent;
        if (mOrders.get(position).getOrderType().equals("movie")) {
            if (orderType.equals("isComment")) {
                //跳转到评论界面
                intent = new Intent();
                intent.setClass(this, CommentActivity.class);
                intent.putExtra("order", mOrders.get(position));
                startActivity(intent);
            } else if (orderType.equals("isUsed")) {
                //更新数据库数据
                presenter.onUsedUpdateOrder(mOrders.get(position));
            } else {
                intent = new Intent();
                intent.setClass(OrderActivity.this, TicketActivity.class);
                intent.putExtra("order", mOrders.get(position));
                startActivity(intent);
            }

        } else if (mOrders.get(position).getOrderType().equals("shop")) {
            intent = new Intent();
            intent.setClass(OrderActivity.this, ShopOrderActivity.class);
            intent.putExtra("orderId", mOrders.get(position).getObjectId());
            startActivity(intent);
        }
    }

    @Override
    public void onLayoutLongClick(int position) {
        //显示控件
        tv_deleted_cancel.setVisibility(View.VISIBLE);
        ll_order_deleted.setVisibility(View.VISIBLE);
        adapter.setCheckBoxVisible();
    }

    @Override
    public void onCheckChangeCount(int count) {
        if (count == 0) {
            tv_order_deleted.setText("删除");
        } else {
            tv_order_deleted.setText("删除(" + count + ")");
        }

    }

    @OnClick({R.id.tv_order_allSelect,R.id.tv_order_deleted,R.id.tv_deleted_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_order_allSelect:
                //全选
                adapter.setSelectAll();
//                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_order_deleted:
                //删除
                ArrayList<Boolean> booleens = adapter.getIsChecks();
                presenter.onDeletedOrder(booleens,mOrders);
                break;
            case R.id.tv_deleted_cancel:
                //取消,隐藏控件
                adapter.setCheckBoxGone();
                tv_deleted_cancel.setVisibility(View.GONE);
                ll_order_deleted.setVisibility(View.GONE);
                break;
        }
    }
}
