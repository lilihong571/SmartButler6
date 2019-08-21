package com.llh.smartbutler.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.llh.smartbutler.R;

/**
 * 项目名:    SmartButler5
 * 包名:      com.llh.smartbutler.view
 * 文件名:    CustomDialog
 * 创建者:    LLH
 * 创建时间:  2019/6/27 17:18
 * 描述:      自定义Dialog
 */
public class CustomDialog extends Dialog {
    //定义UI模板
    public CustomDialog(Context context,int layout,int style) {
         //this就是重载我们的方法
        this(context,
                WindowManager.LayoutParams.WRAP_CONTENT,//宽，高
                WindowManager.LayoutParams.WRAP_CONTENT,
                layout,
                style,
                Gravity.CENTER //方向居中
        );
    }
    //定义属性
    public CustomDialog(Context context,int width, int height,int layout,int style, int gravity,int anim){
        super(context,style);
        //设置属性
        setContentView(layout);
        //拿到Window   用于更改他的宽高
        Window window = getWindow();
        //用window拿到他的一些属性
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        //直接定义
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = gravity;
        //把已经修改过的layoutParams设置回去
        window.setAttributes(layoutParams);
        //加上动画
        window.setWindowAnimations(anim);
    }

    //真正的实例
    public CustomDialog(Context context,int width, int height,int layout,int style, int gravity){
        //这个this引用的是模板，模板去调用属性              //动画用style去装载
        this(context,width,height,layout,style,gravity,R.style.pop_anim_style);
    }
}

