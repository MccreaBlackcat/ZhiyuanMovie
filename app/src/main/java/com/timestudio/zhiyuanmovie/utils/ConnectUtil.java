package com.timestudio.zhiyuanmovie.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ShenGqiang on 2016/10/24.
 */

public class ConnectUtil {



    /**
     * @description 检测用户名是否符合规格
     * @param name 用户名
     * @return 返回结果，true为符合，反之。
     */
    public static boolean inspectUserName(String name) {
        Pattern pattern = Pattern
                .compile("^[a-zA-Z0-9]{1,20}$");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
    /**
     * @description 检测用户名是否符合规格
     * @param phone 用户名
     * @return 返回结果，true为符合，反之。
     */
    public static boolean inspectPhoneNumber(String phone) {
        Pattern pattern = Pattern
                .compile("^[0-9]{11}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * @desciprion 检测用户密码是否符合规格
     * @param password 用户密码
     * @return 返回结果，true为符合，反之。
     */
    public static boolean inspectPassWord(String password) {
        Pattern pattern = Pattern
                .compile("^[a-zA-Z0-9]{6,16}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * @description 检测用户填写的邮箱是否符合规范
     * @param emails 用户填写的邮箱地址
     * @return 返回结果，true为符合，反之。
     */
    public static boolean inspectEmails(String emails) {
        Pattern pattern = Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)" +
                        "|(([a-zA-Z0-9\\-]+\\.)+))" +
                        "([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher matcher = pattern.matcher(emails);
        return matcher.matches();
    }

    /**
     * @description 判断是否有网络
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        //判断有无网络
        if (info == null || !info.isAvailable()) {
            return false;
        }
        //返回 true 为有网络
        return true;
    }

    /**
     * 将图片裁剪成圆形
     * @param bitmap
     * @return
     */
    public static Bitmap makeBitmapCircle(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int left = 0, top = 0, right = width, bottom = height;
        float roundPx = height/2;
        // 宽高相同
        if (width > height) {
            left = (width - height)/2;
            top = 0;
            right = left + height;
            bottom = height;
        } else if (height > width) {
            left = 0;
            top = (height - width)/2;
            right = width;
            bottom = top + width;
            roundPx = width/2;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(left, top, right, bottom);
        // 创建一个矩形
        RectF rectF = new RectF(rect);
        // 抗锯齿
        paint.setAntiAlias(true);
//        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.RED);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 获取当前时间
     */
    public static String getTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());
        return time;
    }
    /**
     * 获取时间
     * @return
     */
    public static String getDate(String time){
        String data = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date oDate = simpleDateFormat.parse(time);
            long length = System.currentTimeMillis() - oDate.getTime();
            // 小时
            long t = length / 1000 / 60 / 60;
            if(t < 1){
                data = "刚刚";
                return data;
            }
            else if(t < 24){
                data = t + "小时前";
                return data;
            }
            // 天
            t = t / 24;
            if(t < 30){
                data = t + "天前";
                return data;
            }
            int count = 0;
            for (int i = 12; i >= 1; i--) {
                if(t >= 30 * i){
                    count = i;
                    break;
                }
            }
            data = count + "个月前";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
