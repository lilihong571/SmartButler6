package com.llh.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.llh.smartbutler.R;
import com.llh.smartbutler.entity.ChatListData;

import java.util.List;

/**
 * 项目名:    SmartButler5
 * 包名:      com.llh.smartbutler.adapter
 * 文件名:    ChatListAdapter
 * 创建者:    LLH
 * 创建时间:  2019/8/19 18:43
 * 描述:      TODO
 */
public class ChatListAdapter extends BaseAdapter {
    //左边的type
    public static final int VALUE_LEFT_TEXT = 1;
    //右边的type
    public static final int VALUE_RIGHT_TEXT = 2;
    private Context mContext;
    private LayoutInflater inflater;
    private List<ChatListData> mList;
    public ChatListAdapter(Context mContext, List<ChatListData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        //获取系统服务
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //实例化
        LeftViewHolder leftViewHolder = null;
        RightViewHolder rightViewHolder = null;
        //获取当前要显示的type，根据type来区分数据的加载
        int type = getItemViewType(position);
        //Log.d("llhData", "type1: "+type);
        if(convertView == null){
            switch (type){
                case VALUE_LEFT_TEXT:
                    leftViewHolder = new LeftViewHolder();
                    convertView = inflater.inflate(R.layout.left_item,null);
                    leftViewHolder.tv_left = convertView.findViewById(R.id.tv_left_text);
                    convertView.setTag(leftViewHolder);
                    break;
                case VALUE_RIGHT_TEXT:
                    rightViewHolder = new RightViewHolder();
                    convertView = inflater.inflate(R.layout.right_item,null);
                    rightViewHolder.tv_right = convertView.findViewById(R.id.tv_right_text);
                    convertView.setTag(rightViewHolder);
                    break;
            }
        }else {
            //Log.d("llhData", "type2: "+type);
            switch (type){
                case VALUE_LEFT_TEXT:
                    //leftViewHolder = (LeftViewHolder) convertView.getTag();
                    leftViewHolder = (LeftViewHolder) convertView.getTag();
                    break;
                case VALUE_RIGHT_TEXT:
                    rightViewHolder = (RightViewHolder) convertView.getTag();
                    //Log.d("llhData","rightViewHolder.tv_right="+rightViewHolder);
                    break;
            }
        }
        //设置数据
        ChatListData chat = mList.get(position);
        //Log.d("llhData", "type3: "+type);
        switch (type){
            case VALUE_LEFT_TEXT:
                //设置数据
                leftViewHolder.tv_left.setText(chat.getTalling());
                break;
            case VALUE_RIGHT_TEXT:
                rightViewHolder.tv_right.setText(chat.getTalling());
                break;
        }
        return convertView;
    }
        //根据数据源的position来返回要显示的item
    @Override
    public int getItemViewType(int position) {
        ChatListData chat = mList.get(position);
        int type = chat.getType();
        return type;
    }
    //返回所有的layout数量

    @Override
    public int getViewTypeCount() {
        //return mList.size()+1;//左边1个，右边一个，自己也是一个
        return 3;
    }
        //两个ViewHolder
    class LeftViewHolder{
        TextView tv_left;
    }
    class RightViewHolder{
        TextView tv_right;
    }
}
