package com.starsep.piramids;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class GameActivity extends AppCompatActivity {
    private GameBoard gameBoard;
    private GameView gameView;
    private View lastView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameBoard = new GameBoard(6);
        gameView = (GameView) findViewById(R.id.gameView);
        gameView.setGameBoard(gameBoard);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        for (int i = 0; i <= gameBoard.getSize(); i++)
            menu.add(String.valueOf(i));
        lastView = v;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
      //  (Button)lastView.setInteger.valueOf(item.getTitle().toString());
        int x = (int)lastView.getX();
        int y = (int)lastView.getY();
        int value = Integer.valueOf(item.getTitle().toString());
        gameBoard.setTile(x, y, value);
        gameView.refresh();
        return true;
    }
}

