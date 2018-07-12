package com.beatutify.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beatutify.R;
import com.beatutify.databinding.BookingProductDetailRowBinding;
import com.beatutify.databinding.GeneralBookingDetailRowBinding;
import com.beatutify.databinding.ProductBookingListRowBinding;
import com.beatutify.model.GeneralBookingDetailResponseModel;
import com.beatutify.model.GeneralBookingListResponseModel;
import com.beatutify.model.ProductBookings;
import com.beatutify.model.Products;
import com.beatutify.model.ServiceDetail;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BookedServiceListAdapter extends  RecyclerView.Adapter<BookedServiceListAdapter.MyViewHolder>{
    private Context mContext;
    private GeneralBookingDetailResponseModel responseModel;

    private ArrayList<ServiceDetail> serviceDetailList;
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        GeneralBookingDetailRowBinding binding;
        public MyViewHolder(View view){
            super(view);
            binding = DataBindingUtil.bind(view);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
        }
    }
    public BookedServiceListAdapter(Context mContext, GeneralBookingDetailResponseModel responseModel) {
        this.mContext = mContext;
        this.responseModel = responseModel;
        this.serviceDetailList =(ArrayList<ServiceDetail>) responseModel.getServiceLists();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.general_booking_detail_row, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ServiceDetail serviceDetail = serviceDetailList.get(position);
        holder.binding.txtServiceName.setText(serviceDetail.getServiceName());
        Resources resources = mContext.getResources();
        String serviceDuration = String.format(resources.getString(R.string.service_duration),serviceDetail.getServiceTime());
        String servicePrice = String.format(resources.getString(R.string.product_price), serviceDetail.getCost());
        holder.binding.txtSeviceDuration.setText(serviceDuration);
        holder.binding.txtServicePrice.setText(servicePrice);
        holder.binding.txtEmployeeName.setText(responseModel.getEmployeeName());
        holder.binding.txtBookingAdditionalDetail.setText(serviceDetail.getServiceComment());

    }
    @Override
    public int getItemCount() {
        return serviceDetailList.size();
    }

}