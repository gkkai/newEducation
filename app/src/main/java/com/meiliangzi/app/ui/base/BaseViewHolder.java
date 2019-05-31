package com.meiliangzi.app.ui.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.tools.OkhttpUtils;
import com.meiliangzi.app.tools.picompressor.HttpCallback;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Response;


public class BaseViewHolder {
    private final SparseArray<View> mViews = new SparseArray<View>();
    private View mConvertView;
    private int mPosition;

    /**
     * @param position the position to set
     */
    public void setPosition(int position) {
        this.mPosition = position;
    }

    /**
     * @return the position
     */
    public int getPosition() {
        return mPosition;
    }

    private BaseViewHolder(Context context, ViewGroup parent, int layoutID, int position) {

        mConvertView = LayoutInflater.from(context).inflate(layoutID, parent, false);
        mPosition = position;
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     */
    @SuppressWarnings("null")
    public static BaseViewHolder get(Context context, View convertView, ViewGroup parent, int layoutID, int pos) {
        BaseViewHolder holder = null;
        if (convertView == null) {
            holder = new BaseViewHolder(context, parent, layoutID, pos);
        } else {
            holder = (BaseViewHolder) convertView.getTag();
            holder.mPosition = pos;
        }

       // holder = new BaseViewHolder(context, parent, layoutID, pos);
        return holder;
    }


    /**
     * 通过控件的ID获取对于的控件，如果没有则加入views
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewID) {
        View view = mViews.get(viewID);
        if (view == null) {
            view = mConvertView.findViewById(viewID);
            mViews.put(viewID, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewID
     * @param text
     * @return
     */
    public void setText(int viewID, String text) {
        TextView view = getView(viewID);
        view.setText(text);
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewID
     * @param grage
     * @return
     */
    public void setRatingBar(int viewID, String grage) {
        RatingBar view = getView(viewID);
        if (grage==null|| TextUtils.isEmpty(grage)) {
            view.setNumStars(1);
        } else {
//            view.setProgress(((int)Double.parseDouble(grage)));
            view.setNumStars(((int) Double.parseDouble(grage)));
        }
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewID
     * @param grage
     * @return
     */
    public void setRatingBar(int viewID, int grage) {
        RatingBar view = getView(viewID);

//            view.setProgress(grage);
        if (grage==0){
            view.setNumStars(0);
//             view.setVisibility(View.INVISIBLE);
            view.setNumStars(1);
        }else {
            view.setNumStars(grage);
        }

    }

    /**
     * 为TextView设置中划线
     *
     * @param viewID
     */
    public void setTextFlags(int viewID) {
        TextView view = getView(viewID);
        view.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    /**
     * 为LinearLayout 动态添加内容
     *
     * @param viewID
     * @param
     * @return
     */
    public void addViewsInLinearLayout(int viewID, View child) {
        LinearLayout layout = getView(viewID);
        layout.addView(child);
    }

    /**
     * 为LinearLayout 清除内容
     *
     * @param viewID
     * @param
     * @return
     */
    public void removeViewsInLinearLayout(int viewID) {
        LinearLayout layout = getView(viewID);
        layout.removeAllViews();
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewID
     * @param drawableID
     * @return
     */
    public void setImageResource(int viewID, int drawableID) {
        ImageView view = getView(viewID);
        view.setImageResource(drawableID);
    }

    /**
     * 隐藏ImageView
     *
     * @param viewID
     * @return
     */
    public void showOrHideView(int viewID, boolean isShowView) {
        View view = getView(viewID);
        if (isShowView) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * @param viewID
     * @return
     */
    public void showOrGoneView(int viewID, boolean isShowView) {
        View view = getView(viewID);
        if (isShowView) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 为SmratImageView设置图片、加载中图片、加载失败图片
     *
     * @param viewID
     * @param url
     * @return
     */
    public void setImageByUrl(int viewID, String url, Integer fallbackResource, Integer loadingResource) {
        ImageView view = getView(viewID);

        ImageLoader.getInstance().displayImage(url, view, MyApplication.getSimpleOptions(fallbackResource, loadingResource));
    }
    /**
     * 为SmratImageView设置图片、加载中图片、加载失败图片
     *
     * @param viewID
     * @param url
     * @return
     */
    public void setImageByUrlTAG(int viewID, String url, final int mPosition, final int page) {
        final ImageView view = getView(viewID);
        view.setTag("imaggurl");
        OkhttpUtils.imageloger(url, new HttpCallback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("page=============================================================="+"error");
            }

            @Override
            public void onResponse(Call call, Response response) {

                InputStream is = response.body().byteStream();
                Bitmap bm = BitmapFactory.decodeStream(is);
                // 通过 tag 来防止图片错位
                if (view.getTag() != null && view.getTag().equals("imaggurl")) {
                    MyApplication.newsactivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                    System.out.println("page=============================================================="+page+"");
                    System.out.println("type======================================================="+mPosition);
                }
            }
        });

        //ImageLoader.getInstance().displayImage(url, view, MyApplication.getSimpleOptions(fallbackResource, loadingResource));
    }

    /**
     * 显示图片
     *
     * @param view
     * @param url
     * @param fallbackResource
     * @param loadingResource
     */
    public void setImageByUrl(ImageView view, String url, Integer fallbackResource, Integer loadingResource) {
        ImageLoader.getInstance().displayImage(url, view, MyApplication.getSimpleOptions(fallbackResource, loadingResource));
    }

    public void setImageByUrlRoundImage(int viewID, String url, Integer fallbackResource, Integer loadingResource) {
        ImageView view = getView(viewID);

        ImageLoader.getInstance().displayImage(url, view, MyApplication.getRoundImage(fallbackResource, loadingResource));
    }

    /**
     * 为 设置 点击事件
     *
     * @param viewID
     * @param onClickListener
     */
    public void setOnClickListener(int viewID, OnClickListener onClickListener) {
        View copyFancyBtn = getView(viewID);
        copyFancyBtn.setOnClickListener(onClickListener);
    }
}
