package com.beatutify.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.beatutify.R;
import com.beatutify.activity.SignUpActivity;
import com.beatutify.customviews.NMGTextView;
import com.beatutify.model.Cities;
import com.beatutify.util.AppCommons;

import java.util.ArrayList;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.COMPLEX_UNIT_PX;
import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * Created by gaurav.singh on 3/30/2018.
 */

public class CityListSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    private  Context context;
    private ArrayList<Cities> cityList;
    private int requestCode;

    public CityListSpinnerAdapter(Context context,ArrayList<Cities> cityList , int requestCode) {
        this.cityList = cityList;
        this.context = context;
        this.requestCode = requestCode;
    }

    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long)position;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView txt = requestCode == 99
            ?(TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.city_spinner_list_item_small,parent,false):
             (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.city_spinner_list_item,parent,false);


        txt.setText(cityList.get(position).getCityName());
       return  txt;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewgroup) {

        TextView txt = requestCode == 99
                ?(TextView) LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.city_spinner_list_item_small,viewgroup,false):
                (TextView) LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.city_spinner_list_item,viewgroup,false);

            txt.setPadding(0, 0, 0, 0);
        Drawable arrow_down =ContextCompat.getDrawable(context,R.drawable.ic_arrow_down);

        Drawable drawable = DrawableCompat.wrap(arrow_down);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(context,R.color.colorWhite));
        txt.setCompoundDrawablesWithIntrinsicBounds(null, null,arrow_down, null);
          if (requestCode == 99){

            txt.setText(cityList.get(i).getCityName());
      }else{

            txt.setText(cityList.get(i).getCityName());

        }
        return  txt;
    }

}
