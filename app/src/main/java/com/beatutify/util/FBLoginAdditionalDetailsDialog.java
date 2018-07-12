package com.beatutify.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.beatutify.R;
import com.beatutify.model.Cities;
import com.beatutify.model.CityListResponseModel;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.model.RegistrationRequestModel;
import com.beatutify.retrofit.WebAPIHelper;
import com.reginald.editspinner.EditSpinner;
import com.rey.material.widget.ProgressView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by karan.kalsi on 5/24/2017.
 */
public class FBLoginAdditionalDetailsDialog {
    private Dialog dialog = null;
    private Context mContext;
    private Handler mHander;
    private ProgressView circularProgressView;
    private EditSpinner citySpinner;
    private int cityId = 0;
    private List<Cities> citiesList = null;
    private ArrayAdapter<String> mCityAdapter;
    private EditText enterEmailEt;
    private EditText enterAddressEt;

    public FBLoginAdditionalDetailsDialog(Context context, final RegistrationRequestModel loginViaFBRequest, OnFbMissingDetailsAddListener listener) {
        mHander = new Handler();
        onFbMissingDetailsAddListener = listener;
        mContext = context;
        dialog = new Dialog(context, R.style.AppDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //
        // dialog.setCancelable(false);
        dialog.setContentView(R.layout.enter_email_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        circularProgressView = dialog.findViewById(R.id.circular_progress_view);
        citySpinner = dialog.findViewById(R.id.city_list_spinner);

        enterAddressEt = (EditText) dialog.findViewById(R.id.enter_address_et);
        enterEmailEt = (EditText) dialog.findViewById(R.id.enter_email_et);


        enterEmailEt.setText(loginViaFBRequest.getEmail());


        citySpinner.getBackground().setColorFilter(ContextCompat.getColor(context,R.color.colorAccent),
                PorterDuff.Mode.SRC_ATOP);

        Button enter_email_btn = (Button) dialog.findViewById(R.id.enter_email_btn);
        enter_email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValid()) {
                    if (onFbMissingDetailsAddListener != null)
                    {
                        loginViaFBRequest.setEmail(enterEmailEt.getText().toString());
                        loginViaFBRequest.setAddress(enterAddressEt.getText().toString());
                        loginViaFBRequest.setCity_id(cityId);
                        onFbMissingDetailsAddListener.onFbMissingDetailAdd(loginViaFBRequest);

                    }
                        }

            }
        });

        mCityAdapter = new ArrayAdapter<>(context, R.layout.dropdown_item_gray,
                new ArrayList());
        citySpinner.setAdapter(mCityAdapter);
        circularProgressView.setVisibility(View.VISIBLE);
        WebAPIHelper.getInstance().getCityList(new Callback<GenericResponseModel<CityListResponseModel>>() {
            @Override
            public void onResponse(Call<GenericResponseModel<CityListResponseModel>> call, Response<GenericResponseModel<CityListResponseModel>> response) {
                circularProgressView.setVisibility(View.GONE);
                if (response != null && response.body() != null) {
                    citiesList = response.body().getData().getCities();
                    updateAdapter(mCityAdapter, citiesList);
                }

                citySpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        citySpinner.setError(null);
                        if (i < citiesList.size()) {
                            citySpinner.setText(citiesList.get(i).getCityName());
                            cityId = citiesList.get(i).getCityId();


                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<GenericResponseModel<CityListResponseModel>> call, Throwable t) {
                circularProgressView.setVisibility(View.GONE);
            }
        });

    }


    public void show() {

        try {
            if (dialog != null && ((Activity) mContext).hasWindowFocus()) {


                dialog.show();
            }

        } catch (Exception e) {

        }

    }

    public void dismiss() {
        try {
            if (dialog != null && dialog.isShowing() && !((Activity) mContext).isFinishing())
                dialog.dismiss();
        } catch (Exception e) {

        }

    }

    public OnFbMissingDetailsAddListener onFbMissingDetailsAddListener = null;

    public OnFbMissingDetailsAddListener getOnFbMissingDetailsAddListener() {
        return onFbMissingDetailsAddListener;
    }

    public void setOnFbMissingDetailsAddListener(OnFbMissingDetailsAddListener onFbMissingDetailsAddListener) {
        this.onFbMissingDetailsAddListener = onFbMissingDetailsAddListener;
    }

    public boolean isValid() {
      /*  if(enterAddressEt.getText().toString().trim().isEmpty())
        {
            enterEmailEt.setError(mContext.getString(R.string.enter_valid_email_error));
            return false;
        }*/
        if (!AppCommons.isValidEmail(enterEmailEt.getText().toString())) {
            enterEmailEt.setError(mContext.getString(R.string.enter_valid_email_error));
            return false;
        }
        if (cityId == 0) {
            citySpinner.setError(mContext.getString(R.string.select_city_error));
            return false;
        }
        return true;
    }

    public interface OnFbMissingDetailsAddListener {
        void onFbMissingDetailAdd(RegistrationRequestModel loginViaFBRequest);
    }

    private <T> void updateAdapter(ArrayAdapter<String> adapter, List<T> list) {
        if (list == null) return;
        adapter.clear();

        for (T model : list) {

            adapter.insert(((Cities) model).getCityName(), adapter.getCount());

        }
        adapter.notifyDataSetChanged();
    }

}
