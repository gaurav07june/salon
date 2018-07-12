package com.beatutify.fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beatutify.R;
import com.beatutify.activity.GeneralBookingListActivity;
import com.beatutify.activity.MarriageBookingListActivity;
import com.beatutify.activity.ProductBookingListActivity;
import com.beatutify.databinding.FragmentMyBookingsBinding;
import com.beatutify.model.GenericResponseModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyBookingsFragment extends BaseFragment implements View.OnClickListener{

    FragmentMyBookingsBinding binding;
    public MyBookingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_my_bookings, container, false);
        binding = DataBindingUtil.bind(view);
        return view;
    }

    @Override
    public <D> void onApiSuccess(GenericResponseModel<D> model, int request_code) {

    }

    @Override
    public <D> void onApiFail(GenericResponseModel<D> model, int request_code) {

    }

    @Override
    public void onApiException(Throwable t, int request_code) {

    }

    @Override
    public void setViews() {
        binding.rltlayGeneralBooking.setOnClickListener(this);
        binding.rltlayMarriageBooking.setOnClickListener(this);
        binding.rltlayProductBooking.setOnClickListener(this);
    }

    @Override
    public void hitApi(int request) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rltlay_general_booking:
                startActivity(new Intent(getActivity(), GeneralBookingListActivity.class));
                break;
            case R.id.rltlay_marriage_booking:
                startActivity(new Intent(getActivity(), MarriageBookingListActivity.class));
                break;
            case R.id.rltlay_product_booking:
                startActivity(new Intent(getActivity(), ProductBookingListActivity.class));
                break;
        }
    }
}
