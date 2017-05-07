package com.timestudio.zhiyuanmovie.ui.fragment.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.adapter.MineAdapter;
import com.timestudio.zhiyuanmovie.bean.MyUser;
import com.timestudio.zhiyuanmovie.ui.activity.user.login.LoginActivity;
import com.timestudio.zhiyuanmovie.ui.activity.user.regist.RegistView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class MineFragment extends Fragment implements View.OnClickListener,RegistView,MineAdapter.onBtnClickLitstener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private View view;
    private MineAdapter mineAdapter;
    @Bind(R.id.rv_mine)
    RecyclerView rv_mine;
    @Bind(R.id.iv_mine_photo)
    ImageView iv_mine_photo;
    @Bind(R.id.tv_user_name)
    TextView tv_user_name;
    @Bind(R.id.tv_vip_name)
    TextView tv_vip_name;

    MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
    private String[] vipLvlName = {"普通用户","V1 黄铜会员","V2 白银会员","V3 黄金会员","V4 白金会员","V5 钻石会员","V6 黑钻会员"};

    public MineFragment() {
        // Required empty public constructor
    }

    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this,view);
        mineAdapter = new MineAdapter();
        rv_mine.setLayoutManager(new GridLayoutManager(getContext(),1));
        rv_mine.setAdapter(mineAdapter);
        if (myUser != null) {
            tv_user_name.setText(myUser.getUsername());
//        tv_vip_name.setText(vipLvlName[Integer.parseInt(myUser.getVipLvl())]);
            tv_vip_name.setText(vipLvlName[4]);
        }
        mineAdapter.setOnClickListener(this);
        return view;
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick({R.id.iv_mine_photo,R.id.tv_user_name})
    public void onClick(View view) {
        //根据用户偏好，判断用户是否登录

        if (myUser != null) {
            //用户已经登录，进入个人中心

        } else {
            //用户未登录，跳转到登录Activity
            Intent intent = new Intent();
            intent.setClass(getActivity(), LoginActivity.class);
            startActivityForResult(intent,0x001);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 0x001) {
                myUser = BmobUser.getCurrentUser(MyUser.class);
                tv_user_name.setText(myUser.getUsername());
                int vip = Integer.parseInt(myUser.getVipLvl());
                tv_vip_name.setText(vipLvlName[vip]);

            }
        }
    }

    @Override
    public void registSuccess() {

    }

    @Override
    public void registFailure() {

    }

    @Override
    public void onViewClick(int index) {
        switch (index) {
            case 0x1001:
                break;
            case 0x1002:
                break;
            case 0x1003:
                break;
            case 0x1004:
                break;
            case 0x1005:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                BmobUser.logOut();
                myUser = BmobUser.getCurrentUser(MyUser.class);
                tv_user_name.setText("立即登录");
                tv_vip_name.setText("会员等级");
                break;

        }
    }

}
