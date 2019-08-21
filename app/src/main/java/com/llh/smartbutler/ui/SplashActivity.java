package com.llh.smartbutler.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.llh.smartbutler.MainActivity;
import com.llh.smartbutler.R;
import com.llh.smartbutler.utils.ShareUtils;
import com.llh.smartbutler.utils.StaticClass;

public class SplashActivity extends AppCompatActivity {
    //声明控件
    private TextView tv_smart_butler;
    //延时
    Handler handler = new Handler(){
      //重写

        @Override
        public void handleMessage(Message msg) {
            //过滤
            switch (msg.what){
                case StaticClass.HANDLER_MASSAGE:
                    if(isFirst()){
                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    }else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initData();
    }

    private void initData() {
        //传一个Handler
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_MASSAGE,2000);
    }

    private boolean isFirst() {
        boolean isFirst = ShareUtils.getBoolean(this,StaticClass.HANDLER_KEY,true);
        if(isFirst){
            //修改值
            ShareUtils.putBoolean(this,StaticClass.HANDLER_KEY,false);
            return true;
        }else {
            return false;
        }
    }
}
