package com.beatutify.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beatutify.R;
import com.beatutify.databinding.MarriageBookingListRowBinding;
import com.beatutify.model.MarriageBookingDetail;
import com.beatutify.model.Products;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by gaurav.singh on 4/9/2018.
 */

public class MarriageBookingListAdapter extends  RecyclerView.Adapter<MarriageBookingListAdapter.MyViewHolder>{
    private Context mContext;
    private ArrayList<MarriageBookingDetail> bookingList;
    private static MarriageBookingListListener listener;
    private int requestCode;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        MarriageBookingListRowBinding binding;
        public MyViewHolder(View view){
            super(view);
            binding=DataBindingUtil.bind(view);
            binding.btnViewBooking.setOnClickListener(this);
            binding.txtPastBookingStatus.setOnClickListener(this);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_view_booking:
                    listener.onViewBooking(v, getLayoutPosition(), binding.imgSalonLogo);
                    break;
                case R.id.txt_past_booking_status:
                    listener.onViewBooking(v, getLayoutPosition(), binding.imgSalonLogo);
            }
        }
    }

    public MarriageBookingListAdapter(Context mContext, ArrayList<MarriageBookingDetail> bookingList, MarriageBookingListListener listener, int requestCode) {
        this.mContext = mContext;
        this.bookingList = bookingList;
        this.listener = listener;
        this.requestCode = requestCode;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.marriage_booking_list_row, parent, false);
        if (requestCode == 301 || requestCode == 601){
            itemView.setAlpha(1);
        }else{
            itemView.setAlpha((float)0.6);
        }
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        MarriageBookingDetail bookingDetail = bookingList.get(position);
        ImageLoader.getInstance().displayImage(bookingDetail.getSalonLogoUrl(), holder.binding.imgSalonLogo);
        holder.binding.txtSalonName.setText(bookingDetail.getSalonName());
        holder.binding.txtSalonAddress.setText(bookingDetail.getAddress());

        DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try{
            date = inputFormatter.parse(bookingDetail.getBookingDate());
        }catch (Exception e){

        }

        DateFormat outputFormatter = new SimpleDateFormat("dd MMM,yyyy");
        String output = outputFormatter.format(date);
        if (requestCode == 601 || requestCode == 602){
            holder.binding.lnrlayGeneralBookingSlot.setVisibility(View.VISIBLE);
            holder.binding.lnrlayMarriageBookingSlot.setVisibility(View.GONE);
        }else if(requestCode == 301 || requestCode == 302){
            holder.binding.lnrlayGeneralBookingSlot.setVisibility(View.GONE);
            holder.binding.lnrlayMarriageBookingSlot.setVisibility(View.VISIBLE);
        }
        holder.binding.txtBookingDate.setText(output);
        holder.binding.txtEmployeeName.setText(bookingDetail.getEmployeeName());
        holder.binding.txtDateTimeSlot.setText(output+","+bookingDetail.getBookingTimeSlot());

        if (bookingDetail.getTimeSlot() == 1){
            holder.binding.txtBookingSlot.setText(mContext.getResources().getString(R.string.morning));
        }else{
            holder.binding.txtBookingSlot.setText(mContext.getResources().getString(R.string.evening));
        }
        holder.binding.txtSalonDetail.setText(bookingDetail.getAdditionalDetail());
        if (bookingDetail.getBookingStatus() != null){
            holder.binding.btnViewBooking.setVisibility(View.GONE);
            holder.binding.txtPastBookingStatus.setVisibility(View.VISIBLE);
            holder.binding.txtPastBookingStatus.setText(bookingDetail.getBookingStatus());
        }else{
            holder.binding.btnViewBooking.setVisibility(View.VISIBLE);
            holder.binding.txtPastBookingStatus.setVisibility(View.GONE);

        }
        if (requestCode == 301){
            holder.binding.btnViewBooking.setVisibility(View.VISIBLE);
            holder.binding.txtPastBookingStatus.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }
    public interface MarriageBookingListListener {
        public void onViewBooking(View v, int position , View salonImageView);
    }
}
