package com.beatutify.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beatutify.R;
import com.beatutify.model.BridesMaid;

import java.util.List;

/**
 * Created by gaurav.singh on 3/27/2018.
 */

public class BridesmaidAdapter extends RecyclerView.Adapter<BridesmaidAdapter.MyViewHolder> {
    private Context mContext;
    private List<BridesMaid> bridesMaidList;
    private static BridesMaidAdapterListener listener;
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtBridesMaidName, txtBridesMaidNumbet, txtServiceType;
        ImageView  imgEdit, imgDelete;

        public MyViewHolder(View view){
            super(view);
            txtBridesMaidName = view.findViewById(R.id.txt_bridesmaid_name);
            txtBridesMaidNumbet = view.findViewById(R.id.txt_numbering);
            txtServiceType = view.findViewById(R.id.txt_bridesmaid_service_type);
            imgEdit = view.findViewById(R.id.img_edit_bridesmaid);
            imgDelete = view.findViewById(R.id.img_delete_bridesmaid);
            imgEdit.setOnClickListener(this);
            imgDelete.setOnClickListener(this);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.img_edit_bridesmaid){
                listener.onEditSelected(v, getLayoutPosition());
            }else if (v.getId() == R.id.img_delete_bridesmaid){
                listener.onDeleteSelected(v, getLayoutPosition());
            }

        }
    }

    public BridesmaidAdapter(Context mContext, List<BridesMaid> bridesMaidList, BridesMaidAdapterListener listener) {
        this.mContext = mContext;
        this.bridesMaidList = bridesMaidList;
        this.listener = listener;
    }

    public BridesmaidAdapter(Context mContext, List<BridesMaid> bridesMaidList){
        this.mContext = mContext;
        this.bridesMaidList = bridesMaidList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_bridesmaid_layout, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        BridesMaid bridesMaid = bridesMaidList.get(position);
        holder. txtBridesMaidName.setText(bridesMaid.getBrideName());
        holder.txtBridesMaidNumbet.setText((position+1)+".");
        switch (bridesMaid.getServiceType()){
            case 1:
                holder.txtServiceType.setText("("+mContext.getResources().getString(R.string.hair)+")");
                break;
            case 2 :
                holder.txtServiceType.setText("("+mContext.getResources().getString(R.string.makeup)+")");
                break;
            case 3:
                holder.txtServiceType.setText("("+mContext.getResources().getString(R.string.both)+")");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return bridesMaidList.size();
    }
    public interface BridesMaidAdapterListener {
        public void onEditSelected(View v, int position);
        public void onDeleteSelected(View v, int position);
    }

}
