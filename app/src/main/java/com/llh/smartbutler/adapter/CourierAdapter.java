package com.llh.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.llh.smartbutler.R;
import com.llh.smartbutler.entity.CourierData;

import java.util.List;

/**
 * 项目名:    SmartButler5
 * 包名:      com.llh.smartbutler.adapter
 * 文件名:    CourierAdapter
 * 创建者:    LLH
 * 创建时间:  2019/8/18 16:37
 * 描述:      快递查询适配器
 */
public class CourierAdapter extends BaseAdapter {
    //声明上下文
    private Context mContext;
    private List<CourierData> mList;
    //布局加载器(用来加载item)
    private LayoutInflater inflater;
    //声明一个实体类
    private CourierData data;
    //构造方法
    public CourierAdapter(Context mContext,List<CourierData> mList){
        this.mContext = mContext;
        this.mList = mList;
        //布局加载器获取系统服务
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
        //声明ViewHolder
        ViewHolder viewHolder = null;
        //判断一下，用的是ViewHolder的缓存
        //第一次加载
        if(convertView == null){
            viewHolder = new ViewHolder();
            //把布局给添加了
            convertView = inflater.inflate(R.layout.layout_courier_item,null);
            viewHolder.tv_remark = convertView.findViewById(R.id.tv_remark);
            viewHolder.tv_zone = convertView.findViewById(R.id.tv_zone);
            viewHolder.tv_datetime = convertView.findViewById(R.id.tv_datetime);
            //设置缓存
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        data = mList.get(position);
        viewHolder.tv_remark.setText(data.getRemark());
        viewHolder.tv_zone.setText(data.getZone());
        viewHolder.tv_datetime.setText(data.getDatetime());
        return convertView;
    }
    //ListView的优化
    class ViewHolder{
        private TextView tv_remark;
        private TextView tv_zone;
        private TextView tv_datetime;
    }
}
