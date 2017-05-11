package com.timestudio.zhiyuanmovie.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.timestudio.zhiyuanmovie.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by strongShen on 2017/5/8.
 */

public class SelectSeatAdapter extends BaseAdapter {


    private List<String> seats = new ArrayList<String>();
    private LayoutInflater inflater;

    public SelectSeatAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setSeats(List<String> seats) {
        this.seats = seats;
        Log.i("shen", "座位长度--adapter-" + seats.size());
    }

    public void clearSeats() {
        seats.clear();
    }

    @Override
    public int getCount() {
        return seats.size();
    }

    @Override
    public Object getItem(int i) {
        return seats.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        MySeatNameHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_select_seat, null);
            holder = new MySeatNameHolder();
            holder.tv_seat_name = (TextView) view.findViewById(R.id.tv_seat_name);
            holder.iv_seat_disable = (ImageView) view.findViewById(R.id.iv_seat_disable);
            view.setTag(holder);
        } else {
            holder = (MySeatNameHolder) view.getTag();
        }
        holder.tv_seat_name.setText(seats.get(i));
//        holder.iv_seat_disable.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                seats.remove(i);
//                notifyDataSetChanged();
//            }
//        });
        return view;
    }

    class MySeatNameHolder {
        TextView tv_seat_name ;
        ImageView iv_seat_disable ;
    }
}
