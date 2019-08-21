package com.llh.smartbutler.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.llh.smartbutler.R;
import com.llh.smartbutler.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RedisterActivity extends BaseActivity implements View.OnClickListener {
    //声明控件
    private EditText et_username;
    private EditText et_age;
    private EditText et_desc;
    private RadioGroup mRadioGroup;
    private EditText et_pass;
    private EditText et_password;
    private EditText et_email;
    private Button btn_register;
    //声明一个状态
    private static boolean isGender = true;//默认男
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redister);
        initView();
    }

    private void initView() {
        et_username = (EditText)findViewById(R.id.et_username);
        et_age = (EditText)findViewById(R.id.et_age);
        et_desc = (EditText)findViewById(R.id.et_desc);
        mRadioGroup = (RadioGroup)findViewById(R.id.mRadioGroup);
        et_pass = (EditText)findViewById(R.id.et_pass);
        et_password = (EditText)findViewById(R.id.et_password);
        et_email = (EditText)findViewById(R.id.et_email);
        btn_register = (Button)findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                //点击注册按钮的时候，获取输入框内容
                String username = et_username.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String desc = et_desc.getText().toString().trim();
                String pass = et_pass.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                //判断一些必填内容是否为空
                if(!TextUtils.isEmpty(username) & !TextUtils.isEmpty(age)
                & !TextUtils.isEmpty(pass) & !TextUtils.isEmpty(password)
                        & !TextUtils.isEmpty(email)){
                    //判断两次输入的密码是否一致
                    if(pass.equals(password)){
                        //对性别进行判断
                        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                if(checkedId == R.id.rb_boy){
                                    isGender = true;
                                }else {
                                    isGender = false;
                                }
                            }
                        });
                        //对简介进行判断
                        if(TextUtils.isEmpty(desc)){
                            desc = "这个人很懒，什么都没有留下";
                        }
                        //用户注册
                        MyUser user = new MyUser();
                        //添加数据
                        user.setUsername(username);
                        user.setPassword(password);
                        user.setEmail(email);
                        user.setAge(Integer.parseInt(age));
                        user.setSex(isGender);
                        user.setDesc(desc);
                        //注册
                        user.signUp(new SaveListener<MyUser>() {
                            @Override
                            public void done(MyUser myUser, BmobException e) {
                                if(e == null){
                                    Toast.makeText(RedisterActivity.this,"注册成功,请前往邮箱认证!",Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    Toast.makeText(RedisterActivity.this,"注册失败:"+e.toString(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
