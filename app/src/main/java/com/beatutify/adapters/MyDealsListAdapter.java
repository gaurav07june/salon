package com.beatutify.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beatutify.R;
import com.beatutify.databinding.MarriageBookingListRowBinding;
import com.beatutify.databinding.MyDealsItemRowBinding;
import com.beatutify.model.DealList;
import com.beatutify.model.MarriageBookingDetail;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by gaurav.singh on 4/9/2018.
 */

public class MyDealsListAdapter extends  RecyclerView.Adapter<MyDealsListAdapter.MyViewHolder>{
    private Context mContext;
    private ArrayList<DealList> dealList;
    private static MyDealListListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        MyDealsItemRowBinding binding;
        public MyViewHolder(View view){
            super(view);
            binding=DataBindingUtil.bind(view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onViewDeal(v, getLayoutPosition());
        }
    }

    public MyDealsListAdapter(Context mContext, ArrayList<DealList> dealList, MyDealListListener listener) {
        this.mContext = mContext;
        this.dealList = dealList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_deals_item_row, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        DealList deal = dealList.get(position);
        ImageLoader.getInstance().displayImage(deal.getDealImageUrl(), holder.binding.imgDealImage);
        holder.binding.txtSalonName.setText(deal.getSalonName());
        holder.binding.txtSalonAddress.setText(deal.getSalonAddress());
        DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date validFromDate = new Date();
        Date validToDate = new Date();
        try{
            validFromDate = inputFormatter.parse(deal.getValidFrom());
            validToDate = inputFormatter.parse(deal.getValidFrom());
        }catch (Exception e){
        }

        DateFormat outputFormatter = new SimpleDateFormat("dd MMM,yyyy");
        String outputValidFromDate = outputFormatter.format(validFromDate);
        String outputValidToDate = outputFormatter.format(validToDate);
        Resources resources = mContext.getResources();

        holder.binding.txtDealValidity.setText(String.format(resources.getString(R.string.valid_date),
                deal.getValidFrom(), deal.getValidTo()));
        holder.binding.txtDealTitle.setText(deal.getDealTitle());
        holder.binding.txtDealDescription.setText(deal.getDealDescription());
    }

    @Override
    public int getItemCount() {
        return dealList.size();
    }
    public interface MyDealListListener {
        public void onViewDeal(View v, int position);
    }
}
