package com.timestudio.zhiyuanmovie.ui.fragment.find;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.adapter.FindAdapter;
import com.timestudio.zhiyuanmovie.bean.Find;
import com.timestudio.zhiyuanmovie.ui.activity.find.BookingActivity;
import com.timestudio.zhiyuanmovie.ui.activity.find.FileAdvisoryActivity;
import com.timestudio.zhiyuanmovie.ui.activity.find.FindWebActivity;
import com.timestudio.zhiyuanmovie.ui.activity.find.TopTenActivity;
import com.timestudio.zhiyuanmovie.utils.ConnectUtil;
import com.timestudio.zhiyuanmovie.widget.MyPtrClassicFrameLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class FindFragment extends Fragment implements View.OnClickListener,FindView {

    @Bind(R.id.refreshLayout)
    MyPtrClassicFrameLayout refreshLayout;
    @Bind(R.id.rv_find)
    RecyclerView rv_find;
    @Bind(R.id.iv_backTopView)
    ImageView iv_backTopView;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private View view;
    private FindPresenter presenter = new FindPresenter();
    private FindAdapter adapter;


    public FindFragment() {
    }

    public static FindFragment newInstance(String param1, String param2) {
        FindFragment fragment = new FindFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        presenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_find, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycleView();
        initMyPtr();
    }

    @Override
    public void onStart() {
        super.onStart();
        /*当前页面没有数据时,刷新*/
        if (ConnectUtil.isNetworkAvailable(getContext())) {
            if (adapter.getItemCount() <= 1) {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.autoRefresh();
                    }
                }, 200);
            }
        }

    }

    //加载设置 RecycleView
    public void initRecycleView() {
        rv_find.setLayoutManager(new GridLayoutManager(getContext(),1));
        adapter = new FindAdapter();
        rv_find.addOnScrollListener(onScrollListener);
        rv_find.setAdapter(adapter);
        adapter.setItemClickListener(new FindAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(Find find) {
                //点击跳转，跳转到详细的网页内容
                Intent intent = new Intent();
                intent.setClass(getActivity(), FindWebActivity.class);
                intent.putExtra("webUrl", find.getNetUrl());
                startActivity(intent);
            }
        });
        adapter.setTitleClickListener(new FindAdapter.OnTitleClickListener() {
            @Override
            public void onTitleClick(String tag) {
                switch (tag) {
                    case "TOP":
                        //跳转到TOP10
                        Intent Topten = new Intent();
                        Topten.setClass(getActivity(), TopTenActivity.class);
                        startActivity(Topten);
                        break;
                    case "ADVISORY":
                        //跳转到影视资讯
                        Intent fileAdv = new Intent();
                        fileAdv.setClass(getActivity(), FileAdvisoryActivity.class);
                        startActivity(fileAdv);
                        break;
                    case "BOOKING":
                        //跳转到实时票房
                        Intent booking = new Intent();
                        booking.setClass(getActivity(), BookingActivity.class);
                        booking.putExtra("webUrl", "http://m.maoyan.com/newGuide/maoyanpiaofang?f=nohdft&share=Android");
                        startActivity(booking);
                        break;

                }
            }
        });
    }

    //设置ptr下拉刷新，上拉加载更多
    public void initMyPtr() {
        //对ptrFrameLayout的一些设置
        //使用本对象作为key，记录刷新时间，如果两次下拉刷新的时间间隔太短，则不会触发新的刷新
        refreshLayout.setLastUpdateTimeHeaderRelateObject(this);
        //设置刷新时的背景颜色
        refreshLayout.setBackgroundResource(R.color.BackgroundGray);
        //刷新结束后，关闭ptr所需要的时间
        refreshLayout.setDurationToCloseHeader(1000);
        //设置下拉刷新，上滑加载更多
        refreshLayout.setPtrHandler(ptrDefaultHandler2);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick({R.id.iv_backTopView})
    public void onClick(View view) {
        rv_find.scrollToPosition(0);
        iv_backTopView.setVisibility(View.GONE);
    }


    @Override
    public void hideRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.refreshComplete();
                    }
                });
            }
        }).start();
    }

    @Override
    public void addRefreshData(List<Find> data) {
        adapter.setmFind(data);
    }

    @Override
    public void loadMoreData(List<Find> data) {

    }

    @Override
    public void loadDataError() {

    }

    /**
     * PtrClassicFrameLayout的下拉上拉事件
     */
    private PtrDefaultHandler2 ptrDefaultHandler2 = new PtrDefaultHandler2() {
        @Override
        public void onLoadMoreBegin(PtrFrameLayout frame) {
            // 加载更多
            hideRefresh();
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            // 下拉刷新-
            presenter.getMovieAdvisory();
        }
    };

    /**
     * recyclerView滑动事件
     */
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener(){

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
                if (manager.findFirstVisibleItemPosition() > 1) {
                    iv_backTopView.setVisibility(View.VISIBLE);
                } else {
                    iv_backTopView.setVisibility(View.GONE);
                }
            }
        }
    };

}

