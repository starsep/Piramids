package com.starsep.piramids.view;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


public class HintView extends TextView {
    public HintView(Context context) {
        this(0, context);
    }

    public HintView(int value, Context context) {
        super(context);
        setText(value == 0 ? "" : String.valueOf(value));
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
    }
}