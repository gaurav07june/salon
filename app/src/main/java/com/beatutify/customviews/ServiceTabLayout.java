package com.beatutify.customviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beatutify.R;
import com.beatutify.adapters.ServiceListAdapter;
import com.beatutify.model.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karan.kalsi on 3/29/2018.
 */

public class ServiceTabLayout extends RecyclerView {
    private List<Service> serviceList = new ArrayList<>();
    private ServiceTabLayoutAdapter serviceTablayoutAdapter;

    public ServiceTabLayout(Context context) {
        super(context);
        init();
    }

    public ServiceTabLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ServiceTabLayout(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        serviceTablayoutAdapter = new ServiceTabLayoutAdapter();
        setAdapter(serviceTablayoutAdapter);

    }


    public class ServiceTabLayoutAdapter extends Adapter<ServiceTabLayoutAdapter.ServiceTabHolder> {

        @Override
        public ServiceTabHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_tab_item, parent, false);
            return new ServiceTabHolder(view);
        }

        @Override
        public void onBindViewHolder(ServiceTabHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return serviceList.size();
        }

        public class ServiceTabHolder extends ViewHolder {

            TextView serviceNameTv;

            public ServiceTabHolder(View itemView) {
                super(itemView);
                serviceNameTv = itemView.findViewById(R.id.service_name_tv);

                serviceNameTv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();


                        if (onServiceTabCheckListener != null &&  onServiceTabCheckListener.onServiceTabCheck(serviceList.get(position)))
                        {
                            boolean isSelected = serviceList.get(position).isSelected();
                            serviceList.get(position).setSelected(!isSelected);
                            serviceNameTv.setSelected(!isSelected);
                        }

                    }
                });

            }

            public void bind(int position) {
                serviceNameTv.setText(serviceList.get(position).getServiceName());
                serviceNameTv.setSelected(serviceList.get(position).isSelected());
            }
        }

    }

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        if (serviceList == null) return;
        this.serviceList.addAll(serviceList);
        serviceTablayoutAdapter.notifyDataSetChanged();
    }

    private OnServiceTabCheckListener onServiceTabCheckListener;

    public OnServiceTabCheckListener getOnServiceTabCheckListener() {
        return onServiceTabCheckListener;
    }

    public void setOnServiceTabCheckListener(OnServiceTabCheckListener onServiceTabCheckListener) {
        this.onServiceTabCheckListener = onServiceTabCheckListener;
    }

    public interface OnServiceTabCheckListener {
        boolean onServiceTabCheck(Service service);

    }

    public void setServiceSelected(Service service, boolean isChecked) {
        int position = serviceList.indexOf(service);

        if (position >= 0) {
            serviceList.get(position).setSelected(isChecked);
            serviceTablayoutAdapter.notifyItemChanged(position);
        }
    }
}
