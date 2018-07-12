package com.beatutify.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.beatutify.R;

/**
 * Created by gaurav.singh on 3/29/2018.
 */

public class NMGCheckBox extends com.rey.material.widget.CheckBox {

    public NMGCheckBox(Context context) {
        super(context);
        init(null);
    }

    public NMGCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public NMGCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
    private void init(AttributeSet attrs) {
        if (attrs!=null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.NMGTextView);
            String fontName = a.getString(R.styleable.NMGTextView_fontName);
            if (fontName!=null) {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "font/" +fontName);
                if(myTypeface!=null)

                    setTypeface(myTypeface);
            }
            a.recycle();
        }

    }
}
