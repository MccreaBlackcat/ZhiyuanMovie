package com.timestudio.zhiyuanmovie.ui.fragment.mine;

import android.support.annotation.NonNull;
import android.util.Log;

import com.timestudio.zhiyuanmovie.base.BasePresenter;
import com.timestudio.zhiyuanmovie.bean.MyUser;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by strongShen on 2017/4/26.
 */

public class MinePresenter extends BasePresenter<MineView> {


    /**
     * 获取订单
     */
    public void getOrderData(String userId) {

    }

    /**
     * 更换头像上传文件
     * */
    public void onPutUserPhoto(String picPath, final MyUser myUser) {
        final BmobFile bmobFile = new BmobFile(new File(picPath));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    myUser.setPhoto(bmobFile);
                    myUser.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Log.i("bmob", "更新头像成功，" + e.toString());
                            } else {
                                Log.i("bmob", "更新头像失败，" + e.toString());
                            }
                        }
                    });
                    Log.i("bmob", "上传头像成功，" + e.toString());
                } else {
                    Log.i("bmob", "上传头像失败，" + e.toString());
                }
            }
        });

    }

    @NonNull
    @Override
    protected MineView getNullObject() {
        return null;
    }
}
