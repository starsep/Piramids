package com.starsep.piramids;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DatabaseManager extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Piramids.db";
    public static final String TABLE_ACTUAL_GAME = "actual_game";
    public static final String TABLE_GAMES = "games";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SIZE = "size";
    public static final String COLUMN_LEFT_HINTS = "left_hints";
    public static final String COLUMN_RIGHT_HINTS = "right_hints";
    public static final String COLUMN_UP_HINTS = "up_hints";
    public static final String COLUMN_DOWN_HINTS = "down_hints";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String gamesQuery = "CREATE TABLE " + TABLE_GAMES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SIZE + " INTEGER, " +
                COLUMN_LEFT_HINTS + " TEXT, " +
                COLUMN_RIGHT_HINTS + " TEXT, " +
                COLUMN_UP_HINTS + " TEXT, " +
                COLUMN_DOWN_HINTS + " TEXT " +
                ");";
        Log.d("com.starsep.piramids", gamesQuery);
        db.execSQL(gamesQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTUAL_GAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
        onCreate(db);
    }

    private String hintsToString(int[] hints) {
        String result = "";
        for(int hint : hints) {
            result += (char) hint;
        }
        return result;
    }

    public void addGame(GameBoard game) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SIZE, game.getSize());
        values.put(COLUMN_LEFT_HINTS, hintsToString(game.getLeftHints()));
        values.put(COLUMN_RIGHT_HINTS, hintsToString(game.getRightHints()));
        values.put(COLUMN_UP_HINTS, hintsToString(game.getUpHints()));
        values.put(COLUMN_DOWN_HINTS, hintsToString(game.getDownHints()));
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_GAMES, null, values);
        db.close();
    }
}
