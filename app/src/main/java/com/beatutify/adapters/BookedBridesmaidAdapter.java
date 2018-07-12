package com.beatutify.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beatutify.R;
import com.beatutify.databinding.BridesMaidRowBinding;
import com.beatutify.model.BridesMaid;

import java.util.List;

/**
 * Created by gaurav.singh on 3/27/2018.
 */

public class BookedBridesmaidAdapter extends RecyclerView.Adapter<BookedBridesmaidAdapter.MyViewHolder> {
    private Context mContext;
    private List<BridesMaid> bridesMaidList;
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        BridesMaidRowBinding bridesMaidRowBinding;
        public MyViewHolder(View view){
            super(view);
            bridesMaidRowBinding = DataBindingUtil.bind(view);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

        }
    }

    public BookedBridesmaidAdapter(Context mContext, List<BridesMaid> bridesMaidList){
        this.mContext = mContext;
        this.bridesMaidList = bridesMaidList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.brides_maid_row, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        BridesMaid bridesMaid = bridesMaidList.get(position);
        holder.bridesMaidRowBinding.bridesmaidCount.setText(Integer.toString(position+1)+".");

        holder.bridesMaidRowBinding.bridesmaidName.setText(bridesMaid.getBrideName());
        Resources resources = mContext.getResources();
        String serviceType = "";
        switch (bridesMaid.getServiceType()){
            case 1:
                serviceType = String.format(resources.getString(R.string.service_type), mContext.getResources().getString(R.string.hair));
                break;
            case 2 :
                serviceType = String.format(resources.getString(R.string.service_type), mContext.getResources().getString(R.string.makeup));
                break;
            case 3:
                serviceType = String.format(resources.getString(R.string.service_type), mContext.getResources().getString(R.string.both));
                break;
        }
        holder.bridesMaidRowBinding.bridesmaidServiceType.setText(serviceType);
    }
    @Override
    public int getItemCount() {
        return bridesMaidList.size();
    }
}
