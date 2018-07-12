package com.beatutify.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beatutify.R;
import com.beatutify.datepicker.DayItemModel;
import com.beatutify.model.Employee;
import com.beatutify.model.Service;
import com.beatutify.util.AppCommons;
import com.beatutify.util.AppConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karan.kalsi on 3/30/2018.
 */

public class ServiceBookListAdapter extends RecyclerView.Adapter<ServiceBookListAdapter.ServiceBookItemHolder> {
private  List<Service> serviceList = new ArrayList<>();
private Employee selectedEmployee ;
private DayItemModel selectedDate ;
private Context mContext;

    public ServiceBookListAdapter(Context context)
    {
        mContext=context;
    }
    public void setData(List<Service> serviceList, Employee selectedEmployee,DayItemModel selectedDate)
    {
        this.serviceList.clear();
        this.serviceList.addAll(serviceList);
        this.selectedEmployee= selectedEmployee;
        this.selectedDate = selectedDate;
        notifyDataSetChanged();

    }

    @Override
    public ServiceBookItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_book_list_item,parent,false);
        return new ServiceBookItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ServiceBookItemHolder holder, int position) {
    holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public class ServiceBookItemHolder extends RecyclerView.ViewHolder
    {
        TextView serviceNameTv;
        TextView serviceDurationTv;
        TextView serviceDescTv;
        TextView employeeNameTv;
        TextView dateTimeTv;
        View editServiceBooking;
        View deleteServiceBooking;
        public ServiceBookItemHolder(View itemView) {
            super(itemView);
            serviceNameTv=itemView.findViewById(R.id.service_name_tv);
            serviceDurationTv=itemView.findViewById(R.id.service_duration_tv);
            serviceDescTv=itemView.findViewById(R.id.service_desc_tv);
            employeeNameTv=itemView.findViewById(R.id.employee_name_tv);
            dateTimeTv=itemView.findViewById(R.id.date_time_tv);
            editServiceBooking=itemView.findViewById(R.id.edit_service_booking);
            deleteServiceBooking=itemView.findViewById(R.id.delete_service_booking);

            editServiceBooking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (serviceBookListItemListener!=null)serviceBookListItemListener.onEditClicked(serviceList.get(position));
                }
            });

            deleteServiceBooking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (serviceBookListItemListener!=null)serviceBookListItemListener.onDeleteClicked(serviceList.get(position));

                }
            });

        }

        public void bind(int position)
        {
            serviceNameTv.setText(serviceList.get(position).getServiceName());
            serviceDurationTv.setText(mContext.getString(R.string.service_duration_format,serviceList.get(position).getTime()));
            serviceDescTv.setText(serviceList.get(position).getServiceDesc());
            if (selectedEmployee!=null)
            employeeNameTv.setText(selectedEmployee.getEmployeeName());
            if (selectedDate!=null)
                dateTimeTv.setText(AppCommons.getUIDateTime(mContext,selectedDate));



        }
    }

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void removeService(Service service)
    {
        int serviceIndex = serviceList.indexOf(service);
        serviceList.remove(serviceIndex);
        notifyItemRemoved(serviceIndex);
    }
    public void addService(Service service)
    {
        serviceList.add(service);
        notifyItemInserted(getItemCount()-1);
    }


private ServiceBookListItemListener serviceBookListItemListener;

    public ServiceBookListItemListener getServiceBookListItemListener() {
        return serviceBookListItemListener;
    }

    public void setServiceBookListItemListener(ServiceBookListItemListener serviceBookListItemListener) {
        this.serviceBookListItemListener = serviceBookListItemListener;
    }

    public interface ServiceBookListItemListener
    {
        void onEditClicked(Service service);
        void onDeleteClicked(Service service);
    }
}
