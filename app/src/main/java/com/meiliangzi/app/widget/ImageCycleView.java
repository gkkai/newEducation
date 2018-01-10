package com.meiliangzi.app.widget;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.BannerBean;

import java.util.ArrayList;
import java.util.List;



/**
 * 广告图片自动轮播控件</br>
 */
public class ImageCycleView extends LinearLayout {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 图片轮播视图
     */
    private ViewPager mAdvPager = null;
    /**
     * 滚动图片视图适配
     */
    private ImageCycleAdapter mAdvAdapter;

    /**
     * 图片轮播指示个图
     */
    private ImageView mImageView = null;


    /**
     * 图片滚动当前图片下标
     */
    private int mImageIndex = 0;

    /**
     * 手机密度
     */
    private float mScale;
    private boolean isStop;
    private TextView[] mTextViews;
    private TextView mTextView;
    ArrayList<String> imageNameList;
    ViewGroup mGroup;
    private ImageView[] mImageViews;

    /**
     * @param context
     */
    public ImageCycleView(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public ImageCycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mScale = context.getResources().getDisplayMetrics().density;
        LayoutInflater.from(context).inflate(R.layout.view_cycle_view, this);
        mAdvPager = (ViewPager) findViewById(R.id.adv_pager);
        mGroup = (ViewGroup) findViewById(R.id.viewGroup);

        mAdvPager.setOnPageChangeListener(new GuidePageChangeListener());
        mAdvPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        // 开始图片滚动
                        startImageTimerTask();
                        break;
                    default:
                        // 停止图片滚动
                        stopImageTimerTask();
                        break;
                }
                return false;
            }
        });
        // 滚动图片右下指示器视
    }


    /**
     * 图片轮播(手动控制自动轮播与否，便于资源控件）
     */
    public void startImageCycle() {
        startImageTimerTask();
    }

    /**
     * 暂停轮播—用于节省资源
     */
    public void pushImageCycle() {
        stopImageTimerTask();
    }

    /**
     * 图片滚动任务
     */
    private void startImageTimerTask() {
        stopImageTimerTask();
        // 图片滚动
        mHandler.postDelayed(mImageTimerTask, 3000);
    }

    /**
     * 停止图片滚动任务
     */
    private void stopImageTimerTask() {
        isStop = true;
        mHandler.removeCallbacks(mImageTimerTask);
    }

    private Handler mHandler = new Handler();

    /**
     * 图片自动轮播Task
     */
    private Runnable mImageTimerTask = new Runnable() {
        @Override
        public void run() {
            mAdvPager.setCurrentItem(mAdvPager.getCurrentItem() + 1);
            if (!isStop) {  //if  isStop=true   //当你退出后 要把这个给停下来 不然 这个一直存在 就一直在后台循环
                mHandler.postDelayed(mImageTimerTask, 3000);
            }
        }
    };

    /**
     * 轮播图片监听
     *
     * @author minking
     */
    private final class GuidePageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE)
                startImageTimerTask();
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int index) {
			index=index%mImageViews.length;
			// 设置当前显示的图片
			mImageIndex = index;
			// 设置图片滚动指示器背
			mImageViews[index].setBackgroundResource(R.mipmap.ic_point_selected);
			for (int i = 0; i < mImageViews.length; i++) {
				if (index != i) {
					mImageViews[i].setBackgroundResource(R.mipmap.ic_point_unselect);
				}
			}
        }
    }

    private class ImageCycleAdapter extends PagerAdapter {

        /**
         * 图片视图缓存列表
         */
        private ArrayList<ImageView> mImageViewCacheList;

        /**
         * 图片资源列表
         */
        private List<BannerBean.DataBean> mAdList = new ArrayList<BannerBean.DataBean>();

        /**
         * 广告图片点击监听
         */
        private ImageCycleViewListener mImageCycleViewListener;

        private Context mContext;

        public ImageCycleAdapter(Context context, List<BannerBean.DataBean> adList, ImageCycleViewListener imageCycleViewListener) {
            this.mContext = context;
            this.mAdList = adList;
            mImageCycleViewListener = imageCycleViewListener;
            mImageViewCacheList = new ArrayList<ImageView>();
        }




        @Override
        public int getCount() {
//			return mAdList.size();
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            BannerBean.DataBean dataBean = mAdList.get(position % mAdList.size());
            ImageView imageView = null;
            if (mImageViewCacheList.isEmpty()) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                // 设置图片点击监听
                imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mImageCycleViewListener.onImageClick(position % mAdList.size(), v);
                    }
                });
            } else {
                imageView = mImageViewCacheList.remove(0);
            }
            imageView.setTag(dataBean.getId());
            container.addView(imageView);

            mImageCycleViewListener.displayImage(dataBean.getImage(), imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView view = (ImageView) object;
            mAdvPager.removeView(view);
            mImageViewCacheList.add(view);

        }

    }

    /**
     * 装填图片数据
     *
     * @param imageUrlList
     * @param imageCycleViewListener
     */
    public void setImageResources(List<BannerBean.DataBean> imageUrlList, ImageCycleViewListener imageCycleViewListener) {
        // 清除
        mGroup.removeAllViews();
        // 图片广告数量
        final int imageCount = imageUrlList.size();
        mImageViews = new ImageView[imageCount];
        for (int i = 0; i < imageCount; i++) {
            mImageView = new ImageView(mContext);
            int imageParams = (int) (mScale * 10 + 0.5f);// XP与DP转换，适应应不同分辨率
            int imagePadding = (int) (mScale *10 + 0.5f);
            LayoutParams params = new LayoutParams(imageParams, imageParams);
            params.leftMargin = 10;
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageView.setLayoutParams(params);
            mImageView.setPadding(imagePadding, imagePadding, imagePadding, imagePadding);
            mImageViews[i] = mImageView;
            if (i == 0) {
                mImageViews[i].setBackgroundResource(R.mipmap.ic_point_selected);
            } else {
                mImageViews[i].setBackgroundResource(R.mipmap.ic_point_unselect);
            }
            mGroup.addView(mImageViews[i]);
        }
        mAdvAdapter = new ImageCycleAdapter(mContext, imageUrlList, imageCycleViewListener);
        mAdvPager.setAdapter(mAdvAdapter);
//        circleIn.setViewPager(mAdvPager);
        startImageTimerTask();
    }

    /**
     * 轮播控件的监听事件
     *
     * @author minking
     */
    public static interface ImageCycleViewListener {
        /**
         * 加载图片资源
         */
        public void displayImage(String imageURL, ImageView imageView);

        /**
         * 单击图片事件
         */
        public void onImageClick(int position, View imageView);
    }

}
