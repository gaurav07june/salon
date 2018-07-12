package com.beatutify.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.beatutify.R;
import com.beatutify.customviews.AddRemoveItemButton;
import com.beatutify.databinding.ActivityReservedProductDetailBinding;
import com.beatutify.databinding.ReservedProductRowBinding;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.model.ReserveProductRequestModel;
import com.beatutify.model.ReserveProductResponseModel;
import com.beatutify.model.Salon;
import com.beatutify.model.SalonProduct;

import com.beatutify.model.SoldOutItem;
import com.beatutify.retrofit.WebAPIHelper;
import com.beatutify.util.AppConstants;
import com.beatutify.util.AppGlobalData;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;


public class ReservedProductDetailActivity extends BaseActivity implements View.OnClickListener{
    ActivityReservedProductDetailBinding binding;
    private Salon salonData;
    private ArrayList<SalonProduct> reservedProductsList;
    private String pickupDate;
    private String submitDate;
    private ReserveProductRequestModel reserveProductRequestModel;
    private final int RESERVE_PRODUCT_REQ_CODE = 101;
    private int productInCart;
    @Override
    public void setContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reserved_product_detail);
        reserveProductRequestModel = new ReserveProductRequestModel();
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void getExtras() {
        Intent intent = getIntent();
        salonData = (Salon) intent.getSerializableExtra(AppConstants.EXTRAS.SALON_DATA);
        reservedProductsList = (ArrayList<SalonProduct>) intent.getSerializableExtra(AppConstants.EXTRAS.RESERVED_PRODUCT_DETAIL);
        pickupDate = intent.getStringExtra(AppConstants.EXTRAS.PICK_UP_DATE);
        submitDate = intent.getStringExtra(AppConstants.EXTRAS.SUBMIT_PICK_DATE);
    }

    @Override
    public void initViews() {
    }

    @Override
    public void setViews() {
        binding.layoutContentReservedProductDetail.btnReserveNow.setOnClickListener(this);
        binding.imgAddMoreProducts.setOnClickListener(this);

        if (salonData != null){
            binding.layoutContentReservedProductDetail.txtSalonName.setText(salonData.getSalon_name());
            binding.layoutContentReservedProductDetail.txtSalonAddress.setText(salonData.getAddress());
            binding.layoutContentReservedProductDetail.txtPickUpDate.setText(pickupDate);
            ImageLoader.getInstance().displayImage(salonData.getImage(), binding.layoutContentReservedProductDetail.imgSalonLogo);

        }
        displayReservedProductList();
        binding.layoutContentReservedProductDetail.txtAddedProductNo.setText(Integer.toString(productInCart));
    }

    @Override
    public <D> void onApiSuccess(GenericResponseModel<D> model, int request_code) {

        if (model != null){
            GenericResponseModel<ReserveProductResponseModel> responseModel = (GenericResponseModel<ReserveProductResponseModel>) model;
            List<SoldOutItem> soldOutItemList = responseModel.getData().getSoldOut();
            if (soldOutItemList.size() > 0){
                Toast.makeText(this, model.getMessage(), Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(this , OrderPlacedActivity.class);
                intent.putExtra(AppConstants.EXTRAS.REQ_FROM_PAGE, 11);
                intent.putExtra(AppConstants.EXTRAS.RESERVE_PRODUCT_MSG, responseModel.getMessage());
                startActivity(intent);
            }
        }

    }

    private void displayReservedProductList() {
        productInCart = 0;
        if (reservedProductsList != null && reservedProductsList.size() > 0){
            for (final SalonProduct salonProduct : reservedProductsList){
                productInCart+= salonProduct.getSelectedQuantity();
                LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final ReservedProductRowBinding reservedProductRowBinding;
                final View view = layoutInflater.inflate(R.layout.reserved_product_row, null);
                //View hiddenInfo = getLayoutInflater().inflate(R.layout.hidden, myLayout, false);
                reservedProductRowBinding = DataBindingUtil.bind(view);
                ImageLoader.getInstance().displayImage(salonProduct.getImageName(), reservedProductRowBinding.imgProductLogo );
                reservedProductRowBinding.txtProductName.setText(salonProduct.getName());
                Resources res = getResources();
                String category = String.format(res.getString(R.string.product_subtitle), salonProduct.getCategoryName());
                String price = String.format(res.getString(R.string.product_price), salonProduct.getCost());
                reservedProductRowBinding.txtProductSubtitle.setText(category);
                reservedProductRowBinding.txtProductPrice.setText(price);
                reservedProductRowBinding.imgDeleteProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reservedProductsList.remove(salonProduct);
                        productInCart-= salonProduct.getSelectedQuantity();
                        binding.layoutContentReservedProductDetail.txtAddedProductNo.setText(Integer.toString(productInCart));
                        TransitionManager.beginDelayedTransition(binding.layoutContentReservedProductDetail.lnrlayReserveProductList);
                        binding.layoutContentReservedProductDetail.lnrlayReserveProductList.removeView(view);
                    }
                });
                reservedProductRowBinding.btnAddRemoveReservedProcduct.setQuantity(salonProduct.getSelectedQuantity());
                if (salonProduct.getSelectedQuantity() == 0){
                    reservedProductRowBinding.btnAddRemoveReservedProcduct.setRemoveButtonDisabled();
                }
                if ((salonProduct.getQuantity() <= 0) || (salonProduct.getQuantity() == salonProduct.getSelectedQuantity())){
                    reservedProductRowBinding.btnAddRemoveReservedProcduct.setRemoveButtonDisabled();
                    reservedProductRowBinding.btnAddRemoveReservedProcduct.setAddButtonDisabled();
                    reservedProductRowBinding.txtQuantityInStock.setVisibility(View.VISIBLE);
                    reservedProductRowBinding.txtQuantityInStock.setText(getResources().getString(R.string.out_of_stock));
                }
                if ((salonProduct.getQuantity() != 0) && (salonProduct.getQuantity() == salonProduct.getSelectedQuantity())){
                    reservedProductRowBinding.btnAddRemoveReservedProcduct.setRemoveButtonEnabled();
                    reservedProductRowBinding.btnAddRemoveReservedProcduct.setAddButtonDisabled();
                    reservedProductRowBinding.txtQuantityInStock.setVisibility(View.VISIBLE);
                    Resources resources = getResources();
                    String quantityInStock = String.format(resources.getString(R.string.quantity_in_stock), salonProduct.getQuantity());
                    reservedProductRowBinding.txtQuantityInStock.setText(quantityInStock);
                }
                reservedProductRowBinding.btnAddRemoveReservedProcduct.setAddRemoveItemButtonListener(new AddRemoveItemButton.AddRemoveItemButtonListener() {
                    boolean doAdd = true;
                    boolean doRemove = true;
                    @Override
                    public void onAddClicked() {
                        if (doAdd){
                            salonProduct.addSelectedQuantity();
                        }
                        enableDisableButton(salonProduct);
                        reservedProductRowBinding.btnAddRemoveReservedProcduct.setQuantity(salonProduct.getSelectedQuantity());
                        productInCart++;
                        binding.layoutContentReservedProductDetail.txtAddedProductNo.setText(Integer.toString(productInCart));

                    }

                    @Override
                    public void onRemovedClicked() {
                        if (doRemove){
                            salonProduct.removeSelectedQuantity();
                        }
                        enableDisableButton(salonProduct);
                        reservedProductRowBinding.btnAddRemoveReservedProcduct.setQuantity(salonProduct.getSelectedQuantity());
                        productInCart--;
                        binding.layoutContentReservedProductDetail.txtAddedProductNo.setText(Integer.toString(productInCart));

                    }
                    private void enableDisableButton(SalonProduct salonProduct) {
                        doAdd = true;
                        doRemove = true;
                        reservedProductRowBinding.txtQuantityInStock.setVisibility(View.GONE);
                        reservedProductRowBinding.btnAddRemoveReservedProcduct.setAddButtonEnabled();
                        reservedProductRowBinding.btnAddRemoveReservedProcduct.setRemoveButtonEnabled();
                        if (salonProduct.getSelectedQuantity() <= 0){
                            reservedProductRowBinding.btnAddRemoveReservedProcduct.setRemoveButtonDisabled();
                            doRemove = false;
                        }
                        if (salonProduct.getSelectedQuantity() >= salonProduct.getQuantity()){
                            reservedProductRowBinding.btnAddRemoveReservedProcduct.setAddButtonDisabled();
                            reservedProductRowBinding.txtQuantityInStock.setVisibility(View.VISIBLE);
                            Resources resources = getResources();
                            String quantityInStock = String.format(resources.getString(R.string.quantity_in_stock), salonProduct.getQuantity());
                            reservedProductRowBinding.txtQuantityInStock.setText(quantityInStock);
                            doAdd = false;
                        }
                    }
                });
                binding.layoutContentReservedProductDetail.lnrlayReserveProductList.addView(view,0,
                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }

        }
    }


    @Override
    public <D> void onApiFail(GenericResponseModel<D> model, int request_code) {

    }

    @Override
    public void onApiException(Throwable t, int request_code) {

    }

    @Override
    public void hitApi(int request) {
        switch (request){
            case RESERVE_PRODUCT_REQ_CODE:
                WebAPIHelper.getInstance().doProductReservation(reserveProductRequestModel, new APICallback<ReserveProductResponseModel>(RESERVE_PRODUCT_REQ_CODE));
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_reserve_now:
                doProductReservation();
                break;
            case R.id.img_add_more_products:
                onBackPressed();
                break;
        }
    }
    private void doProductReservation() {

        List<SalonProduct> productsList = new ArrayList<>();
        reserveProductRequestModel.setSalonId(salonData.getSalon_id());
        reserveProductRequestModel.setDate(submitDate);
        reserveProductRequestModel.setToken(AppGlobalData.getInstance().getToken());
        for (int i =0; i < reservedProductsList.size(); i++){
            if (reservedProductsList.get(i).getSelectedQuantity() > 0){
                SalonProduct products = new SalonProduct();
                products.setProductId(reservedProductsList.get(i).getProductId());
                products.setQuantity(reservedProductsList.get(i).getSelectedQuantity());
                products.setName(reservedProductsList.get(i).getName());
                productsList.add(products);
            }
        }
        reserveProductRequestModel.setProducts(productsList);
        if (reserveProductRequestModel.getProducts().size() > 0){
            checkInternetAndHitApi(RESERVE_PRODUCT_REQ_CODE);
        }else{
            Toast.makeText(this, getResources().getString(R.string.choose_product), Toast.LENGTH_SHORT).show();
        }

    }
}
