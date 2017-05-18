package com.timestudio.zhiyuanmovie.ui.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.base.BaseActivity;
import com.timestudio.zhiyuanmovie.bean.MyUser;
import com.timestudio.zhiyuanmovie.utils.VipManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

public class MineVipActivity extends BaseActivity {

    @Bind(R.id.iv_vip_bg)
    ImageView iv_vip_bg;
    @Bind(R.id.iv_vip_lvl)
    ImageView iv_vip_lvl;
    @Bind(R.id.iv_vip_name)
    ImageView iv_vip_name;
    @Bind(R.id.tv_vip_JiFen)
    TextView tv_vip_JiFen;
    @Bind(R.id.mineVip_toolbar)
    Toolbar mineVip_toolbar;

    private MyUser myUser = BmobUser.getCurrentUser(MyUser.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContent());
        initView();
        initListener();
        initData();
    }

    @Override
    protected int setContent() {
        return R.layout.activity_mine_vip;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        setSupportActionBar(mineVip_toolbar);
        getSupportActionBar().setTitle("会员中心");
        mineVip_toolbar.setNavigationIcon(R.drawable.back);

    }

    @Override
    protected void initListener() {
        mineVip_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initData() {
        tv_vip_JiFen.setText(myUser.getVipIntegral() + "");
        switch (VipManager.getInstance().getVipName(myUser.getVipIntegral())) {
            case "V2 白银会员":
                iv_vip_bg.setBackgroundResource(R.drawable.ic_vip_level2_bang);
                iv_vip_lvl.setBackgroundResource(R.drawable.ic_vip_level2_medal);
                iv_vip_name.setBackgroundResource(R.drawable.ic_vip_level2_des);
                break;
            case "V3 黄金会员":
                iv_vip_bg.setBackgroundResource(R.drawable.ic_vip_level3_bang);
                iv_vip_lvl.setBackgroundResource(R.drawable.ic_vip_level3_medal);
                iv_vip_name.setBackgroundResource(R.drawable.ic_vip_level3_des);
                break;
            case "V4 白金会员":
                iv_vip_bg.setBackgroundResource(R.drawable.ic_vip_level4_bang);
                iv_vip_lvl.setBackgroundResource(R.drawable.ic_vip_level4_medal);
                iv_vip_name.setBackgroundResource(R.drawable.ic_vip_level4_des);
                break;
            case "V5 钻石会员":
                iv_vip_bg.setBackgroundResource(R.drawable.ic_vip_level5_bang);
                iv_vip_lvl.setBackgroundResource(R.drawable.ic_vip_level5_medal);
                iv_vip_name.setBackgroundResource(R.drawable.ic_vip_level5_des);
                break;
            case "V1 黄铜会员":
                iv_vip_lvl.setBackgroundResource(R.drawable.ic_mine_vip);
                break;
        }

    }
}
