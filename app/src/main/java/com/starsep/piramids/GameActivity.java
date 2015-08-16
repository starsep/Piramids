package com.starsep.piramids;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.starsep.piramids.view.GameView;

public class GameActivity extends Activity {
    private GameBoard gameBoard;
    private GameView gameView;
    private View lastView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Toast.makeText(this, "Diff: " + String.valueOf(difficulty) + " Level: " + String.valueOf(level), Toast.LENGTH_LONG).show();

        gameBoard = DatabaseManager.getInstance(this).getActualGame();
        gameView = (GameView) findViewById(R.id.gameView);
        gameView.setGameBoard(gameBoard);
        int x = gameBoard.getChosenX();
        int y = gameBoard.getChosenY();
        gameView.chooseButton(gameView.getRows()[x].getElements()[y]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        gameView.chooseButton(v);
        super.onCreateContextMenu(menu, v, menuInfo);
        for (int i = 0; i <= gameBoard.getSize(); i++)
            menu.add(String.valueOf(i));
        lastView = v;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int value = Integer.valueOf(item.getTitle().toString());
        gameView.chooseButton(lastView);
        gameView.changeChosen(value);
        return true;
    }

    public void numberButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.button0:
                gameView.numberClicked(0);
                break;
            case R.id.button1:
                gameView.numberClicked(1);
                break;
            case R.id.button2:
                gameView.numberClicked(2);
                break;
            case R.id.button3:
                gameView.numberClicked(3);
                break;
            case R.id.button4:
                gameView.numberClicked(4);
                break;
            case R.id.button5:
                gameView.numberClicked(5);
                break;
            case R.id.button6:
                gameView.numberClicked(6);
                break;
            case R.id.button7:
                gameView.numberClicked(7);
                break;
            default:
        }
    }
}

