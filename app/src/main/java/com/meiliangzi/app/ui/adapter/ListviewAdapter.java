package com.meiliangzi.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.PartyBranchBean;

import java.util.List;


/**
 * Created by kk on 2017/9/21.
 */

public class ListviewAdapter<T> extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<T> mData;
    String type="1";
    public ListviewAdapter(Context context,List<T> mData) {

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
          //  holder.image = (ImageView) convertView.findViewById(R.id.layout_partybranch_list_adapter_right_arrow);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (type.equals("1")) {
            holder.tv_title.setText(((PartyBranchBean.DataBean) mData.get(position)).getName());
            if (((PartyBranchBean.DataBean) mData.get(position)).getPartybranchs_info().size() == 0) {
              //  holder.image.setVisibility(View.GONE);

            }
            //holder.tv_title.setText(((PartyBranchBean.DataBean) mData.get(position)).getName());

        }else {
            holder.tv_title.setText(((PartyBranchBean.DataBean.Partybranchs_info) mData.get(position)).getName());
           // holder.image.setVisibility(View.GONE);
        }
        return convertView;
    }

    public void setdata(List<T> mData,String type){
        this.mData=mData;
        this.type=type;
    }
    class ViewHolder {
        private TextView tv_title;
        //private ImageView image;
    }
}
