package com.beatutify.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beatutify.R;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.model.SalonListRequestModel;

import java.util.ArrayList;


public class SalonListMapFragment extends BaseFragment {

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

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_salon_list_map, container, false);

        return  view;
    }



    @Override
    public void hitApi(int request) {

    }
}
