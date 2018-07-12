package com.beatutify.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beatutify.R;
import com.beatutify.model.SalonServiceList;
import com.beatutify.model.ServiceCategory;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by gaurav.singh on 3/14/2018.
 */

public class ProductListAdapter extends RecyclerView.Adapter <ProductListAdapter.MyViewHolder>{

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private List<ServiceCategory> serviceCategories;
    Context context;
    public ProductListAdapter(Context context, List<ServiceCategory> serviceCategories){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.serviceCategories = serviceCategories;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.service_item_cell, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String productName = serviceCategories.get(position).getServiceCatName();
        holder.txtProductName.setText(productName);
        ImageLoader.getInstance().displayImage(serviceCategories.get(position).getServiceCatImage(), holder.imgProductImage);
    }
    // convenience method for getting data at click position
    String getItem(int id) {
        return serviceCategories.get(id).getServiceCatName();
    }
    @Override
    public int getItemCount() {
        return serviceCategories.size();
    }
    // stores and recycles views as they are scrolled off screen
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtProductName;
        CircleImageView imgProductImage;
        MyViewHolder(View itemView) {
            super(itemView);
            txtProductName = (TextView) itemView.findViewById(R.id.txt_product);
            imgProductImage = (CircleImageView) itemView.findViewById(R.id.img_product);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onServiceCatClick(view, serviceCategories.get(getAdapterPosition()));
        }
    }
    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onServiceCatClick(View view, ServiceCategory serviceCategory);
    }

}
