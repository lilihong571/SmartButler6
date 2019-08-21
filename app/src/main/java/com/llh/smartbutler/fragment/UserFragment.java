package com.llh.smartbutler.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.llh.smartbutler.R;
import com.llh.smartbutler.entity.MyUser;
import com.llh.smartbutler.ui.CourierActivity;
import com.llh.smartbutler.ui.LoginActivity;
import com.llh.smartbutler.ui.PhoneActivity;
import com.llh.smartbutler.utils.L;
import com.llh.smartbutler.view.CustomDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名:    SmartButler5
 * 包名:      com.llh.smartbutler.fragment
 * 文件名:    ButlerFragment
 * 创建者:    LLH
 * 创建时间:  2019/6/27 9:26
 * 描述:      TODO
 */
public class UserFragment extends Fragment implements View.OnClickListener {
    //声明控件
    private Button btn_exit_user;

    private TextView edit_user;
    private EditText et_username;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_desc;

    private Button btn_update_ok;
    private CircleImageView profile_image;
    private TextView tv_courier;
    private TextView tv_query_addr;

    public static File tempFile;
    public static File tempFile1 = null;
    private Uri imageUri;
    public static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;



    //点击头像弹出提示框
    private CustomDialog dialog;

    private Button btn_camera;
    private Button btn_picture;
    private Button btn_cancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,null);
        //初始化View   Fragment中初始化里面的控件需要view
        findView(view);
        return view;
    }
    //初始化View
    private void findView(View view) {
//
        btn_exit_user = (Button) view.findViewById(R.id.btn_exit_user);
        btn_exit_user.setOnClickListener(this);
        edit_user = (TextView) view.findViewById(R.id.edit_user);
        edit_user.setOnClickListener(this);
        tv_courier = (TextView)view.findViewById(R.id.tv_courier);
        tv_courier.setOnClickListener(this);
        tv_query_addr = view.findViewById(R.id.tv_query_addr);
        tv_query_addr.setOnClickListener(this);

        et_username = (EditText) view.findViewById(R.id.et_username);
        et_sex = (EditText) view.findViewById(R.id.et_sex);
        et_age = (EditText) view.findViewById(R.id.et_age);
        et_desc = (EditText) view.findViewById(R.id.et_desc);

        btn_update_ok = (Button) view.findViewById(R.id.btn_update_ok);
        btn_update_ok.setOnClickListener(this);

        profile_image = (CircleImageView) view.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);

        //初始化dialog
        dialog = new CustomDialog(getActivity(), 0, 0,
                R.layout.dialog_photo, R.style.pop_anim_style, Gravity.BOTTOM, 0);
        //提示框以外点击无效
        dialog.setCancelable(false);
        btn_camera = (Button) dialog.findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(this);
        btn_picture = (Button) dialog.findViewById(R.id.btn_picture);
        btn_picture.setOnClickListener(this);
        btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);

        //默认是不可点击的/不可输入
        setEnabled(false);

