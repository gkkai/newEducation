package com.meiliangzi.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.ui.view.Academy.bean.IndexColumnBean;

import java.util.List;


/**
 * Created by kk on 2019/5/4.
 */

public class HorizontalListViewAdapter extends BaseAdapter {

    private int[] mIconIDs;
    private List<IndexColumnBean.Data.Children> mTitles;
    private Context mContext;
    private LayoutInflater mInflater;
    private int selectIndex = 0;

    public HorizontalListViewAdapter(Context context, List<IndexColumnBean.Data.Children> titles) {
        this.mContext = context;
        this.mTitles = titles;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public Object getItem(int position) {
        return mTitles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.two_level_menu, null);
            holder.mTitle = (CheckBox) convertView.findViewById(R.id.ck);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == selectIndex) {
           // convertView.setSelected(true);
            holder.mTitle.setClickable(true);
        } else {
            //convertView.setSelected(false);
            holder.mTitle.setClickable(false);
        }

        holder.mTitle.setText(mTitles.get(position).getColumnName());

        return convertView;
    }



private static class ViewHolder {
    private CheckBox mTitle;
}
    public void setSelectIndex(int i) {
        selectIndex = i;
    }
}
