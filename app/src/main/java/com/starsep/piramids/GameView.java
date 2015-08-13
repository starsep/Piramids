package com.starsep.piramids;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class GameView extends LinearLayout {

    public class GameViewRow extends LinearLayout {

        public class GameViewElement extends Button {
            private int x;
            private int y;
            public GameViewElement(Context context, int x, int y) {
                super(context);
                /*setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setText("2");
                    }
                });*/
                this.x = x;
                this.y = y;
                ((Activity)getContext()).registerForContextMenu(this);
                //setBackgroundResource();
                setBackgroundResource(R.drawable.game_button);
                setHighlightColor(Color.BLUE);
                setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                setOrientation(VERTICAL);
            }

            @Override
            public float getX() {
                return x;
            }

            @Override
            public float getY() {
                return y;
            }
        }

        public GameViewElement[] elements;

        public GameViewRow(Context context, int size, int x) {
            super(context);
            elements = new GameViewElement[size];
            for(int i = 0; i < size; i++) {
                elements[i] = new GameViewElement(getContext(), x, i);
                addView(elements[i]);
            }
            setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        }
    }

    private GameViewRow[] rows;
    private GameBoard gameBoard;

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GameView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
    }

    public void refresh() {
        for(int x =  0; x < rows.length; x++)
            for(int y = 0; y < rows[x].elements.length; y++)
                if(gameBoard.getTile(x, y) != 0)
                    rows[x].elements[y].setText(String.valueOf(gameBoard.getTile(x, y)));
                else rows[x].elements[y].setText("");
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        rows = new GameViewRow[gameBoard.getSize()];
        for(int i = 0; i < rows.length; i++) {
            rows[i] = new GameViewRow(getContext(), gameBoard.getSize(), i);
        }
        for(GameViewRow row : rows) {
            addView(row);
        }
        refresh();
        //setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
    }
}
