package com.beatutify.customviews;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.beatutify.R;
import com.beatutify.activity.DetailActivity;
import com.beatutify.databinding.ActivityDetailNewBinding;
import com.beatutify.model.SalonProduct;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by gaurav.singh on 4/24/2018.
 */

public class ProductDetailPopUp extends Dialog implements View.OnClickListener {

    public Activity activity;
    public Dialog dialog;
    private ProductDetailListener listener;
    private SalonProduct salonProduct;
    boolean doAdd = true;
    boolean doRemove = false;
    private ActivityDetailNewBinding binding;
    ImageView imgProduct, imgCancel;
    NMGTextView txtProductName, txtProductCategory, txtProductDesc, txtProductPrice, txtProductQuantityStatus;
    AddRemoveItemButton addRemoveItemButton;


    public ProductDetailPopUp(Activity activity, SalonProduct salonProduct, ActivityDetailNewBinding binding, ProductDetailListener listener) {
        super(activity);
        this.activity = activity;
        this.salonProduct = salonProduct;
        this.listener = listener;
        this.binding = binding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.product_detail_pop_up);
        this.setCancelable(false);
        imgProduct = findViewById(R.id.img_product_logo);
        imgCancel = findViewById(R.id.img_close);
        txtProductName = findViewById(R.id.txt_product_name);
        txtProductCategory = findViewById(R.id.txt_product_category);
        txtProductDesc = findViewById(R.id.txt_product_detail);
        txtProductPrice = findViewById(R.id.txt_product_cost);
        txtProductQuantityStatus = findViewById(R.id.txt_product_quantity_status);
        addRemoveItemButton = findViewById(R.id.btn_add_remove_quantity);
        ImageLoader.getInstance().displayImage( salonProduct.getImageName(), imgProduct);
        txtProductName.setText(salonProduct.getName());
        Resources resources = activity.getResources();
        txtProductCategory.setText(String.format(resources.getString(R.string.product_subtitle),salonProduct.getCategoryName()));
        txtProductPrice.setText(String.format(resources.getString(R.string.product_price), salonProduct.getCost()));
        txtProductQuantityStatus.setVisibility(View.GONE);
        addRemoveItemButton.setAddButtonEnabled();
        addRemoveItemButton.setRemoveButtonEnabled();
        if (salonProduct.getSelectedQuantity() == 0){
            addRemoveItemButton.setRemoveButtonDisabled();
        }
        if (salonProduct.getQuantity() <= 0){
            addRemoveItemButton.setRemoveButtonDisabled();
            addRemoveItemButton.setAddButtonDisabled();
            txtProductQuantityStatus.setVisibility(View.VISIBLE);
            txtProductQuantityStatus.setText(resources.getString(R.string.out_of_stock));
        }else if (salonProduct.getSelectedQuantity() >= salonProduct.getQuantity()){
            addRemoveItemButton.setAddButtonDisabled();
            addRemoveItemButton.setRemoveButtonEnabled();
            txtProductQuantityStatus.setVisibility(View.VISIBLE);
            String quantityInStock = String.format(resources.getString(R.string.quantity_in_stock), salonProduct.getQuantity());
            txtProductQuantityStatus.setText(quantityInStock);
        }
        addRemoveItemButton.setQuantityColor(ContextCompat.getColor(activity, R.color.colorBlack));
        addRemoveItemButton.setQuantity(salonProduct.getSelectedQuantity());

        addRemoveItemButton.setAddRemoveItemButtonListener(new AddRemoveItemButton.AddRemoveItemButtonListener() {
            @Override
            public void onAddClicked() {

                if (doAdd){
                    salonProduct.addSelectedQuantity();

                    DetailActivity.productsInCart++;
                    binding.txtAddedProductNo.setText(Integer.toString(DetailActivity.productsInCart));
                }
                enableDisableButton(salonProduct);
                //notifyItemChanged(position);
                addRemoveItemButton.setQuantity(salonProduct.getSelectedQuantity());
            }


            private void enableDisableButton(SalonProduct salonProduct) {
                doAdd = true;
                doRemove = true;
                txtProductQuantityStatus.setVisibility(View.GONE);
                addRemoveItemButton.setAddButtonEnabled();
                addRemoveItemButton.setRemoveButtonEnabled();
                if (salonProduct.getSelectedQuantity() <= 0){
                    addRemoveItemButton.setRemoveButtonDisabled();
                    doRemove = false;
                }
                if (salonProduct.getSelectedQuantity() >= salonProduct.getQuantity()){
                    addRemoveItemButton.setAddButtonDisabled();
                    txtProductQuantityStatus.setVisibility(View.VISIBLE);
                    Resources resources = activity.getResources();
                    String quantityInStock = String.format(resources.getString(R.string.quantity_in_stock), salonProduct.getQuantity());
                    txtProductQuantityStatus.setText(quantityInStock);
                    doAdd = false;
                }
            }


            @Override
            public void onRemovedClicked() {
                //Toast.makeText(activity, "remove", Toast.LENGTH_SHORT).show();
                if (doRemove){
                    salonProduct.removeSelectedQuantity();
                    DetailActivity.productsInCart--;
                    binding.txtAddedProductNo.setText(Integer.toString(DetailActivity.productsInCart));
                }
                enableDisableButton(salonProduct);
                //notifyItemChanged(position);
                addRemoveItemButton.setQuantity(salonProduct.getSelectedQuantity());
            }
        });
        imgCancel.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_close:
                listener.onCancelClicked();
                break;
            default:
                break;
        }
        dismiss();
    }
    public interface ProductDetailListener{
        void onCancelClicked();

    }
}
