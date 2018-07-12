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
import com.beatutify.databinding.ProductBookingListRowBinding;
import com.beatutify.model.BookedProductDetail;
import com.beatutify.model.ProductBookings;
import com.beatutify.model.Products;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.security.Key;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class ProductBookingListAdapter1 extends  RecyclerView.Adapter<ProductBookingListAdapter1.MyViewHolder>{
    private Context mContext;
    private LinkedHashMap<String, List<BookedProductDetail>> bookedProductMap;
    private int requestCode;
    private Set<String> keySet;
    private List<String> keyList;
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ProductBookingListRowBinding binding;
        public MyViewHolder(View view){
            super(view);
            binding = DataBindingUtil.bind(view);
            binding.txtPastBookingStatus.setOnClickListener(this);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
        }
    }
    public ProductBookingListAdapter1(Context mContext, LinkedHashMap<String, List<BookedProductDetail>> bookedProductMap
            , int requestCode) {
        this.mContext = mContext;
        this.bookedProductMap = bookedProductMap;

        this.requestCode = requestCode;
        keySet = bookedProductMap.keySet();
        keyList = new ArrayList<>(keySet);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_booking_list_row, parent, false);
        if (requestCode == 501){
            itemView.setAlpha(1);
        }else{
            itemView.setAlpha((float)0.6);

        }
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String key = keyList.get(position);
        List<BookedProductDetail> bookedProductDetailList = bookedProductMap.get(key);

        ImageLoader.getInstance().displayImage(bookedProductDetailList.get(0).getSalonLogoUrl(), holder.binding.imgSalonLogo);
        holder.binding.txtSalonName.setText(bookedProductDetailList.get(0).getSalonName());
        holder.binding.txtSalonAddress.setText(bookedProductDetailList.get(0).getAddress());
        holder.binding.txtPastBookingStatus.setText(bookedProductDetailList.get(0).getBookingStatus());

        if (requestCode == 501){
            // upcoming products
            holder.binding.txtPickUpDate.setVisibility(View.VISIBLE);
            holder.binding.txtPastBookingStatus.setVisibility(View.GONE);
            holder.binding.txtPickupDateLabel.setVisibility(View.VISIBLE);
        }else{
            // past products
            //holder.binding.txtPickUpDate.setVisibility(View.GONE);
            holder.binding.txtPastBookingStatus.setVisibility(View.VISIBLE);
            holder.binding.txtPastBookingStatus.setAlpha(1);
            //holder.binding.txtPickupDateLabel.setVisibility(View.GONE);
        }
        Date date = new Date();
        DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        try{
            date = inputFormatter.parse(bookedProductDetailList.get(0).getPickupDate());
        }catch (Exception e){
        }
        DateFormat outputFormatter = new SimpleDateFormat("dd-MMM-yyyy");
        String output = outputFormatter.format(date);
        holder.binding.txtPickUpDate.setText(output);
        holder.binding.lnrlayProductList.removeAllViews();
        for (int i =0; i < bookedProductDetailList.size(); i++){
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            BookingProductDetailRowBinding productDetailRowBinding;
            View view = layoutInflater.inflate(R.layout.booking_product_detail_row, null);
            //View hiddenInfo = getLayoutInflater().inflate(R.layout.hidden, myLayout, false);
            productDetailRowBinding = DataBindingUtil.bind(view);
            ImageLoader.getInstance().displayImage(bookedProductDetailList.get(i).getProductLogo(), productDetailRowBinding.imgProductLogo );

            productDetailRowBinding.txtProductQuantity.setText(Integer.toString(bookedProductDetailList.get(i).getQuantity()));
            productDetailRowBinding.txtProductName.setText(bookedProductDetailList.get(i).getProductname());
            Resources res = mContext.getResources();
            String category = String.format(res.getString(R.string.product_subtitle), bookedProductDetailList.get(i).getCategory());
            String price = String.format(res.getString(R.string.product_price), bookedProductDetailList.get(i).getPrice());
            productDetailRowBinding.txtProductSubtitle.setText(category);
            productDetailRowBinding.txtProductPrice.setText(price);
            holder.binding.lnrlayProductList.addView(view,0,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }
    @Override
    public int getItemCount() {
        return bookedProductMap.size();
    }

}