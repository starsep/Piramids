package com.starsep.piramids;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GameView extends View {

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GameView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
    }
}
