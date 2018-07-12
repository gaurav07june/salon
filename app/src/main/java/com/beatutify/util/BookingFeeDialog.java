package com.beatutify.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.beatutify.R;
import com.beatutify.customviews.ServiceBillListView;
import com.beatutify.model.Service;

import java.util.List;


/**
 * Created by karan.kalsi on 4/7/2017.
 */
public class BookingFeeDialog {
    private Dialog dialog;
    private ServiceBillListView serviceBillListView;
    public BookingFeeDialog(Context context)
    {
        dialog = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.booking_fee_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        serviceBillListView = dialog.findViewById(R.id.service_bill_list_view);
        dialog.findViewById(R.id.booking_fee_dlg_close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
      //  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
    }
public void show(List<Service> bookedServices)
{
    try{
        if(dialog==null )return;

        serviceBillListView.setBookedServices(bookedServices);
        dialog.show();
    }
    catch (Exception e)
    {
    }

}
    public void dismiss()
    {
        try{
        if(dialog==null )return;
        dialog.dismiss();
    }
    catch (Exception e)
    {

    }
    }

    public boolean isShowing()
    {
        return dialog!=null && dialog.isShowing();
    }
}
