package com.timestudio.zhiyuanmovie.adapter;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.timestudio.zhiyuanmovie.bean.Banner;
import com.timestudio.zhiyuanmovie.utils.ImageLoadOptions;

import java.util.List;

/**
 * Created by strongShen on 2017/5/3.
 */

public class MovieBannerAdapter extends LoopPagerAdapter{


    private List<Banner> mList;
    private OnBannerClickListener listener;

    public MovieBannerAdapter(RollPagerView viewPager) {
        super(viewPager);
    }

    public void setmList(List<Banner> mList) {
        this.mList = mList;
    }

    @Override
    public View getView(ViewGroup container, final int position) {

        ImageView view = new ImageView(container.getContext());
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //用回调，或者EventBus将点击事件传递，跳转Activity
                listener.onBannerClick(mList.get(position));
            }
        });
        ImageLoader.getInstance().displayImage(mList.get(position).getPhoto().getUrl()
                ,view
                , ImageLoadOptions.build_item());
        return view;
    }

    @Override
    public int getRealCount() {
        return mList.size();
    }

    public void setListener(OnBannerClickListener listener) {
        this.listener = listener;
    }

    public interface OnBannerClickListener {

        void onBannerClick(Banner banner);
    }

}
