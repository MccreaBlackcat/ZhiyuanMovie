package com.timestudio.zhiyuanmovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.bean.TopTen;
import com.timestudio.zhiyuanmovie.utils.ImageLoadOptions;

import java.util.List;

/**
 * Created by strongShen on 2017/4/28.
 */

public class TopTenAdapter extends BaseAdapter {

    private List<TopTen> mTopTens ;
    private LayoutInflater inflater;

    public TopTenAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void setmTopTens(List<TopTen> mTopTens) {
        this.mTopTens = mTopTens;
    }

    @Override
    public int getCount() {
        return mTopTens.size();
    }

    @Override
    public Object getItem(int i) {
        return mTopTens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyTopHolder topHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_find_top, null);
            topHolder = new MyTopHolder();
            topHolder.tv_top_title = (TextView) view.findViewById(R.id.tv_top_title);
            topHolder.tv_top_source = (TextView) view.findViewById(R.id.tv_top_source);
            topHolder.tv_top_time = (TextView) view.findViewById(R.id.tv_top_time);
            topHolder.iv_top_photo1 = (ImageView) view.findViewById(R.id.iv_top_photo1);
            topHolder.iv_top_photo2 = (ImageView) view.findViewById(R.id.iv_top_photo2);
            topHolder.iv_top_photo3 = (ImageView) view.findViewById(R.id.iv_top_photo3);
            view.setTag(topHolder);
        } else {
            topHolder = (MyTopHolder) view.getTag();
        }
        topHolder.tv_top_title.setText(mTopTens.get(i).getTitle());
        topHolder.tv_top_source.setText(mTopTens.get(i).getSource());
        topHolder.tv_top_time.setText(mTopTens.get(i).getCreatedAt());
        ImageLoader.getInstance().displayImage(mTopTens.get(i).getPhoto1().getUrl(),
                topHolder.iv_top_photo1,
                ImageLoadOptions.build_item());
        ImageLoader.getInstance().displayImage(mTopTens.get(i).getPhoto2().getUrl(),
                topHolder.iv_top_photo2,
                ImageLoadOptions.build_item());
        ImageLoader.getInstance().displayImage(mTopTens.get(i).getPhoto3().getUrl(),
                topHolder.iv_top_photo3,
                ImageLoadOptions.build_item());
        return view;
    }

    class MyTopHolder {

        private TextView tv_top_title;
        private TextView tv_top_time;
        private TextView tv_top_source;
        private ImageView iv_top_photo1;
        private ImageView iv_top_photo2;
        private ImageView iv_top_photo3;
    }

}
