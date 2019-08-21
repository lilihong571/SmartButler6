package com.llh.smartbutler.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.llh.smartbutler.R;
import com.llh.smartbutler.adapter.CourierAdapter;
import com.llh.smartbutler.entity.CourierData;
import com.llh.smartbutler.utils.L;
import com.llh.smartbutler.utils.StaticClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 项目名:    SmartButler5
 * 包名:      com.llh.smartbutler.ui
 * 文件名:    CourierActivity
 * 创建者:    LLH
 * 创建时间:  2019/8/18 15:43
 * 描述:      TODO
 */
public class CourierActivity extends BaseActivity implements View.OnClickListener {
    private TextView et_name;
    private TextView et_number;
    private Button btn_get_courier;
    private ListView mListView;
    private List<CourierData> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);
        initView();
    }
    //初始化View
    private void initView() {
        et_name = findViewById(R.id.et_name);
        et_number = findViewById(R.id.et_number);
        btn_get_courier = findViewById(R.id.btn_get_courier);
        btn_get_courier.setOnClickListener(this);
        mListView = findViewById(R.id.mListView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_get_courier:
                /**
                 * 1.获取输入框的内容
                 * 2.判断是否为空
                 * 3.拿到数据去请求数据（Json）
                 * 4.解析Json
                 * 5.ListView适配器
                 * 6.实体类（item）
                 * 7.设置数据/显示效果
                 */
                //1.获取输入框的内容
                String name = et_name.getText().toString().trim();
                String number = et_number.getText().toString().trim();
                //拼接我们的url
                String url = "http://v.juhe.cn/exp/index?key="+ StaticClass.COURIER_KEY +"&com="+name+"&no="+number;
                //2.判断是否为空
                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)){
                    //3.拿到数据去请求数据（Json）
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                           // Toast.makeText(CourierActivity.this,t,Toast.LENGTH_SHORT).show();
                            L.i("Json" + t);
                            //4.解析Json
                            parsingJson(t);
                        }
                    });
                }else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    //解析数据
    private void parsingJson(String t) {
        //把String转换为JSONObject
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            //遍历array
            for (int i=0; i<jsonArray.length(); i++){
                JSONObject json = (JSONObject) jsonArray.get(i);
                CourierData data = new CourierData();
                data.setRemark(json.getString("remark"));
                data.setZone(json.getString("zone"));
                data.setDatetime(json.getString("datetime"));
                mList.add(data);
            }
            //倒序处理
            Collections.reverse(mList);
            CourierAdapter adapter = new CourierAdapter(this,mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
