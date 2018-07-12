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
import com.beatutify.model.ProductBookings;
import com.beatutify.model.Products;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ProductBookingListAdapter extends  RecyclerView.Adapter<ProductBookingListAdapter.MyViewHolder>{
    private Context mContext;
    private ArrayList<ProductBookings> bookingList;
    private static ProductBookingListListener listener;
    private int requestCode;
    private ArrayList<Products> productList;
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
            switch (v.getId()){
                case R.id.btn_view_booking:
                    listener.onViewBooking(v, getLayoutPosition());
                    break;
                case R.id.txt_past_booking_status:
                    listener.onViewBooking(v, getLayoutPosition());
            }
        }
    }
    public ProductBookingListAdapter(Context mContext, ArrayList<ProductBookings> bookingList,
                                     ProductBookingListListener listener, int requestCode) {
        this.mContext = mContext;
        this.bookingList = bookingList;
        this.listener = listener;
        this.requestCode = requestCode;
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
        ProductBookings bookingDetail = bookingList.get(position);
        ImageLoader.getInstance().displayImage(bookingDetail.getSalonLogoUrl(), holder.binding.imgSalonLogo);
        holder.binding.txtSalonName.setText(bookingDetail.getSalonName());
        holder.binding.txtSalonAddress.setText(bookingDetail.getAddress());
        holder.binding.txtPastBookingStatus.setText(bookingDetail.getBookingStatus());

        if (requestCode == 501){
            // upcoming products
            holder.binding.txtPickUpDate.setVisibility(View.VISIBLE);
            holder.binding.txtPastBookingStatus.setVisibility(View.GONE);
            holder.binding.txtPickupDateLabel.setVisibility(View.VISIBLE);
        }else{
            // past products
            holder.binding.txtPickUpDate.setVisibility(View.GONE);
            holder.binding.txtPastBookingStatus.setVisibility(View.VISIBLE);
            holder.binding.txtPickupDateLabel.setVisibility(View.GONE);
        }
        Date date = new Date();
        DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        try{
            date = inputFormatter.parse(bookingDetail.getPickupDate());
        }catch (Exception e){
        }
        DateFormat outputFormatter = new SimpleDateFormat("dd-MMM-yyyy");
        String output = outputFormatter.format(date);
        holder.binding.txtPickUpDate.setText(output);

        productList = (ArrayList<Products>) bookingDetail.getProducts();
        holder.binding.lnrlayProductList.removeAllViews();
        for (int i =0; i < productList.size(); i++){
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            BookingProductDetailRowBinding productDetailRowBinding;
            View view = layoutInflater.inflate(R.layout.booking_product_detail_row, null);
            //View hiddenInfo = getLayoutInflater().inflate(R.layout.hidden, myLayout, false);
            productDetailRowBinding = DataBindingUtil.bind(view);
            ImageLoader.getInstance().displayImage(productList.get(i).getProductLogo(), productDetailRowBinding.imgProductLogo );

            productDetailRowBinding.txtProductQuantity.setText(Integer.toString(productList.get(i).getQuantity()));
            productDetailRowBinding.txtProductName.setText(productList.get(i).getProductName());
            Resources res = mContext.getResources();
            String category = String.format(res.getString(R.string.product_subtitle), productList.get(i).getCategory());
            String price = String.format(res.getString(R.string.product_price), productList.get(i).getPrice());
            productDetailRowBinding.txtProductSubtitle.setText(category);
            productDetailRowBinding.txtProductPrice.setText(price);
            holder.binding.lnrlayProductList.addView(view,0,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }
    @Override
    public int getItemCount() {
        return bookingList.size();
    }
    public interface ProductBookingListListener {
        public void onViewBooking(View v, int position);
    }
}