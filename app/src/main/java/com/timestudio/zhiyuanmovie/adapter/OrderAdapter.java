package com.timestudio.zhiyuanmovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.bean.Order;
import com.timestudio.zhiyuanmovie.utils.ImageLoadOptions;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by strongShrn on 2017/5/10.
 */

public class OrderAdapter extends BaseAdapter {

    private List<Order> orders = new ArrayList<>();
    private LayoutInflater inflater;
    private String orderType;
    //isUsed isPaid isRefund isComment
    private String IS_USED = "isUsed";
    private String IS_PAID = "isPaid";
    private String IS_COMMENT = "isComment";
    private String IS_REFUND = "isRefund";
    private String ORDER = "order";
    private ArrayList<Boolean> isChecks = new ArrayList<>();
    private boolean isCheck = false;
    private int count;

    public OrderAdapter(Context context,String orderType) {
        inflater = LayoutInflater.from(context);
        this.orderType = orderType;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        //记录可选数据
        for (int i = 0; i < orders.size(); i++) {
            isChecks.add(false);
        }
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void upDateOrders() {
        //将删除的数据移除掉
        for (int i = 0; i < orders.size(); i++) {
            if (isChecks.get(i)) {
                orders.remove(i);
            }
        }
        //重新设置
        isChecks = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            isChecks.add(false);
        }
    }

    public ArrayList<Boolean> getIsChecks() {
        return isChecks;
    }

    public void setCheckBoxVisible() {
        isCheck = true;
        notifyDataSetChanged();
    }
    public void setCheckBoxGone() {
        isCheck = false;
        cancelSelectAll();
    }

    public void setSelectAll() {
        isChecks.clear();
        for (int i = 0; i < orders.size(); i++) {
            isChecks.add(i, true);
        }
        count = orders.size();
        listener.onCheckChangeCount(count);
        notifyDataSetChanged();
    }

    public void cancelSelectAll() {
        isChecks.clear();
        for (int i = 0; i < orders.size(); i++) {
            isChecks.add(i, false);
        }
        count = 0;
        listener.onCheckChangeCount(count);
        notifyDataSetChanged();
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final MyHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_order_mine, null);
            holder = new MyHolder();
            holder.iv_order_photo = (ImageView) view.findViewById(R.id.iv_order_photo);
            holder.tv_order_name = (TextView) view.findViewById(R.id.tv_order_name);
            holder.tv_order_time = (TextView) view.findViewById(R.id.tv_order_time);
            holder.btn_order_button = (Button) view.findViewById(R.id.btn_order_button);
            holder.ll_order_details = (LinearLayout) view.findViewById(R.id.ll_order_details);
            holder.cb_order_checkBox = (CheckBox) view.findViewById(R.id.cb_order_checkBox);
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
                holder.btn_order_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onBtnClick(i,IS_PAID);
                    }
                });
                break;
            case "isUsed":
                holder.btn_order_button.setVisibility(View.VISIBLE);
                holder.btn_order_button.setText("消费");
                holder.btn_order_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onBtnClick(i,IS_USED);
                    }
                });
                break;
            case "isComment":
                if (orders.get(i).getOrderType().equals("movie")) {
                    holder.btn_order_button.setVisibility(View.VISIBLE);
                    holder.btn_order_button.setText("评论");
                    holder.btn_order_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            listener.onBtnClick(i,IS_COMMENT);
                        }
                    });
                } else if (orders.get(i).getOrderType().equals("shop")) {
                }
                break;
            case "isRefund":
                holder.btn_order_button.setVisibility(View.VISIBLE);
                holder.btn_order_button.setText("取消");
                break;

        }

        holder.ll_order_details.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //长按控件后，将checkBox显示出来
                listener.onLayoutLongClick(i);
                return true;
            }
        });
        if (isCheck) {
            holder.cb_order_checkBox.setVisibility(View.VISIBLE);
            holder.btn_order_button.setVisibility(View.GONE);
        } else {
            holder.cb_order_checkBox.setVisibility(View.GONE);
            if (!orderType.equals("order")) {
                holder.btn_order_button.setVisibility(View.VISIBLE);
            }
        }
        holder.cb_order_checkBox.setChecked(isChecks.get(i));
        holder.cb_order_checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChecks.get(i)) {
                    isChecks.set(i, false);
                    count -= 1;
                    listener.onCheckChangeCount(count);

                } else {
//                    Log.i("shen", isChecked + "");
                    isChecks.set(i, true);
                    count += 1;
                    listener.onCheckChangeCount(count);

                }
            }
        });

        return view;
    }


    class MyHolder {
        ImageView iv_order_photo;
        TextView tv_order_name;
        TextView tv_order_time;
        Button btn_order_button;
        LinearLayout ll_order_details;
        CheckBox cb_order_checkBox;

    }

    private OnClickListener listener;

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener {
        void onBtnClick(int position,String orderType);
        void onLayoutLongClick(int position);
        void onCheckChangeCount(int count);

    }

}
