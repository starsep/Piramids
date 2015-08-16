package com.starsep.piramids.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class NumberButton extends Button {
    public NumberButton(Context context) {
        super(context);
        setTextSize(getTextSize());
    }

    public NumberButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTextSize(getTextSize());
    }

    public NumberButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextSize(getTextSize());
    }
}
