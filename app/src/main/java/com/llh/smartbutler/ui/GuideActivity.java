package com.llh.smartbutler.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.llh.smartbutler.MainActivity;
import com.llh.smartbutler.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {
    //声明控件
    private ViewPager mViewPager;
    //声明集合
    private List<View> mList;
    private View view1, view2, view3;
    //小圆点
    private ImageView iv_point1;
    private ImageView iv_point2;
    private ImageView iv_point3;
    //按钮
    private Button btn_start;
    //返回
    private ImageView iv_black;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //初始化View
        initView();
    }

    private void initView() {
        iv_black = (ImageView)findViewById(R.id.iv_black);
        iv_black.setOnClickListener(this);

        iv_point1 = (ImageView)findViewById(R.id.point_1);
        iv_point2 = (ImageView)findViewById(R.id.point_2);
        iv_point3 = (ImageView)findViewById(R.id.point_3);
        //默认
        setPointBg(true,false,false);

        mList = new ArrayList<>();
        view1 = View.inflate(this,R.layout.viewpager_one,null);
        view2 = View.inflate(this,R.layout.viewpager_two,null);
        view3 = View.inflate(this,R.layout.viewpager_three,null);
        mList.add(view1);
        mList.add(view2);
        mList.add(view3);

        btn_start = (Button)view3.findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
        mViewPager = (ViewPager)findViewById(R.id.mViewPager);
        //预加载
        mViewPager.setOffscreenPageLimit(mList.size());
        //设置滑动监听
       // mViewPager.addOnPageChangeListener(new Vpage);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        setPointBg(true,false,false);
                        break;
                    case 1:
                        setPointBg(false,true,false);
                        break;
                    case 2:
                        setPointBg(false,false,true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //设置适配器
        mViewPager.setAdapter(new GuideAdapter());
    }

    private void setPointBg(boolean p1, boolean p2, boolean p3) {
        if(p1){
            iv_point1.setBackgroundResource(R.drawable.point_on);
        }else{
            iv_point1.setBackgroundResource(R.drawable.point_off);
        }

        if(p2){
            iv_point2.setBackgroundResource(R.drawable.point_on);
        }else{
            iv_point2.setBackgroundResource(R.drawable.point_off);
        }

        if(p3){
            iv_point3.setBackgroundResource(R.drawable.point_on);
        }else{
            iv_point3.setBackgroundResource(R.drawable.point_off);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
            case R.id.iv_black:
                startActivity(new Intent(this,MainActivity.class));
                finish();
                break;
        }
    }

    private class GuideAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }
        //增加

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ((ViewPager)container).addView(mList.get(position));
            return mList.get(position);
        }
        //删除

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            ((ViewPager)container).removeView(mList.get(position));
            //super.destroyItem(container, position, object);
        }
    }
}
