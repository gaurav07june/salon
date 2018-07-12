package com.beatutify.datepicker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beatutify.R;
import com.beatutify.util.AppCommons;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karan.kalsi on 3/28/2018.
 */

public class DayItemGridAdapter extends RecyclerView.Adapter<DayItemGridAdapter.DayItemHolder> {

    private List<DayItemModel> dayItemList = new ArrayList<>();
    private Context mContext;
    private boolean isStartDateSelected = false;

    public DayItemGridAdapter(Context context) {
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
    public DayItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_picker_day_item, parent, false);
        return new DayItemHolder(view);
    }

    @Override
    public void onBindViewHolder(DayItemHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return dayItemList.size();
    }

    private View selectedDayTv = null;

    public class DayItemHolder extends RecyclerView.ViewHolder {

        public DayItemHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            dayNameTv = itemView.findViewById(R.id.day_name_tv);
            dayItemNameContainer = itemView.findViewById(R.id.day_item_name_container);
            monthNameTv = itemView.findViewById(R.id.month_name_tv);
            yearNameTv = itemView.findViewById(R.id.year_name_tv);
            monthTagTv = itemView.findViewById(R.id.month_tag_lay);
            dayNameTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        if (dayItemList.get(position).getDay()<=0)return;

                    if (!dayItemList.get(position).isPastDate() && !dayItemList.get(position).isLeaveDate()
                            && !dayItemList.get(position).isDayOff()
                            ) {
                        if (selectedDayTv != null)
                            selectedDayTv.setSelected(false);

                        if (onDaySelectListener != null)
                            onDaySelectListener.onDaySelect(v, dayItemList.get(position));
                        dayNameTv.setSelected(true);
                        selectedDayTv = dayNameTv;
                    }
                }
            });
        }

        TextView dayNameTv;
        View dayItemNameContainer;
        TextView monthNameTv;
        TextView yearNameTv;
        View monthTagTv;
        View itemView;
        int position;


        public void bind(int position) {
            this.position = position;
            dayNameTv.setText(String.format("%d", dayItemList.get(position).getDay()));

            if (dayItemList.get(position).isPastDate() || dayItemList.get(position).isDayOff()  ) {
                dayItemNameContainer.setAlpha(0.5f);
            } else {
                dayItemNameContainer.setAlpha(1f);
            }

            if (dayItemList.get(position).isLeaveDate())
                dayNameTv.setBackgroundResource(R.drawable.date_on_leave_circle);
            else
                dayNameTv.setBackgroundResource(R.drawable.date_item_selector);

            if (!dayItemList.get(position).isLeaveDate() && dayItemList.get(position).isStartDate() && !isStartDateSelected) {

                if (onDaySelectListener != null)
                    onDaySelectListener.onDaySelect(dayNameTv, dayItemList.get(position));
                isStartDateSelected = true;
                dayNameTv.setSelected(true);
                selectedDayTv=dayNameTv;
            }

            itemView.setVisibility(View.VISIBLE);
            if (dayItemList.get(position).getDay() == 0 || dayItemList.get(position).getDay()==-1) {
                itemView.setVisibility(View.INVISIBLE);
            }

            if (dayItemList.get(position).getDay() == 1) {
                monthTagTv.setVisibility(View.VISIBLE);
                yearNameTv.setText(String.format("%d", dayItemList.get(position).getYear()));
                monthNameTv.setText(String.format("%s", AppCommons.getMonthName(mContext, dayItemList.get(position).getMonthIndex())));
            } else if (dayItemList.get(position).getDay() != -1 && dayItemList.get(position).getDay() <= DatePickerConfig.DAYS_COLUMN_SIZE - dayItemList.get(position).getColumnStartIndex()) {
                monthTagTv.setVisibility(View.INVISIBLE);

            } else {
                monthTagTv.setVisibility(View.GONE);

            }
        }
    }

    private OnDaySelectListener onDaySelectListener = null;

    public OnDaySelectListener getOnDaySelectListener() {
        return onDaySelectListener;
    }

    public void setOnDaySelectListener(OnDaySelectListener onDaySelectListener) {
        this.onDaySelectListener = onDaySelectListener;
    }

    public interface OnDaySelectListener {
        void onDaySelect(View view, DayItemModel day);
    }
}
