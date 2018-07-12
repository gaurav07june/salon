package com.beatutify.datepicker;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.beatutify.R;
import com.beatutify.util.AppCommons;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by karan.kalsi on 3/22/2018.
 */

public class AppDatePicker extends FrameLayout implements DayItemGridAdapter.OnDaySelectListener {

    private RecyclerView dayItemGridView;
    private DayItemGridAdapter dayItemGridAdapter;
    private TabLayout tabLayout;
    private DayItemGridAdapter.OnDaySelectListener onDaySelectListener;

    public AppDatePicker(@NonNull Context context) {
        super(context);
        init();
    }

    public AppDatePicker(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppDatePicker(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.app_date_picker, this, false);
        addView(view);

        dayItemGridView = findViewById(R.id.day_item_grid_view);
        tabLayout = findViewById(R.id.date_picker_week_day_tab_lay);
        dayItemGridAdapter = new DayItemGridAdapter(getContext());
        dayItemGridView.setAdapter(dayItemGridAdapter);
        dayItemGridView.setLayoutManager(new GridLayoutManager(getContext(), DatePickerConfig.DAYS_COLUMN_SIZE, LinearLayoutManager.VERTICAL, false));
        dayItemGridAdapter.setOnDaySelectListener(this);
        initWeekDayTabLay();
    }

    private void initWeekDayTabLay() {

        tabLayout.addTab(tabLayout.newTab().setText(R.string.sun));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.mon));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tue));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.wed));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.thu));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.fri));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.sat));
        tabLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    public void setData(List<DayItemModel> dayItemList) {
        dayItemGridAdapter.setDayItemList(dayItemList);


    }


    @Override
    public void onDaySelect(View view, DayItemModel day) {

        Date date = AppCommons.formatAppDate(day.getDate());
        if (date != null) {
            Calendar selCalendar = Calendar.getInstance();
            selCalendar.setTime(date);
            selectDayTab(selCalendar.get(Calendar.DAY_OF_WEEK) - 1);

        }
        if (onDaySelectListener != null) onDaySelectListener.onDaySelect(view, day);
    }

    private void selectDayTab(int dayIndex) {
        try {
            tabLayout.getTabAt(dayIndex).select();
        } catch (Exception e) {

        }
    }

    public DayItemGridAdapter.OnDaySelectListener getOnDaySelectListener() {
        return onDaySelectListener;
    }

    public void setOnDaySelectListener(DayItemGridAdapter.OnDaySelectListener onDaySelectListener) {
        this.onDaySelectListener = onDaySelectListener;
    }
}
