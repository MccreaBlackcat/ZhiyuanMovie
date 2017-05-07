package com.timestudio.zhiyuanmovie.ui.activity.find;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.adapter.TopTenAdapter;
import com.timestudio.zhiyuanmovie.base.BaseActivity;
import com.timestudio.zhiyuanmovie.bean.TopTen;
import com.timestudio.zhiyuanmovie.utils.ConnectUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TopTenActivity extends BaseActivity implements TopTenView{


    @Bind(R.id.lv_find_top)
    ListView lv_find_top;
    @Bind(R.id.top_toolbar)
    Toolbar top_toolbar;


    private List<TopTen> topTens;
    private TopTenAdapter adapter ;
    private TopTenPresenter presenter = new TopTenPresenter();

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(top_toolbar);
        getSupportActionBar().setTitle("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContent());
        adapter = new TopTenAdapter(this);
        presenter.attachView(this);
        if (ConnectUtil.isNetworkAvailable(this)) {
            presenter.getTopTenData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void initData() {
        lv_find_top.setAdapter(adapter);
        lv_find_top.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("webUrl", topTens.get(i).getWebUrl());
                intent.setClass(TopTenActivity.this, FindWebActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int setContent() {
        return R.layout.activity_top_ten;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void getTopTenDataSuccess(List<TopTen> topTens) {
        this.topTens = topTens;
        adapter.setmTopTens(topTens);
        initData();
    }

    @Override
    public void getTopTenDataFailure() {

    }
}
