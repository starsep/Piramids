package com.starsep.piramids;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class LevelChooseActivity extends AppCompatActivity {

    private int difficulty;
    private LinearLayout mainLayout;

    public class LevelItem extends Button {
        private final int number;

        public int getNumber() {
            return number;
        }

        public LevelItem(int number, Context context) {
            super(context);
            this.number = number;
            setText(getResources().getString(R.string.level) + " " + String.valueOf(number + 1));
            setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
            setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), GameActivity.class);
                    intent.putExtra("difficulty", difficulty);
                    intent.putExtra("number", getNumber());
                    startActivity(intent);
                }
            });
        }
    }

    private void addLevels() {
        //for(int i = 3; i<=7;i++)
        //    for(int j=0;j<i;j++)
        //        DatabaseManager.getInstance().addGame(new GameBoard(i));
        int numberOfLevels = DatabaseManager.getInstance().getNumberOfGames(difficulty + 2);
        for(int i = 0; i < numberOfLevels; i++) {
            mainLayout.addView(new LevelItem(i, this));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_choose);

        mainLayout = (LinearLayout) findViewById(R.id.levelChooser);
        difficulty = getIntent().getIntExtra("difficulty", 0);

        //DEBUG
        try {
            DatabaseManager.getInstance().addGame(new GameGenerator(0).generate(difficulty + 2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //DEBUG

        addLevels();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_level_choose, menu);
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
}
