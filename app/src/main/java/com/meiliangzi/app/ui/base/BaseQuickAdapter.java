package com.meiliangzi.app.ui.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.meiliangzi.app.widget.DateUtil;
import com.meiliangzi.app.widget.XListView;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseQuickAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected final int mItemID;
    private XListView mXListView;
    private GridView mGridView;
    private ListView mListView;
    protected List<T> mDatas = new ArrayList<T>();
    private int position;

    public int getPosition() {
        return position;
    }


    public abstract void convert(BaseViewHolder helper, T item);
    public  void convert(BaseViewHolder helper, T item, int pos){};
    public BaseQuickAdapter(Context context, int itemID) {
        this.mContext = context;
        this.mItemID = itemID;
    }

    public List<T> getmDatas() {
        return mDatas;
    }

    public BaseQuickAdapter(Context context, XListView xListView, int itemID) {
        this.mContext = context;
        this.mXListView = xListView;
        this.mItemID = itemID;
    }

    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public BaseQuickAdapter(Context context, GridView gridView, int itemID) {
        this.mContext = context;
        this.mGridView = gridView;
        this.mItemID = itemID;
    }
//
//    public BaseQuickAdapter(Context context, ListView mListView, int itemID) {
//        this.mContext = context;
//        this.mListView = mListView;
//        this.mItemID = itemID;
//    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /**
     * ��������
     */
    public void pullLoad(List<T> datas) {
        mXListView.closePullLoadMore(datas.size());
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * ��һ��ˢ�»�����ˢ��
     */
    public void pullRefresh(List<T> datas) {
        mXListView.stopRefresh();
        mXListView.setRefreshTime(DateUtil.getFormatData(System.currentTimeMillis(), DateUtil.YYYYMMDDhhmm));
        mXListView.closePullLoadMore(datas.size());
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }


    /**
     * ��������
     */
    public void pullLoadCommon(List<T> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }


    /**
     * ��һ��ˢ�»�����ˢ��
     */
    public void pullRefreshCommon(List<T> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

//    /**
//     * �޷�ҳ�������
//     */
//    public void pullRefreshNoPage(List<T> datas) {
//        mXListView.stopRefresh();
//        mXListView.setRefreshTime(TimeUtils.getCurrentDate());
//        mDatas.clear();
//        mDatas.addAll(datas);
//        notifyDataSetChanged();
//    }

    /**
     * ��һ��ˢ�»�����ˢ��
     */
    public void refreshGridOrListViews(List<T> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearData() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.position = position;
        BaseViewHolder viewHolder = BaseViewHolder.get(mContext, convertView, parent, mItemID, position);
        convert(viewHolder, mDatas.get(position));
        convert(viewHolder, mDatas.get(position),position);

        return viewHolder.getConvertView();
    }

    public void setDatas(List<T> mDatas) {

        this.mDatas = mDatas;
        notifyDataSetChanged();
    }
}
