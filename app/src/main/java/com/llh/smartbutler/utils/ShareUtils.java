package com.llh.smartbutler.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 项目名:    SmartButler5
 * 包名:      com.llh.smartbutler.utils
 * 文件名:    ShareUtils
 * 创建者:    LLH
 * 创建时间:  2019/6/27 9:52
 * 描述:      TODO
 */
public class ShareUtils {
    private static final String NAME = "LLH";
    //封装
    //存
    public static void putString(Context mContext,String key,String value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }
    //取
    public static String getString(Context mContent,String key,String defValue){
        SharedPreferences sp = mContent.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getString(key,defValue);
    }

    public static void putInt(Context mContext,String key,int value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).commit();
    }
    //取
    public static int getString(Context mContent,String key,int defValue){
        SharedPreferences sp = mContent.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getInt(key,defValue);
    }

    public static void putBoolean(Context mContext,String key,boolean value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }
    //取
    public static boolean getBoolean(Context mContent,String key,boolean defValue){
        SharedPreferences sp = mContent.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getBoolean(key,defValue);
    }
    //删除单个
    public static void deleteShare(Context mContent,String key){
        SharedPreferences sp = mContent.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }
    //删除全部
    public static void deleteAll(Context mContent){
        SharedPreferences sp = mContent.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }
}
