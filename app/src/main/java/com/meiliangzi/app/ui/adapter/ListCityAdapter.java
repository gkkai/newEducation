package com.meiliangzi.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.CityListBean;
import com.meiliangzi.app.model.bean.PartyBranchBean;

import java.util.List;


/**
 * Created by kk on 2017/9/21.
 */

public class ListCityAdapter<T> extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<T> mData;
    String type="1";
    public ListCityAdapter(Context context, List<T> mData) {

        this.mContext = context;
        this.mData = mData;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.list_partybranch_adapter, viewGroup, false);
            holder.tv_title = (TextView) convertView.findViewById(R.id.layout_part_list_adapter_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText(((CityListBean.DataBean)mData.get(position)).getName());
        return convertView;
    }

    public void setdata(List<T> mData){
        this.mData=mData;
    }
    class ViewHolder {
        private TextView tv_title;
        //private ImageView image;
    }
}
