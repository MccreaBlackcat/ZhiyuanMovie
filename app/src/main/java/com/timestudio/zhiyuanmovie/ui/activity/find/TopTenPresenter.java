package com.timestudio.zhiyuanmovie.ui.activity.find;

import android.support.annotation.NonNull;

import com.timestudio.zhiyuanmovie.base.BasePresenter;
import com.timestudio.zhiyuanmovie.bean.TopTen;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by strongShen on 2017/4/28.
 */

public class TopTenPresenter extends BasePresenter<TopTenView> {



    /**
     * 查询TopTen数据
     * */
    public void getTopTenData() {
        BmobQuery<TopTen> query = new BmobQuery<TopTen>();
        query.findObjects(new FindListener<TopTen>() {
            @Override
            public void done(List<TopTen> list, BmobException e) {
                if (e == null) {
                    getView().getTopTenDataSuccess(list);
                }
            }
        });
    }


    @NonNull
    @Override
    protected TopTenView getNullObject() {
        return null;
    }
}
