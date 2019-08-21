package com.llh.smartbutler.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.llh.smartbutler.R;

/**
 * 项目名:    SmartButler5
 * 包名:      com.llh.smartbutler.ui
 * 文件名:    BaseActivity
 * 创建者:    LLH
 * 创建时间:  2019/6/27 9:07
 * 描述:      TODO
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //显示返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //菜单栏操作

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
