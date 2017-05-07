package com.timestudio.zhiyuanmovie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.bean.MovieShow;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by strongShen on 2017/5/6.
 */

public class MovieShowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<MovieShow> movieShows;
    private OnBuyClickListener listener;

    public void setMovieShows(List<MovieShow> movieShows) {
        this.movieShows = movieShows;
    }

    public List<MovieShow> getMovieShows() {
        return movieShows;
    }

    public void setListener(OnBuyClickListener listener) {
        this.listener = listener;
    }

    public void clearMovieShows() {
        movieShows.clear();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movieshow, null);
        return new ShowHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ShowHolder showHolder = (ShowHolder) holder;
        showHolder.tv_showtime.setText(movieShows.get(position).getShowTime());
        showHolder.tv_endTime.setText(movieShows.get(position).getEndTime());
        showHolder.tv_showLanguage.setText(movieShows.get(position).getLanguage());
        showHolder.tv_showHall.setText(movieShows.get(position).getShowHall());
        showHolder.tv_showPrice.setText(movieShows.get(position).getPrice() + "元");
        showHolder.btn_show_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onBuyClick(movieShows.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieShows.size();
    }


    class ShowHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_showtime)
        TextView tv_showtime;
        @Bind(R.id.tv_endTime)
        TextView tv_endTime;
        @Bind(R.id.tv_showLanguage)
        TextView tv_showLanguage;
        @Bind(R.id.tv_showHall)
        TextView tv_showHall;
        @Bind(R.id.tv_showPrice)
        TextView tv_showPrice;
        @Bind(R.id.btn_show_buy)
        Button btn_show_buy;

        public ShowHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnBuyClickListener {
        void onBuyClick(MovieShow show);
    }

}
