package com.llh.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.llh.smartbutler.R;
import com.llh.smartbutler.entity.WeChatListData;

import java.util.List;

/**
 * 项目名:    SmartButler
 * 包名:      com.llh.wechat
 * 文件名:    WeChatAdapter
 * 创建者:    LLH
 * 创建时间:  2019/8/20 9:52
 * 描述:      TODO
 */
public class WeChatAdapter extends BaseAdapter {
    //4大金刚
    private Context mContext;
    private List<WeChatListData> mList;
    private LayoutInflater inflater;
    private WeChatListData data;
    //构造函数
    public WeChatAdapter(Context mContext, List<WeChatListData> mList){
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
        ViewHolder viewHolder = null;
        //判断缓存是否为空
        if(convertView == null){
            viewHolder = new ViewHolder();
            //填充布局
            convertView = inflater.inflate(R.layout.item_news,null);
            //绑定控件
            viewHolder.iv_wechat_image = convertView.findViewById(R.id.iv_wechat_image);
            viewHolder.tv_wechat_title = convertView.findViewById(R.id.tv_wechat_title);
            viewHolder.tv_wechat_descriptions = convertView.findViewById(R.id.tv_wechat_descriptions);
            //设置标记
            convertView.setTag(viewHolder);
        }else {
            //获取标记
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        data = mList.get(position);
        viewHolder.iv_wechat_image.setImageResource(R.drawable.ic_launcher_background);
        viewHolder.tv_wechat_title.setText(data.getWechatTitle());
        viewHolder.tv_wechat_descriptions.setText(data.getWechatDescription());
        return convertView;
    }
    //内部类
    class ViewHolder{
        ImageView iv_wechat_image;
        TextView tv_wechat_title;
        TextView tv_wechat_descriptions;
    }
}
