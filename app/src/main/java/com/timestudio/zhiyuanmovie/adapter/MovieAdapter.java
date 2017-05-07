package com.timestudio.zhiyuanmovie.adapter;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.bean.Banner;
import com.timestudio.zhiyuanmovie.bean.Movie;
import com.timestudio.zhiyuanmovie.utils.ImageLoadOptions;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by strongShen on 2017/5/3.
 */

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>implements View.OnClickListener {

    private List<Movie> movies ;
    private List<Banner> banners;
    private String MOVIE_TYPE_REVEAL = "reveal";
    private String MOVIE_TYPE_WAIT = "wait";



    private int[] mViewType = {0x001,0x002};
    private MovieBannerAdapter bannerAdapter;
    private Context context;
    private OnTypeClickListener typeClickListener;
    private OnBannerClickListener bannerClickListener;
    private boolean istype;



    public void setMovies(List<Movie> movies,boolean istype) {
        this.movies = movies;
        this.istype = istype;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    public void clearMoviesData() {
        movies.clear();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == 1) {
            return mViewType[position];
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == mViewType[0]) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_banner, null);
            context = parent.getContext();
            return new BannerHolder(view);
        } else if (viewType == mViewType[1]) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_type, null);
            return new MovieTypeHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_item, null);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BannerHolder) {
            BannerHolder bannerHolder = (BannerHolder) holder;
            bannerHolder.rpv_banner.setAnimationDurtion(1000);  //设置切换时间
            bannerAdapter = new MovieBannerAdapter(bannerHolder.rpv_banner); //设置适配器
            bannerAdapter.setmList(banners);
            bannerAdapter.setListener(new MovieBannerAdapter.OnBannerClickListener() {
                @Override
                public void onBannerClick(Banner banner) {
                    //用eventbus发送一条消息
                    bannerClickListener.onBannerClick(banner);
                }
            });
            bannerHolder.rpv_banner.setAdapter(bannerAdapter);
            bannerHolder.rpv_banner.setHintView(new ColorPointHintView(context
                    , Color.WHITE, Color.GRAY));// 设置圆点指示器颜色
        } else if (holder instanceof MovieHolder) {
            MovieHolder movieHolder = (MovieHolder) holder;
            movieHolder.tv_movie_name.setText(movies.get(position - 2).getMovieName());
            movieHolder.tv_movie_introduction.setText(movies.get(position - 2).getIntroduction());
            ImageLoader.getInstance().displayImage(movies.get(position - 2).getPhoto().getUrl()
            ,movieHolder.iv_movie_photo
            , ImageLoadOptions.build_item());
            if (!istype) {
                movieHolder.btn_movie_buy.setVisibility(View.VISIBLE);
                movieHolder.btn_movie_buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        buyClickListener.onBuyClick(movies.get(position - 2));
                    }
                });
            } else {
                movieHolder.btn_movie_buy.setVisibility(View.GONE);
            }

            movieHolder.ll_movie_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(movies.get(position - 2));
                }
            });

        } else if (holder instanceof MovieTypeHolder) {
            final MovieTypeHolder typeHolder = (MovieTypeHolder) holder;
            typeHolder.ll_movie_reveal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    typeHolder.tv_movie_wait.setTextColor(context.getResources().getColor(R.color.colorButtonFalse));
                    typeHolder.tv_movie_reveal.setTextColor(context.getResources().getColor(R.color.colorButtonTrue));
                    typeHolder.tv_movie_reveal_line.setVisibility(View.VISIBLE);
                    typeHolder.tv_movie_wait_line.setVisibility(View.GONE);
                    typeClickListener.onTypeClick(MOVIE_TYPE_REVEAL);
                }
            });
            typeHolder.ll_movie_wait.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    typeHolder.tv_movie_reveal.setTextColor(context.getResources().getColor(R.color.colorButtonFalse));
                    typeHolder.tv_movie_wait.setTextColor(context.getResources().getColor(R.color.colorButtonTrue));
                    typeHolder.tv_movie_wait_line.setVisibility(View.VISIBLE);
                    typeHolder.tv_movie_reveal_line.setVisibility(View.GONE);
                    typeClickListener.onTypeClick(MOVIE_TYPE_WAIT);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return movies.size() + 2;
    }

    @Override
    public void onClick(View view) {

    }

    class MovieHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ll_movie_item)
        LinearLayout ll_movie_item;
        @Bind(R.id.iv_movie_photo)
        ImageView iv_movie_photo;
        @Bind(R.id.tv_movie_name)
        TextView tv_movie_name;
        @Bind(R.id.tv_movie_introduction)
        TextView tv_movie_introduction;
        @Bind(R.id.btn_movie_buy)
        Button btn_movie_buy;

        public MovieHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class MovieTypeHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.ll_movie_reveal)
        LinearLayout ll_movie_reveal;
        @Bind(R.id.ll_movie_wait)
        LinearLayout ll_movie_wait;
        @Bind(R.id.tv_movie_reveal)
        TextView tv_movie_reveal;
        @Bind(R.id.tv_movie_reveal_line)
        TextView tv_movie_reveal_line;
        @Bind(R.id.tv_movie_wait)
        TextView tv_movie_wait;
        @Bind(R.id.tv_movie_wait_line)
        TextView tv_movie_wait_line;

        public MovieTypeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class BannerHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.rpv_banner)
        RollPagerView rpv_banner;

        public BannerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }



    public void setTypeClickListener(OnTypeClickListener typeClickListener) {
        this.typeClickListener = typeClickListener;
    }

    public interface OnTypeClickListener {
        void onTypeClick(String type);
    }

    private OnMovieItemClickListener itemClickListener;

    public void setItemClickListener(OnMovieItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnMovieItemClickListener {
        void onItemClick(Movie movie);

    }

    private OnMovieBuyClickListener buyClickListener;

    public void setBuyClickListener(OnMovieBuyClickListener buyClickListener) {
        this.buyClickListener = buyClickListener;
    }

    public interface OnMovieBuyClickListener {
        void onBuyClick(Movie movie);
    }



    public void setBannerClickListener(OnBannerClickListener bannerClickListener) {
        this.bannerClickListener = bannerClickListener;
    }

    public interface OnBannerClickListener {
        void onBannerClick(Banner banner);
    }



}

