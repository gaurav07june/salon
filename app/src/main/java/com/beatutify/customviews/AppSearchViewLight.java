package com.beatutify.customviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beatutify.R;

/**
 * Created by karan.kalsi on 3/21/2018.
 */

public class AppSearchViewLight extends LinearLayout {
    public AppSearchViewLight(Context context) {
        super(context);
        init();
    }

    public AppSearchViewLight(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppSearchViewLight(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.search_view_light,this,false);
        addView(view);
        final EditText search_et = view.findViewById(R.id.search_edit_text_light);
        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (onSearchQueryListener!=null)onSearchQueryListener.onSearchQueryChange(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (onSearchQueryListener!=null)onSearchQueryListener.onSearchQuery(search_et.getText().toString());
                    return true;
                }
                return false;
            }
        });

    }

    public OnSearchQueryListener getOnSearchQueryListener() {
        return onSearchQueryListener;
    }

    public void setOnSearchQueryListener(OnSearchQueryListener onSearchQueryListener) {
        this.onSearchQueryListener = onSearchQueryListener;
    }

    private OnSearchQueryListener onSearchQueryListener;
    public interface OnSearchQueryListener
    {
        void onSearchQuery(String query);
        void onSearchQueryChange(String query);

    }

}
