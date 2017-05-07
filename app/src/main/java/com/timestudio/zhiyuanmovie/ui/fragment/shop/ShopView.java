package com.timestudio.zhiyuanmovie.ui.fragment.shop;

import com.timestudio.zhiyuanmovie.base.BaseMvpView;
import com.timestudio.zhiyuanmovie.bean.Shop;

import java.util.List;

/**
 * Created by hasee on 2017/4/24.
 */

public interface ShopView extends BaseMvpView {

    void getGoodsSuccess(List<Shop> datas);

    void submitSuccess(String orderId);

    void submitFailure(String msg);

    ShopView NULL = new ShopView() {
        @Override
        public void getGoodsSuccess(List<Shop> datas) {

        }

        @Override
        public void submitSuccess(String orderId) {

        }

        @Override
        public void submitFailure(String msg) {

        }
    };

}
