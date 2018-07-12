package com.beatutify.adapters;

import android.app.Activity;
import android.content.Context;

import android.location.Location;
import android.view.View;
import android.widget.TextView;


import com.beatutify.R;
import com.beatutify.model.Salon;

import com.beatutify.util.AppCommons;
import com.beatutify.util.AppGlobalData;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DecimalFormat;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomInfoWindowGoogleMapAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;


    public CustomInfoWindowGoogleMapAdapter(Context ctx){
        context = ctx;

    }
    @Override
    public View getInfoWindow(Marker marker) {

        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.map_info_window, null);
        TextView title = view.findViewById(R.id.txt_info_window_title);
        TextView address = view.findViewById(R.id.txt_info_window_address);
        TextView distance =  view.findViewById(R.id.txt_info_window_distance);
        CircleImageView circleImage = view.findViewById(R.id.img_info_window_image);
        Salon infoWindowData = (Salon) marker.getTag();
        ImageLoader.getInstance().displayImage(infoWindowData.getImage(), circleImage);
        //ImageLoader.getInstance().displayImage(infoWindowData.getImage(), circleImage,AppCommons.getCustomImageOptions(R.drawable.placeholder_square_gray));
        title.setText(infoWindowData.getSalon_name());
        address.setText(infoWindowData.getAddress());
        float[] results = AppCommons.getDistance(AppGlobalData.getInstance().getLoggedInCustomer().getLatitude(), AppGlobalData.getInstance().getLoggedInCustomer().getLongitude(), Double.parseDouble(infoWindowData.getLatitude()), Double.parseDouble(infoWindowData.getLongitude()));
        distance.setText(new DecimalFormat("##.##").format(results[0]/1000)+" km");
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
       return null;
    }

}
