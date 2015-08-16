package com.starsep.piramids;

import android.util.Log;

import java.util.Arrays;
import java.util.Random;

public class GameGenerator {
    private final Random random;

    public GameGenerator(long seed) {
        random = new Random(seed);
    }

    public class BadSizeException extends Exception {
    }

    private void shuffleRow(int[] row) {
        for (int i = row.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int tmp = row[index]; //swap
            row[index] = row[i];
            row[i] = tmp;
        }
    }

    private void generateRow(GameBoard gameBoard, int nr) {
        int[] row = new int[gameBoard.getSize()];
        for (int i = 0; i < row.length; i++)
            row[i] = i + 1;
        do {
            shuffleRow(row);
            for (int i = 0; i < gameBoard.getSize(); i++)
                gameBoard.setTile(nr, i, row[i]);
        } while (!gameBoard.isValid(false));
    }

    private int findMissing(int[] values) {
        Arrays.sort(values);
        for (int i = 1; i < values.length; i++) {
            if (values[i] != i)
                return i;
        }
        return values.length;
    }

    private void fillLastRow(GameBoard gameBoard) {
        for (int i = 0; i < gameBoard.getSize(); i++) {
            gameBoard.setTile(gameBoard.getSize() - 1, i, findMissing(gameBoard.getColumn(i)));
        }
    }

    private GameBoard generateFullGameBoard(int size) throws Exception {
        if (size < GameBoard.MIN_SIZE || size > GameBoard.MAX_SIZE) {
            throw new BadSizeException();
        }
        GameBoard gameBoard = new GameBoard(size);
        for (int i = 0; i < size - 1; i++) {
            generateRow(gameBoard, i);
        }
        fillLastRow(gameBoard);
        return gameBoard;
    }

    private void removeUnnecessaryHints(GameBoard gameBoard) {
        int side, number;
        do {
            side = random.nextInt(4);
            number = random.nextInt(gameBoard.getSize());
        } while (gameBoard.getHints()[side][number] == 0);
        int value = gameBoard.getHints()[side][number];
        gameBoard.getHints()[side][number] = 0;
        if (!gameBoard.hasOnlyOneSolution()) {
            gameBoard.getHints()[side][number] = value;
            return;
        }
        removeUnnecessaryHints(gameBoard);
    }

    public GameBoard generate(int size) throws Exception {
        GameBoard gameBoard = generateFullGameBoard(size);
        gameBoard.generateHints();
        removeUnnecessaryHints(gameBoard);
        //Log.d("gameGenerator: ", gameBoard.debug());
        return gameBoard;
    }
}
