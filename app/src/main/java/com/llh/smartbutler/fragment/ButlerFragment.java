package com.llh.smartbutler.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.kymjs.rxvolley.http.VolleyError;
import com.llh.smartbutler.MainActivity;
import com.llh.smartbutler.R;
import com.llh.smartbutler.adapter.ChatListAdapter;
import com.llh.smartbutler.entity.ChatListData;
import com.llh.smartbutler.utils.StaticClass;

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
public class ButlerFragment extends Fragment implements View.OnClickListener {
    private ListView listView;
//    private Button btn_left;
//    private Button btn_right;
    private List<ChatListData> chatListDataList = new ArrayList<>();
    ChatListAdapter adapter;
    //输入框，button
    private EditText et_text;
    private Button btn_send;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_butler,container,false);
        findView(view);
        return view;
    }

    private void findView(View view) {
        listView = view.findViewById(R.id.lv_chat);
//        btn_left = view.findViewById(R.id.btn_left);
//        btn_left.setOnClickListener(this);
//        btn_right = view.findViewById(R.id.btn_right);
//        btn_right.setOnClickListener(this);
        et_text = view.findViewById(R.id.et_text);
        btn_send = view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);

        //创建适配器
        adapter = new ChatListAdapter(getActivity(), chatListDataList);
        listView.setAdapter(adapter);
        //链表
        addLeftItem("你好,我是图图");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:
                /**
                 * 逻辑：
                 * 1.获取输入框的内容
                 * 2.判断是否为空
                 * 3.判断字符长度不能大于30
                 * 4.清空输入框的内容
                 * 5.添加你输入的内容到rightitem
                 * 6.发送给机器人，请求返回内容
                 * 7.拿到机器人的返回值之后添加在leftitem
                 */
                //获取输入框的内容
                String text = et_text.getText().toString();
                String url = "http://www.tuling123.com/openapi/api";
                HttpParams params = new HttpParams();
                params.put("key", "1c6cedb04c904faeb494d24e60b2eb6a");
                Log.d("llhData", "onClick: "+StaticClass.CHAT_LIST_KEY);
                params.put("info",text);
                //2.判断是否为空
                if(!TextUtils.isEmpty(text)){
                    //3.判断字符长度不能大于30
                    if (text.length()<30){
                        //4.清空输入框的内容
                        et_text.setText("");
                        //5.添加你输入的内容到rightitem
                        addRightItem(text);
                        //6.发送给机器人，请求返回内容
                        RxVolley.post(url, params, new HttpCallback() {
                            @Override
                            public void onSuccess(String t) {
//                                Log.d("llhData", "onSuccess: "+t);
//                                Toast.makeText(getActivity(),t,Toast.LENGTH_SHORT).show();
                                parsingJson(t);
                            }

                            @Override
                            public void onFailure(VolleyError error) {
                                Toast.makeText(getActivity(),"请求失败",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }else {
                    Toast.makeText(getActivity(),"输入框不能为空！",Toast.LENGTH_SHORT).show();
                }
                break;
//            case R.id.btn_left:
//                addLeftItem("左边");
//                break;
//            case R.id.btn_right:
//                addRightItem("右边");
//                break;
        }
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            String text = jsonObject.getString("text");
            //7.拿到机器人的返回值之后添加在leftitem
            addLeftItem(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addLeftItem(String text) {
        ChatListData chatListData = new ChatListData();
        chatListData.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        chatListData.setTalling(text);
        chatListDataList.add(chatListData);
        //通知adapter更新
        adapter.notifyDataSetChanged();
        //滚动到底部
        listView.setSelection(listView.getBottom());
    }
    private void addRightItem(String text) {
        ChatListData chatListData = new ChatListData();
        chatListData.setType(ChatListAdapter.VALUE_RIGHT_TEXT);
        chatListData.setTalling(text);
        chatListDataList.add(chatListData);
        //通知adapter更新
        adapter.notifyDataSetChanged();
        //滚动到底部
        listView.setSelection(listView.getBottom());
    }
}
