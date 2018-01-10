package com.meiliangzi.app.ui.base;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class BasePagerAdapter extends android.support.v4.view.PagerAdapter {


    private List<? extends View> views = new ArrayList<View>();
    private ViewGroup viewGroup;

    public void refreshViews(List<? extends View> views) {
        this.views = views;
        notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        viewGroup = container;
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
