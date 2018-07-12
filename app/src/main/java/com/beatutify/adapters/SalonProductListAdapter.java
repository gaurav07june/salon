package com.beatutify.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beatutify.R;
import com.beatutify.activity.DetailActivity;
import com.beatutify.customviews.AddRemoveItemButton;
import com.beatutify.databinding.ActivityDetailBinding;
import com.beatutify.databinding.ActivityDetailNewBinding;
import com.beatutify.databinding.SalonProductRowBinding;

import com.beatutify.model.SalonProduct;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by gaurav.singh on 3/14/2018.
 */

public class SalonProductListAdapter extends RecyclerView.Adapter <SalonProductListAdapter.MyViewHolder>{

    private LayoutInflater mInflater;
    private SalonProductItemClickListener listener;
    private List<SalonProduct> productList;
    private ActivityDetailNewBinding binding;
    Context context;
    public SalonProductListAdapter(Context context, List<SalonProduct> productList, SalonProductItemClickListener listener, ActivityDetailNewBinding binding){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.binding = binding;
        this.productList = productList;
        this.listener = listener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.salon_product_row, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        SalonProduct salonProduct = productList.get(position);
        ImageLoader.getInstance().displayImage(salonProduct.getImageName(), holder.salonProductRowBinding.imgProductLogo);
        holder.salonProductRowBinding.txtProductName.setText(salonProduct.getName());
        Resources res = context.getResources();
        String category = String.format(res.getString(R.string.product_subtitle), salonProduct.getCategoryName());
        holder.salonProductRowBinding.txtProductSubtitle.setText(category);
        String price = String.format(res.getString(R.string.product_price), salonProduct.getCost());
        holder.salonProductRowBinding.txtProductPrice.setText(price);
        holder.salonProductRowBinding.btnAddRemoveQuantity.setQuantity(salonProduct.getSelectedQuantity());
        holder.salonProductRowBinding.txtQuantityInStock.setVisibility(View.GONE);
        holder.salonProductRowBinding.btnAddRemoveQuantity.setAddButtonEnabled();
        holder.salonProductRowBinding.btnAddRemoveQuantity.setRemoveButtonEnabled();
        if (salonProduct.getSelectedQuantity() == 0){
            holder.salonProductRowBinding.btnAddRemoveQuantity.setRemoveButtonDisabled();
        }
        if (salonProduct.getQuantity() <= 0){
            holder.salonProductRowBinding.btnAddRemoveQuantity.setRemoveButtonDisabled();
            holder.salonProductRowBinding.btnAddRemoveQuantity.setAddButtonDisabled();
            holder.salonProductRowBinding.txtQuantityInStock.setVisibility(View.VISIBLE);
            holder.salonProductRowBinding.txtQuantityInStock.setText(context.getResources().getString(R.string.out_of_stock));
        }else if (salonProduct.getSelectedQuantity() >= salonProduct.getQuantity()){
            holder.salonProductRowBinding.btnAddRemoveQuantity.setAddButtonDisabled();
            holder.salonProductRowBinding.btnAddRemoveQuantity.setRemoveButtonEnabled();
            holder.salonProductRowBinding.txtQuantityInStock.setVisibility(View.VISIBLE);
            Resources resources = context.getResources();
            String quantityInStock = String.format(resources.getString(R.string.quantity_in_stock), salonProduct.getQuantity());
            holder.salonProductRowBinding.txtQuantityInStock.setText(quantityInStock);
        }
        holder.salonProductRowBinding.btnAddRemoveQuantity.setAddRemoveItemButtonListener(new AddRemoveItemButton.AddRemoveItemButtonListener() {
            boolean doAdd = true;
            boolean doRemove = false;
            @Override
            public void onAddClicked() {

                if (doAdd){
                    productList.get(position).addSelectedQuantity();
                    DetailActivity.productsInCart++;
                    binding.txtAddedProductNo.setText(Integer.toString(DetailActivity.productsInCart));
                }
                enableDisableButton(productList.get(position));
                //notifyItemChanged(position);
                holder.salonProductRowBinding.btnAddRemoveQuantity.setQuantity(productList.get(position).getSelectedQuantity());
            }

            private void enableDisableButton(SalonProduct salonProduct) {
                doAdd = true;
                doRemove = true;
                holder.salonProductRowBinding.txtQuantityInStock.setVisibility(View.GONE);
                holder.salonProductRowBinding.btnAddRemoveQuantity.setAddButtonEnabled();
                holder.salonProductRowBinding.btnAddRemoveQuantity.setRemoveButtonEnabled();
                if (salonProduct.getSelectedQuantity() <= 0){
                    holder.salonProductRowBinding.btnAddRemoveQuantity.setRemoveButtonDisabled();
                    doRemove = false;
                }
                if (salonProduct.getSelectedQuantity() >= salonProduct.getQuantity()){
                    holder.salonProductRowBinding.btnAddRemoveQuantity.setAddButtonDisabled();
                    holder.salonProductRowBinding.btnAddRemoveQuantity.setRemoveButtonEnabled();
                    holder.salonProductRowBinding.txtQuantityInStock.setVisibility(View.VISIBLE);
                    Resources resources = context.getResources();
                    String quantityInStock = String.format(resources.getString(R.string.quantity_in_stock), salonProduct.getQuantity());
                    holder.salonProductRowBinding.txtQuantityInStock.setText(quantityInStock);
                    doAdd = false;
                }
            }
            @Override
            public void onRemovedClicked() {

                if (doRemove){
                    productList.get(position).removeSelectedQuantity();
                    DetailActivity.productsInCart--;
                    binding.txtAddedProductNo.setText(Integer.toString(DetailActivity.productsInCart));
                }
                enableDisableButton(productList.get(position));
                //notifyItemChanged(position);
                holder.salonProductRowBinding.btnAddRemoveQuantity.setQuantity(productList.get(position).getSelectedQuantity());
            }
        });

    }
    @Override
    public int getItemCount() {
        return productList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        SalonProductRowBinding salonProductRowBinding;

        MyViewHolder(View itemView) {
            super(itemView);
            salonProductRowBinding = DataBindingUtil.bind(itemView);
            salonProductRowBinding.imgProductLogo.setOnClickListener(this);
            salonProductRowBinding.txtProductName.setOnClickListener(this);
            salonProductRowBinding.txtProductPrice.setOnClickListener(this);
            salonProductRowBinding.txtProductSubtitle.setOnClickListener(this);

        }
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.img_product_logo:
                    listener.onProductItemClick(view, getLayoutPosition());
                    break;
                case R.id.txt_product_name:
                    listener.onProductItemClick(view, getLayoutPosition());
                    break;
                case R.id.txt_product_price:
                    listener.onProductItemClick(view, getLayoutPosition());
                    break;
                case R.id.txt_product_subtitle:
                    listener.onProductItemClick(view, getLayoutPosition());
                    break;
            }
        }
    }

    public interface SalonProductItemClickListener {
        void onProductItemClick(View view, int position);
        void onAddItemClick(View view, int position);
        void onDeleteItemClick(View view, int position);
    }
    public List<SalonProduct> getProductsinCart()
    {
        List<SalonProduct> productsInCart = new ArrayList<>();
        for (SalonProduct product : productList)
        {
            if (product.getSelectedQuantity() > 0)
            {
                productsInCart.add(product);
            }
        }
        return productsInCart;
    }
}
