package com.timestudio.zhiyuanmovie.adapter;

import android.content.pm.ProviderInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.bean.Find;
import com.timestudio.zhiyuanmovie.utils.ImageLoadOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by strongShen on 2017/4/27.
 */

public class FindAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<Find> mFind = new ArrayList<Find>();
    private int mViewType = 0x001;
    private String[] tag = {"TOP","ADVISORY","BOOKING"};



    private OnItemClickListener itemClickListener;
    private OnTitleClickListener titleClickListener;


    public void setmFind(List<Find> mFind) {
        this.mFind = mFind;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return mViewType;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == mViewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_title, null);
            return new TitleHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_list, null);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TitleHolder) {
            TitleHolder titleHolder = (TitleHolder) holder;
            titleHolder.tv_find_top.setOnClickListener(this);
            titleHolder.tv_find_advisory.setOnClickListener(this);
            titleHolder.tv_find_bookingOffice.setOnClickListener(this);
        } else if (holder instanceof ListHolder) {
            ListHolder listHolder = (ListHolder) holder;
            listHolder.tv_find_listTitle.setText(mFind.get(position - 1).getTitle());
            listHolder.tv_find_listSource.setText(mFind.get(position - 1).getSource());
            listHolder.tv_find_listTime.setText(mFind.get(position - 1).getCreatedAt());
            ImageLoader.getInstance().displayImage(mFind.get(position - 1).getPhoto().getUrl()
                    , listHolder.iv_find_listPic
                    ,ImageLoadOptions.build());
            listHolder.ll_find_listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClickListener(mFind.get(position - 1));
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mFind.size() + 1;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_find_top:
                titleClickListener.onTitleClick(tag[0]);
                //跳转到TOP10
                break;
            case R.id.tv_find_advisory:
                titleClickListener.onTitleClick(tag[1]);
                //跳转到影视资讯
                break;
            case R.id.tv_find_bookingOffice:
                titleClickListener.onTitleClick(tag[2]);
                //跳转到实时票房
                break;
        }

    }

    class TitleHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_find_top)
        TextView tv_find_top;
        @Bind(R.id.tv_find_advisory)
        TextView tv_find_advisory;
        @Bind(R.id.tv_find_bookingOffice)
        TextView tv_find_bookingOffice;

        public TitleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class ListHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_find_listTitle)
        TextView tv_find_listTitle;
        @Bind(R.id.tv_find_listSource)
        TextView tv_find_listSource;
        @Bind(R.id.tv_find_listTime)
        TextView tv_find_listTime;
        @Bind(R.id.iv_find_listPic)
        ImageView iv_find_listPic;
        @Bind(R.id.ll_find_listItem)
        LinearLayout ll_find_listItem;

        public ListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(Find find);
    }

    public void setTitleClickListener(OnTitleClickListener titleClickListener) {
        this.titleClickListener = titleClickListener;
    }

    public interface OnTitleClickListener {
        void onTitleClick(String tag);
    }


}
