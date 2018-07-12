package com.beatutify.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.beatutify.R;
import com.beatutify.adapters.ServiceBookListAdapter;
import com.beatutify.customviews.ServiceTabLayout;
import com.beatutify.datepicker.DayItemModel;
import com.beatutify.model.Employee;
import com.beatutify.model.EmployeeWorkingDaysResponseModel;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.model.Salon;
import com.beatutify.model.Service;
import com.beatutify.model.ServiceBookingRequestModel;
import com.beatutify.model.ServiceCategory;
import com.beatutify.model.TimeSlot;
import com.beatutify.retrofit.WebAPIHelper;
import com.beatutify.util.AppCommons;
import com.beatutify.util.AppConstants;
import com.beatutify.util.BookingFeeDialog;

import java.util.ArrayList;
import java.util.List;

public class ServiceBookNowActivity extends BaseActivity implements View.OnClickListener, ServiceTabLayout.OnServiceTabCheckListener, ServiceBookListAdapter.ServiceBookListItemListener {

    private ServiceTabLayout serviceTabLayout;
    private Employee selectedEmployee;
    private DayItemModel selectedDate;
    private Salon selectedSalon;
    private ServiceCategory selectedCategory;
    private TimeSlot selectedTimeSlot;
    private List<TimeSlot> availableTimeSlots;
    private List<Service> serviceList = new ArrayList<>();
    private RecyclerView serviceBookingListView;
    private ServiceBookListAdapter serviceBookListAdapter;
    private TextView bookingFeeNoteTv;
    private View btnBookNow;
    private BookingFeeDialog bookingFeeDialog;
    private static final int BOOK_SERVICE_RQ = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        serviceTabLayout = findViewById(R.id.service_tab_layout);
        serviceBookingListView = findViewById(R.id.service_booking_list_view);
        bookingFeeNoteTv = findViewById(R.id.booking_fee_note_tv);
        btnBookNow = findViewById(R.id.btn_book_now);
        serviceBookListAdapter = new ServiceBookListAdapter(this);
        bookingFeeDialog = new BookingFeeDialog(this);
    }

    @Override
    public void setViews() {
        getSupportActionBar().setTitle(R.string.book_now);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        serviceTabLayout.setServiceList(serviceList);
        serviceTabLayout.setOnServiceTabCheckListener(this);
        serviceBookingListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        serviceBookingListView.setAdapter(serviceBookListAdapter);
        btnBookNow.setOnClickListener(this);
        serviceBookListAdapter.setServiceBookListItemListener(this);
        serviceBookListAdapter.setData(getSelectedServices(), selectedEmployee, selectedDate);

        SpannableString bookingFeeNoteText = new SpannableString(getString(R.string.booking_fee_note));
        bookingFeeNoteText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorAccent)), 10, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ClickableSpan booking_fee_click_span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                bookingFeeDialog.show(serviceBookListAdapter.getServiceList());
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                // ds.setUnderlineText(true);
            }
        };

        bookingFeeNoteText.setSpan(booking_fee_click_span, 10, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        bookingFeeNoteTv.setMovementMethod(LinkMovementMethod.getInstance());
        bookingFeeNoteTv.setText(bookingFeeNoteText);
        bookingFeeNoteTv.setVisibility(serviceBookListAdapter.getItemCount() > 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setContentView() {

        setContentView(R.layout.activity_service_book_now);
    }

    @Override
    public void getExtras() {
        selectedCategory = (ServiceCategory) getIntent().getSerializableExtra(AppConstants.EXTRAS.SERVICE_CATEGORY_DATA);
        serviceList = (List<Service>) getIntent().getSerializableExtra(AppConstants.EXTRAS.SELECTED_SERVICES);
        selectedEmployee = (Employee) getIntent().getSerializableExtra(AppConstants.EXTRAS.SELECTED_EMPLOYEE);
        selectedDate = (DayItemModel) getIntent().getSerializableExtra(AppConstants.EXTRAS.SELECTED_DATE);
        selectedSalon = (Salon) getIntent().getSerializableExtra(AppConstants.EXTRAS.SALON_DATA);
        selectedTimeSlot = (TimeSlot) getIntent().getSerializableExtra(AppConstants.EXTRAS.SELECTED_TIME_SLOT);
        availableTimeSlots = (List<TimeSlot>) getIntent().getSerializableExtra(AppConstants.EXTRAS.AVAILABLE_TIME_SLOTS);

    }

    @Override
    public <D> void onApiSuccess(GenericResponseModel<D> model, int request_code) {
        switch (request_code) {
            case BOOK_SERVICE_RQ:
                startActivity(new Intent(this, OrderPlacedActivity.class));
                break;
        }
    }

    @Override
    public <D> void onApiFail(GenericResponseModel<D> model, int request_code) {
        switch (request_code) {
            case BOOK_SERVICE_RQ:
                break;
        }
    }

    @Override
    public void onApiException(Throwable t, int request_code) {
        switch (request_code) {
            case BOOK_SERVICE_RQ:
                break;
        }
    }

    @Override
    public void hitApi(int request) {
        switch (request) {
            case BOOK_SERVICE_RQ:

                int serviceTotalTime = AppCommons.getServicesTotalTime(serviceList);
                List<TimeSlot> bookedTimeSlots = getBookedTimeSlots(serviceTotalTime);
                ServiceBookingRequestModel requestModel = new ServiceBookingRequestModel();
                requestModel.setCategoryId(selectedCategory.getServiceCatId());
                requestModel.setSalonId(selectedSalon.getSalon_id());
                requestModel.setEmpId(selectedEmployee.getEmployeeId());
                requestModel.setServiceList(getSelectedServices());
                requestModel.setServiceTime(serviceTotalTime);
                requestModel.setTimeSlot(bookedTimeSlots);
                requestModel.setServiceDate(AppCommons.toWebDate(selectedDate.getDate()));
                requestModel.setServiceStart(bookedTimeSlots.size() > 0 ? bookedTimeSlots.get(0).getTime() : "");
                WebAPIHelper.getInstance().doServiceBooking(requestModel, new APICallback<>(BOOK_SERVICE_RQ));
                break;
        }
    }

    @Override
    public boolean onServiceTabCheck(Service service) {
        if (!service.isSelected()) {
            serviceBookListAdapter.removeService(service);
            return true;
        } else if (validateTimeSlot()) {
            serviceBookListAdapter.addService(service);
            return true;
        } else {
            Toast.makeText(this, R.string.time_slot_not_available_enough, Toast.LENGTH_SHORT).show();
        }

        bookingFeeNoteTv.setVisibility(serviceBookListAdapter.getItemCount() > 0 ? View.VISIBLE : View.GONE);

        return false;
    }

    private boolean validateTimeSlot() {
        int availableMinutes = availableTimeSlots.size() * AppConstants.SERVICE_BOOKING.TIME_SLOT_DIFF;

        if (availableMinutes >= AppCommons.getServicesTotalTime(serviceList)) {
            return true;
        } else {
            return false;
        }
    }

    public List<Service> getSelectedServices() {

        List<Service> selectedServices = new ArrayList<>();
        if (serviceList == null) return selectedServices;
        for (Service service : serviceList) {
            selectedServices.add(service);
        }
        return selectedServices;
    }

    @Override
    public void onEditClicked(Service service) {
        Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClicked(Service service) {
        serviceBookListAdapter.removeService(service);
        serviceTabLayout.setServiceSelected(service, false);
        bookingFeeNoteTv.setVisibility(serviceBookListAdapter.getItemCount() > 0 ? View.VISIBLE : View.GONE);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_book_now:

                checkInternetAndHitApi(BOOK_SERVICE_RQ);
                break;
        }
    }

    private List<TimeSlot> getBookedTimeSlots(int serviceTotalTime) {
        List<TimeSlot> bookedTimeSlots = new ArrayList<>();

        int bookedTime = 0;
        for (TimeSlot timeSlot : availableTimeSlots) {
            bookedTime += AppConstants.SERVICE_BOOKING.TIME_SLOT_DIFF;
            bookedTimeSlots.add(timeSlot);
            if (bookedTime >= serviceTotalTime) break;

        }
        return bookedTimeSlots;
    }
}
