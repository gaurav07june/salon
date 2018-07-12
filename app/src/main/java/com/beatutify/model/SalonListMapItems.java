package com.beatutify.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by gaurav.singh on 3/20/2018.
 */

public class SalonListMapItems implements ClusterItem {
    private final LatLng mPosition;


    public SalonListMapItems(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }



    @Override
    public LatLng getPosition() {
        return mPosition;
    }


}
