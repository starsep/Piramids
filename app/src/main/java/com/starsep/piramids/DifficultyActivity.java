package com.starsep.piramids;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class DifficultyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_difficulty, menu);
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

    public void chooseDifficulty(View view) {
        Intent intent = new Intent(this, LevelChooseActivity.class);
        int difficulty;
        switch (view.getId()) {
            case R.id.button_level1:
                difficulty = 1;
                break;
            case R.id.button_level2:
                difficulty = 2;
                break;
            case R.id.button_level3:
                difficulty = 3;
                break;
            case R.id.button_level4:
                difficulty = 4;
                break;
            case R.id.button_level5:
                difficulty = 5;
                break;
            default:
                difficulty = 0;
                break;
        }
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }
}
