package com.beatutify.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.beatutify.R;
import com.beatutify.camera.MaterialCamera;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.util.AppCommons;
import com.beatutify.util.AppConstants;
import com.beatutify.util.CircularProgressDialog;
import com.beatutify.util.GalleryPathUtils;
import com.beatutify.util.InternetCheck;
import com.beatutify.util.InternetRetryDialog;
import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseActivity extends AppCompatActivity implements BottomSheetListener{

    public abstract void setContentView();
    public abstract void getExtras();
    public abstract void initViews();
    public abstract void setViews();

    public abstract <D> void onApiSuccess(GenericResponseModel<D> model, int request_code);

    public abstract <D> void onApiFail(GenericResponseModel<D> model, int request_code);

    public abstract void onApiException(Throwable t, int request_code);

    public abstract void hitApi(int request);


    private static final int CAMERA_PIC_REQUEST = 101;
    private static final int GALLERY_PIC_REQUEST = 102;

    private BottomSheet imagePicker;
    private int imagePickRequest = 0;
    private boolean isSelfie = false;

    public CircularProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imagePicker = new BottomSheet.Builder(this, R.style.MyBottomSheetStyle)
                .setSheet(R.menu.img_picker_menu)
                .setTitle(R.string.pick_image_from)
                .setListener(this).create();

        progressDialog=new CircularProgressDialog(this);
        getExtras();
        setContentView();
        initViews();
        setViews();

    }

    public void checkInternetAndHitApi(final int request_code) {
        progressDialog.show();
        new InternetCheck(this).isInternetConnectionAvailable(new InternetCheck.InternetCheckListener() {

            @Override
            public void onComplete(boolean connected) {
                if (connected)
                    hitApi(request_code);
                else {
                    new InternetRetryDialog(BaseActivity.this, new InternetRetryDialog.RetryInternetListener() {
                        @Override
                        public void onRetry() {
                            checkInternetAndHitApi(request_code);
                        }
                        @Override
                        public void onExit() {
                            progressDialog.show();
                            ActivityCompat.finishAffinity(BaseActivity.this);
                        }
                    }).show();
                }
            }
        });
    }

    public void doImagePick(int request, boolean isSelfie) {
        this.isSelfie = isSelfie;
        this.imagePickRequest = request;

        imagePicker.show();
    }

    public void onImageSelect(String photo_url, int request) {

    }

    public void processGalleryImagePath(int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            try {
                String photo_url = GalleryPathUtils.getRealPathFromURI(this, data.getData());
                onImageSelect(photo_url, imagePickRequest);
            } catch (Exception e1) {

                e1.printStackTrace();

            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GALLERY_PIC_REQUEST:
                if (data != null)
                    processGalleryImagePath(resultCode, data);
                break;
            case CAMERA_PIC_REQUEST:
                if (data != null && data.getData() != null)
                    onImageSelect(data.getData().toString(), imagePickRequest);
                break;
        }
    }

    @Override
    public void onSheetShown(@NonNull BottomSheet var1)
    {

    }

    @Override
    public void onSheetItemSelected(@NonNull BottomSheet var1, MenuItem var2)
    {
        switch (var2.getItemId())
        {
            case R.id.img_picker_camera:
                new MaterialCamera(BaseActivity.this)
                        .stillShot()
                        .startAsResult(true)
                        .defaultToFrontFacing(isSelfie)
                        .saveDir(AppCommons.getAppHiddenDirectory(BaseActivity.this))
                        .start(CAMERA_PIC_REQUEST);
                break;
            case R.id.img_picker_gallery:
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);//
                startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), GALLERY_PIC_REQUEST);

                break;
        }

    }

    @Override
    public void onSheetDismissed(@NonNull BottomSheet var1, @BottomSheetListener.DismissEvent int var2)
    {

    }

    public class APICallback<T> implements Callback<GenericResponseModel<T>>
    {
        private int request_code=0;
        public APICallback(int request_code)
        {
            this.request_code=request_code;
        }


        @Override
        public void onResponse(Call<GenericResponseModel<T>> call, Response<GenericResponseModel<T>> response) {


            if(progressDialog.isShowing())progressDialog.dismiss();
            if (response.code() == AppConstants.API.TOKEN_EXPIRED_CODE){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BaseActivity.this);
                alertDialogBuilder.setMessage(getResources().getString(R.string.session_expired));
                        alertDialogBuilder.setPositiveButton(getResources().getString(R.string.login),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

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
            if(progressDialog.isShowing())progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), R.string.check_internet_connection_msg,Toast.LENGTH_SHORT).show();
            onApiException(t,request_code);
        }
    }
}
