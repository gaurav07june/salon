package com.beatutify.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beatutify.R;
import com.beatutify.model.TimeSlot;
import com.beatutify.util.AppCommons;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karan.kalsi on 3/27/2018.
 */

public class TimeSlotListAdapter extends RecyclerView.Adapter<TimeSlotListAdapter.TimeSlotHolder> {

    private List<TimeSlot> timeSlots = new ArrayList<>();
    private int selectedIndex=-1;
    private Context mContext;
    public TimeSlotListAdapter(Context context) {
    mContext = context;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots.clear();
        this.timeSlots.addAll(timeSlots);
        notifyDataSetChanged();
    }

    @Override
    public TimeSlotHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_slot_list_item, parent, false);
        return new TimeSlotHolder(view);
    }

    @Override
    public void onBindViewHolder(TimeSlotHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return timeSlots.size();
    }

    class TimeSlotHolder extends RecyclerView.ViewHolder {
        TextView timeSlotTv;
        View container;
        public TimeSlotHolder(View itemView) {
            super(itemView);
            this.container=itemView;
            timeSlotTv = itemView.findViewById(R.id.time_slot_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (onTimeSlotSelectListener!=null && onTimeSlotSelectListener.onTimeSlotSelect(timeSlots.get(position)))
                    {
                        if (timeSlots.get(position).isDisabled())
                            return;
                        timeSlotTv.setSelected(true);
                        container.setSelected(true);
                        int oldPos = selectedIndex;
                        selectedIndex=position;
                        if (selectedIndex>=0)
                            notifyItemChanged(oldPos,false);
                    }
                }
            });

        }

        public void bind(int position) {
            timeSlotTv.setText (AppCommons.to12H(mContext,timeSlots.get(position).getTime()));
            timeSlotTv.setAlpha(timeSlots.get(position).isDisabled() ? 0.5f : 1f);
            timeSlotTv.setSelected(position==selectedIndex);
            container.setSelected(position==selectedIndex);
        }
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }
private OnTimeSlotSelectListener onTimeSlotSelectListener;

    public OnTimeSlotSelectListener getOnTimeSlotSelectListener() {
        return onTimeSlotSelectListener;
    }

    public void setOnTimeSlotSelectListener(OnTimeSlotSelectListener onTimeSlotSelectListener) {
        this.onTimeSlotSelectListener = onTimeSlotSelectListener;
    }

    public interface OnTimeSlotSelectListener
{
    boolean onTimeSlotSelect(TimeSlot timeSlot);
}

}
