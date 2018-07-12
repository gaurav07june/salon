package com.beatutify.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.transition.TransitionManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;

import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.Toast;

import com.beatutify.R;
import com.beatutify.adapters.BridesmaidAdapter;

import com.beatutify.databinding.ActivityMarriageBookingBinding;
import com.beatutify.model.BridesMaid;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.model.MarriageBookingRequestModel;
import com.beatutify.retrofit.WebAPIHelper;
import com.beatutify.util.AppCommons;
import com.beatutify.util.AppConstants;
import com.beatutify.util.AppGlobalData;
import com.beatutify.util.BeautifyDividerItemDecoration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MarriageBookingActivity extends BaseActivity implements View.OnClickListener , BridesmaidAdapter.BridesMaidAdapterListener {
    private ActivityMarriageBookingBinding binding;
    Calendar myCalendar;
    private List<BridesMaid> bridesMaidList;
    private Animation animSlideup;
    private boolean isGoingToUpdate = false;
    private int updatingIndex;
    private BridesmaidAdapter bridesmaidAdapter;
    private final int MARRIAGE_BOOKING_REQ_CODE = 301;
    MarriageBookingRequestModel marriageBookingRequestModel;
    private int salonId;
    private boolean isCurretnDate = false;

    @Override
    public void initViews() {
        bridesMaidList = new ArrayList<BridesMaid>();
        marriageBookingRequestModel = new MarriageBookingRequestModel();

    }

    @Override
    public void setViews() {
        setSupportActionBar(binding.toolbarMarrriageBooking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        binding.toolbarMarrriageBooking.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.headerTitle.setText(getResources().getString(R.string.marriage_booking));
        binding.rltlayAddBridesmaidButton.setVisibility(View.VISIBLE);
        binding.rltlayTimeslot.setOnClickListener(this);
        binding.rltlayTimeslotFilled.setOnClickListener(this);
        binding.rltlayBookingDate.setVisibility(View.VISIBLE);
        binding.rltlayBookingDateFilled.setVisibility(View.GONE);
        binding.rltlayAdditionalDetail.setVisibility(View.VISIBLE);
        binding.rltlayAdditionalDetailFilled.setVisibility(View.GONE);
        binding.rltlayTimeslot.setVisibility(View.VISIBLE);
        binding.rltlayTimeslotFilled.setVisibility(View.GONE);
        binding.rltlayBookingDate.setOnClickListener(this);
        binding.rltlayBookingDateFilled.setOnClickListener(this);
        binding.rltlayAdditionalDetail.setOnClickListener(this);
        binding.rltlayAdditionalDetailFilled.setOnClickListener(this);
        binding.rltlayAddBridesmaidButton.setOnClickListener(this);
        binding.txtAddBridesmaidLabel.setOnClickListener(this);
        binding.btnMarriageBookNow.setOnClickListener(this);
        //binding.marriageBookingHeaderLayout.imgBack.setOnClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        binding.recyclerViewBridesmaidList.setLayoutManager(mLayoutManager);
        binding.recyclerViewBridesmaidList.addItemDecoration(new BeautifyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, AppCommons.dpToPx(10, this)));
        binding.recyclerViewBridesmaidList.setItemAnimator(new DefaultItemAnimator());
        //binding.marriageBookingHeaderLayout.txtTitleHeader.setText(getResources().getString(R.string.marriage_booking));
        binding.radioAddBridesmaid.clearCheck();
        Typeface font = Typeface.createFromAsset(getAssets(), "font/sf_ui_text_regular.otf");
        binding.rdbtnBoth.setTypeface(font);
        binding.rdbtnHair.setTypeface(font);
        binding.rdbtnMakeup.setTypeface(font);
    }

    @Override
    public void setContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_marriage_booking);
    }

    @Override
    public void getExtras() {
        Intent intent = getIntent();
        salonId = intent.getIntExtra(AppConstants.EXTRAS.SALON_ID, 0);
    }

    @Override
    public <D> void onApiSuccess(GenericResponseModel<D> model, int request_code) {
        switch (request_code){
            case MARRIAGE_BOOKING_REQ_CODE:
                if (model!=null && model.getStatus() == 1 && model.getMessage() != null){
                    Intent intent = new Intent(this, OrderPlacedActivity.class);
                    intent.putExtra(AppConstants.EXTRAS.MARRIAGE_BOOKING_MSG, model.getMessage());
                    intent.putExtra(AppConstants.EXTRAS.REQ_FROM_PAGE, AppConstants.CONSTANTS.MARRIAGE_BOOKING_REQ);
                    startActivity(intent);
                }
                break;

        }

    }

    @Override
    public <D> void onApiFail(GenericResponseModel<D> model, int request_code) {
        if (model!=null && model.getError() !=null && model.getError().getMessage()!=null)
        {
            Toast.makeText(this,model.getError().getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onApiException(Throwable t, int request_code) {

    }

    @Override
    public void hitApi(int request) {
        switch (request) {
            case MARRIAGE_BOOKING_REQ_CODE:
                WebAPIHelper.getInstance().doMarriageBooking(marriageBookingRequestModel, new APICallback<Object>(MARRIAGE_BOOKING_REQ_CODE));
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rltlay_booking_date:
                doFillBookingDate();
                break;
            case R.id.rltlay_booking_date_filled:
                doFillBookingDate();
                break;
            case R.id.rltlay_additional_detail:
                binding.rltlayAdditionalDetail.setVisibility(View.GONE);
                binding.rltlayAdditionalDetailFilled.setVisibility(View.VISIBLE);
                binding.edtAdditionalDetailFilled.requestFocus();
                showSoftKeyboard(binding.edtAdditionalDetailFilled);
                break;
            case R.id.edt_additional_detail_filled:
                break;
            case R.id.rltlay_timeslot:
                doOpenTimeSlot();
                break;
            case R.id.rltlay_timeslot_filled:
                doOpenTimeSlot();
                break;
            case R.id.rltlay_add_bridesmaid_button:
                if (isGoingToUpdate){
                    updateBrideMaidList(updatingIndex);
                }else{
                    if (binding.txtAddBridesmaidBtnText.getText().toString().equals(getResources().getString(R.string.add_more_bridesmaid))){
                        if (bridesMaidList.size() <= 9){
                            populateBridesMaidDetail();
                        }else{
                            Toast.makeText(this, getResources().getString(R.string.bridesmaid_addition_error),Toast.LENGTH_SHORT).show();
                            binding.edtAddBridesmaidName.setText(" ");
                            binding.radioAddBridesmaid.clearCheck();
                        }
                    }else{
                        TransitionManager.beginDelayedTransition(binding.lnrlayTransitionContainer);
                        binding.lnrLayAddMoreBridesmaid.setVisibility(View.VISIBLE);
                        binding.txtAddBridesmaidBtnText.setText(getResources().getString(R.string.add_more_bridesmaid));
                    }
                }
                break;
            case R.id.btn_marriage_book_now:
                doMarriageBookNowTask();
                break;

        }
    }
    private void doFillBookingDate(){
        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(MarriageBookingActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        myCalendar.add(Calendar.DATE, 5);
        datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
        myCalendar.add(Calendar.MONTH, 8);
        datePickerDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
        datePickerDialog.show();

    }
    private void updateLabel() {
        binding.rltlayBookingDate.setVisibility(View.GONE);
        binding.rltlayBookingDateFilled.setVisibility(View.VISIBLE);
        String submitFormat = "yyyy-MM-dd";
        SimpleDateFormat submitDateFormat = new SimpleDateFormat(submitFormat, Locale.US);

        binding.txtSubmitBookingDate.setText(submitDateFormat.format(myCalendar.getTime()));
        String myFormat = "dd MMM,yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        binding.dateTimeBookingDate.setText(sdf.format(myCalendar.getTime()));
        isCurretnDate = checkForCurrentDate(submitDateFormat.format(myCalendar.getTime()));

    }

    private boolean checkForCurrentDate(String format) {
        Calendar cal = Calendar.getInstance();
        String checkFormat = "yyyy-MM-dd";
        SimpleDateFormat checkDateFormat = new SimpleDateFormat(checkFormat, Locale.US);
        if (format.equals(checkDateFormat.format(cal.getTime()))){
            return true;
        }
        return false;
    }

    private void doOpenTimeSlot(){

        PopupMenu popup = new PopupMenu(MarriageBookingActivity.this, binding.rltlayTimeslot);
        popup.getMenuInflater()
                .inflate(R.menu.timeslot_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                binding.rltlayTimeslot.setVisibility(View.GONE);
                binding.rltlayTimeslotFilled.setVisibility(View.VISIBLE);
                binding.txtTimeSlotFilled.setText(item.getTitle());
               // binding.txtBookingDateS.setText(item.getTitle());
                return true;
            }
        });
        popup.show(); //showing popup menu
    }
    private void populateBridesMaidDetail(){
        int serviceType = 3;
        String bridesmaidName = binding.edtAddBridesmaidName.getText().toString().trim();
        if (bridesmaidName.isEmpty() || bridesmaidName.length() == 0 || bridesmaidName.equals("") || bridesmaidName == null){
            Toast.makeText(MarriageBookingActivity.this, getResources().getString(R.string.bridesmaid_name_error), Toast.LENGTH_SHORT).show();
            return;
        }
        int selectedRadioId = binding.radioAddBridesmaid.getCheckedRadioButtonId();
        if (selectedRadioId < 0){
            Toast.makeText(MarriageBookingActivity.this, getResources().getString(R.string.service_type_error), Toast.LENGTH_SHORT).show();
            return;
        }
        BridesMaid bridesMaid = new BridesMaid();
        bridesMaid.setBrideName(binding.edtAddBridesmaidName.getText().toString());
        switch (selectedRadioId){
            case R.id.rdbtn_hair:
                serviceType = 1;
                break;
            case R.id.rdbtn_makeup:
                serviceType = 2;
                break;
            case R.id.rdbtn_both:
                serviceType = 3;
                break;
        }
        bridesMaid.setServiceType(serviceType);
        bridesMaidList.add(bridesMaid);
        int noOfBridesMaid = bridesMaidList.size();
        binding.edtAddBridesmaidName.setText("");
        binding.radioAddBridesmaid.clearCheck();
        bridesmaidAdapter = new BridesmaidAdapter(this, bridesMaidList, this);
        binding.recyclerViewBridesmaidList.setAdapter(bridesmaidAdapter);
        //bridesmaidAdapter.notifyDataSetChanged();

        //addBrideMaidToList(bridesMaid, noOfBridesMaid);
    }

    @Override
    public void onEditSelected(View v, int position) {

        //Toast.makeText(this, "editing on position "+position, Toast.LENGTH_SHORT).show();

        binding.edtAddBridesmaidName.setText(bridesMaidList.get(position).getBrideName());
        binding.edtAddBridesmaidName.setSelection(binding.edtAddBridesmaidName.getText().length());
        binding.radioAddBridesmaid.clearCheck();
        switch (bridesMaidList.get(position).getServiceType()){
            case 1:
                binding.rdbtnHair.setChecked(true);
                break;
            case 2:
                binding.rdbtnMakeup.setChecked(true);
                break;
            case 3:
                binding.rdbtnBoth.setChecked(true);
                break;
        }
        isGoingToUpdate = true;
        updatingIndex = position;

    }

    @Override
    public void onDeleteSelected(View v, int position) {
       // Toast.makeText(this, "deleting on position "+position, Toast.LENGTH_SHORT).show();
        bridesMaidList.remove(position);
        bridesmaidAdapter.notifyItemRemoved(position);
        bridesmaidAdapter.notifyItemRangeChanged(position,bridesMaidList.size());
    }
   /* private void addBrideMaidToList(final BridesMaid bridesMaid, int numbering){

        View child = getLayoutInflater().inflate(R.layout.add_bridesmaid_layout, null);
        TextView txtBridesMaidName = child.findViewById(R.id.txt_bridesmaid_name);
        TextView txtBridesMaidNumbet = child.findViewById(R.id.txt_numbering);
        TextView txtServiceType = child.findViewById(R.id.txt_bridesmaid_service_type);
        ImageView imgEdit = child.findViewById(R.id.img_edit_bridesmaid);
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtAddBridesMaidName.setText(bridesMaid.getBrideName());
                edtAddBridesMaidName.setSelection(edtAddBridesMaidName.getText().length());

                int selectedButtonId = bridesMaid.getServiceName();
                radioGroupAddBridesMaid.clearCheck();
                switch (selectedButtonId){
                    case 1:
                        radioButtonHair.setChecked(true);
                        break;
                    case 2:
                        radioButtonMakeup.setChecked(true);
                        break;
                    case 3:
                        radioButtonBoth.setChecked(true);
                        break;
                }
            }
        });
        txtBridesMaidName.setText(bridesMaid.getBrideName());
        switch (bridesMaid.getServiceName()){
            case 1:
                txtServiceType.setText("("+getResources().getString(R.string.hair)+")");
                break;
            case 2 :
                txtServiceType.setText("("+getResources().getString(R.string.makeup)+")");
                break;
            case 3:
                txtServiceType.setText("("+getResources().getString(R.string.both)+")");
                break;
        }
        txtBridesMaidNumbet.setText(numbering+".");
        lnrLayAddedBridesMaidList.addView(child);

    }*/

   private void updateBrideMaidList(int updatingIndex){
      // Toast.makeText(this, " updating index "+updatingIndex, Toast.LENGTH_SHORT).show();
       int serviceType = 3;
       String bridesmaidName = binding.edtAddBridesmaidName.getText().toString().trim();
       if (bridesmaidName.isEmpty() || bridesmaidName.length() == 0 || bridesmaidName.equals("") || bridesmaidName == null){
           Toast.makeText(MarriageBookingActivity.this, getResources().getString(R.string.bridesmaid_name_error), Toast.LENGTH_SHORT).show();
           return;
       }
       int selectedRadioId = binding.radioAddBridesmaid.getCheckedRadioButtonId();
       if (selectedRadioId < 0){
           Toast.makeText(MarriageBookingActivity.this, getString(R.string.service_type_error), Toast.LENGTH_SHORT).show();
           return;
       }

       bridesMaidList.get(updatingIndex).setBrideName(binding.edtAddBridesmaidName.getText().toString());
       switch (selectedRadioId){
           case R.id.rdbtn_hair:
               serviceType = 1;
               break;
           case R.id.rdbtn_makeup:
               serviceType = 2;
               break;
           case R.id.rdbtn_both:
               serviceType = 3;
               break;
       }
       bridesMaidList.get(updatingIndex).setServiceType(serviceType);
       binding.edtAddBridesmaidName.setText("");
       binding.radioAddBridesmaid.clearCheck();
       bridesmaidAdapter.notifyItemChanged(updatingIndex);
       isGoingToUpdate = false;
   }

   private void doMarriageBookNowTask(){
       // field validation
       String bookingDate, timeSlot, additionDetails;
       bookingDate = binding.txtSubmitBookingDate.getText().toString().trim();
       timeSlot = binding.txtTimeSlotFilled.getText().toString().trim();
       additionDetails = binding.edtAdditionalDetailFilled.getText().toString().trim();
       if (bookingDate.isEmpty() || bookingDate.equals("") || bookingDate.length() == 0 || bookingDate == null){
           Toast.makeText(this, getResources().getString(R.string.booking_date_error), Toast.LENGTH_SHORT).show();
           return;
       }
       if (timeSlot.isEmpty() || timeSlot.equals("") || timeSlot.length() == 0 || timeSlot == null){
           Toast.makeText(this, getResources().getString(R.string.time_slot_error), Toast.LENGTH_SHORT).show();
           return;
       }
       if (additionDetails.isEmpty() || additionDetails.equals("") || additionDetails.length() == 0 || additionDetails == null){
           Toast.makeText(this, getResources().getString(R.string.additional_detail_error), Toast.LENGTH_SHORT).show();
           return;
       }
       String bridesMaidName = binding.edtAddBridesmaidName.getText().toString().trim();
       int serviceType = binding.radioAddBridesmaid.getCheckedRadioButtonId();
       if (!bridesMaidName.isEmpty() || !bridesMaidName.equals("") || serviceType >1){
           Toast.makeText(this, getResources().getString(R.string.empty_bridesmaid_error), Toast.LENGTH_SHORT).show();
           return;
       }
       if (bridesMaidList.size() == 0){
           Toast.makeText(this, getResources().getString(R.string.empty_bridesmaid_error), Toast.LENGTH_SHORT).show();
           return;
       }

        // fill the request model
       marriageBookingRequestModel.setDate(bookingDate);
       if (timeSlot.equals(getResources().getString(R.string.morning))){
           marriageBookingRequestModel.setTimeSlot(1);
       }else{
           marriageBookingRequestModel.setTimeSlot(2);
       }
       marriageBookingRequestModel.setAdditionalDetails(additionDetails);
       marriageBookingRequestModel.setBridesMaid(bridesMaidList);
       marriageBookingRequestModel.setSalonId(salonId);
       marriageBookingRequestModel.setToken(AppGlobalData.getInstance().getToken());
       checkInternetAndHitApi(MARRIAGE_BOOKING_REQ_CODE);
   }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}
