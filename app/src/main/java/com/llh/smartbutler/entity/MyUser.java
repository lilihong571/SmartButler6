package com.llh.smartbutler.entity;

import cn.bmob.v3.BmobUser;

/**
 * 项目名:    SmartButler5
 * 包名:      com.llh.smartbutler.entity
 * 文件名:    MyUser
 * 创建者:    LLH
 * 创建时间:  2019/6/27 11:02
 * 描述:      TODO
 */
public class MyUser extends BmobUser {
    //性别，年龄，简介
    private boolean sex;
    private int age;
    private String desc;

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
