package com.starsep.piramids;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Piramids.db";
    public static final String TABLE_ACTUAL_GAMES = "actual_games";
    public static final String TABLE_GAMES = "games";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SIZE = "size";
    public static final String COLUMN_LEFT_HINTS = "left_hints";
    public static final String COLUMN_RIGHT_HINTS = "right_hints";
    public static final String COLUMN_UP_HINTS = "up_hints";
    public static final String COLUMN_DOWN_HINTS = "down_hints";
    public static final String COLUMN_BOARD = "board";
    public static final String COLUMN_CHOSENX = "chosen_x";
    public static final String COLUMN_CHOSENY = "chosen_y";
    public static final String COLUMN_ERRORS = "errors";
    public static final int GENERATED_GAMES = 5;
    private static DatabaseManager instance;

    private DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void initialize(Context context) {
        instance = new DatabaseManager(context);
    }

    public static DatabaseManager getInstance(Context context) {
        if(instance == null)
            initialize(context);
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
                COLUMN_DOWN_HINTS + " TEXT, " +
                COLUMN_BOARD + " TEXT, " +
                COLUMN_CHOSENX + " INTEGER, " +
                COLUMN_CHOSENY + " INTEGER, " +
                COLUMN_ERRORS + " TEXT " +
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

    /*public void addGame(GameBoard game) {
        SQLiteDatabase db = getWritableDatabase();
        addGame(game, db);
        db.close();
    }*/

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

    private String boardToString(int[][] board) {
        String result = "";
        for(int[] tab : board)
            for(int k : tab)
                result += (char) k;
        return result;
    }

    private void addActualGame(GameBoard gameBoard, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SIZE, gameBoard.getSize());
        values.put(COLUMN_LEFT_HINTS, hintsToString(gameBoard.getLeftHints()));
        values.put(COLUMN_RIGHT_HINTS, hintsToString(gameBoard.getRightHints()));
        values.put(COLUMN_UP_HINTS, hintsToString(gameBoard.getUpHints()));
        values.put(COLUMN_DOWN_HINTS, hintsToString(gameBoard.getDownHints()));
        values.put(COLUMN_BOARD, boardToString(gameBoard.getTiles()));
        values.put(COLUMN_CHOSENX, gameBoard.getChosenX());
        values.put(COLUMN_CHOSENY, gameBoard.getChosenY());
        values.put(COLUMN_ERRORS, boardToString(gameBoard.getErrorCounters()));
        db.insert(TABLE_ACTUAL_GAMES, null, values);
    }

    private void removeOldGames(SQLiteDatabase db) {
        //TODO: remove old games from actualGames (leave only the newest)
    }

    public void setActualGame(GameBoard gameBoard) {
        SQLiteDatabase db = getWritableDatabase();
        addActualGame(gameBoard, db);
        removeOldGames(db);
    }

    private void stringToBoard(GameBoard gameBoard, String board) {
        for(int i = 0; i < board.length(); i++)
            gameBoard.setTile(i / gameBoard.getSize(), i % gameBoard.getSize(), board.charAt(i));
    }

    private void stringToErrors(GameBoard gameBoard, String errors) {
        for(int i = 0; i < errors.length(); i++)
            gameBoard.setErrorCounter(i / gameBoard.getSize(), i % gameBoard.getSize(), errors.charAt(i));
    }

    public GameBoard getActualGame() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ACTUAL_GAMES
                + " WHERE 1=1 "
                + "ORDER BY " + COLUMN_ID + " DESC";
        Cursor cursor = db.rawQuery(query, null);
        if(!cursor.moveToFirst())
            return null;
        int size = cursor.getInt(cursor.getColumnIndex(COLUMN_SIZE));
        GameBoard result = new GameBoard(size);
        String board = cursor.getString(cursor.getColumnIndex(COLUMN_BOARD));
        stringToBoard(result, board);
        result.setLeftHints(cursor.getString(cursor.getColumnIndex(COLUMN_LEFT_HINTS)));
        result.setRightHints(cursor.getString(cursor.getColumnIndex(COLUMN_RIGHT_HINTS)));
        result.setUpHints(cursor.getString(cursor.getColumnIndex(COLUMN_UP_HINTS)));
        result.setDownHints(cursor.getString(cursor.getColumnIndex(COLUMN_RIGHT_HINTS)));
        int chosenX = cursor.getInt(cursor.getColumnIndex(COLUMN_CHOSENX));
        int chosenY = cursor.getInt(cursor.getColumnIndex(COLUMN_CHOSENY));
        result.setChosen(chosenX, chosenY);
        String errors = cursor.getString(cursor.getColumnIndex(COLUMN_ERRORS));
        stringToErrors(result, errors);
        cursor.close();
        db.close();
        return result;
    }

    public int getNumberOfGames(int size) {
        return getGames(size).size();
    }
}
