package com.example.yanbi.smart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yanbi.smart.R;
import com.example.yanbi.smart.entity.WeChatData;

import java.util.List;

/**
 * Created by yanbi on 2017/9/17.
 */

public class WeChatAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<WeChatData> mList;
    private WeChatData data;

    public WeChatAdapter(Context mContext, List<WeChatData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            view=inflater.inflate(R.layout.wechat_item,null);
            viewHolder.iv_img= (ImageView) view.findViewById(R.id.iv_img);
            viewHolder.tv_title= (TextView) view.findViewById(R.id.tv_title);
            viewHolder.tv_source= (TextView) view.findViewById(R.id.tv_source);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }
        data=mList.get(i);
        viewHolder.tv_title.setText(data.getTitle());
        viewHolder.tv_source.setText(data.getSource());
        return view;
    }

    class ViewHolder{
        private ImageView iv_img;
        private TextView tv_title;
        private TextView tv_source;
    }
}
