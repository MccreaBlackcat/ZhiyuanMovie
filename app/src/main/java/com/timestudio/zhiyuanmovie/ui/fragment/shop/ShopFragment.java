package com.timestudio.zhiyuanmovie.ui.fragment.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.adapter.ShopAdapter;
import com.timestudio.zhiyuanmovie.bean.Shop;
import com.timestudio.zhiyuanmovie.bean.ShopOrder;
import com.timestudio.zhiyuanmovie.ui.activity.shop.ShopOrderActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;


public class ShopFragment extends Fragment implements ShopView,View.OnClickListener{


    @Bind(R.id.lv_shop)
    ListView lv_shop;
    @Bind(R.id.tv_shop_totalPrice)
    TextView tv_shop_totalPrice;
    @Bind(R.id.btn_shop_subTotal)
    Button btn_shop_subTotal;


    private View view;
    private ShopPresenter presenter = new ShopPresenter();
    private List<Shop> mData;
    private List<ShopOrder> mShopOrder = new ArrayList<ShopOrder>();
    private ShopAdapter adapter;
    private Shop mshop;
    private int priceTotal = 0;

    public ShopFragment() {
        // Required empty public constructor
    }


    public static ShopFragment newInstance() {
        ShopFragment fragment = new ShopFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        presenter.attachView(this);
        presenter.getGoods();
        initData();
    }

    public void initData() {
        adapter = new ShopAdapter(getContext());
        //设置回调监听，监听购买商品数量的变化和总的价钱变化
        adapter.setListener(new ShopAdapter.OnAddsubClickListener() {
            @Override
            public void onButtonClick(String tag,Shop shop,int num) {
                mshop = shop;
                //判断回调传过来的是 + 还是 - 的标记
                if (tag.equals("add")) {
                    priceTotal += shop.getPrice();
                    //判断，如果数据链表中没有数据的话，直接添加一条新的数据
                    if (mShopOrder.size() == 0) {
                        ShopOrder shopOrder = new ShopOrder();
                        shopOrder.setShopName(shop.getGoodsName());
                        shopOrder.setUnitPrice(shop.getPrice());
                        shopOrder.setAmount(num);
                        shopOrder.setTotalPrice(shop.getPrice()*num);
                        mShopOrder.add(shopOrder);
                    } else {
                        //如果数据链表中存在数据，那就重新设置该对象的数量和总价
                        int i;
                        for (i = 0; i < mShopOrder.size(); i++) {
                            if (shop.getGoodsName().equals(mShopOrder.get(i).getShopName())) {
                                mShopOrder.get(i).setAmount(num);
                                mShopOrder.get(i).setTotalPrice(shop.getPrice()*num);
                                break;
                            }
                        }
                        //数据链表中没有找到对应的对象，把这个对象添加到数据链表
                        if (i == mShopOrder.size()) {
                            ShopOrder shopOrder = new ShopOrder();
                            shopOrder.setShopName(shop.getGoodsName());
                            shopOrder.setUnitPrice(shop.getPrice());
                            shopOrder.setAmount(num);
                            shopOrder.setTotalPrice(shop.getPrice()*num);
                            mShopOrder.add(shopOrder);
                        }
                    }
                } else if (tag.equals("sub")) {
                    priceTotal -= shop.getPrice();
                    for (int i = 0; i < mShopOrder.size(); i++) {
                        if (shop.getGoodsName().equals(mShopOrder.get(i).getShopName())) {
                            if (num == 0) {
                                mShopOrder.remove(i);
                                break;
                            } else {
                                mShopOrder.get(i).setAmount(num);
                                mShopOrder.get(i).setTotalPrice(shop.getPrice()*num);
                            }
                            break;
                        }
                    }
                }
                if (priceTotal > 0) {
                    btn_shop_subTotal.setEnabled(true);
                    btn_shop_subTotal.setBackgroundResource(R.drawable.btn_sel_getverification);
                } else if (priceTotal == 0) {
                    btn_shop_subTotal.setEnabled(false);
                    btn_shop_subTotal.setBackgroundResource(R.color.colorButtonFalse);
                }
                tv_shop_totalPrice.setText("￥" + priceTotal);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_shop, container, false);
        ButterKnife.bind(this,view);
        return view;
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void getGoodsSuccess(List<Shop> datas) {
        //获取商品信息成功后，将数据设置到ListView的适配器
        mData = datas;
        adapter.setmList(mData);
        lv_shop.setAdapter(adapter);

    }

    @Override
    public void submitSuccess(String orderId) {
        Toast.makeText(getActivity(), "下单成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setClass(getActivity(), ShopOrderActivity.class);
        intent.putExtra("orderId",orderId);
        startActivityForResult(intent,0x002);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x002) {
            //支付成功，重新刷新界面，数据归零
            Toast.makeText(getActivity(), "支付成功", Toast.LENGTH_SHORT).show();
            tv_shop_totalPrice.setText("￥00.00");
            btn_shop_subTotal.setEnabled(false);
            btn_shop_subTotal.setBackgroundResource(R.color.colorButtonFalse);
            adapter.notifyDataSetChanged();
            lv_shop.setAdapter(adapter);
        }
    }

    @Override
    public void submitFailure(String msg) {
        Toast.makeText(getActivity(), "下单失败", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.btn_shop_subTotal})
    public void onClick(View view) {
        //要提交订单，先判断用户是都登录，用户没有登录，就提示用户要先登录才能提交订单
        //要正确提交订单，需要数据：用户ID、购物车数据、总的价格
        BmobUser bmobUser = BmobUser.getCurrentUser();
        if (bmobUser == null) {
            Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
        } else {
            presenter.submitTotal(priceTotal,mShopOrder,bmobUser.getObjectId(),mshop);
        }
        //测试
//        presenter.submitTotal(priceTotal,mShopOrder,"a78bb4bf5e");


    }
}
