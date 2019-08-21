package com.llh.smartbutler.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.llh.smartbutler.R;
import com.llh.smartbutler.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {
    //声明控件
    private Button btn_forget_password;
    private EditText et_email;

    private EditText et_now;
    private EditText et_new;
    private EditText et_new_password;
    private Button btn_update_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initView();
    }

    private void initView() {
        btn_forget_password = (Button)findViewById(R.id.btn_forget_password);
        btn_forget_password.setOnClickListener(this);
        et_email = (EditText)findViewById(R.id.et_email);

        et_now = (EditText)findViewById(R.id.et_now);
        et_new = (EditText)findViewById(R.id.et_new);
        et_new_password = (EditText)findViewById(R.id.et_new_password);
        btn_update_password = (Button)findViewById(R.id.btn_update_password);
        btn_update_password.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_forget_password:
              //获取输入框的内容
                String email = et_email.getText().toString().trim();
                //判断输入框是否为空
                if(!TextUtils.isEmpty(email)){
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                Toast.makeText(ForgetPasswordActivity.this,"发送邮件成功，请前往邮箱重置密码",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(ForgetPasswordActivity.this,"发送邮件失败"+e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_update_password:
                //获取输入框的内容
                String now_password = et_now.getText().toString().trim();
                String new_pass = et_new.getText().toString().trim();
                String new_password = et_new_password.getText().toString().trim();
                //判断输入框是否为空
                if(!TextUtils.isEmpty(now_password) & !TextUtils.isEmpty(new_pass) & !TextUtils.isEmpty(new_password)){
                    //判断两次输入的密码是否一致
                    if(new_pass.equals(new_password)){
                        //重置密码
                        MyUser.updateCurrentUserPassword(now_password, new_pass, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e == null){
                                    Toast.makeText(ForgetPasswordActivity.this,"重置密码成功",Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    Toast.makeText(ForgetPasswordActivity.this,"重置密码失败"+e.toString(),Toast.LENGTH_SHORT).show();
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

/*
  //拿到输入框的内容
                final String email = et_email.getText().toString().trim();
                //判断是否为空
                if(!TextUtils.isEmpty(email)){
                    //发送邮件
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                Toast.makeText(ForgetPasswordActivity.this,"邮件已经发送至："+email,Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(ForgetPasswordActivity.this,"邮件发送失败"+e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }



                 //获取三个输入框的值
                String now_password = et_now.getText().toString().trim();
                String new_pass = et_new.getText().toString().trim();
                String new_password = et_new_password.getText().toString().trim();
                //判断是否为空
                if(!TextUtils.isEmpty(now_password) & !TextUtils.isEmpty(new_pass) & !TextUtils.isEmpty(new_password)){
                    //判断两次新密码是否一致
                    if(new_pass.equals(new_password)){
                        //重置密码
                        MyUser.updateCurrentUserPassword(now_password, new_pass, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e == null){
                                    Toast.makeText(ForgetPasswordActivity.this,"重置密码成功", Toast.LENGTH_LONG).show();
                                    finish();
                                }else {
                                    Toast.makeText(ForgetPasswordActivity.this,"重置密码失败"+e.toString(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
 */
