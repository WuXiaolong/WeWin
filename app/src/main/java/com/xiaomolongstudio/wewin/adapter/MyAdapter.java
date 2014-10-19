package com.xiaomolongstudio.wewin.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etsy.android.grid.util.DynamicHeightImageView;
import com.xiaomolongstudio.wewin.R;

import java.util.List;
import java.util.Map;

/**
 * Created by WuXL on 2014/10/19.
 */
public class MyAdapter extends MyBaseAdapter {
    List<Map<String, Object>> mData;
    Activity activity;
    private LayoutInflater mInflater;

    public MyAdapter(Activity activity, List<Map<String, Object>> mData) {
        super();
        this.activity = activity;
        this.mData = mData;
        this.mInflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public List<Map<String, Object>> getmData() {
        return mData;
    }

    public void setmData(List<Map<String, Object>> mData) {
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.my_adapter_item, null);
            holder.imgView = (DynamicHeightImageView) view.findViewById(R.id.imgView);
            holder.title = (TextView) view.findViewById(R.id.title);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        imageLoader.displayImage(mData.get(position).get("imgUrl").toString(),
                holder.imgView, options);
        holder.title.setText(mData.get(position).get("title").toString());
        return view;
    }

    public class ViewHolder {
        DynamicHeightImageView imgView;
        TextView title;
    }
}
