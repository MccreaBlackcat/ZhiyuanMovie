package com.timestudio.zhiyuanmovie.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.timestudio.zhiyuanmovie.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by StrongShen on 2017/4/18.
 */

public class MineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private int ItemType = 0x001;
    private String[] mine = {"我的会员", "看过的电影", "帮助", "设置"};
    private onBtnClickLitstener lisener;

    @Override
    public int getItemViewType(int position) {

        if (position == 0){
            return ItemType;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ItemType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mine_order, null);
            return new MyOrderHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mine_button, null);
        return new MyButtonHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //订单
        if (holder instanceof MyOrderHolder) {
            MyOrderHolder myOrderHolder = (MyOrderHolder) holder;

            myOrderHolder.ll_myOrder.setOnClickListener(this);
            myOrderHolder.ll_mine_order_unpaied.setOnClickListener(this);
            myOrderHolder.ll_mine_order_unused.setOnClickListener(this);
            myOrderHolder.ll_mine_order_uncomment.setOnClickListener(this);
            myOrderHolder.ll_mine_order_refund.setOnClickListener(this);

        } else if (holder instanceof MyButtonHolder) {
            //其他按钮
            MyButtonHolder myButtonHolder = (MyButtonHolder) holder;
            myButtonHolder.tv_mine_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lisener.onViewClick(position);
                }
            });
            myButtonHolder.tv_mine_button.setText(mine[position - 1]);

        }

    }

    @Override
    public int getItemCount() {
        return mine.length + 1;
    }

    @Override
    public void onClick(View view) {
        //通过回调，将view的ID返回的UI，跳转到相应的Activity
        switch (view.getId()) {
            case R.id.ll_myOrder:
                lisener.onViewClick(0x1001);
                break;
            case R.id.ll_mine_order_unpaied:
                lisener.onViewClick(0x1002);
                break;
            case R.id.ll_mine_order_unused:
                lisener.onViewClick(0x1003);
                break;
            case R.id.ll_mine_order_uncomment:
                lisener.onViewClick(0x1004);
                break;
            case R.id.ll_mine_order_refund:
                lisener.onViewClick(0x1005);
                break;

        }
    }

    public class MyOrderHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ll_myOrder)
        LinearLayout ll_myOrder;
        @Bind(R.id.ll_mine_order_unpaied)
        LinearLayout ll_mine_order_unpaied;
        @Bind(R.id.ll_mine_order_unused)
        LinearLayout ll_mine_order_unused;
        @Bind(R.id.ll_mine_order_uncomment)
        LinearLayout ll_mine_order_uncomment;
        @Bind(R.id.ll_mine_order_refund)
        LinearLayout ll_mine_order_refund;
        public MyOrderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class MyButtonHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_mine_button)
        TextView tv_mine_button;
        @Bind(R.id.tv_mine_description)
        TextView tv_mine_description;

        public MyButtonHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface onBtnClickLitstener {
        void onViewClick(int index);
    }

    public void setOnClickListener(onBtnClickLitstener lisener) {
        this.lisener = lisener;
    }

}
