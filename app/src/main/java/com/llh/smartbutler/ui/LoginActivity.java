package com.llh.smartbutler.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.llh.smartbutler.MainActivity;
import com.llh.smartbutler.R;
import com.llh.smartbutler.entity.MyUser;
import com.llh.smartbutler.utils.ShareUtils;
import com.llh.smartbutler.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //声明控件
    private Button btn_register;
    private EditText et_username;
    private EditText et_password;
    private Button btn_login;

    private TextView tv_forget;
    //记住密码
    private CheckBox keep_password;

    //初始化Dialog
    private CustomDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        btn_register = (Button)findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        et_username = (EditText)findViewById(R.id.et_username);
        et_password = (EditText)findViewById(R.id.et_password);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        keep_password = (CheckBox)findViewById(R.id.keep_password);
        tv_forget =  (TextView)findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(this);

        dialog = new CustomDialog(this,100,100,R.layout.dialog_loading,R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
        //屏幕外点击无效
        dialog.setCancelable(false);
        //设置选择状态
        boolean isCheck = ShareUtils.getBoolean(this,"keeppass",false);
        keep_password.setChecked(isCheck);
        //根据选中状态设置用户名和密码
        if(isCheck){
            et_username.setText(ShareUtils.getString(this,"name",""));
            et_password.setText(ShareUtils.getString(this,"password",""));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_forget:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
            case R.id.btn_register:
                startActivity(new Intent(this,RedisterActivity.class));
                break;
            case R.id.btn_login:
                //获取输入框的内容
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                //判断输入框是否为空
                if(!TextUtils.isEmpty(username) & !TextUtils.isEmpty(password)){
                    dialog.show();
                    //登录
                    //创建一个对象
                    final MyUser user = new MyUser();
                    //此处替换为你的用户名
                    user.setUsername(username);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            dialog.dismiss();
                            if(e == null){
                                //判断邮箱是否验证
                                if(user.getEmailVerified()){
                                    //Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }else{
                                    Toast.makeText(LoginActivity.this,"请前往邮箱验证",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(LoginActivity.this,"登录失败"+e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
    //保存用户名和密码

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //存入选中状态
        ShareUtils.putBoolean(this,"keeppass",keep_password.isChecked());
        if(keep_password.isChecked()){
            //保存用户名和密码
            ShareUtils.putString(this,"name",et_username.getText().toString().trim());
            ShareUtils.putString(this,"password",et_password.getText().toString().trim());
        }else {
            ShareUtils.deleteShare(this,"name");
            ShareUtils.deleteShare(this,"password");
        }
    }
}


/*

//第一次进来，头一次进来
        //设置选中状态
        boolean isCheck = ShareUtils.getBoolean(this,"keeppass",false);
        //如果是true就选中了
        keep_password.setChecked(isCheck);
        if(isCheck){
            //选择了true就要把密码设置出来
            //设置密码
            et_username.setText(ShareUtils.getString(this,"name",""));
            et_password.setText(ShareUtils.getString(this,"password",""));
        }

//在销毁Activity的时候  保存选中状态
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //保存状态                                  //true
        ShareUtils.putBoolean(this,"keeppass",keep_password.isChecked());

        //是否记住密码   //选中的时候记住密码
        if(keep_password.isChecked()){
            //记住用户名和密码
            ShareUtils.putString(this,"name",et_username.getText().toString().trim());
            ShareUtils.putString(this,"password",et_password.getText().toString().trim());
        }else {
            //清除
            ShareUtils.deleteShare(this,"name");
            ShareUtils.deleteShare(this,"password");
        }
    }
 */
