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
import com.timestudio.zhiyuanmovie.bean.Shop;
import com.timestudio.zhiyuanmovie.utils.ImageLoadOptions;

import java.util.List;

/**
 * Created by strongShen on 2017/4/24.
 */

public class ShopAdapter extends BaseAdapter {

    private List<Shop> mList;  //商品数据
    private LayoutInflater inflater;
    private int[] num ;   //记录每件商品购买的数量
    private OnAddsubClickListener listener;  // + - 的点击监听对象


    public ShopAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * 设置商品数据
     * */
    public void setmList(List<Shop> mList) {
        this.mList = mList;
        num = new int[mList.size()];
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final MyHolder holder;
        if (view == null) {
            holder = new MyHolder();
            view = inflater.inflate(R.layout.item_shop_goods, null);
            holder.tv_goodsName = (TextView) view.findViewById(R.id.tv_goodsName);
            holder.tv_goodsDis = (TextView) view.findViewById(R.id.tv_goodsDiscrip);
            holder.tv_goodsPrice = (TextView) view.findViewById(R.id.tv_goods_Price);
            holder.tv_number = (TextView) view.findViewById(R.id.tv_goodsNumber);
            holder.iv_NumberSub = (ImageView) view.findViewById(R.id.iv_goodsNumber_sub);
            holder.iv_NumberAdd = (ImageView) view.findViewById(R.id.iv_goodsNumber_add);
            holder.iv_goodsPhoto = (ImageView) view.findViewById(R.id.iv_goodsPhoto);
            view.setTag(holder);
        } else {
            holder = (MyHolder) view.getTag();
        }
        num[i] = Integer.parseInt((String) holder.tv_number.getText());
        holder.tv_goodsName.setText(mList.get(i).getGoodsName());
        holder.tv_goodsDis.setText(mList.get(i).getDiscription());
        holder.tv_goodsPrice.setText(mList.get(i).getPrice() + "元");
        ImageLoader.getInstance().displayImage(mList.get(i).getGoodsPhoto(),holder.iv_goodsPhoto, ImageLoadOptions.build_item());
        holder.iv_NumberAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //回调
                num[i] += 1;
                if (num[i] > 0) {
                    holder.iv_NumberSub.setEnabled(true);
                }
                holder.tv_number.setText(num[i] + "");
                listener.onButtonClick("add",mList.get(i),num[i]);
            }
        });
        holder.iv_NumberSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //回调
                if (num[i] == 0) {
                    holder.iv_NumberSub.setEnabled(false);
                } else {
                    num[i] -= 1;
                    holder.tv_number.setText(num[i] + "");
                    listener.onButtonClick("sub",mList.get(i),num[i]);
                }
            }
        });
        return view;
    }

    /**
     * 用以控件复用的ViewHolder
     * */
    class MyHolder {
        ImageView iv_goodsPhoto;
        TextView tv_goodsName;
        TextView tv_goodsDis;
        TextView tv_goodsPrice;
        ImageView iv_NumberSub;
        ImageView iv_NumberAdd;
        TextView tv_number;
    }


    /**
     * 设置回调监听
     * */
    public void setListener(OnAddsubClickListener listener) {
        this.listener = listener;
    }

    /**
     * tag 标记是点击的是 + 还是 -
     * shop 获取的对象
     * num 当前点击对象的数量
     * */
    public interface OnAddsubClickListener {

        void onButtonClick(String tag,Shop shop,int num);
    }

}
