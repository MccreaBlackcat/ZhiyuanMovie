package com.timestudio.zhiyuanmovie.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.bean.Order;
import com.timestudio.zhiyuanmovie.bean.ShopOrder;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hasee on 2017/4/26.
 */

public class ShopOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Order mOrder;
    private List<ShopOrder> mList;
    private int[] ItemType = {0x0001, 0x0002};


    public void setmOrder(Order mOrder) {
        this.mOrder = mOrder;
    }

    public void setmList(List<ShopOrder> mList) {
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ItemType[0]) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_order_title, null);
            return new OrderTitleHolder(view);
        } else if (viewType == ItemType[1]) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_order_end, null);
            return new OrderEndHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_order_details, null);
        return new OrderMiddleHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OrderTitleHolder) {
            OrderTitleHolder titleHolder = (OrderTitleHolder) holder;
            titleHolder.tv_orderId.setText(mOrder.getObjectId());
        } else if (holder instanceof OrderEndHolder) {
            OrderEndHolder endHolder = (OrderEndHolder) holder;
            endHolder.tv_allPrice.setText(mOrder.getTotalPrice() + "元");
            endHolder.tv_orderTime.setText(mOrder.getCreatedAt());
        } else if (holder instanceof OrderMiddleHolder) {
            OrderMiddleHolder middleHolder = (OrderMiddleHolder) holder;
            middleHolder.tv_shopName.setText(mList.get(position - 1).getShopName());
            middleHolder.tv_shopUnitPrice.setText(mList.get(position - 1).getUnitPrice() + "");
            middleHolder.tv_shopAmount.setText(mList.get(position - 1).getAmount() + "");
            middleHolder.tv_shopTotalPrice.setText(mList.get(position - 1).getTotalPrice() + "");
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return ItemType[0];
        } else if (position >= mList.size() + 1) {
            return ItemType[1];
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mList.size() + 2;
    }

    /**
     * 第一部分的holder
     * */
    class OrderTitleHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_orderId)
        TextView tv_orderId;

        public OrderTitleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    /**
     * 第二部分的holder
     */
    class OrderMiddleHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_shopName)
        TextView tv_shopName;
        @Bind(R.id.tv_shopUnitPrice)
        TextView tv_shopUnitPrice;
        @Bind(R.id.tv_shopAmount)
        TextView tv_shopAmount;
        @Bind(R.id.tv_shopTotalPrice)
        TextView tv_shopTotalPrice;

        public OrderMiddleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    /**
     * 第三部分的holder
     */
    class OrderEndHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_orderTime)
        TextView tv_orderTime;
        @Bind(R.id.tv_allPrice)
        TextView tv_allPrice;

        public OrderEndHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }



}
