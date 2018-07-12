package com.beatutify.customviews;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import android.view.View;
import android.widget.FrameLayout;

import com.beatutify.R;
import com.beatutify.databinding.AddRemoveItemBtnViewBinding;


/**
 *
 * Created by karan.kalsi on 12/12/2017.
 */

public class AddRemoveItemButton extends FrameLayout implements View.OnClickListener {
    Context context;
    public AddRemoveItemButton(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    public AddRemoveItemButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public AddRemoveItemButton(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private AddRemoveItemBtnViewBinding binding;

    private void init() {

      View view =  inflate(getContext(), R.layout.add_remove_item_btn_view, this);
        binding =DataBindingUtil.bind(((AddRemoveItemButton)view).getChildAt(0));
        binding.addItemBtn.setSelected(true);
        binding.addItemBtn.setOnClickListener(this);
        binding.removeItemBtn.setOnClickListener(this);

    }
    public void setRemoveButtonEnabled(){
        binding.removeItemBtn.setClickable(true);
        binding.removeItemBtn.setBackgroundResource(R.drawable.add_remove_btn_item_bg_lc);
    }
    public void setRemoveButtonDisabled(){
        binding.removeItemBtn.setClickable(false);
        binding.removeItemBtn.setBackgroundResource(R.drawable.add_remove_btn_item_disabled);
    }
    public void setAddButtonEnabled(){
        binding.addItemBtn.setClickable(true);
        binding.addItemBtn.setBackgroundResource(R.drawable.add_remove_btn_item_bg_rc);
    }
    public void setAddButtonDisabled(){
        binding.addItemBtn.setClickable(false);
        binding.addItemBtn.setBackgroundResource(R.drawable.add_remove_btn_item_disabled);
    }

    public void setQuantityColor(int color){
        binding.quantity.setTextColor(color);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.add_item_btn:
                if (addRemoveItemButtonListener!=null)addRemoveItemButtonListener.onAddClicked();
                break;
            case R.id.remove_item_btn:
                if (addRemoveItemButtonListener!=null)addRemoveItemButtonListener.onRemovedClicked();
                break;
        }
    }
    private AddRemoveItemButtonListener addRemoveItemButtonListener=null;

    public AddRemoveItemButtonListener getAddRemoveItemButtonListener() {
        return addRemoveItemButtonListener;
    }

    public void setAddRemoveItemButtonListener(AddRemoveItemButtonListener addRemoveItemButtonListener) {
        this.addRemoveItemButtonListener = addRemoveItemButtonListener;
    }

    public interface AddRemoveItemButtonListener
    {
        void onAddClicked();
        void onRemovedClicked();
    }
    public void setQuantity(int quantity)
    {
        if (quantity==0)
        {
            binding.addItemBtn.setSelected(true);
        }
        else
        {
            binding.addItemBtn.setSelected(false);
        }
        binding.quantity.setText(String.valueOf(quantity));
    }
}
