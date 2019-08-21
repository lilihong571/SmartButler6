package com.llh.smartbutler.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.llh.smartbutler.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PhoneActivity extends BaseActivity implements View.OnClickListener {
    //声明控件
    private EditText et_phone;
    private ImageView iv_company;
    private TextView tv_addr;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button0;
    private Button buttonDel;
    private Button buttonQuery;
    //做一个标记
    public static boolean conpanyTag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        initView();
    }

    private void initView() {
        et_phone = findViewById(R.id.et_phone);
        iv_company = findViewById(R.id.iv_company);
        tv_addr = findViewById(R.id.tv_addr);
        button0 = findViewById(R.id.btn_0);
        button1 = findViewById(R.id.btn_1);
        button2 = findViewById(R.id.btn_2);
        button3 = findViewById(R.id.btn_3);
        button4 = findViewById(R.id.btn_4);
        button5 = findViewById(R.id.btn_5);
        button6 = findViewById(R.id.btn_6);
        button7 = findViewById(R.id.btn_7);
        button8 = findViewById(R.id.btn_8);
        button9 = findViewById(R.id.btn_9);
        buttonDel = findViewById(R.id.btn_del);
        buttonQuery = findViewById(R.id.btn_query);
        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttonQuery.setOnClickListener(this);
        buttonDel.setOnClickListener(this);
        //长按事件
        buttonDel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                et_phone.setText("");
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        //获取输入框的文本
        String str = et_phone.getText().toString().trim();
        switch (v.getId()){
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
                Log.d("llhData","conpanyTag= "+conpanyTag);
                if(conpanyTag == true){
                    conpanyTag = false;
                    et_phone.setText(((Button)v).getText().toString());
                }else {
                    //点击就把当前按钮的文本加入到输入框中
                    et_phone.setText(str+((Button)v).getText().toString());
                    // Log.d("llhData", String.valueOf(str.length()));//str.length()是最后一个下标值
                    //移动光标 012
                    et_phone.setSelection(str.length()+1);
                }

                break;
            case R.id.btn_del:
                if(!TextUtils.isEmpty(str) && str.length()>0){
//                    //移除一个  012
                   et_phone.setText(str.substring(0,str.length()-1));//原来0-2   现在0-1
                    //移动光标 012
                    et_phone.setSelection(et_phone.getText().length());
                }
                break;
            case R.id.btn_query:
                getPhone(str);
                break;
        }
    }

    private void getPhone(String str) {
        //拼接字符串
        String url = "http://apis.juhe.cn/mobile/get?phone="+str+"&key=97a25b53fe0f0e5854c14a062c774a83";
        //请求数据
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
//                Toast.makeText(MainActivity.this,t,Toast.LENGTH_SHORT).show();
//                Log.d("llhData",t);
                parsingJson(t);
            }
        });
    }

    private void parsingJson(String t) {
        //将t转换为一个JSONObject对象
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            String province = jsonResult.getString("province");
            String city = jsonResult.getString("city");
            String areacode = jsonResult.getString("areacode");
            String zip = jsonResult.getString("zip");
            String company = jsonResult.getString("company");
            String card = jsonResult.getString("card");
            //将这些数据写到textView
            tv_addr.setText("归属地"+"\n"
                    + "省份："+province+"\n"
                    +"城市："+city+"\n"
                    +"区号："+areacode+"\n"
                    +"邮编："+zip+"\n"
                    +"运营商："+company+"\n"
                    +"card："+card+"\n");
            //设置图片
            if(company.equals("电信")){
                iv_company.setImageResource(R.drawable.china_telecom);
            }else if(company.equals("联通")){
                iv_company.setImageResource(R.drawable.china_unicom);
            }else {
                iv_company.setImageResource(R.drawable.china_mobile);
            }
            conpanyTag = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
