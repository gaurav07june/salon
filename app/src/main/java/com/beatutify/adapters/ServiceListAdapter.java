package com.beatutify.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.beatutify.R;
import com.beatutify.model.Service;
import com.beatutify.util.AppCommons;
import com.rey.material.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karan.kalsi on 3/21/2018.
 */

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ServiceHolder> {
private ArrayList<Service> serviceList = new ArrayList<>();
private List<Service> unfilteredServiceList = new ArrayList<>();
private Context mContext;

    public ArrayList<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        unfilteredServiceList=serviceList;
        this.serviceList.clear();
        this.serviceList.addAll(serviceList);
        notifyDataSetChanged();
    }

    public ServiceListAdapter(Context context)
    {
        mContext = context;

    }
    @Override
    public ServiceHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.services_list_item,parent,false);
        return new ServiceHolder(view);
    }

    @Override
    public void onBindViewHolder(ServiceHolder holder, int position) {
    holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public class ServiceHolder extends RecyclerView.ViewHolder
    {

        TextView serviceNameTv;
        TextView serviceCostTV;
        TextView serviceDescTv;
        TextView serviceDurationTv;
        CheckBox serviceCheckBox;
        int position;

        public ServiceHolder(View itemView) {
            super(itemView);
            serviceNameTv = itemView.findViewById(R.id.service_name_tv);
            serviceCostTV = itemView.findViewById(R.id.service_cost_tv);
            serviceDescTv = itemView.findViewById(R.id.service_desc_tv);
            serviceDurationTv = itemView.findViewById(R.id.service_duration_tv);
            serviceCheckBox = itemView.findViewById(R.id.service_check_box);
            serviceCheckBox.setEnabled(false);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    serviceCheckBox.setChecked(!serviceCheckBox.isChecked());
                    serviceList.get(position).setSelected(serviceCheckBox.isChecked());
                }
            });

        }

        public void bind(int position) {
            this.position=position;
            serviceNameTv.setText(serviceList.get(position).getServiceName());
            serviceCostTV.setText(mContext.getString(R.string.service_cost_format, AppCommons.formatCost(serviceList.get(position).getCost())));
            serviceDurationTv.setText(mContext.getString(R.string.service_duration_format,serviceList.get(position).getTime()));
            serviceDescTv.setText(serviceList.get(position).getServiceDesc());
            serviceCheckBox.setChecked(serviceList.get(position).isSelected());
        }


    }

public ArrayList<Service> getSelectedServices()
{
    ArrayList<Service> selectedServices = new ArrayList<>();

    for (Service service : serviceList)
    {
        if (service.isSelected())
        selectedServices.add(service);
    }
    return selectedServices;
}

public void filter(String query)
{
    List<Service> selectedServices = getSelectedServices();
    serviceList.clear();
    for (Service service : unfilteredServiceList)
    {
    if(service.getServiceName().indexOf(query)>=0)
    {
        service.setSelected(selectedServices.contains(service));
        serviceList.add(service);
    }

    }

    notifyDataSetChanged();
}
}
