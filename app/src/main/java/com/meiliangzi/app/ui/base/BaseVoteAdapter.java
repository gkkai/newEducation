package com.meiliangzi.app.ui.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.meiliangzi.app.widget.DateUtil;
import com.meiliangzi.app.widget.HorizontalListView;
import com.meiliangzi.app.widget.XListView;

import java.util.ArrayList;
import java.util.List;


public abstract class    BaseVoteAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected final int mItemID;
    private XListView mXListView;

    private RecyclerView recyclerView;
    private GridView mGridView;
    private ListView mListView;
    private HorizontalListView hListView;
    protected List<T> mDatas = new ArrayList<T>();
    private int position;

    public int getPosition() {
        return position;
    }


    public abstract void convert(BaseViewHolder helper, T item);
    public  void convert(BaseViewHolder helper, T item, int pos){};
    public BaseVoteAdapter(Context context, int itemID) {
        this.mContext = context;
        this.mItemID = itemID;
    }

    public List<T> getmDatas() {
        return mDatas;
    }
    public BaseVoteAdapter(Context context, RecyclerView recyclerView, int itemID) {
        this.mContext = context;
        this.recyclerView = recyclerView;
        this.mItemID = itemID;
    }
    public BaseVoteAdapter(Context context, XListView xListView, int itemID) {
        this.mContext = context;
        this.mXListView = xListView;
        this.mItemID = itemID;
    }
    public BaseVoteAdapter(Context context, HorizontalListView hListView, int itemID) {
        this.mContext = context;
        this.hListView = hListView;
        this.mItemID = itemID;
    }

    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public BaseVoteAdapter(Context context, GridView gridView, int itemID) {
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
    private static <E> void replaceAll(List<E> list,E oldObject,E newObject) {
        for (int i = 0; i < list.size(); i++) {		//遍历
            if(oldObject.equals(list.get(i))) {		//如果list中存在与oldObject相同的值，则用newObject替换
                list.set(i, newObject);				//设置索引为i的值为newObject
            }
        }
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
