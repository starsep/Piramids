package com.starsep.piramids.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.starsep.piramids.R;

public class GameViewElement extends Button {
    private int x;
    private int y;

    public GameViewElement(Context context, int x, int y, final GameView gameView) {
        super(context);
        this.x = x;
        this.y = y;
        ((Activity) getContext()).registerForContextMenu(this);
        setTextSize(getTextSize());
        setBackgroundResource(R.drawable.game_button);
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.chooseButton(v);
            }
        });
    }

    //TODO: Awful hack, delete
    @Override
    public float getX() {
        return x;
    }

    //TODO: Awful hack, delete
    @Override
    public float getY() {
        return y;
    }
}