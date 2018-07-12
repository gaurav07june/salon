package com.beatutify.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyViewPagerAdapter extends PagerAdapter{
    private LayoutInflater layoutInflater;
    private Context context;
    int[] slides_layouts;
    public MyViewPagerAdapter(Context context, int[] slides_layouts) {
        this.context = context;
        this.slides_layouts = slides_layouts;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(slides_layouts[position], container, false);
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return slides_layouts.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
