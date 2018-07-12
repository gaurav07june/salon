package com.beatutify.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.beatutify.R;
import com.beatutify.model.Salon;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;



public class SalonListAdapter extends RecyclerView.Adapter<SalonListAdapter.MyViewHolder>{
    private Context context;
    private List<Salon> salonList;
    private SalonListAdapterListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtSalonName, txtSalonAddress;
        public ImageView imgSaonImage;

        public MyViewHolder(View view) {
            super(view);
            txtSalonName = view.findViewById(R.id.txt_salon_name);
            txtSalonAddress = view.findViewById(R.id.txt_salon_address);
            imgSaonImage = view.findViewById(R.id.img_salon_image);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onSalonSelected(salonList.get(getAdapterPosition()), imgSaonImage);
                }
            });
        }
    }


    public SalonListAdapter(Context context, List<Salon> salonList, SalonListAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.salonList = salonList;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.salon_item_row, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Salon salonListItem = salonList.get(position);
        holder.txtSalonName.setText(salonListItem.getSalon_name());
       // holder.txtSalonName.setMovementMethod(new ScrollingMovementMethod());
        holder.txtSalonAddress.setText(salonListItem.getAddress());
       // holder.txtSalonAddress.setMovementMethod(new ScrollingMovementMethod());
        ImageLoader.getInstance().displayImage(salonListItem.getImage(), holder.imgSaonImage);
    }

    @Override
    public int getItemCount() {
        return salonList.size();
    }

   /* @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    salonListFiltered = salonList;
                } else {
                    List<SalonList> filteredList = new ArrayList<>();
                    for (SalonList row : salonList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getSalon_name().toLowerCase().contains(charString.toLowerCase()) || row.getAddress().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    salonListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = salonListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                salonListFiltered = (ArrayList<SalonList>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }*/
    public interface SalonListAdapterListener {
        void onSalonSelected(Salon salonList, View imageView);
    }
}
