package com.llh.smartbutler.application;

import android.app.Application;

import com.llh.smartbutler.utils.StaticClass;

import cn.bmob.v3.Bmob;

/**
 * 项目名:    SmartButler5
 * 包名:      com.llh.smartbutler.application
 * 文件名:    BaseApplication
 * 创建者:    LLH
 * 创建时间:  2019/6/27 9:05
 * 描述:      TODO
 */
public class BaseApplication extends Application {
    //创建
    @Override
    public void onCreate() {
        super.onCreate();
        //默认初始化
        Bmob.initialize(this, StaticClass.APPLICATION_ID);
    }
}
