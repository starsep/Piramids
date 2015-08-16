package com.starsep.piramids.view;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.starsep.piramids.GameBoard;

public class GameViewRow extends LinearLayout {

    GameBoard gameBoard;
    GameViewElement[] elements;

    public GameViewRow(Context context, GameBoard gameBoard, int x, GameView gameView) {
        super(context);
        this.gameBoard = gameBoard;
        elements = new GameViewElement[gameBoard.getSize()];
        addView(new HintView(gameBoard.getLeftHints()[x], getContext()));
        for (int i = 0; i < gameBoard.getSize(); i++) {
            elements[i] = new GameViewElement(getContext(), x, i, gameView);
            addView(elements[i]);
        }
        addView(new HintView(gameBoard.getRightHints()[x], getContext()));
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        setOrientation(HORIZONTAL);
    }
}