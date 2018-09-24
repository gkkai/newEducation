package com.meiliangzi.app.widget;

import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.ImageBean;
import com.meiliangzi.app.tools.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by kk on 2018/8/15.
 */

public class ScaleImageView {
    private static final byte URLS = 0;
    private static final byte FILES = 1;
    private byte status;

    private Activity activity;

    private List<String> urls;
    private List<ImageBean> paths;
    private List<File> downloadFiles;

    private int selectedPosition;

    private Dialog dialog;

    private ImageView delete;
    private ImageView download;
    private TextView imageCount;
    private ViewPager viewPager;

    private List<View> views;
    private MyPagerAdapter adapter;

    private OnDeleteItemListener listener;
    private int startPosition;
    private ThreadPoolExecutor IOThread;

    public ScaleImageView(Activity activity) {
        this.activity = activity;
        init();
    }

    public void setUrls(List<String> urls, int startPosition) {
        if (this.urls == null) {
            this.urls = new ArrayList<>();
        } else {
            this.urls.clear();
        }
        this.urls.addAll(urls);
        status = URLS;
        delete.setVisibility(View.GONE);
        if (downloadFiles == null) {
            downloadFiles = new ArrayList<>();
        } else {
            downloadFiles.clear();
        }
        this.startPosition = startPosition++;
        String text = startPosition + "/" + urls.size();
        imageCount.setText(text);
    }

    public void setFiles(List<ImageBean> paths, int startPosition) {
//        if (this.files == null) {
//            this.files = new LinkedList<>();
//        } else {
//            this.files.clear();
//        }
        this.paths=paths;
        status = FILES;
        download.setVisibility(View.GONE);
        this.startPosition = startPosition++;
        String text = startPosition + "/" + paths.size();
        imageCount.setText(text);
    }

    public void setOnDeleteItemListener(OnDeleteItemListener listener) {
        this.listener = listener;
    }

