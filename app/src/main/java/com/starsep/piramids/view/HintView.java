package com.starsep.piramids.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starsep.piramids.R;


public class HintView extends TextView {
    public HintView(Context context) {
        this(0, context);
    }

    public HintView(int value, Context context) {
        super(context);
        setText(value == 0 ? "" : String.valueOf(value));
        setTextSize(Dip.get(getContext(), R.dimen.activity_game_text_size));
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
    }
}