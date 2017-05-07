package com.timestudio.zhiyuanmovie.utils;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.timestudio.zhiyuanmovie.R;

/**
 * 设置imageLoader加载选项
 */

public class ImageLoadOptions {

    private ImageLoadOptions() {
    }

    private static DisplayImageOptions gallery_options;

    public static DisplayImageOptions galleryBuild() {
        if (gallery_options == null) {
            gallery_options = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.default_portrait)/*如果空url显示什么*/
                    .showImageOnLoading(R.drawable.default_portrait)/*加载中显示什么*/
                    .showImageOnFail(R.drawable.default_portrait)/*加载失败显示什么*/
                    .cacheOnDisk(true)/*开启硬盘缓存*/
                    .cacheInMemory(true)/*开启内存缓存*/
                    .resetViewBeforeLoading(true)/*加载前重置ImageView*/
                    .build();
        }
        return gallery_options;
    }


    private static DisplayImageOptions options;

    /**
     * 用户头像加载选项
     */
    public static DisplayImageOptions build() {
        if (options == null) {
            options = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.mipmap.mine_photo_default)/*如果空url显示什么*/
                    .showImageOnLoading(R.mipmap.mine_photo_default)/*加载中显示什么*/
                    .showImageOnFail(R.mipmap.mine_photo_default)/*加载失败显示什么*/
                    .cacheOnDisk(true)/*开启硬盘缓存*/
                    .cacheInMemory(true)/*开启内存缓存*/
                    .resetViewBeforeLoading(true)/*加载前重置ImageView*/
                    .build();
        }
        return options;
    }


    private static DisplayImageOptions options_item;

    /**
     * 图片加载选项
     */
    public static DisplayImageOptions build_item() {
        if (options_item == null) {
            options_item = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.default_portrait)/*如果空url显示什么*/
                    .showImageOnLoading(R.drawable.default_portrait)/*加载中显示什么*/
                    .showImageOnFail(R.drawable.default_portrait)/*加载失败显示什么*/
                    .cacheOnDisk(true)/*开启硬盘缓存*/
                    .cacheInMemory(true)/*开启内存缓存*/
                    .resetViewBeforeLoading(true)/*加载前重置ImageView*/
                    .build();
        }
        return options_item;
    }
}
