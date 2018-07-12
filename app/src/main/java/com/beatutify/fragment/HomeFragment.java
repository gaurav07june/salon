package com.beatutify.fragment;


import android.Manifest;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.beatutify.R;
import com.beatutify.activity.DetailActivity;
import com.beatutify.activity.HomeActivity;
import com.beatutify.activity.LoginActivity;
import com.beatutify.adapters.CustomInfoWindowGoogleMapAdapter;
import com.beatutify.adapters.SalonListAdapter;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.model.Salon;
import com.beatutify.model.SalonListRequestModel;
import com.beatutify.model.SalonListResponseModel;
import com.beatutify.retrofit.WebAPIHelper;
import com.beatutify.util.AppConstants;
import com.beatutify.util.AppGlobalData;
import com.beatutify.util.BeautifyDividerItemDecoration;
import com.beatutify.util.SVGToBitmap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseFragment implements SalonListAdapter.SalonListAdapterListener,OnMapReadyCallback{
    private GoogleMap googleMap = null;


    private static final String TAG = HomeFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<Salon> salonList;
    private SalonListAdapter salonListAdapter;
    private SearchView searchView;
    private SalonListRequestModel salonListRequestModel;
    private static final int SALON_LIST_REQ_CODE = 201;
    private SupportMapFragment mapFragment=null;
    private Spinner cityListSpinner;
    private TextView txtNoSalon;
    private GenericResponseModel<SalonListResponseModel> responseModel;
    public HomeFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        txtNoSalon = view.findViewById(R.id.txt_no_salon);
        txtNoSalon.setVisibility(View.GONE);

        salonListRequestModel = new SalonListRequestModel();
        recyclerView = view.findViewById(R.id.recycler_view);
        salonList = new ArrayList<Salon>();

        // hit api to get the salon list
        checkInternetAndHitApi(SALON_LIST_REQ_CODE);
        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        hideMap();

        cityListSpinner = getActivity().findViewById(R.id.city_list_spinner);
        cityListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //hit api to get salon list according to the city selected
                AppGlobalData.getInstance().getLoggedInCustomer().setCityId(HomeActivity.cityList.get(position).getCityId());
                checkInternetAndHitApi(SALON_LIST_REQ_CODE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return  view;
    }
    @Override
    public <D> void onApiSuccess(GenericResponseModel<D> model, int request_code) {
        switch (request_code){
            case SALON_LIST_REQ_CODE:
                if (model !=null && model.getMessage() != null){

                    responseModel = (GenericResponseModel<SalonListResponseModel>)model;
                    if (responseModel.getData().getSalonList().size() == 0){
                        txtNoSalon.setVisibility(View.VISIBLE);
                    }else{
                        txtNoSalon.setVisibility(View.GONE);

                    }
                    salonListAdapter = new SalonListAdapter(getActivity(), responseModel.getData().getSalonList() , this);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    //recyclerView.addItemDecoration(new BeautifyDividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL, 36));
                    recyclerView.setAdapter(salonListAdapter);
                    placeMarkers();
                }
                break;
        }
    }
    @Override
    public <D> void onApiFail(GenericResponseModel<D> model, int request_code) {
        switch (request_code){
            case SALON_LIST_REQ_CODE:
                break;
        }

        /*if (model != null && model.getError() != null && model.getError().getMessage() != null){
            Toast.makeText(getActivity(),model.getError().getMessage(),Toast.LENGTH_SHORT).show();
        }*/
    }
    @Override
    public void onApiException(Throwable t, int request_code) {
        if (t != null && t.getMessage() != null){
            Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void setViews() {

    }

    @Override
    public void hitApi(int request) {
        switch (request){
            case SALON_LIST_REQ_CODE:
                salonListRequestModel.setToken(AppGlobalData.getInstance().getToken());
                salonListRequestModel.setCity_id(AppGlobalData.getInstance().getLoggedInCustomer().getCityId());
                salonListRequestModel.setCountry_id("AE");
                WebAPIHelper.getInstance().showSalonList(salonListRequestModel, new APICallback<SalonListResponseModel>(SALON_LIST_REQ_CODE));
        }
    }
    @Override
    public void onSalonSelected(Salon salon, View imageView) {
       /* Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(AppConstants.EXTRAS.SALON_IMAGE_URL, salon.getImage());
        intent.putExtra(AppConstants.EXTRAS.SALON_ID, salon.getSalon_id());
        intent.putExtra(AppConstants.EXTRAS.SALON_DATA, salon);
        startActivity(intent);*/
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra(AppConstants.EXTRAS.SALON_IMAGE_URL, salon.getImage());
            intent.putExtra(AppConstants.EXTRAS.SALON_ID, salon.getSalon_id());
            intent.putExtra(AppConstants.EXTRAS.SALON_DATA, salon);
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(getActivity(), imageView, getString(R.string.salon_transition));
            startActivity(intent, options.toBundle());
        }else{
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra(AppConstants.EXTRAS.SALON_IMAGE_URL, salon.getImage());
            intent.putExtra(AppConstants.EXTRAS.SALON_ID, salon.getSalon_id());
            intent.putExtra(AppConstants.EXTRAS.SALON_DATA, salon);
            startActivity(intent);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    public void showMap()
    {
        if (mapFragment!=null)
        {
            getChildFragmentManager().beginTransaction().show(mapFragment).commit();
        }

    }
    public void hideMap()
    {
        if (mapFragment!=null)
        {
            getChildFragmentManager().beginTransaction().hide(mapFragment).commit();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getActivity(), R.raw.map_style));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
            this.googleMap=googleMap;
            placeMarkers();
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

    }

    private void  placeMarkers() {
        /*googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setMinZoomPreference(11);*/
        if (responseModel != null && googleMap != null) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            if (responseModel != null && googleMap != null) {
                ArrayList<Salon> salonList = (ArrayList<Salon>) responseModel.getData().getSalonList();
                if (salonList != null) {
                    int counter = 0;
                    if (salonList.size() == 0){
                        googleMap.clear();
                    }
                    for (int i = 0; i < salonList.size(); i++) {
                        String latitude = salonList.get(i).getLatitude() ;
                        String longitude = salonList.get(i).getLongitude();
                        if (latitude != null && longitude != null) {
                            counter++;
                            createMarkerOnMap(latitude, longitude, googleMap, salonList.get(i), builder);
                        }
                    }
                    if (counter > 0){
                        LatLngBounds bounds = builder.build();
                        int width = getResources().getDisplayMetrics().widthPixels;
                        int height = getResources().getDisplayMetrics().heightPixels;
                        int padding = (int) (width * 0.30); // offset from edges of the map 10% of screen
                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                        googleMap.animateCamera(cu);
                    }
                }else{
                    // hide map and show no salon available
                    googleMap.clear();
                }
            }
        }
    }
    private void createMarkerOnMap(String latitude, String longitude, GoogleMap googleMap,Salon salonList,LatLngBounds.Builder builder)
    {
        LatLng latLng = new LatLng(Float.parseFloat(latitude), Float.parseFloat(longitude));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng)
                .icon(SVGToBitmap.bitmapDescriptorFromVector(getActivity(), R.drawable.ic_location_big));
        CustomInfoWindowGoogleMapAdapter customInfoWindowAdapter = new CustomInfoWindowGoogleMapAdapter(getActivity());
        googleMap.setInfoWindowAdapter(customInfoWindowAdapter);
        Marker marker = googleMap.addMarker(markerOptions);
        marker.setTag(salonList);
        builder.include(marker.getPosition());
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Salon salon =(Salon) marker.getTag();
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(AppConstants.EXTRAS.SALON_IMAGE_URL, salon.getImage());
                intent.putExtra(AppConstants.EXTRAS.SALON_ID, salon.getSalon_id());
                intent.putExtra(AppConstants.EXTRAS.SALON_DATA, salon);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onPause() {
        super.onPause();
    }
}
