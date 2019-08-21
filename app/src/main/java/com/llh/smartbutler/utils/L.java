package com.llh.smartbutler.utils;

import android.util.Log;

/**
 * 项目名:    SmartButler5
 * 包名:      com.llh.smartbutler.utils
 * 文件名:    L
 * 创建者:    LLH
 * 创建时间:  2019/6/27 9:47
 * 描述:      TODO
 */
public class L {
    private static final String TAG = "SmartButler";
    //封装diewf
    public static void d(String text){
        Log.d(TAG,text);
    }
    public static void i(String text){
        Log.i(TAG,text);
    }
    public static void e(String text){
        Log.e(TAG,text);
    }
    public static void w(String text){
        Log.w(TAG,text);
    }
}
