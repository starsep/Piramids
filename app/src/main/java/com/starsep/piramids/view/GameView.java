package com.starsep.piramids.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.starsep.piramids.GameBoard;
import com.starsep.piramids.R;

public class GameView extends LinearLayout {

    private static final int COLOR_ERROR_VALUE = Color.RED;
    private static final int COLOR_SAME_VALUE = Color.BLUE;
    private static final int COLOR_ANOTHER_VALUE = Color.BLACK;

    private GameViewRow[] rows;
    private GameBoard gameBoard;
    private Button chosen;
    private int[][] errorCounter;

    private void colorCross(int x, int y) {
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].elements.length; j++) {
                Button element = rows[i].elements[j];
                boolean cross = (i == x || j == y) && !(i == x && j == y);
                element.setBackgroundResource(cross ? R.drawable.game_highlighted_button : R.drawable.game_button);
            }
        }
    }

    private void colorSameValues(int x, int y) {
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].elements.length; j++) {
                Button element = rows[i].elements[j];
                if (element.getCurrentTextColor() != COLOR_ERROR_VALUE) {
                    boolean sameValue = gameBoard.getTile(i, j) == gameBoard.getTile(x, y);
                    element.setTextColor(sameValue ? COLOR_SAME_VALUE : COLOR_ANOTHER_VALUE);
                }
            }
        }
    }

    private void colorChosen() {
        chosen.setBackgroundResource(R.drawable.game_chosen_button);
        if (chosen.getCurrentTextColor() != COLOR_ERROR_VALUE)
            chosen.setTextColor(COLOR_SAME_VALUE);
    }

    private void colorErrors(int x, int y) {
        for (int i = 0; i < rows.length; i++)
            rows[x].elements[i].setTextColor(errorCounter[x][i] > 0 ? COLOR_ERROR_VALUE : COLOR_ANOTHER_VALUE);
        for (int i = 0; i < rows.length; i++)
            rows[i].elements[y].setTextColor(errorCounter[i][y] > 0 ? COLOR_ERROR_VALUE : COLOR_ANOTHER_VALUE);
    }


    public void chooseButton(View view) {
        chosen = (Button) view;
        int x = (int) chosen.getX();
        int y = (int) chosen.getY();
        colorCross(x, y);
        colorSameValues(x, y);
        colorChosen();
        refresh();
    }

    private void updateErrorCounters(int x, int y, int oldValue, int value) {
        int[][] tiles = gameBoard.getTiles();
        //delete old errors
        if (oldValue != 0)
            for (int i = 0; i < tiles.length; i++) {
                if (tiles[x][i] == oldValue) //same row, used to be an error
                    errorCounter[x][i]--;
                if (tiles[i][y] == oldValue) //same column, used to be an error
                    errorCounter[i][y]--;
            }
        //add new errors
        if (value != 0)
            for (int i = 0; i < tiles.length; i++) {
                if (tiles[x][i] == value) //same row, new error
                    errorCounter[x][i]++;
                if (tiles[i][y] == value) //same column, new error
                    errorCounter[i][y]++;
            }
        //update chosen error counter
        errorCounter[x][y] = 0;
        if (value == 0)
            return;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[x][i] == value) //same row, same value => new error
                errorCounter[x][y]++;
            if (tiles[i][y] == value) //same column, same value => new error
                errorCounter[x][y]++;
        }
        //we added two unnecessary errors (because chosen has same column and row as itself)
        errorCounter[x][y] -= 2;
    }

    public void changeChosen(int value) {
        int x = (int) chosen.getX();
        int y = (int) chosen.getY();
        int oldValue = gameBoard.getTile(x, y);
        if (oldValue == value)
            return;
        gameBoard.setTile(x, y, value);
        refresh();
        updateErrorCounters(x, y, oldValue, value);
        colorErrors(x, y);
        chooseButton(chosen);
    }

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
    }

    public void refresh() {
        for (int x = 0; x < rows.length; x++)
            for (int y = 0; y < rows[x].elements.length; y++)
                if (gameBoard.getTile(x, y) != 0)
                    rows[x].elements[y].setText(String.valueOf(gameBoard.getTile(x, y)));
                else rows[x].elements[y].setText("");
    }

    public LinearLayout makeHorizontalHints(int[] source) {
        LinearLayout result = new LinearLayout(getContext());
        result.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        result.setOrientation(HORIZONTAL);
        result.addView(new HintView(getContext()));
        for (int hint : source)
            result.addView(new HintView(hint, getContext()));
        result.addView(new HintView(getContext()));
        return result;
    }

    public void setGameBoard(GameBoard gameBoard) {
        setOrientation(VERTICAL);
        errorCounter = new int[gameBoard.getSize()][];
        for (int i = 0; i < gameBoard.getSize(); i++)
            errorCounter[i] = new int[gameBoard.getSize()];
        this.gameBoard = gameBoard;
        rows = new GameViewRow[gameBoard.getSize()];
        for (int i = 0; i < rows.length; i++) {
            rows[i] = new GameViewRow(getContext(), gameBoard, i, this);
        }
        addView(makeHorizontalHints(gameBoard.getUpHints()));
        for (GameViewRow row : rows) {
            addView(row);
        }
        addView(makeHorizontalHints(gameBoard.getDownHints()));
        refresh();
    }

    public void numberClicked(int number) {
        if (number < 0 || number > gameBoard.getSize() || chosen == null)
            return;
        changeChosen(number);
    }
}
