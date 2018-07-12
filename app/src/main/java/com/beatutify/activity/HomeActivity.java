package com.beatutify.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.beatutify.R;
import com.beatutify.adapters.CityListSpinnerAdapter;
import com.beatutify.customviews.CustomAlertDialog;
import com.beatutify.customviews.HomeMenuNavigationView;
import com.beatutify.databinding.ActivityHomeBinding;
import com.beatutify.fragment.DealsFragment;
import com.beatutify.fragment.HomeFragment;
import com.beatutify.fragment.MoreFragment;
import com.beatutify.fragment.MyBookingsFragment;
import com.beatutify.model.Cities;
import com.beatutify.model.CityListResponseModel;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.retrofit.WebAPIHelper;
import com.beatutify.util.AppCommons;
import com.beatutify.util.AppGlobalData;
import com.beatutify.util.BottomNavigationBehavior;
import com.beatutify.util.BottomNavigationViewHelper;
import com.beatutify.util.BeautifyDrawerListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity implements View.OnClickListener,
        HomeMenuNavigationView.HomeMenuNavigationListener {
    private ActivityHomeBinding binding;
    private static final String SELECTED_ITEM = "selected_item";
    private boolean isGuest = AppGlobalData.getInstance().isGuest();
    private Toolbar toolbar;
    Fragment selectedFragment;
    BottomNavigationView navigation;
    private MenuItem menuItemSelected;
    private int mMenuItemSelected;
    private String SELECTED_NAV = "";
    private FusedLocationProviderClient mFusedLocationClient;
    private final int BEAUTIFY_PERMISSIONS_REQUEST_LOCATION = 91;
    private final int CITY_LIST_REQ_CODE = 99;
    public static ArrayList<Cities> cityList;
    private CityListSpinnerAdapter cityListSpinnerAdapter;

    @Override
    public void initViews() {

    }
    @Override
    public void setViews() {



    }
    @Override
    public void setContentView() {

    }
    @Override
    public void getExtras() {

    }
    @Override
    public <D> void onApiSuccess(GenericResponseModel<D> model, int request_code) {
        switch (request_code){
            case CITY_LIST_REQ_CODE:
                if (model != null){
                   // Toast.makeText(this, model.getMessage(), Toast.LENGTH_SHORT).show();
                    GenericResponseModel<CityListResponseModel> responseModel = (GenericResponseModel<CityListResponseModel>) model;
                    cityList = (ArrayList<Cities>) responseModel.getData().getCities();
                    cityListSpinnerAdapter = new CityListSpinnerAdapter(this, cityList, CITY_LIST_REQ_CODE );
                    binding.cityListSpinner.setAdapter(cityListSpinnerAdapter);
                }
                break;
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
            case CITY_LIST_REQ_CODE:
                WebAPIHelper.getInstance().getCityList(new APICallback<CityListResponseModel>(CITY_LIST_REQ_CODE));
                break;
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.listViewImage.setOnClickListener(this);
        binding.mapViewImage.setOnClickListener(this);

        // hit api to retrieve city list
        checkInternetAndHitApi(CITY_LIST_REQ_CODE);
        // spinner
        BottomNavigationViewHelper.removeShiftMode(binding.navigation);
        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) binding.navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        if (savedInstanceState != null) {
            mMenuItemSelected = savedInstanceState.getInt(SELECTED_ITEM, 0);
            menuItemSelected = binding.navigation.getMenu().findItem(mMenuItemSelected);
        } else {
            menuItemSelected = binding.navigation.getMenu().getItem(0);
        }
        //SELECTED_NAV = getResources().getString(R.string.home_fragment);
        selectFragment(menuItemSelected);
        binding.listViewImage.setSelected(true);
        binding.mapViewImage.setSelected(false);

        binding.drawerLayout.setScrimColor(Color.TRANSPARENT);
        binding.drawerLayout.addDrawerListener(new BeautifyDrawerListener(binding.container));
        binding.homeNavView.setHomeMenuNavigationListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAndGetLocation();


    }
    private void checkAndGetLocation(){
        if (AppCommons.isLocationEnabled(this)){
            //Toast.makeText(this, "location is enabled now", Toast.LENGTH_SHORT).show();
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            requestForLocation();
        }else{
            //Toast.makeText(this, "location is not enabled now", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage(getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                }
            });
            dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                }
            });
            dialog.show();
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            selectFragment(item);
            return true;
        }
    };
    private void selectFragment(MenuItem item) {
        Class fragmentClass;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                if (!SELECTED_NAV.equals(getResources().getString(R.string.home_fragment))){
                    fragmentClass = HomeFragment.class;
                    replaceFragment(fragmentClass);
                    SELECTED_NAV = getResources().getString(R.string.home_fragment);
                    binding.toolbarTitle.setText(getResources().getString(R.string.dashboard));
                    changeToolbarStyle(fragmentClass);
                }
                break;
            case R.id.navigation_deals:
                if (!SELECTED_NAV.equals(getResources().getString(R.string.deal_fragment))){
                    fragmentClass = DealsFragment.class;
                    replaceFragment(fragmentClass);
                    SELECTED_NAV = getResources().getString(R.string.deal_fragment);
                    binding.toolbarTitle.setText(getResources().getString(R.string.deals));
                    changeToolbarStyle(fragmentClass);
                }
                break;
            case R.id.navigation_myBookings:
                if (isGuest){
                    CustomAlertDialog customAlertDialog = new CustomAlertDialog(this, new CustomAlertDialog.AlertListener() {
                        @Override
                        public void onYesButtonClicked() {
                            //Toast.makeText(HomeActivity.this, "yes", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                        @Override
                        public void onNoButtonClicked() {
                            //Toast.makeText(HomeActivity.this, "no", Toast.LENGTH_SHORT).show();
                        }
                    });
                    customAlertDialog.setYesButtonText(getResources().getString(R.string.login));
                    customAlertDialog.setNoButtonText(getResources().getString(R.string.cancel));
                    customAlertDialog.setAlertMessage(getResources().getString(R.string.login_alert));
                    customAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    customAlertDialog.show();
                }else if (!SELECTED_NAV.equals(getResources().getString(R.string.my_booking_fragment))){
                    fragmentClass = MyBookingsFragment.class;
                    replaceFragment(fragmentClass);
                    SELECTED_NAV = getResources().getString(R.string.my_booking_fragment);
                    binding.toolbarTitle.setText(getResources().getString(R.string.my_bookings));
                    changeToolbarStyle(fragmentClass);
                }
                break;
            case R.id.navigation_more:
                if (isGuest){
                    CustomAlertDialog customAlertDialog = new CustomAlertDialog(this, new CustomAlertDialog.AlertListener() {
                        @Override
                        public void onYesButtonClicked() {
                            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
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
                    binding.drawerLayout.openDrawer(Gravity.START);
                }

                break;
            default:
                fragmentClass = HomeFragment.class;
                replaceFragment(fragmentClass);
                SELECTED_NAV = getResources().getString(R.string.home_fragment);
                binding.toolbarTitle.setText(getResources().getString(R.string.dashboard));
                changeToolbarStyle(fragmentClass);
        }
    }
    private void changeToolbarStyle(Class fragment){
        Fragment activeFragment ;
        try{
            activeFragment = (Fragment) fragment.newInstance();
            if ((HomeFragment.class).isInstance(activeFragment)){
                binding.toolbarHome.setBackgroundColor(getResources().getColor(R.color.beautify_light_black));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(getResources().getColor(R.color.beautify_background_black));
                }
                binding.listViewImage.setVisibility(View.VISIBLE);
                binding.mapViewImage.setVisibility(View.VISIBLE);
                binding.cityListSpinner.setVisibility(View.VISIBLE);
                binding.imgMyBookingCalendarView.setVisibility(View.GONE);
            }
            if ((MyBookingsFragment.class).isInstance(activeFragment)){
                binding.toolbarHome.setBackgroundColor(getResources().getColor(R.color.beautyfy_brown));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(getResources().getColor(R.color.beautify_brown_dark));
                }
                binding.listViewImage.setVisibility(View.GONE);
                binding.mapViewImage.setVisibility(View.GONE);
                binding.cityListSpinner.setVisibility(View.GONE);
                binding.imgMyBookingCalendarView.setVisibility(View.VISIBLE);
            }
            if ((DealsFragment.class).isInstance(activeFragment)){
                binding.toolbarHome.setBackgroundColor(getResources().getColor(R.color.beautyfy_brown));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(getResources().getColor(R.color.beautify_brown_dark));
                }
                binding.listViewImage.setVisibility(View.GONE);
                binding.mapViewImage.setVisibility(View.GONE);
                binding.cityListSpinner.setVisibility(View.GONE);
                binding.imgMyBookingCalendarView.setVisibility(View.GONE);
            }
            if ((MoreFragment.class).isInstance(activeFragment)){
                binding.toolbarHome.setBackgroundColor(getResources().getColor(R.color.beautyfy_brown));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(getResources().getColor(R.color.beautify_brown_dark));
                }
                binding.listViewImage.setVisibility(View.GONE);
                binding.mapViewImage.setVisibility(View.GONE);
                binding.cityListSpinner.setVisibility(View.GONE);
                binding.imgMyBookingCalendarView.setVisibility(View.GONE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void replaceFragment(Class fragmentClass){
        try {
            selectedFragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, selectedFragment).commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mMenuItemSelected);
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.map_view_image:

                if (selectedFragment!=null && selectedFragment instanceof  HomeFragment)
                {
                    binding.mapViewImage.setSelected(true);
                    binding.listViewImage.setSelected(false);
                    ((HomeFragment)selectedFragment).showMap();

                }
                break;

            case R.id.list_view_image:
                if (selectedFragment!=null && selectedFragment instanceof  HomeFragment)
                {
                    binding.mapViewImage.setSelected(false);
                    binding.listViewImage.setSelected(true);
                    ((HomeFragment)selectedFragment).hideMap();
                }
                break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case BEAUTIFY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkAndGetLocation();
                } else {
                    Toast.makeText(HomeActivity.this, "permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void requestForLocation(){
        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                // set location to the customer profile
                                AppGlobalData.getInstance().getLoggedInCustomer().setLatitude(location.getLatitude());
                                AppGlobalData.getInstance().getLoggedInCustomer().setLongitude(location.getLongitude());
                                //Toast.makeText(HomeActivity.this, AppGlobalData.getInstance().getLoggedInCustomer().getLatitude()+" "+AppGlobalData.getInstance().getLoggedInCustomer().getLongitude(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("Beautify needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(HomeActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        BEAUTIFY_PERMISSIONS_REQUEST_LOCATION );

                            }
                        })
                        .create()
                        .show();

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        BEAUTIFY_PERMISSIONS_REQUEST_LOCATION);


            }
        }
    }

    @Override
    public void onDashBoardClick() {

    }

    @Override
    public void onHomeMenuCloseClick() {
        binding.drawerLayout.closeDrawer(Gravity.START);
    }

}
