package com.timestudio.zhiyuanmovie.ui.fragment.movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.adapter.MovieAdapter;
import com.timestudio.zhiyuanmovie.adapter.MovieBannerAdapter;
import com.timestudio.zhiyuanmovie.bean.Banner;
import com.timestudio.zhiyuanmovie.bean.Movie;
import com.timestudio.zhiyuanmovie.bean.MovieDetails;
import com.timestudio.zhiyuanmovie.bean.MovieShow;
import com.timestudio.zhiyuanmovie.ui.activity.movie.MovieDetailsActivity;
import com.timestudio.zhiyuanmovie.ui.activity.movie.MovieShowActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieFragment extends Fragment implements MovieView,MovieAdapter.OnBannerClickListener
        ,MovieAdapter.OnTypeClickListener
        ,MovieAdapter.OnMovieItemClickListener,MovieAdapter.OnMovieBuyClickListener{

    @Bind(R.id.rv_movie)
    RecyclerView rv_movie;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private View view;
    private MoviePresenter presenter;
    private List<Banner> mBanners;
    private List<Movie> mMovies;
    private MovieAdapter adapter;
    private boolean isType = false;


    public MovieFragment() {
        // Required empty public constructor
    }

    public static MovieFragment newInstance(String param1, String param2) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MovieAdapter();
        presenter = new MoviePresenter();
        presenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        rv_movie.setLayoutManager(new GridLayoutManager(getContext(),1));
        presenter.getBannerData();
        adapter.setTypeClickListener(this);
        adapter.setItemClickListener(this);
        adapter.setBuyClickListener(this);
        adapter.setBannerClickListener(this);

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void getBannerSuccess(List<Banner> banners) {
        mBanners = banners;
        adapter.setBanners(mBanners);
        //获取 Banner 成功后获取影片数据
        presenter.getHotMovieData();
    }

    @Override
    public void getBannerFailure() {

    }

    @Override
    public void getMovieFailure() {

    }

    @Override
    public void getMovieSuccess(List<Movie> movies) {

        if (mMovies == null) {
            mMovies = movies;
            adapter.setMovies(mMovies,isType);
            rv_movie.setAdapter(adapter);
        } else if (mMovies != null) {
            mMovies = movies;
            adapter.clearMoviesData();
            adapter.setMovies(mMovies,isType);
        }
        adapter.notifyDataSetChanged();


    }

    @Override
    public void onBannerClick(Banner banner) {
        //点击 Banner 跳转到对应电影的详细界面
        Intent intent = new Intent();
        intent.putExtra("movieName",banner.getMovieName());
        intent.putExtra("status","待映");
        intent.setClass(getActivity(), MovieDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onTypeClick(String type) {
        if (type.equals("reveal")) {
            if (isType) {
                isType = false;
                presenter.getHotMovieData();
            }
        } else if (type.equals("wait")) {
            if (!isType) {
                isType = true;
                presenter.getWaitMovieData();
            }
        }
    }

    @Override
    public void onItemClick(Movie movie) {
        //跳转到电影详情页面
        Intent intent = new Intent();
        intent.putExtra("movieName",movie.getMovieName());
        Log.i("shen", movie.getStatus());
        intent.putExtra("status",movie.getStatus());
        intent.setClass(getActivity(), MovieDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBuyClick(Movie movie) {
        //跳转到电影购买界面
        Toast.makeText(getActivity(), "购买"+movie.getMovieName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setClass(getActivity(), MovieShowActivity.class);
        intent.putExtra("movieName", movie.getMovieName());
        startActivity(intent);

    }
}
