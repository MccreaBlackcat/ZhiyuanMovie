package com.timestudio.zhiyuanmovie.ui.fragment.mine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.timestudio.zhiyuanmovie.R;
import com.timestudio.zhiyuanmovie.adapter.MineAdapter;
import com.timestudio.zhiyuanmovie.bean.MyUser;
import com.timestudio.zhiyuanmovie.bean.Order;
import com.timestudio.zhiyuanmovie.ui.activity.mine.MineVipActivity;
import com.timestudio.zhiyuanmovie.ui.activity.mine.OrderActivity;
import com.timestudio.zhiyuanmovie.ui.activity.mine.QuestionActivity;
import com.timestudio.zhiyuanmovie.ui.activity.mine.SettingActivity;
import com.timestudio.zhiyuanmovie.ui.activity.user.login.LoginActivity;
import com.timestudio.zhiyuanmovie.utils.ImageLoadOptions;
import com.timestudio.zhiyuanmovie.utils.VipManager;
import com.timestudio.zhiyuanmovie.widget.PicWindow;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class MineFragment extends Fragment implements View.OnClickListener,MineView,MineAdapter.onBtnClickLitstener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


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

    private File headFile;
    private Bitmap user_head;
    private PicWindow popup;
    private MinePresenter presenter = new MinePresenter();
    private String ORDER_TYPE = "orderType";

    public MineFragment() {
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
        }
        popup = new PicWindow(getActivity(),picListener);
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this,view);
        mineAdapter = new MineAdapter();
        rv_mine.setLayoutManager(new GridLayoutManager(getContext(),1));
        rv_mine.setAdapter(mineAdapter);
        if (myUser != null) {
            tv_user_name.setText(myUser.getUsername());
            tv_vip_name.setText(VipManager.getInstance().getVipName(myUser.getVipIntegral()));
            if ((myUser.getPhoto()!= null)) {
                ImageLoader.getInstance().displayImage(myUser.getPhoto().getUrl(),
                        iv_mine_photo,
                        ImageLoadOptions.build_item());
            }

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
            //用户已经登录，进入个人中心或者更换头像
            switch (view.getId()) {
                case R.id.iv_mine_photo:
                    if (popup != null && popup.isShowing()) {
                        popup.dismiss();
                    } else {
                        popup.show();
                    }
                    break;
                case R.id.tv_user_name:
                    break;
            }

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
        if (requestCode == 0x001) {
            //登录成功后，更新用户UI
            myUser = BmobUser.getCurrentUser(MyUser.class);
            tv_user_name.setText(myUser.getUsername());
            tv_vip_name.setText(VipManager.getInstance().getVipName(myUser.getVipIntegral()));
            if (myUser.getPhoto() != null) {
                ImageLoader.getInstance().displayImage(myUser.getPhoto().getUrl(),
                        iv_mine_photo,
                        ImageLoadOptions.build_item());
            }
            //获取订单数据

        } else if (requestCode == CropHelper.REQUEST_CROP || requestCode == CropHelper.REQUEST_CAMERA) {
            // 帮助我们去处理结果(剪切完的图像)
            CropHelper.handleResult(cropHandler, requestCode, resultCode, data);
        } else if (requestCode == 0x002) {
            //设置中 改变了信息，更新到UI
            myUser = BmobUser.getCurrentUser(MyUser.class);
            if (myUser == null) {
                tv_user_name.setText("立即登录");
                tv_vip_name.setText("会员等级");
            } else {
                tv_user_name.setText(myUser.getUsername());
                tv_vip_name.setText(VipManager.getInstance().getVipName(myUser.getVipIntegral()));
            }

        }
    }
    //popupWindow的监听
    private PicWindow.Listener picListener = new PicWindow.Listener() {
        @Override
        public void toGallery() {
            /*从相册选择*/
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            Intent intent1 = CropHelper.buildCropFromGalleryIntent(cropHandler.getCropParams());
            startActivityForResult(intent1, CropHelper.REQUEST_CROP);
        }

        @Override
        public void toCamera() {
            /*从相机选择*/
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            Intent intent = CropHelper.buildCaptureIntent(cropHandler.getCropParams().uri);
            startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
        }
    };


    /*图片裁剪*/
    private final CropHandler cropHandler = new CropHandler() {
        @Override
        public void onPhotoCropped(Uri uri) {
            headFile = new File(uri.getPath());
            user_head = BitmapFactory.decodeFile(headFile.getAbsolutePath());
            iv_mine_photo.setImageBitmap(user_head);
            presenter.onPutUserPhoto(uri.getPath(),myUser);
            popup.dismiss();
        }

        @Override
        public void onCropCancel() {
        }

        @Override
        public void onCropFailed(String message) {
        }

        @Override
        public CropParams getCropParams() {
            CropParams cropParams = new CropParams();
            cropParams.aspectX = 400;
            cropParams.aspectY = 400;
            return cropParams;
        }

        @Override
        public Activity getContext() {
            return getActivity();
        }
    };



    @Override
    public void onViewClick(int index) {
        Intent intent;
        switch (index) {
            case 0x1001:
                intent = new Intent();
                intent.setClass(getActivity(), OrderActivity.class);
                intent.putExtra(ORDER_TYPE, "order");
                startActivity(intent);
                break;
            case 0x1002:
                intent = new Intent();
                intent.setClass(getActivity(), OrderActivity.class);
                intent.putExtra(ORDER_TYPE, "isPaid");
                startActivity(intent);
                break;
            case 0x1003:
                intent = new Intent();
                intent.setClass(getActivity(), OrderActivity.class);
                intent.putExtra(ORDER_TYPE, "isUsed");
                startActivity(intent);
                break;
            case 0x1004:
                intent = new Intent();
                intent.setClass(getActivity(), OrderActivity.class);
                intent.putExtra(ORDER_TYPE, "isComment");
                startActivity(intent);
                break;
            case 0x1005:
                intent = new Intent();
                intent.setClass(getActivity(), OrderActivity.class);
                intent.putExtra(ORDER_TYPE, "isRefund");
                startActivity(intent);
                break;
            case 1:
                intent = new Intent();
                intent.setClass(getActivity(), MineVipActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent();
                intent.setClass(getActivity(), OrderActivity.class);
                intent.putExtra(ORDER_TYPE, "isComment");
                startActivity(intent);
                break;
            case 3:
                intent = new Intent();
                intent.setClass(getActivity(), QuestionActivity.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent();
                intent.setClass(getActivity(), SettingActivity.class);
                startActivityForResult(intent,0x002);
                break;

        }
    }

    @Override
    public void getOrderSuccess(List<Order> orders) {

    }

    @Override
    public void getOrderFailure() {

    }
}
