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
        paint.setColor(Color.BLACK);

        float maxSize = Math.min(getHeight(), getWidth()) - 1f;
        float boardSize = 5; //TODO

        for(float q = 0f; q <= maxSize; q += maxSize / boardSize) {
            canvas.drawLine(0, q, maxSize, q, paint);
            canvas.drawLine(q, 0, q, maxSize, paint);
        }


        super.onDraw(canvas);
    }
}
