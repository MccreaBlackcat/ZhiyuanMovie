package com.timestudio.zhiyuanmovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.bean.Order;
import com.timestudio.zhiyuanmovie.bean.Ticket;
import com.timestudio.zhiyuanmovie.utils.ImageLoadOptions;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by hasee on 2017/5/10.
 */

public class OrderAdapter extends BaseAdapter {

    private List<Order> orders = new ArrayList<>();
    private LayoutInflater inflater;
    private String orderType;

    public OrderAdapter(Context context,String orderType) {
        inflater = LayoutInflater.from(context);
        this.orderType = orderType;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }


    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int i) {
        return orders.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_order_mine, null);
            holder = new MyHolder();
            holder.iv_order_photo = (ImageView) view.findViewById(R.id.iv_order_photo);
            holder.tv_order_name = (TextView) view.findViewById(R.id.tv_order_name);
            holder.tv_order_time = (TextView) view.findViewById(R.id.tv_order_time);
            holder.btn_order_button = (Button) view.findViewById(R.id.btn_order_button);
            view.setTag(holder);
        } else {
            holder = (MyHolder) view.getTag();
        }
        //设置UI
        holder.tv_order_name.setText(orders.get(i).getOrderName());
        holder.tv_order_time.setText(orders.get(i).getCreatedAt());
        ImageLoader.getInstance().displayImage(orders.get(i).getPhoto(),
                holder.iv_order_photo,
                ImageLoadOptions.build_item());
        //判断订单类型
        if (orders.get(i).getOrderType().equals("movie")) {

        } else if (orders.get(i).getOrderType().equals("shop")) {

        }
        //isUsed isPaid isRefund isComment
        switch (orderType) {
            case "order":

                break;
            case "isPaid":
                holder.btn_order_button.setVisibility(View.VISIBLE);
                holder.btn_order_button.setText("付款");
                break;
            case "isUsed":
                holder.btn_order_button.setVisibility(View.VISIBLE);
                holder.btn_order_button.setText("消费");
                break;
            case "isComment":
                if (orders.get(i).getOrderType().equals("movie")) {
                    holder.btn_order_button.setVisibility(View.VISIBLE);
                    holder.btn_order_button.setText("评论");
                } else if (orders.get(i).getOrderType().equals("shop")) {
                }
                break;
            case "isRefund":
                holder.btn_order_button.setVisibility(View.VISIBLE);
                holder.btn_order_button.setText("取消");
                break;

        }

        return view;
    }


    class MyHolder {
        ImageView iv_order_photo;
        TextView tv_order_name;
        TextView tv_order_time;
        Button btn_order_button;
    }
}
