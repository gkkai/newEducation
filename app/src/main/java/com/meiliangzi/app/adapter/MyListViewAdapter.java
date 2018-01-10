package com.meiliangzi.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.IndexNewsListsBean;

import java.util.List;

/**
 * Created by kk on 2017/9/23.
 */

public class MyListViewAdapter extends BaseAdapter{
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<IndexNewsListsBean.DataBean> mData;
    String type="1";
    private int page=1;

    public MyListViewAdapter(Context context, List<IndexNewsListsBean.DataBean> mData, int page) {
        this.page=page;
        this.mContext = context;
        this.mData = mData;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public IndexNewsListsBean.DataBean getItem(int position) {
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
            convertView = mLayoutInflater.inflate(R.layout.item_news_lists, viewGroup, false);
            holder.imageView = (ImageView) convertView.findViewById(R.id.ivImg);
            holder.ll = (LinearLayout) convertView.findViewById(R.id.llLayout_item_newslists);
            holder.title = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.titletime = (TextView) convertView.findViewById(R.id.tvTime);
            holder.image_item3 = (ImageView) convertView.findViewById(R.id.image_item3);
            convertView.setTag(holder);

            //holder.tv_title.setText(((PartyBranchBean.DataBean) mData.get(position)).getName());
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (!"".equals(mData.get(2).getImg())) {
            if (!"".equals(mData.get(2).getImg())) {
                holder.image_item3.setVisibility(View.VISIBLE);
                holder.ll.setVisibility(View.GONE);
                holder.image_item3.setTag("imaggurl");
                getimage(holder.image_item3,position);

            } else if ("".equals(mData.get(position).getImg())) {
                holder.imageView.setVisibility(View.GONE);
                holder.title.setText(mData.get(position).getNews_title());
                holder.titletime.setText(mData.get(position).getCreate_time());
               /* titletime.set
                helper.showOrHideView(R.id.ivImg, false);
                helper.setText(R.id.tvTime, item.getCreate_time());
                helper.setText(R.id.tvTitle, item.getNews_title());*/
            }
        } else if ("".equals(mData.get(position).getImg())) {
            holder.imageView.setVisibility(View.GONE);
            holder.title.setText(mData.get(position).getNews_title());
            holder.titletime.setText(mData.get(position).getCreate_time());
            /*helper.showOrHideView(R.id.ivImg, false);
            helper.setText(R.id.tvTime, item.getCreate_time());
            helper.setText(R.id.tvTitle, item.getNews_title());*/
        } else {holder.imageView.setTag("imaggurl");
            getimage(holder.imageView,position);
            holder.title.setText(mData.get(position).getNews_title());
            holder.titletime.setText(mData.get(position).getCreate_time());
          /*  helper.setImageByUrl(R.id.ivImg, item.getImg(), R.mipmap.test_artical, R.mipmap.test_artical);
            helper.setText(R.id.tvTime, item.getCreate_time());
            helper.setText(R.id.tvTitle, item.getNews_title());*/
        }




        return convertView;
    }

    public void setdata(List<IndexNewsListsBean.DataBean> mData, int pages){
        this.page=pages;
        this.mData=mData;
        notifyDataSetChanged();
    }
    public void getimage(final ImageView image_item3,int position){
      /*  OkhttpUtils.imageloger(image_item3,mData.get(position).getUrl(), new HttpCallback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("page=============================================================="+"error");
            }

            @Override
            public void onResponse(Call call, Response response) {

                InputStream is = response.body().byteStream();
                final Bitmap bm = BitmapFactory.decodeStream(is);
                // 通过 tag 来防止图片错位
                if (image_item3.getTag() != null && image_item3.getTag().equals("imaggurl")) {
                    MyApplication.newsactivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            image_item3.setImageBitmap(bm);
                        }
                    });


                }
            }
        });*/
    }
    class ViewHolder {
        ImageView imageView=null,image_item3 = null;
        LinearLayout ll = null;
        TextView title = null,titletime = null;
    }

}
