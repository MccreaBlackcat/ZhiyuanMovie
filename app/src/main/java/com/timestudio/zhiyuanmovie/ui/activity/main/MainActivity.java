package com.timestudio.zhiyuanmovie.ui.activity.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.base.BaseActivity;
import com.timestudio.zhiyuanmovie.ui.fragment.find.FindFragment;
import com.timestudio.zhiyuanmovie.ui.fragment.mine.MineFragment;
import com.timestudio.zhiyuanmovie.ui.fragment.movie.MovieFragment;
import com.timestudio.zhiyuanmovie.ui.fragment.shop.ShopFragment;
import com.timestudio.zhiyuanmovie.utils.ConnectUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainView, View.OnClickListener {

    @Bind(R.id.main_toolbar)
    Toolbar main_toolbar;
    @Bind(R.id.tv_movie_title)
    TextView tv_title;
    @Bind(R.id.fl_fragment)
    FrameLayout fl_fragment;
    @Bind({R.id.tv_movie, R.id.tv_shop, R.id.tv_find, R.id.tv_mine})
    TextView[] tv;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MovieFragment movieFragment = MovieFragment.newInstance("","");
    private ShopFragment shopFragment = ShopFragment.newInstance();
    private FindFragment findFragment = FindFragment.newInstance("","");
    private MineFragment mineFragment = MineFragment.newInstance("","");
    private Fragment[] fragment = {movieFragment,shopFragment,findFragment,mineFragment};
    private Fragment currentFrag;

    private long l_firstClickTime;
    private long l_secondClickTime;
    private boolean isExit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(setContent());
        //进入主界面，设置将第一个默认为选中状态
        tv[0].setSelected(true);
        //设置选中后字体的颜色
        tv[0].setTextColor(getResources().getColor(R.color.colorMainRed));
        if (!ConnectUtil.isNetworkAvailable(this)) {
            Toast.makeText(this, "当前无网络", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onContentChanged() {

        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(main_toolbar);
        getSupportActionBar().setTitle("");

    }

    @Override
    protected int setContent() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        //加载电影fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        if (movieFragment != null) {
            fragmentTransaction.add(R.id.fl_fragment, movieFragment);
            fragmentTransaction.commit();
            currentFrag = movieFragment;
        }


    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.tv_movie, R.id.tv_shop, R.id.tv_find, R.id.tv_mine})
    public void onClick(View view) {
        for (int i = 0; i < tv.length; i++) {
            tv[i].setSelected(false);
            tv[i].setTextColor(getResources().getColor(R.color.mainText));
            tv[i].setTag(i);
        }
        view.setSelected(true);
        ((TextView) view).setTextColor(getResources().getColor(R.color.colorMainRed));
        tv_title.setText(tv[(int) view.getTag()].getText());
        if ((int) view.getTag() == 3) {
            main_toolbar.setVisibility(View.GONE);
        } else {
            main_toolbar.setVisibility(View.VISIBLE);
        }
        if (fragment[(int) view.getTag()] != currentFrag) {
            fragmentTransaction = fragmentManager.beginTransaction();
            if (!fragment[(int) view.getTag()].isAdded()) {
                fragmentTransaction.hide(currentFrag);
                fragmentTransaction.add(R.id.fl_fragment, fragment[(int) view.getTag()]);
                fragmentTransaction.commit();

            } else {
                fragmentTransaction.hide(currentFrag);
                fragmentTransaction.show(fragment[(int) view.getTag()]);
                fragmentTransaction.commit();
            }
            currentFrag = fragment[(int) view.getTag()];
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        doubleClickExit(keyCode, event);
        return true;
    }

    /**
     * @description 双击退出程序的函数
     */
    private void doubleClickExit(int keyCode,KeyEvent event){
        //当用户第一次点击返回按钮时
        if(keyCode == KeyEvent.KEYCODE_BACK && isExit == false){
            isExit = true;//设置记录标志为true
            l_firstClickTime = System.currentTimeMillis();//获取第一次点击退出键的时间
            //显示再次点击退出提示
            Toast.makeText(this,"再次点击退出",Toast.LENGTH_SHORT).show();
        }
        //用户第二次点击返回键
        else if(keyCode == KeyEvent.KEYCODE_BACK && isExit == true){
            l_secondClickTime = System.currentTimeMillis();//记录第二次点击退出的时间
            //时间差在2秒内，退出程序
            if(( l_secondClickTime - l_firstClickTime ) < 2000){
                finish();
            }else {
                isExit = false; //重置记录退出标志
                doubleClickExit(keyCode,event); //超出2000ms时，重新开始逻辑函数
            }
        }
    }
}
