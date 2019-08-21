package com.llh.smartbutler.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.http.VolleyError;
import com.llh.smartbutler.R;
import com.llh.smartbutler.adapter.WeChatAdapter;
import com.llh.smartbutler.entity.WeChatListData;
import com.llh.smartbutler.ui.WebViewActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名:    SmartButler5
 * 包名:      com.llh.smartbutler.fragment
 * 文件名:    ButlerFragment
 * 创建者:    LLH
 * 创建时间:  2019/6/27 9:26
 * 描述:      TODO
 */
public class WeChatFragment extends Fragment {
    //声明控件
    private ListView mListView;
    private List<WeChatListData> mList = new ArrayList<>();
    WeChatAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat,container,false);
        findView(view);
        return view;
    }

    private void findView(View view) {
        mListView = (ListView)view.findViewById(R.id.list_wechat);
        //获取后台数据
        getWechat();
        //创建适配器
        adapter = new WeChatAdapter(getActivity(),mList);
        //设置点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WeChatListData data = mList.get(position);
                //点击把标题，来源传递给下一个活动
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("title",data.getWechatTitle());
//                //bundle.putString("source",data.getWechatDescription());
//                intent.putExtra("message",bundle);
                intent.putExtra("title",data.getWechatTitle());
                intent.putExtra("url",data.getImageUrl());
                startActivity(intent);
            }
        });
    }

    private void getWechat() {
        //拼接字符串
        String url = "http://v.juhe.cn/weixin/query?key=2c77ca5f864b0741871b1b47589c7ee2";
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
//                Toast.makeText(MainActivity.this,t,Toast.LENGTH_SHORT).show();
//                Log.d("llhData",t);
                //解析数据
                parsingJson(t);
            }

            @Override
            public void onFailure(VolleyError error) {
                Toast.makeText(getActivity(),"请求失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parsingJson(String t) {
        //将字符串转换为json数据
        try {
            JSONObject jsonObject = new JSONObject(t);
            //获取返回结果
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            //获取json数组
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            //遍历数组  获取jsonArray里面的对象
            for (int i=0; i<jsonArray.length(); i++){
                JSONObject json = jsonArray.getJSONObject(i);
                //获取图片地址
                //String imageUrl = json.getString("firstImg");
                String url = json.getString("url");
                //获取标题
                String title = json.getString("title");
                //获取来源
                String source = json.getString("source");
                //创建对象
                WeChatListData data = new WeChatListData();
                data.setWechatTitle(title);
                data.setWechatDescription(source);
                data.setImageUrl(url);
                //添加到链表
                mList.add(data);
                //设置适配器
                mListView.setAdapter(adapter);//注意适配器必须要在这里设置
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
