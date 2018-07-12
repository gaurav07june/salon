package com.beatutify.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beatutify.R;
import com.beatutify.databinding.NotificationListItemBinding;
import com.beatutify.model.BeautifyNotificationModel;
import com.beatutify.util.AppCommons;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karan.kalsi on 4/19/2018.
 */

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.NotificationHolder> {

    private List<BeautifyNotificationModel> notificationList = new ArrayList<>();

    public List<BeautifyNotificationModel> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<BeautifyNotificationModel> notificationList) {

        this.notificationList.clear();
        if (notificationList != null)
            this.notificationList.addAll(notificationList);
        notifyDataSetChanged();
    }

    @Override
    public NotificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list_item, parent, false);
        return new NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    class NotificationHolder extends RecyclerView.ViewHolder {
        private NotificationListItemBinding binding;

        public NotificationHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void bind(int position) {
            binding.notificationMsgTv.setText(notificationList.get(position).getMessage());
            binding.notificationTimeTv.setText(notificationList.get(position).getDate());
            binding.salonShortNameTv.setText(AppCommons.getShortName(notificationList.get(position).getSalonName()));
        }

    }
}
