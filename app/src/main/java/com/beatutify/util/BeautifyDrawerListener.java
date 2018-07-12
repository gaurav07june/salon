package com.beatutify.util;

import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

/**
 * Created by karan.kalsi on 4/17/2018.
 */

public class BeautifyDrawerListener implements   DrawerLayout.DrawerListener {
    private  int containerHeight=0;
    private View containerToAnimate;
    public BeautifyDrawerListener(View containerToAnimate)
    {
        this.containerToAnimate = containerToAnimate;
    }
    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        if (containerHeight==0)containerHeight= this.containerToAnimate.getHeight();
         this.containerToAnimate.setX( this.containerToAnimate.getWidth()*0.75f*slideOffset);
         this.containerToAnimate.setPivotX(0);
        float scale = 1f-0.5f*slideOffset;
         this.containerToAnimate.setScaleX(scale);
         this.containerToAnimate.setScaleY(scale);
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
