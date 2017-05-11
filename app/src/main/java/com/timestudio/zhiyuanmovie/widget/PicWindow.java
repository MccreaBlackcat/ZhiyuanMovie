package com.timestudio.zhiyuanmovie.widget;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;


import com.timestudio.zhiyuanmovie.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 切换头像弹出的popupWindow
 */

public class PicWindow extends PopupWindow {


    public interface Listener {
        /**
         * 来自相册
         */
        void toGallery();

        /**
         * 来自相机
         */
        void toCamera();
    }

    private final Activity activity;

    private final Listener listener;

    public PicWindow(final Activity activity, Listener listener) {

        super(activity.getLayoutInflater().inflate(R.layout.consult_popupwindow, null),
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this, getContentView());
        this.activity = activity;
        this.listener = listener;
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setAnimationStyle(R.style.mypopwindow_anim_style);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
    }

    public void show() {
        showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        setBackgroundAlpha(0.5f);
    }

    @OnClick({R.id.consult_popu_insert, R.id.popup_second, R.id.consult_popu_cancel})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.consult_popu_insert:
                listener.toCamera();
                break;
            case R.id.popup_second:
                listener.toGallery();
                break;
            case R.id.consult_popu_cancel:
                dismiss();
                break;
        }
    }


    /**
     * 修改背景可见度
     */
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha;
        activity.getWindow().setAttributes(lp);
    }

}
