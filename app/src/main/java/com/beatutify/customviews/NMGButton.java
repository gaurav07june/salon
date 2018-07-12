package com.beatutify.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.beatutify.R;
import com.rey.material.widget.Button;
public class NMGButton extends Button {

	public NMGButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);


	}

	public NMGButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);

	}

	public NMGButton(Context context) {
		super(context);
		init(null);
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