package com.timestudio.zhiyuanmovie.utils;

import android.app.ActivityManager;
import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobSMS;

/**
 * Created by strongShen on 2017/4/19.
 */

public class ZhiyuanApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        //第一：默认初始化
//        Bmob.initialize(this, "Your Application ID");
        if (isAppProcess()) {
            //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
            BmobConfig config =new BmobConfig.Builder(this)
                    ////设置appkey
                    .setApplicationId("f8b1b5b7fe6a0d9f40cbd3f6ce4abaec")
                    ////请求超时时间（单位为秒）：默认15s
                    .setConnectTimeout(30)
                    ////文件分片上传时每片的大小（单位字节），默认512*1024
                    .setUploadBlockSize(1024*1024)
                    ////文件的过期时间(单位为秒)：默认1800s
                    .setFileExpiration(2500)
                    .build();
            Bmob.initialize(config);

            /* 加载imageLoader */
            DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)/*开启内存缓存*/
                    .cacheOnDisk(true)/*开启硬盘缓存*/
                    .resetViewBeforeLoading(true)/*在ImageView加载前清除它上面之前的图片*/
                    .build();
            ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                    .memoryCacheSize(4 * 1024 * 1024)/*设置内存缓存的大小(4M)*/
                    .defaultDisplayImageOptions(displayImageOptions)
                    .build();
            ImageLoader.getInstance().init(configuration);
        }



    }

    /**
     * 判断Application是否在App进程中启动。
     */
    private boolean isAppProcess() {
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);

        // 如果APP启用了远程的service，此application:onCreate会被调用2次，例如使用百度定位服务时。
        // 默认的APP进程名为APP包名，如果查到的process name不是APP的process name就返回false。

        if (processAppName == null || !processAppName.equalsIgnoreCase(getPackageName())) {
            // 则此application::onCreate 是被service 调用的。
            return false;
        }
        return true;
    }


    // 获取进程名称
    private String getAppName(int pID) {
        String processName;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processes = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : processes) {

            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }
}
