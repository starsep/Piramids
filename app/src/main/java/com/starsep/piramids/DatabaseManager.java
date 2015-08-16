package com.starsep.piramids;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Piramids.db";
    public static final String TABLE_ACTUAL_GAMES = "actual_games";
    public static final String TABLE_GAMES = "games";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SIZE = "size";
    public static final String COLUMN_LEFT_HINTS = "left_hints";
    public static final String COLUMN_RIGHT_HINTS = "right_hints";
    public static final String COLUMN_UP_HINTS = "up_hints";
    public static final String COLUMN_DOWN_HINTS = "down_hints";
    public static final String COLUMN_BOARD = "down_board";
    public static final int GENERATED_GAMES = 5;
    private static DatabaseManager instance;

    private DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void initialize(Context context) {
        instance = new DatabaseManager(context);
    }

    public static DatabaseManager getInstance() {
        return instance;
    }

    private void createGamesTable(SQLiteDatabase db) {
        String gamesQuery = "CREATE TABLE " + TABLE_GAMES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SIZE + " INTEGER, " +
                COLUMN_LEFT_HINTS + " TEXT, " +
                COLUMN_RIGHT_HINTS + " TEXT, " +
                COLUMN_UP_HINTS + " TEXT, " +
                COLUMN_DOWN_HINTS + " TEXT " +
                ");";
        //Log.d("com.starsep.piramids", gamesQuery);
        db.execSQL(gamesQuery);

        GameGenerator gameGenerator = new GameGenerator(0);
        for (int i = GameBoard.MIN_SIZE; i <= GameBoard.MAX_SIZE; i++)
            for (int j = 0; j < GENERATED_GAMES; j++) {
                try {
                    addGame(gameGenerator.generate(i), db);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }

    private void createActualGamesTable(SQLiteDatabase db) {
        String actualGamesQuery = "CREATE TABLE " + TABLE_ACTUAL_GAMES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SIZE + " INTEGER, " +
                COLUMN_LEFT_HINTS + " TEXT, " +
                COLUMN_RIGHT_HINTS + " TEXT, " +
                COLUMN_UP_HINTS + " TEXT, " +
                COLUMN_DOWN_HINTS + " TEXT " +
                COLUMN_BOARD + " TEXT " +
                ");";
        //Log.d("com.starsep.piramids", actualGamesQuery);
        db.execSQL(actualGamesQuery);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createGamesTable(db);
        createActualGamesTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTUAL_GAMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
        onCreate(db);
    }

    private String hintsToString(int[] hints) {
        String result = "";
        for (int hint : hints) {
            result += (char) hint;
        }
        return result;
    }

    public void addGame(GameBoard game) {
        SQLiteDatabase db = getWritableDatabase();
        addGame(game, db);
        db.close();
    }

    public void addGame(GameBoard game, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SIZE, game.getSize());
        values.put(COLUMN_LEFT_HINTS, hintsToString(game.getLeftHints()));
        values.put(COLUMN_RIGHT_HINTS, hintsToString(game.getRightHints()));
        values.put(COLUMN_UP_HINTS, hintsToString(game.getUpHints()));
        values.put(COLUMN_DOWN_HINTS, hintsToString(game.getDownHints()));
        db.insert(TABLE_GAMES, null, values);
    }

    public List<GameBoard> getGames(int size) {
        List<GameBoard> result = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_GAMES + " WHERE SIZE = " + String.valueOf(size);
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        for (; !cursor.isAfterLast(); cursor.moveToNext()) {
            GameBoard gameBoard = new GameBoard(size);
            gameBoard.setLeftHints(cursor.getString(cursor.getColumnIndex(COLUMN_LEFT_HINTS)));
            gameBoard.setRightHints(cursor.getString(cursor.getColumnIndex(COLUMN_RIGHT_HINTS)));
            gameBoard.setUpHints(cursor.getString(cursor.getColumnIndex(COLUMN_UP_HINTS)));
            gameBoard.setDownHints(cursor.getString(cursor.getColumnIndex(COLUMN_DOWN_HINTS)));
            result.add(gameBoard);
        }
        cursor.close();
        db.close();
        return result;
    }

    public int getNumberOfGames(int size) {
        return getGames(size).size();
    }
}