    private void init() {
        RelativeLayout relativeLayout = (RelativeLayout) activity.getLayoutInflater().inflate(R.layout.showbigpicturedialog, null);
        ImageView close = (ImageView) relativeLayout.findViewById(R.id.scale_image_close);
        delete = (ImageView) relativeLayout.findViewById(R.id.scale_image_delete);
        download = (ImageView) relativeLayout.findViewById(R.id.scale_image_save);
        imageCount = (TextView) relativeLayout.findViewById(R.id.scale_image_count);
        viewPager = (ViewPager) relativeLayout.findViewById(R.id.scale_image_view_pager);
        dialog = new Dialog(activity, R.style.Dialog_Fullscreen);
        dialog.setContentView(relativeLayout);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            int size = views.size();
                paths.remove(selectedPosition);
            if (listener != null) {
                listener.onDelete(paths.get(selectedPosition).getPath());
            }
            viewPager.removeView(views.remove(selectedPosition));
            if (selectedPosition != size) {
                int position = selectedPosition + 1;
                String text = position + "/" + views.size();
                imageCount.setText(text);
            }
            adapter.notifyDataSetChanged();
        };
                                  });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(!isFastClick()){
                        try {
                            MediaStore.Images.Media.insertImage(activity.getContentResolver(),
                                    downloadFiles.get(selectedPosition).getAbsolutePath(),
                                    downloadFiles.get(selectedPosition).getName(), null);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Snackbar.make(viewPager, "图片保存成功", Snackbar.LENGTH_SHORT).show();
                    }else {
                        ToastUtils.show("正在下载.....");
                    }

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedPosition = position;
                String text = ++position + "/" + views.size();
                imageCount.setText(text);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void create() {
        dialog.show();
        views = new ArrayList<>();
        adapter = new MyPagerAdapter(views, dialog);
        if (status == URLS) {
            for (final String url : urls) {
                FrameLayout frameLayout = (FrameLayout) activity.getLayoutInflater().inflate(R.layout.view_scale_image, null);
                final SubsamplingScaleImageView imageView = (SubsamplingScaleImageView) frameLayout.findViewById(R.id.scale_image_view);
                views.add(frameLayout);
                Executors.newCachedThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {


                        final File downLoadFile;
                        try {
                            downLoadFile = Glide.with(activity).load(url).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                            downloadFiles.add(downLoadFile);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                 public void run() {
                                    imageView.setImage(ImageSource.uri(Uri.fromFile(downLoadFile)));
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            viewPager.setAdapter(adapter);
        } else if (status == FILES) {

            for (ImageBean path : paths) {
                FrameLayout frameLayout = (FrameLayout) activity.getLayoutInflater().inflate(R.layout.view_scale_image, null);
                SubsamplingScaleImageView imageView = (SubsamplingScaleImageView) frameLayout.findViewById(R.id.scale_image_view);
                views.add(frameLayout);
                imageView.setImage(ImageSource.uri(Uri.fromFile(new File(path.getPath()))));
                //imageView.setimager
            }
            viewPager.setAdapter(adapter);
        }
        viewPager.setCurrentItem(startPosition);
    }

    private static class MyPagerAdapter extends PagerAdapter {

        private List<View> views;
        private Dialog dialog;

        MyPagerAdapter(List<View> views, Dialog dialog) {
            this.views = views;
            this.dialog = dialog;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (position == 0 && views.size() == 0) {
                dialog.dismiss();
                return;
            }
            if (position == views.size()) {
                container.removeView(views.get(--position));
            } else {
                container.removeView(views.get(position));
            }
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }
//    private static class MyPagerAdapter2 extends PagerAdapter {
//
//        private List<Integer> mDrawableResIdList;
//        private Dialog dialog;
//
//        MyPagerAdapter2(List<Integer> views, Dialog dialog) {
//            this.mDrawableResIdList = views;
//            this.dialog = dialog;
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            if (mDrawableResIdList != null && position < mDrawableResIdList.size()) {
//                Integer resId = mDrawableResIdList.get(position);
//                if (resId != null) {
//                    ImageView itemView = new ImageView();
//                    itemView.setb(resId);
//
//                    //此处假设所有的照片都不同，用resId唯一标识一个itemView；也可用其它Object来标识，只要保证唯一即可
//                    itemView.setTag(resId);
//
//                    ((ViewPager) container).addView(itemView);
//                    return itemView;
//                }
//            }
//            return null;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            if (position == 0 && mDrawableResIdList.size() == 0) {
//                dialog.dismiss();
//                return;
//            }
//            if (object != null) {
//                ViewGroup viewPager = ((ViewGroup) container);
//                int count = viewPager.getChildCount();
//                for (int i = 0; i < count; i++) {
//                    View childView = viewPager.getChildAt(i);
//                    if (childView == object) {
//                        viewPager.removeView(childView);
//                        break;
//                    }
//                }
//            }
//
//        }
//
//        @Override
//        public int getCount() {
//            if (mDrawableResIdList != null) {
//                return mDrawableResIdList.size();
//            }
//            return 0;
//
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//        @Override
//        public int getItemPosition(Object object) {
//            if (object != null && mDrawableResIdList != null) {
//                Integer resId = (Integer)((ImageView)object).getTag();
//                if (resId != null) {
//                    for (int i = 0; i < mDrawableResIdList.size(); i++) {
//                        if (resId.equals(mDrawableResIdList.get(i))) {
//                            return i;
//                        }
//                    }
//                }
//            }
//            return POSITION_NONE;
//        }
//        public void updateData(List<Integer> itemsResId) {
//            if (itemsResId == null) {
//                return;
//            }
//            mDrawableResIdList = itemsResId;
//            this.notifyDataSetChanged();
//        }
//
//        }

    public interface OnDeleteItemListener {
        void onDelete(String position);
    }
    private static final int MIN_DELAY_TIME= 2000;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;
    public boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

}

