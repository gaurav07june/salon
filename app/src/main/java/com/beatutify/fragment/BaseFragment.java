package com.beatutify.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.beatutify.R;
import com.beatutify.activity.BaseActivity;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.util.AppConstants;
import com.beatutify.util.InternetCheck;
import com.beatutify.util.InternetRetryDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by karan.kalsi on 4/20/2017.
 */
public abstract class BaseFragment extends Fragment {



    public abstract <D> void onApiSuccess(GenericResponseModel<D> model, int request_code);
    public abstract <D> void onApiFail(GenericResponseModel<D> model, int request_code);
    public abstract  void onApiException(Throwable t,int request_code);
    public abstract  void setViews();
    public abstract  void hitApi(int request);

    public class APICallback<T> implements Callback<GenericResponseModel<T>>

    {



        private int request_code=0;
        public APICallback(int request_code)
        {
            this.request_code=request_code;
        }


        @Override
        public void onResponse(Call<GenericResponseModel<T>> call, Response<GenericResponseModel<T>> response) {
            if(  ((BaseActivity)getActivity()).progressDialog.isShowing())
                ((BaseActivity)getActivity()).progressDialog.dismiss();

            if(response.body()==null)
            {
                onApiFail(null,request_code);
                return;
            }

            int status = response.body().getStatus();

            String msg =response.body().getMessage();

            if(status == AppConstants.API.STATUS_CODE_SUCCESS)
            {
                onApiSuccess(response.body(),request_code);
            }
            else
            {
                onApiFail(response.body(),request_code);
            }
        }
        @Override
        public void onFailure(Call<GenericResponseModel<T>> call, Throwable t) {
           try
           {
               if(  ((BaseActivity)getActivity()).progressDialog.isShowing())
                   ((BaseActivity)getActivity()).progressDialog.dismiss();
               if (getContext()!=null)
               {
                   Toast.makeText(getContext(), R.string.check_internet_connection_msg,Toast.LENGTH_SHORT).show();
               }
               onApiException(t,request_code);
           }
           catch (Exception e)
           {

           }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setViews();
    }


    public void checkInternetAndHitApiWithoutLoader(final int request_code)
    {



        new InternetCheck(getActivity()).isInternetConnectionAvailable(new InternetCheck.InternetCheckListener() {

            @Override
            public void onComplete(boolean connected) {
                if (connected)
                    hitApi(request_code);
                else {

                    if (getActivity()==null)return;
                    new InternetRetryDialog(getActivity(), new InternetRetryDialog.RetryInternetListener() {
                        @Override
                        public void onRetry() {

                            checkInternetAndHitApiWithoutLoader(request_code);
                        }

                        @Override
                        public void onExit() {

                            ActivityCompat.finishAffinity(getActivity());
                        }
                    }).show();
                }

            }
        });
    }


    public void checkInternetAndHitApi(final int request_code)
    {
       ((BaseActivity)getActivity()).progressDialog.show();
        new InternetCheck(getActivity()).isInternetConnectionAvailable(new InternetCheck.InternetCheckListener() {

            @Override
            public void onComplete(boolean connected) {
                if (connected)
                    hitApi(request_code);
                else {
                    //((BaseActivity)getActivity()).progressDialog.dismiss();
                    new InternetRetryDialog(getActivity(), new InternetRetryDialog.RetryInternetListener() {
                        @Override
                        public void onRetry() {

                            checkInternetAndHitApi(request_code);
                        }

                        @Override
                        public void onExit() {
                            //((BaseActivity)getActivity()).progressDialog.dismiss();
                            ActivityCompat.finishAffinity(getActivity());
                        }
                    }).show();
                }

            }
        });
    }

    public void dismissProgressDialog()
    {

        try{
            if (getActivity()==null)return;
          ((BaseActivity)getActivity()).progressDialog.dismiss();
        }
    catch (Exception e)
    {

    }

    }
}
