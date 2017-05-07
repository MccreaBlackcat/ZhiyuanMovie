package com.timestudio.zhiyuanmovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

/**
 * Created by strongShen on 2017/4/28.
 */

public class FileAdvisoryAdapter extends BaseAdapter {

    private List<Find> mFind = new ArrayList<Find>();
    private LayoutInflater inflater;

    public FileAdvisoryAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void setmFind(List<Find> mFind) {
        this.mFind = mFind;
    }

    @Override
    public int getCount() {
        return mFind.size();
    }

    @Override
    public Object getItem(int i) {
        return mFind.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_find_list, null);
            holder = new MyHolder();
            holder.tv_find_listTitle = (TextView) view.findViewById(R.id.tv_find_listTitle);
            holder.tv_find_listSource = (TextView) view.findViewById(R.id.tv_find_listSource);
            holder.tv_find_listTime = (TextView) view.findViewById(R.id.tv_find_listTime);
            holder.iv_find_listPic = (ImageView) view.findViewById(R.id.iv_find_listPic);
            view.setTag(holder);
        } else {
            holder = (MyHolder) view.getTag();
        }
        holder.tv_find_listTitle.setText(mFind.get(i).getTitle());
        holder.tv_find_listSource.setText(mFind.get(i).getSource());
        holder.tv_find_listTime.setText(mFind.get(i).getCreatedAt());
        ImageLoader.getInstance().displayImage(mFind.get(i).getPhoto().getUrl(),
                holder.iv_find_listPic,
                ImageLoadOptions.build_item());
        return view;
    }

    class MyHolder {

        @Bind(R.id.tv_find_listTitle)
        TextView tv_find_listTitle;
        @Bind(R.id.tv_find_listSource)
        TextView tv_find_listSource;
        @Bind(R.id.tv_find_listTime)
        TextView tv_find_listTime;
        @Bind(R.id.iv_find_listPic)
        ImageView iv_find_listPic;
    }

}