//        //设置具体的值
//        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
//        //et_username.setText(userInfo.getUsername());
//        et_age.setText(userInfo.getAge() + "");
//        et_sex.setText(userInfo.isSex() ? "男" : "女");
//        et_desc.setText(userInfo.getDesc());
    }
    //控制焦点
    private void setEnabled(boolean is){
        et_username.setEnabled(is);
        et_age.setEnabled(is);
        et_desc.setEnabled(is);
        et_sex.setEnabled(is);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //退出登录
            case R.id.btn_exit_user:
                //清除缓存用户对象
                MyUser.logOut();
                //现在的currentUser是null了
                BmobUser currentUser = MyUser.getCurrentUser();
                //跳转到登陆页面           //fragment上下文要用getActivity()
                startActivity(new Intent(getActivity(),LoginActivity.class));
                getActivity().finish();
                break;
                //编辑资料
            case R.id.edit_user:
                //获取焦点
                setEnabled(true);
                btn_update_ok.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_update_ok:
                //拿到输入框的值
                String username = et_username.getText().toString();
                String age = et_age.getText().toString();
                String sex = et_sex.getText().toString();
                String desc = et_desc.getText().toString();
                //判断是否为空
                if(!TextUtils.isEmpty(username) & !TextUtils.isEmpty(age) & !TextUtils.isEmpty(sex)){
                    //更新属性
                    MyUser user = new MyUser();
                    user.setUsername(username);
                    user.setAge(Integer.parseInt(age));
                    //性别
                    if (sex.equals("男")){
                        user.setSex(true);
                    }else {
                        user.setSex(false);
                    }
                    //简介
                    if(!TextUtils.isEmpty(desc)){
                        user.setDesc(desc);
                    }else {
                        user.setDesc("这个人很懒，什么都没留下！");
                    }
                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                //修改成功   失去焦点
                                setEnabled(false);
                                btn_update_ok.setVisibility(View.GONE);
                                Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"修改失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }else {
                    Toast.makeText(getActivity(),"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
                //圆形头像
            case R.id.profile_image:
                dialog.show();
                break;
                //取消
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            case R.id.btn_camera:
                toCamera(getActivity());
                break;
            case R.id.btn_picture:
                toPicture();
                break;
            case R.id.tv_courier:
                startActivity(new Intent(getActivity(), CourierActivity.class));
                break;
            case R.id.tv_query_addr:
                startActivity(new Intent(getActivity(), PhoneActivity.class));
                break;
        }
    }

//    //file的名字
//    public static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
//    public static final int CAMERA_REQUEST_CODE = 100;

//    //跳转相机
public void toCamera(Activity activity) {
    //獲取系統版本
    int currentapiVersion = android.os.Build.VERSION.SDK_INT;
    // 激活相机
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    // 判断存储卡是否可以用，可用进行存储
    if (hasSdcard()) {
        SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                "yyyy_MM_dd_HH_mm_ss");
        String filename = timeStampFormat.format(new Date());
        tempFile = new File(Environment.getExternalStorageDirectory(),
                filename + ".jpg");
        if (currentapiVersion < 24) {
            // 从文件中创建uri
            imageUri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        } else {
            //兼容android7.0 使用共享文件的形式
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
            //检查是否有存储权限，以免崩溃
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                Toast.makeText(getActivity(),"请开启存储权限",Toast.LENGTH_SHORT).show();
                return;
            }
            imageUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }
    }
    // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
    activity.startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
    }
    /*
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
    //跳转相册
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        //设置图片     image/*    *表示全部的图片
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_REQUEST_CODE);
       dialog.dismiss();
    }
    //在这个函数里面，可以拿到返回值
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(resultCode!=getActivity().RESULT_CANCELED){
           switch (requestCode){
               //相册数据
               case IMAGE_REQUEST_CODE:
                   startPhotoZoom(data.getData());
                   break;
               case PHOTO_REQUEST_CAREMA:
                   SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                           "yyyy_MM_dd_HH_mm_ss");
                   String filename = timeStampFormat.format(new Date());
                   tempFile1 = new File(Environment.getExternalStorageDirectory(),
                           filename + ".jpg");
                   startPhotoZoom(Uri.fromFile(tempFile1));
                   break;
               case RESULT_REQUEST_CODE:
                   //有可能点击舍弃
                   if (data != null) {
                       //拿到图片设置
                       setImageToView(data);
                       //既然已经设置了图片，我们原先的就应该删除
                       if (tempFile1 != null) {
                           tempFile1.delete();
                       }
                   }
                   break;
           }
       }
    }
    //裁剪
    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            L.e("uri == null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //设置裁剪
        intent.putExtra("crop", "true");
        //裁剪宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪图片的质量
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        //发送数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }
    private void setImageToView(Intent data){
        Bundle bundle = data.getExtras();
        if(bundle != null){
            Bitmap bitmap = bundle.getParcelable("data");
            profile_image.setImageBitmap(bitmap);
        }
    }
}
