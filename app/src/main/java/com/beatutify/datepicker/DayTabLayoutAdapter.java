package com.beatutify.datepicker;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beatutify.R;
import com.beatutify.util.AppCommons;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by karan.kalsi on 3/28/2018.
 */

public class DayTabLayoutAdapter extends RecyclerView.Adapter<DayTabLayoutAdapter.DayTabHolder> {

    private List<DayItemModel> dayItemList = new ArrayList<>();
    private Context mContext;
    private RecyclerView tabLayout;
    private int selectedIndex = 0;



    public DayTabLayoutAdapter(Context context, RecyclerView tabLayout) {
        this.tabLayout = tabLayout;
        mContext = context;
    }

    public List<DayItemModel> getDayItemList() {
        return dayItemList;
    }

    public void setDayItemList(List<DayItemModel> dayItemList) {
        this.dayItemList.clear();
        this.dayItemList.addAll(dayItemList);
        notifyDataSetChanged();
    }

    @Override
    public DayTabHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_slot_day_tab_item, parent, false);
        return new DayTabHolder(view);
    }

    @Override
    public void onBindViewHolder(DayTabHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return dayItemList.size();
    }

    public class DayTabHolder extends RecyclerView.ViewHolder {
        TextView dayName;
        TextView weekDayName;
        View selectionStrip;
        View itemView;

        public DayTabHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            dayName = itemView.findViewById(R.id.time_slot_day_name);
            weekDayName = itemView.findViewById(R.id.time_slot_weekday_name);
            selectionStrip = itemView.findViewById(R.id.selection_strip);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    //     tabLayout.setCurrentItem(position,true);
                    if(dayItemList.get(position).isLeaveDate() || dayItemList.get(position).isPastDate() || dayItemList.get(position).isDayOff() )
                        return;
                    scrollToPosition(position,true);
                    selectionStrip.setSelected(true);
                    dayName.setSelected(true);
                    weekDayName.setSelected(true);
                    int oldPosition = selectedIndex;
                    selectedIndex = position;
                    notifyItemChanged(oldPosition, false);
                    if (onDaySelectListener!=null)onDaySelectListener.onDaySelect(dayItemList.get(position));
                }
            });

        }

        public void bind(int position) {
            DayItemModel day = dayItemList.get(position);
            dayName.setText(String.format("%d", day.getDay()));
            Date date = AppCommons.formatAppDate(day.getDate());
            if (day.isPastDate() || day.isLeaveDate() || day.isDayOff()) {
                itemView.setAlpha(0.5f);
            } else {
                itemView.setAlpha(1f);
            }

            selectionStrip.setSelected(selectedIndex == position);
            dayName.setSelected(selectedIndex == position);
            weekDayName.setSelected(selectedIndex == position);
            Calendar selCalendar = Calendar.getInstance();
            selCalendar.setTime(date);
            weekDayName.setText(AppCommons.getWeekDayName(mContext, selCalendar.get(Calendar.DAY_OF_WEEK)));

        }
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    private int getScrollPosition(int position) {
        int scroll_position;
        LinearLayoutManager layoutManager = (LinearLayoutManager) tabLayout.getLayoutManager();

        int total_visible_size = layoutManager.findLastVisibleItemPosition() - layoutManager.findFirstVisibleItemPosition() + 1;
        int center_position = layoutManager.findFirstVisibleItemPosition() + total_visible_size / 2;
        if (position > center_position)
            scroll_position = (position + total_visible_size / 2)-1;
        else
            scroll_position = (position - total_visible_size / 2)+1;

        if (scroll_position < 0)
            return 0;
        else if (scroll_position > getItemCount())
            return getItemCount() - 1;
        else
            return scroll_position;
    }

    public void scrollToPosition(int position, boolean smoothScroll) {
        if (smoothScroll)
            tabLayout.smoothScrollToPosition(getScrollPosition(position));
        else
            tabLayout.scrollToPosition(getScrollPosition(position));

    }
    private OnDaySelectListener onDaySelectListener;

    public OnDaySelectListener getOnDaySelectListener() {
        return onDaySelectListener;
    }

    public void setOnDaySelectListener(OnDaySelectListener onDaySelectListener) {
        this.onDaySelectListener = onDaySelectListener;
    }

    public interface OnDaySelectListener
    {
        void onDaySelect(DayItemModel day);
    }
}
