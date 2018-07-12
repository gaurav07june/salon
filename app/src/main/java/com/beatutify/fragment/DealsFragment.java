package com.beatutify.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beatutify.R;
import com.beatutify.adapters.MyDealsListAdapter;
import com.beatutify.databinding.FragmentDealsBinding;
import com.beatutify.model.BookingListRequestModel;
import com.beatutify.model.DealList;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.model.MyDealsResponseModel;
import com.beatutify.retrofit.WebAPIHelper;
import com.beatutify.util.AppGlobalData;
import com.beatutify.util.BeautifyDividerItemDecoration;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DealsFragment extends BaseFragment implements MyDealsListAdapter.MyDealListListener {
    private final int MY_DEAL_LIST_REQ_CODE = 1001;
    private FragmentDealsBinding binding;
    private BookingListRequestModel bookingListRequestModel;
    private ArrayList<DealList> dealLists;
    private MyDealsListAdapter myDealsListAdapter;
    public DealsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deals, container, false);
        binding = DataBindingUtil.bind(view);
        checkInternetAndHitApi(MY_DEAL_LIST_REQ_CODE);
        return view;
    }

    @Override
    public <D> void onApiSuccess(GenericResponseModel<D> model, int request_code) {
        switch (request_code){
            case MY_DEAL_LIST_REQ_CODE:
                if (model != null){
                    GenericResponseModel<MyDealsResponseModel> responseModel = (GenericResponseModel<MyDealsResponseModel>) model;
                    if (responseModel.getData() != null && responseModel.getData().getDealList().size() > 0){
                        binding.txtNoDeals.setVisibility(View.GONE);
                        dealLists = new ArrayList<>();
                        dealLists = (ArrayList<DealList>) responseModel.getData().getDealList();
                        myDealsListAdapter = new MyDealsListAdapter(getActivity(), dealLists, this);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                        binding.recyclerViewMyDeals.setLayoutManager(mLayoutManager);
                        binding.recyclerViewMyDeals.setItemAnimator(new DefaultItemAnimator());
                        binding.recyclerViewMyDeals.addItemDecoration(new BeautifyDividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL, 36));
                        binding.recyclerViewMyDeals.setAdapter(myDealsListAdapter);

                    }else{
                        binding.txtNoDeals.setVisibility(View.VISIBLE);
                    }

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
    public void setViews() {
    }

    @Override
    public void hitApi(int request) {
        switch (request){
            case MY_DEAL_LIST_REQ_CODE:
                bookingListRequestModel = new BookingListRequestModel();
                bookingListRequestModel.setCustomerId(AppGlobalData.getInstance().getLoggedInCustomer().getId());
                bookingListRequestModel.setToken(AppGlobalData.getInstance().getToken());
                WebAPIHelper.getInstance().getMyDealList(bookingListRequestModel, new APICallback<MyDealsResponseModel>(MY_DEAL_LIST_REQ_CODE));
                break;
        }
    }

    @Override
    public void onViewDeal(View v, int position) {

    }
}
