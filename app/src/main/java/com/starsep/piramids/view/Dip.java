package com.starsep.piramids.view;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class Dip {
    public static float ratio(Context context) {
        float value = 1000f;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics) / value;
    }

    public static float get(Context context, int resourceId) {
        float dipValue = context.getResources().getDimension(resourceId);
        return dipValue / ratio(context);
    }
}
