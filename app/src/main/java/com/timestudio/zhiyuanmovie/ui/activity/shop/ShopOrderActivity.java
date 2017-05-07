package com.timestudio.zhiyuanmovie.ui.activity.shop;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.adapter.ShopOrderAdapter;
import com.timestudio.zhiyuanmovie.base.BaseActivity;
import com.timestudio.zhiyuanmovie.bean.Order;
import com.timestudio.zhiyuanmovie.bean.ShopOrder;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopOrderActivity extends BaseActivity implements ShopOrderView,View.OnClickListener{


    @Bind(R.id.order_toolbar)
    Toolbar order_toolbar;
    @Bind(R.id.rv_orderDetails)
    RecyclerView rv_orderDetails;
    @Bind(R.id.btn_pay)
    Button btn_pay;

    private String orderId;
    private ShopOrderPresenter presenter = new ShopOrderPresenter();
    private Order mOrder;
    private ShopOrderAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContent());
        orderId = getIntent().getStringExtra("orderId");
        presenter.attachView(this);
        presenter.getOrderData(orderId);
        adapter = new ShopOrderAdapter();
        rv_orderDetails.setLayoutManager(new GridLayoutManager(this,1));
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(order_toolbar);
        getSupportActionBar().setTitle("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    protected int setContent() {
        return R.layout.activity_shop_order;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void getShopOrderSuccess(List<ShopOrder> list) {
        //获取成功，给适配器设置数据，给ListView设置适配器
        adapter.setmList(list);
        rv_orderDetails.setAdapter(adapter);
    }

    @Override
    public void getShopOrderFailure() {

    }

    @Override
    public void onPaidSuccess() {
        setResult(0x002);
        finish();
    }

    @Override
    public void onPaidFailure() {

    }

    @Override
    public void getOrderSuccess(Order order) {
        mOrder = order;
        adapter.setmOrder(order);
        presenter.getOrderDetails(orderId);
        if (!order.isPaid()) {
            btn_pay.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getOrderFailure() {

    }

    @OnClick({R.id.btn_pay})
    public void onClick(View view) {
        presenter.onOrderPay(orderId,mOrder);
    }
}
