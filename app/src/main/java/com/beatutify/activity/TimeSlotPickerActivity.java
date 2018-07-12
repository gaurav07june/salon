package com.beatutify.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.beatutify.R;
import com.beatutify.adapters.TimeSlotListAdapter;
import com.beatutify.datepicker.DayItemModel;
import com.beatutify.datepicker.DayTabLayoutAdapter;
import com.beatutify.datepicker.MonthItemModel;
import com.beatutify.model.Employee;
import com.beatutify.model.EmployeeListResponseModel;
import com.beatutify.model.EmployeeWorkingDaysResponseModel;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.model.Salon;
import com.beatutify.model.Service;
import com.beatutify.model.TimeSlot;
import com.beatutify.model.TimeSlotReponseModel;
import com.beatutify.model.TimeSlotRequestModel;
import com.beatutify.retrofit.WebAPIHelper;
import com.beatutify.util.AppCommons;
import com.beatutify.util.AppConstants;
import com.beatutify.util.AppGlobalData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.beatutify.util.AppCommons.getAvailableTimeSlot;
import static com.beatutify.util.AppCommons.getServicesTotalTime;


public class TimeSlotPickerActivity extends BaseActivity implements View.OnClickListener, DayTabLayoutAdapter.OnDaySelectListener, TimeSlotListAdapter.OnTimeSlotSelectListener {
    private RecyclerView tabLayout;
    private Bundle extras = null;
    private DayItemModel selectedDate;
    private Salon salon;
    private Employee selectedEmployee;
    private EmployeeWorkingDaysResponseModel employeeWorkingDays;
    private RecyclerView timeSlotListView;
    private DayTabLayoutAdapter dayTabLayoutAdapter;
    private Handler mHandler = new Handler();
    private TimeSlot selectedTimeSlot = null;
    private List<Service> serviceList;
    private static final int GET_TIME_SLOT_RQ_CODE = 101;
    private int serviceTotalTime=0;
    private TimeSlotListAdapter timeSlotListAdapter;
    private View btnNext;
    ArrayList<TimeSlot> availableTimeSlots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CreateTimeSlotPickerTask().execute();
        selectedTimeSlot = null;
        checkInternetAndHitApi(GET_TIME_SLOT_RQ_CODE);

    }

    @Override
    public void initViews() {

        tabLayout = findViewById(R.id.time_slot_day_tab_lay);
        timeSlotListView = findViewById(R.id.time_slot_list_view);
        btnNext = findViewById(R.id.btn_next);
    }

    @Override
    public void setViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(AppCommons.getUIMonthYear(this, selectedDate));
        serviceTotalTime = getServicesTotalTime(serviceList);
        timeSlotListAdapter = new TimeSlotListAdapter(this);
        timeSlotListAdapter.setOnTimeSlotSelectListener(this);
        timeSlotListView.setAdapter(timeSlotListAdapter);
        dayTabLayoutAdapter = new DayTabLayoutAdapter(this, tabLayout);
        dayTabLayoutAdapter.setOnDaySelectListener(this);
        tabLayout.setAdapter(dayTabLayoutAdapter);
        tabLayout.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        timeSlotListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        btnNext.setOnClickListener(this);
 /*       tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            TabLayout.Tab selectedTab = null;

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                DayItemModel selectedDay = dayItemList.get(tab.getPosition());
                if (selectedDay.isLeaveDate() || selectedDay.isPastDate()) {
                    if (selectedTab != null) {
                        selectedTab.select();
                    }
                } else {

                    TextView dayName = tab.getCustomView().findViewById(R.id.time_slot_day_name);
                    TextView weekDayName = tab.getCustomView().findViewById(R.id.time_slot_weekday_name);
                    dayName.setSelected(true);
                    weekDayName.setSelected(true);
                    selectedTab = tab;
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView dayName = tab.getCustomView().findViewById(R.id.time_slot_day_name);
                TextView weekDayName = tab.getCustomView().findViewById(R.id.time_slot_weekday_name);
                dayName.setSelected(false);
                weekDayName.setSelected(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("", "");
            }
        });
*/
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_time_slot_picker);

    }

    @Override
    public void getExtras() {
        extras = getIntent().getExtras();
        if (extras != null) {

            selectedEmployee = (Employee) extras.getSerializable(AppConstants.EXTRAS.SELECTED_EMPLOYEE);
            salon = (Salon) extras.getSerializable(AppConstants.EXTRAS.SALON_DATA);
            selectedDate = (DayItemModel) extras.getSerializable(AppConstants.EXTRAS.SELECTED_DATE);
            serviceList = (List<Service>) extras.getSerializable(AppConstants.EXTRAS.SELECTED_SERVICES);
            employeeWorkingDays = (EmployeeWorkingDaysResponseModel) extras.getSerializable(AppConstants.EXTRAS.EMPLOYEE_WORKING_DAYS);
        }
    }

    @Override
    public <D> void onApiSuccess(GenericResponseModel<D> model, int request_code) {
        switch (request_code) {
            case GET_TIME_SLOT_RQ_CODE:
                if (model.getData() != null && model.getData() instanceof TimeSlotReponseModel) {

                    timeSlotListAdapter.setTimeSlots(((TimeSlotReponseModel) model.getData()).getTimeListing());


                }
                break;
        }
    }

    @Override
    public <D> void onApiFail(GenericResponseModel<D> model, int request_code) {

    }

    @Override
    public void onApiException(Throwable t, int request_code) {

    }

    @Override
    public void hitApi(int request) {
        switch (request) {
            case GET_TIME_SLOT_RQ_CODE:
                TimeSlotRequestModel timeSlotRequest = new TimeSlotRequestModel();
                timeSlotRequest.setSalonId(salon.getSalon_id());
                timeSlotRequest.setEmpId(selectedEmployee.getEmployeeId());
                timeSlotRequest.setToken(AppGlobalData.getInstance().getToken());
                timeSlotRequest.setBookingDate(AppCommons.toWebDate(selectedDate.getDate()));
                WebAPIHelper.getInstance().getTimeSlots(timeSlotRequest, new APICallback<TimeSlotReponseModel>(GET_TIME_SLOT_RQ_CODE));
                break;
        }
    }


    private List<DayItemModel> dayItemList = new ArrayList<>();

    @Override
    public void onDaySelect(DayItemModel day) {

        selectedDate = day;
        getSupportActionBar().setTitle(AppCommons.getUIMonthYear(this, day));
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                selectedTimeSlot = null;
                timeSlotListAdapter.setSelectedIndex(-1);
                checkInternetAndHitApi(GET_TIME_SLOT_RQ_CODE);

            }
        }, 200);
    }

    @Override
    public boolean onTimeSlotSelect(TimeSlot timeSlot) {

        if (validateTimeSlot(timeSlot)) {
            selectedTimeSlot = timeSlot;
            return true;
        }

        return false;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                if (isValid()) {
                    Intent intent = new Intent(this, ServiceBookNowActivity.class);
                    intent.putExtras(getIntent().getExtras());
                    intent.putExtra(AppConstants.EXTRAS.SELECTED_DATE, selectedDate);
                    intent.putExtra(AppConstants.EXTRAS.SELECTED_TIME_SLOT, selectedTimeSlot);
                    intent.putExtra(AppConstants.EXTRAS.AVAILABLE_TIME_SLOTS, availableTimeSlots);
                    startActivity(intent);

                }
                break;
        }
    }

    public boolean isValid() {

        if (selectedTimeSlot == null) {
            Toast.makeText(this, R.string.select_time_slot_error, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private class CreateTimeSlotPickerTask extends AsyncTask<Object, Object, Object> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dayItemList.clear();
        }

        @Override
        protected Object doInBackground(Object... params) {
            dayItemList = AppCommons.getDayItemList(employeeWorkingDays, false);
            return null;

        }

        @Override
        protected void onPostExecute(Object o) {

            int selectedIndex = dayItemList.indexOf(selectedDate);
            dayTabLayoutAdapter.setSelectedIndex(selectedIndex >= 0 ? selectedIndex : 0);
            dayTabLayoutAdapter.setDayItemList(dayItemList);
            tabLayout.post(new Runnable() {
                @Override
                public void run() {
                    dayTabLayoutAdapter.scrollToPosition(dayTabLayoutAdapter.getSelectedIndex(), false);
                }
            });
            super.onPostExecute(o);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validateTimeSlot(TimeSlot timeSlot) {
        availableTimeSlots = (ArrayList<TimeSlot>) getAvailableTimeSlot(timeSlot,timeSlotListAdapter.getTimeSlots());

        int availableMinutes = availableTimeSlots.size()*AppConstants.SERVICE_BOOKING.TIME_SLOT_DIFF;
        if (availableMinutes>=serviceTotalTime)
        {
            return true;
        }
        else
        {
            Toast.makeText(this,R.string.time_slot_not_available_enough_plural,Toast.LENGTH_SHORT).show();
            return false;
        }

    }


}
