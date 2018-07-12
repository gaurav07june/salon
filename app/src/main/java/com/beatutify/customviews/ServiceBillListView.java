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
import com.beatutify.model.Service;
import com.beatutify.util.AppCommons;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karan.kalsi on 4/2/2018.
 */

public class ServiceBillListView extends RecyclerView {

    private List<Service> bookedServices = new ArrayList<>();
    private ServiceBillListAdapter serviceBillListAdapter;
    public ServiceBillListView(Context context) {
        super(context);
        init();
    }

    public ServiceBillListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ServiceBillListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
        setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        serviceBillListAdapter = new ServiceBillListAdapter();
        setAdapter(serviceBillListAdapter);
    }

    public float getTotalCost() {
        float totalCost = 0;
        for (Service service : bookedServices) {
            totalCost += service.getCost();
        }

        return totalCost;
    }

    public class ServiceBillListAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final int BILL_ITEM_TYPE = 100;
        private static final int AMOUNT_ITEM_TYPE = 101;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case BILL_ITEM_TYPE:
                    return new ServiceBillItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_list_item, parent, false));

                case AMOUNT_ITEM_TYPE:
                    return new ServiceAmountItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.amount_item, parent, false));

            }
            return null;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof  ServiceBillItemHolder)
        {
            ((ServiceBillItemHolder) holder).bind(position);
        }
        else
        if (holder instanceof  ServiceAmountItemHolder)
        {
            ((ServiceAmountItemHolder) holder).bind(position);
        }

        }

        @Override
        public int getItemViewType(int position) {
            if (position == getItemCount() - 1) {
                return AMOUNT_ITEM_TYPE;
            } else {
                return BILL_ITEM_TYPE;
            }

        }

        @Override
        public int getItemCount() {
            return bookedServices.size() + 1;
        }

        public class ServiceBillItemHolder extends ViewHolder {
            TextView serviceNameTv;
            TextView serviceCostTv;

            public ServiceBillItemHolder(View itemView) {
                super(itemView);

                serviceNameTv = itemView.findViewById(R.id.service_name_tv);
                serviceCostTv = itemView.findViewById(R.id.service_cost_tv);
            }

            public void bind(int position) {
                if (position < bookedServices.size()) {
                    serviceNameTv.setText(bookedServices.get(position).getServiceName());
                    serviceCostTv.setText(getContext().getString(R.string.service_cost_format, AppCommons.formatCost(bookedServices.get(position).getCost())));
                }
            }
        }


        public class ServiceAmountItemHolder extends ViewHolder {
            TextView billAmountTv;

            public ServiceAmountItemHolder(View itemView) {
                super(itemView);
                billAmountTv = itemView.findViewById(R.id.bill_amount_tv);
            }

            public void bind(int position) {
                billAmountTv.setText(getContext().getString(R.string.bill_amount_format, AppCommons.formatCost(getTotalCost())));
            }
        }

    }

    public List<Service> getBookedServices() {
        return bookedServices;
    }

    public void setBookedServices(List<Service> bookedServices) {
        this.bookedServices.clear();
        this.bookedServices.addAll(bookedServices);
        serviceBillListAdapter.notifyDataSetChanged();
    }
}
