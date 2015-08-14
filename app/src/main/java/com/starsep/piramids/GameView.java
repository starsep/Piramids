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
import android.widget.TextView;

public class GameView extends LinearLayout {

    public class HintView extends TextView {
        public HintView(int value, Context context) {
            super(context);
            setText(value == 0 ? "" : String.valueOf(value));
            setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        }
    }

    public class GameViewRow extends LinearLayout {

        public class GameViewElement extends Button {
            private int x;
            private int y;
            public GameViewElement(Context context, int x, int y) {
                super(context);
                this.x = x;
                this.y = y;
                ((Activity)getContext()).registerForContextMenu(this);
                setBackgroundResource(R.drawable.game_button);
                setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                setOrientation(VERTICAL);
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

        public GameViewElement[] elements;

        public GameViewRow(Context context, int size, int x) {
            super(context);
            elements = new GameViewElement[size];
            addView(new HintView(gameBoard.getLeftHints()[x], getContext()));
            for(int i = 0; i < size; i++) {
                elements[i] = new GameViewElement(getContext(), x, i);
                addView(elements[i]);
            }
            addView(new HintView(gameBoard.getRightHints()[x], getContext()));
            setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
            setOrientation(HORIZONTAL);
        }
    }

    private GameViewRow[] rows;
    private GameBoard gameBoard;

    public GameView(Context context) {
        super(context);
        int minSize = Math.min(getWidth(), getHeight());
        setLayoutParams(new LinearLayout.LayoutParams(minSize, minSize, 0.0f));
        setOrientation(VERTICAL);
    }

    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOrientation(VERTICAL);
    }

    public GameView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        setOrientation(VERTICAL);
    }

    public void refresh() {
        for(int x =  0; x < rows.length; x++)
            for(int y = 0; y < rows[x].elements.length; y++)
                if(gameBoard.getTile(x, y) != 0)
                    rows[x].elements[y].setText(String.valueOf(gameBoard.getTile(x, y)));
                else rows[x].elements[y].setText("");
    }

    public LinearLayout makeHorizontalHints(int[] source) {
        LinearLayout result = new LinearLayout(getContext());
        result.setOrientation(HORIZONTAL);
        for(int hint : source)
            result.addView(new HintView(hint, getContext()));
        return result;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        rows = new GameViewRow[gameBoard.getSize()];
        for(int i = 0; i < rows.length; i++) {
            rows[i] = new GameViewRow(getContext(), gameBoard.getSize(), i);
        }
        addView(makeHorizontalHints(gameBoard.getUpHints()));
        for(GameViewRow row : rows) {
            addView(row);
        }
        addView(makeHorizontalHints(gameBoard.getDownHints()));
        refresh();
    }
}
