package com.beatutify.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.beatutify.R;
import com.beatutify.databinding.ActivityDatePickerBinding;
import com.beatutify.datepicker.DayItemGridAdapter;
import com.beatutify.datepicker.DayItemModel;
import com.beatutify.model.Employee;
import com.beatutify.model.EmployeeWorkingDaysRequestModel;
import com.beatutify.model.EmployeeWorkingDaysResponseModel;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.retrofit.WebAPIHelper;
import com.beatutify.util.AppCommons;
import com.beatutify.util.AppConstants;
import com.beatutify.util.AppGlobalData;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static com.beatutify.util.AppCommons.getDayItemList;
import static com.beatutify.util.AppCommons.getMonthItemList;

public class DatePickerActivity extends BaseActivity implements View.OnClickListener,DayItemGridAdapter.OnDaySelectListener {
    private ActivityDatePickerBinding binding;

    private Employee selectedEmployee;
    private Bundle extras;
    private DayItemModel selectedDate;
    private EmployeeWorkingDaysResponseModel employeeWorkingDays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initViews() {

    }

    @Override
    public void setViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.pick_a_date);
        binding.appDatePicker.setOnDaySelectListener(this);
        binding.btnNext.setOnClickListener(this);
        new LoadDatePickerTask().execute();
    }

    @Override
    public void setContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_date_picker);
    }

    @Override
    public void getExtras() {
        extras = getIntent().getExtras();
        if(extras!=null)
        {
            selectedEmployee = (Employee) extras.getSerializable(AppConstants.EXTRAS.SELECTED_EMPLOYEE);
        }
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
    public void hitApi(int request) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_next:
                Intent intent = new Intent(this,TimeSlotPickerActivity.class);
                intent.putExtras(extras);
                intent.putExtra(AppConstants.EXTRAS.SELECTED_DATE,selectedDate);
                intent.putExtra(AppConstants.EXTRAS.EMPLOYEE_WORKING_DAYS,employeeWorkingDays);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onDaySelect(View view, DayItemModel day) {
        selectedDate=day;

        getSupportActionBar().setTitle(AppCommons.getUIMonthYear(this,day));
    }

    private class LoadDatePickerTask extends AsyncTask<Object, Object, Object> {
        List<DayItemModel> dayItemList;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object... params) {


            EmployeeWorkingDaysRequestModel request = new EmployeeWorkingDaysRequestModel();
            request.setToken(AppGlobalData.getInstance().getToken());
            request.setEmpId(selectedEmployee.getEmployeeId());
             employeeWorkingDays = WebAPIHelper.getInstance().getEmployeeWorkingDaysSync(request);
            dayItemList = getDayItemList(employeeWorkingDays,true);

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            progressDialog.dismiss();
            binding.appDatePicker.setData(dayItemList);
            super.onPostExecute(o);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
