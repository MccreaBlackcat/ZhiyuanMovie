package com.timestudio.zhiyuanmovie.ui.activity.find;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.adapter.FileAdvisoryAdapter;
import com.timestudio.zhiyuanmovie.base.BaseActivity;
import com.timestudio.zhiyuanmovie.bean.Find;
import com.timestudio.zhiyuanmovie.ui.fragment.find.FindPresenter;
import com.timestudio.zhiyuanmovie.ui.fragment.find.FindView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FileAdvisoryActivity extends BaseActivity implements FindView{

    @Bind(R.id.adv_toolbar)
    Toolbar adv_toolbar;
    @Bind(R.id.lv_find_advisory)
    ListView lv_find_advisory;

    private FindPresenter presenter;
    private FileAdvisoryAdapter adapter;
    private List<Find> mFind = new ArrayList<Find>();

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(adv_toolbar);
        getSupportActionBar().setTitle("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContent());
        adapter = new FileAdvisoryAdapter(this);
        presenter = new FindPresenter();
        presenter.attachView(this);
        presenter.getMovieAdvisory();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    protected int setContent() {
        return R.layout.activity_file_advisory;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void hideRefresh() {

    }

    @Override
    public void addRefreshData(List<Find> data) {
        mFind = data;
        adapter.setmFind(data);
        lv_find_advisory.setAdapter(adapter);
        lv_find_advisory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("webUrl", mFind.get(i).getNetUrl());
                intent.setClass(FileAdvisoryActivity.this, FindWebActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void loadMoreData(List<Find> data) {

    }

    @Override
    public void loadDataError() {

    }
}
