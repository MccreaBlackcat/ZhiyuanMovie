package com.timestudio.zhiyuanmovie.ui.fragment.find;

import android.support.annotation.NonNull;

import com.timestudio.zhiyuanmovie.base.BasePresenter;
import com.timestudio.zhiyuanmovie.bean.Find;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by strongShen on 2017/4/27.
 */

public class FindPresenter extends BasePresenter<FindView> {



    /**
     * 获取影视数据
     *
     * */
    public void getMovieAdvisory() {
        BmobQuery<Find> query = new BmobQuery<Find>();
        query.findObjects(new FindListener<Find>() {
            @Override
            public void done(List<Find> list, BmobException e) {
                getView().addRefreshData(list);
                getView().hideRefresh();
            }
        });
    }


    @NonNull
    @Override
    protected FindView getNullObject() {
        return null;
    }
}
