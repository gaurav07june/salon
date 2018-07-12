package com.beatutify.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.beatutify.R;
import com.beatutify.adapters.ProductListAdapter;
import com.beatutify.adapters.SalonProductListAdapter;


import com.beatutify.customviews.CustomAlertDialog;
import com.beatutify.customviews.ProductDetailPopUp;
import com.beatutify.databinding.ActivityDetailNewBinding;

import com.beatutify.model.GenericResponseModel;
import com.beatutify.model.ProductListRequestModel;
import com.beatutify.model.ProductListResponseModel;
import com.beatutify.model.Products;
import com.beatutify.model.Salon;
import com.beatutify.model.SalonDetailRequestModel;
import com.beatutify.model.SalonDetailResponseModel;
import com.beatutify.model.SalonProduct;
import com.beatutify.model.ServiceCatListRequestModel;
import com.beatutify.model.ServiceCatListResponseModel;
import com.beatutify.model.ServiceCategory;
import com.beatutify.retrofit.WebAPIHelper;
import com.beatutify.util.AppCommons;
import com.beatutify.util.AppConstants;
import com.beatutify.util.AppGlobalData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DetailActivity extends BaseActivity implements View.OnClickListener,
        ProductListAdapter.ItemClickListener, SalonProductListAdapter.SalonProductItemClickListener {
    private ActivityDetailNewBinding binding;
    private  String salon_latitude = "";
    private  String salon_longitude = "";
    private GenericResponseModel<SalonDetailResponseModel> salonDetailResponseModelGenericResponseModel;
    private String imageUrl;
    private Calendar calendar;
    private boolean isGuest = AppGlobalData.getInstance().isGuest();
    private int salonId;
    private Double myLatitude, myLongitude;
    RecyclerView productShowRecyclerView;
    ProductListAdapter productListAdapter;
    private ServiceCatListRequestModel serviceCatListRequestModel;
    private SalonDetailRequestModel salonDetailRequestModel;
    private static final int SALON_DETAIL_REQ_CODE = 202;
    private static final int SERVICE_CAT_LIST_REQ_CODE = 203;
    private static final int PRODUCT_LIST_REQ_CODE = 204;
    private ProductListRequestModel productListRequestModel;
    private SalonProductListAdapter salonProductListAdapter;
    private ArrayList<SalonProduct> productList;
    private Salon salon;
    public static int productsInCart = 0;
    @Override
    public void initViews() {
        calendar = Calendar.getInstance();
    }
    @Override
    public void setViews() {

        binding.btnDetailBookForMarriage.setOnClickListener(this);
        binding.btnServices.setOnClickListener(this);
        binding.btnProducts.setOnClickListener(this);
        binding.txtViewMap.setOnClickListener(this);
        binding.imgViewMap.setOnClickListener(this);
        binding.backBtn.setOnClickListener(this);
        binding.favoriteBtn.setOnClickListener(this);
        binding.btnReserveProduct.setOnClickListener(this);
        binding.btnCancelReserveProduct.setOnClickListener(this);
        binding.lnrlayReserveCancelProduct.setVisibility(View.GONE);
        binding.btnDetailBookForMarriage.setVisibility(View.VISIBLE);

        ImageLoader.getInstance().displayImage(imageUrl, binding.salonImageIv, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                supportStartPostponedEnterTransition();
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                supportStartPostponedEnterTransition();
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                supportStartPostponedEnterTransition();
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                supportStartPostponedEnterTransition();
            }
        });
        ImageLoader.getInstance().displayImage(imageUrl, binding.salonLogo);
        checkInternetAndHitApi(SALON_DETAIL_REQ_CODE);
        checkInternetAndHitApi(SERVICE_CAT_LIST_REQ_CODE);
    }

    @Override
    public void setContentView() {

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_new);
        supportPostponeEnterTransition();
        salonDetailRequestModel = new SalonDetailRequestModel();
        serviceCatListRequestModel = new ServiceCatListRequestModel();
        productListRequestModel = new ProductListRequestModel();
    }
    @Override
    public void getExtras() {
        imageUrl = getIntent().getStringExtra(AppConstants.EXTRAS.SALON_IMAGE_URL);
        salonId = getIntent().getIntExtra(AppConstants.EXTRAS.SALON_ID, 0);
        myLatitude = getIntent().getDoubleExtra("my_latitude", 0.0);
        myLongitude = getIntent().getDoubleExtra("my_longitude", 0.0);
        salon = (Salon) getIntent().getSerializableExtra(AppConstants.EXTRAS.SALON_DATA);
    }

    @Override
    public <D> void onApiSuccess(GenericResponseModel<D> model, int request_code) {
        switch (request_code){
            case SALON_DETAIL_REQ_CODE:
                if (model !=null && model.getMessage() != null){
                    salonDetailResponseModelGenericResponseModel = (GenericResponseModel<SalonDetailResponseModel>) model;
                    setSalonDetailViews(salonDetailResponseModelGenericResponseModel.getData().getSalon());
                    salon_latitude = salonDetailResponseModelGenericResponseModel.getData().getSalon().getLatitude();
                    salon_longitude = salonDetailResponseModelGenericResponseModel.getData().getSalon().getLongitude();
                    //Toast.makeText(this, responseModel.getMessage()+""+responseModel.getData().getSalon().getAddress(), Toast.LENGTH_SHORT).show();
                }
                break;
            case SERVICE_CAT_LIST_REQ_CODE:
                if (model !=null && model.getData() instanceof  ServiceCatListResponseModel){
                    GenericResponseModel<ServiceCatListResponseModel> responseModel = (GenericResponseModel<ServiceCatListResponseModel>) model;
                    if (responseModel.getData().getCategoriesList().size() == 0){
                        binding.txtNoServiceOrProduct.setVisibility(View.VISIBLE);
                        binding.txtNoServiceOrProduct.setText(getResources().getString(R.string.no_service_found));
                    }else {
                        binding.txtNoServiceOrProduct.setVisibility(View.GONE);
                    }
                    int numberOfColumns = 3;
                    binding.serviceProductListView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
                    productListAdapter = new ProductListAdapter(this, responseModel.getData().getCategoriesList());
                    productListAdapter.setClickListener(this);
                    binding.serviceProductListView.setAdapter(productListAdapter);
                    binding.serviceProductListView.setNestedScrollingEnabled(false);
                    //Toast.makeText(this, model.getMessage(), Toast.LENGTH_SHORT).show();


                }
                break;
            case PRODUCT_LIST_REQ_CODE:
                if (model != null && model.getData() instanceof ProductListResponseModel){
                    GenericResponseModel<ProductListResponseModel> responseModel = (GenericResponseModel<ProductListResponseModel>) model;
                    productList = (ArrayList<SalonProduct>) responseModel.getData().getProductList();
                    if (productList.size() == 0){
                        binding.txtNoServiceOrProduct.setVisibility(View.VISIBLE);
                        binding.txtNoServiceOrProduct.setText(getResources().getString(R.string.no_product_found));
                    }else {
                        binding.txtNoServiceOrProduct.setVisibility(View.GONE);
                    }
                    salonProductListAdapter = new SalonProductListAdapter(this, productList, this, binding);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    binding.serviceProductListView.setLayoutManager(mLayoutManager);
                    binding.serviceProductListView.setItemAnimator(new DefaultItemAnimator());
                    DividerItemDecoration itemDecor = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
                    itemDecor.setDrawable(ContextCompat.getDrawable(this, R.drawable.item_divider));
                    binding.serviceProductListView.addItemDecoration(itemDecor);
                    binding.serviceProductListView.setAdapter(salonProductListAdapter);
                }
                break;
        }
    }
    @Override
    public <D> void onApiFail(GenericResponseModel<D> model, int request_code) {
        if (model !=null && model.getMessage() != null){
            Toast.makeText(this, model.getError().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onApiException(Throwable t, int request_code) {
        if (t != null && t.getMessage() != null){
            Toast.makeText(this,t.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void hitApi(int request) {
        switch (request){
            case SALON_DETAIL_REQ_CODE:
                salonDetailRequestModel.setToken(AppGlobalData.getInstance().getToken());
                salonDetailRequestModel.setSalonId(salonId);
                WebAPIHelper.getInstance().showSalonDetail(salonDetailRequestModel, new APICallback<SalonDetailResponseModel>(SALON_DETAIL_REQ_CODE));
                break;
            case SERVICE_CAT_LIST_REQ_CODE:
                serviceCatListRequestModel.setToken(AppGlobalData.getInstance().getToken());
                serviceCatListRequestModel.setSalonId(salonId);
                WebAPIHelper.getInstance().getServiceCatList(serviceCatListRequestModel, new APICallback<ServiceCatListResponseModel>(SERVICE_CAT_LIST_REQ_CODE));
                break;
            case PRODUCT_LIST_REQ_CODE:
                //populate the request model
                productListRequestModel.setToken(AppGlobalData.getInstance().getToken());
                productListRequestModel.setLimit(10);
                productListRequestModel.setPage(1);
                productListRequestModel.setSalonId(salonId);
               // productListRequestModel.setSalonId(57);
                productListRequestModel.setTitle("");
                WebAPIHelper.getInstance().getSalonProductList(productListRequestModel, new APICallback<ProductListResponseModel>(PRODUCT_LIST_REQ_CODE));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(DetailActivity.this, ViewMapDirectionActivity.class);
        intent.putExtra("salon_detail", salonDetailResponseModelGenericResponseModel.getData().getSalon());

        switch (v.getId()){
            case R.id.back_btn:
               onBackPressed();
                break;
            case R.id.favorite_btn:
                binding.favoriteBtn.setSelected(!binding.favoriteBtn.isSelected());
                break;

            case R.id.btn_services:
                toggleButtonClick(R.id.btn_services);
                checkInternetAndHitApi(SERVICE_CAT_LIST_REQ_CODE);
                binding.lnrlayAddedProductsNo.setVisibility(View.GONE);
                break;
            case R.id.btn_products:
                toggleButtonClick(R.id.btn_products);
                checkInternetAndHitApi(PRODUCT_LIST_REQ_CODE);
                binding.lnrlayAddedProductsNo.setVisibility(View.VISIBLE);
                productsInCart = 0;
                binding.txtAddedProductNo.setText(Integer.toString(productsInCart));
                break;
            case R.id.txt_view_map:
                startActivity(intent);
                break;
            case R.id.img_view_map:
                startActivity(intent);
                break;
            case R.id.btn_detail_book_for_marriage:
                if (isGuest){
                    CustomAlertDialog customAlertDialog = new CustomAlertDialog(this, new CustomAlertDialog.AlertListener() {
                        @Override
                        public void onYesButtonClicked() {
                            Intent intent = new Intent(DetailActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                        @Override
                        public void onNoButtonClicked() {

                        }
                    });
                    customAlertDialog.setYesButtonText(getResources().getString(R.string.login));
                    customAlertDialog.setNoButtonText(getResources().getString(R.string.cancel));
                    customAlertDialog.setAlertMessage(getResources().getString(R.string.login_alert));
                    customAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    customAlertDialog.show();
                }else{
                    Intent marriageBookingIntent = new Intent(this, MarriageBookingActivity.class);
                    marriageBookingIntent.putExtra(AppConstants.EXTRAS.SALON_ID, salonId);
                    startActivity(marriageBookingIntent);
                }

                break;
            case R.id.btn_reserve_product:
                if (isGuest){
                    CustomAlertDialog customAlertDialog = new CustomAlertDialog(this, new CustomAlertDialog.AlertListener() {
                        @Override
                        public void onYesButtonClicked() {
                            Intent intent = new Intent(DetailActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                        @Override
                        public void onNoButtonClicked() {

                        }
                    });
                    customAlertDialog.setYesButtonText(getResources().getString(R.string.login));
                    customAlertDialog.setNoButtonText(getResources().getString(R.string.cancel));
                    customAlertDialog.setAlertMessage(getResources().getString(R.string.login_alert));
                    customAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    customAlertDialog.show();
                }else{
                    ArrayList<SalonProduct> productsInCart = (ArrayList<SalonProduct>) salonProductListAdapter.getProductsinCart();
                    if (productsInCart.size() > 0){
                        doProductReservation();
                    }else{
                        Toast.makeText(this, getResources().getString(R.string.choose_product), Toast.LENGTH_SHORT).show();
                    }

                    break;
                }

            case R.id.btn_cancel_reserve_product:
                doCancelProductResevation();
                break;
        }
    }
    private void doProductReservation(){
        DatePickerDialog datePickerDialog;
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                goToReservedProductPage(calendar);
            }
        };
        datePickerDialog = new DatePickerDialog(DetailActivity.this, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void goToReservedProductPage(Calendar calendar) {
        ArrayList<SalonProduct> productsInCart = (ArrayList<SalonProduct>) salonProductListAdapter.getProductsinCart();
        if (productsInCart.size() > 0){
            Intent intent = new Intent(this, ReservedProductDetailActivity.class);
            intent.putExtra(AppConstants.EXTRAS.SALON_DATA, salon);
            intent.putExtra(AppConstants.EXTRAS.RESERVED_PRODUCT_DETAIL, productsInCart);
            SimpleDateFormat format1 = new SimpleDateFormat("dd MMM,yyyy");
            String strDate = format1.format(calendar.getTime());
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            String strDate2 = format2.format(calendar.getTime());
            intent.putExtra(AppConstants.EXTRAS.PICK_UP_DATE, strDate);
            intent.putExtra(AppConstants.EXTRAS.SUBMIT_PICK_DATE, strDate2);
            startActivity(intent);
        }else{
            Toast.makeText(this, getResources().getString(R.string.choose_product), Toast.LENGTH_SHORT).show();
        }
    }
    private void doCancelProductResevation(){
        for (int i = 0; i < productList.size(); i++){
            productList.get(i).setSelectedQuantity(0);
        }
        binding.serviceProductListView.setAdapter(salonProductListAdapter);
        productsInCart = 0;
        binding.txtAddedProductNo.setText(Integer.toString(productsInCart));
        Toast.makeText(this, getResources().getString(R.string.reservation_cancelled), Toast.LENGTH_SHORT).show();
        //salonProductListAdapter.notifyDataSetChanged();
    }
    private void toggleButtonClick(int btnId){
        switch (btnId){
            case R.id.btn_services:
                binding.btnServices.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.button_left_corner_round_brown));
                binding.btnProducts.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.button_right_corner_round_white));
                binding.btnServices.setTextColor(ContextCompat.getColor(this,R.color.colorWhite));
                binding.btnProducts.setTextColor(ContextCompat.getColor(this,R.color.beutify_light_white));
                binding.lnrlayReserveCancelProduct.setVisibility(View.GONE);
                binding.btnDetailBookForMarriage.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_products:
                binding.btnServices.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.button_left_corner_round_white));
                binding.btnProducts.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.button_right_corner_round_brown));
                binding.btnServices.setTextColor(ContextCompat.getColor(this,R.color.beutify_light_white));
                binding.btnProducts.setTextColor(ContextCompat.getColor(this,R.color.colorWhite));
                binding.lnrlayReserveCancelProduct.setVisibility(View.VISIBLE);
                binding.btnDetailBookForMarriage.setVisibility(View.GONE);
                break;
        }
    }
    private void setSalonDetailViews(Salon salon){
        if (salon==null)return;
        binding.salonTitleTv.setText(salon.getSalon_name());
        binding.salonAddressTv.setText(salon.getAddress());
        binding.salonDetailTv.setText(salon.getDescription());
        binding.txtStartTimeInterval.setText(AppCommons.get12HrFormat(salon.getWork_from_time()));
        binding.txtEndTimeInterval.setText(AppCommons.get12HrFormat(salon.getWork_to_time()));
        if (AppGlobalData.getInstance().getLoggedInCustomer().getLatitude() != null && AppGlobalData.getInstance().getLoggedInCustomer().getLongitude() != null && salon.getLatitude() != null && salon.getLongitude() != null ){
            float[] result = AppCommons.getDistance(AppGlobalData.getInstance().getLoggedInCustomer().getLatitude(), AppGlobalData.getInstance().getLoggedInCustomer().getLongitude(), Double.parseDouble(salon.getLatitude()), Double.parseDouble(salon.getLongitude()));
            if (result != null){
                binding.txtDistance.setText(new DecimalFormat("##.##").format(result[0]/1000)+" km");
            }
        }
    }

    @Override
    public void onServiceCatClick(View view, ServiceCategory serviceCategory) {
        Intent intent = new Intent(this,ServiceSelectActivity.class);
        intent.putExtra(AppConstants.EXTRAS.SERVICE_CATEGORY_DATA,serviceCategory);
        intent.putExtra(AppConstants.EXTRAS.SALON_DATA,salon);
        startActivity(intent);
    }

    @Override
    public void onProductItemClick(View view, final int position) {
        SalonProduct salonProduct = productList.get(position);
        ProductDetailPopUp productDetailPopUp = new ProductDetailPopUp(this, salonProduct, binding, new ProductDetailPopUp.ProductDetailListener(){
            @Override
            public void onCancelClicked() {
                salonProductListAdapter.notifyDataSetChanged();

            }
        });
        productDetailPopUp.show();
    }
    @Override
    public void onAddItemClick(View view, int position) {
    }
    @Override
    public void onDeleteItemClick(View view, int position) {

    }
}
