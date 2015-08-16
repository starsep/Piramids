package com.starsep.piramids;

import java.util.Arrays;

public class GameBoard {
    public static final int MIN_SIZE = 3;
    public static final int MAX_SIZE = 7;
    private final int[][] tiles;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;
    private final int[][] hints;
    private final int size;
    private int chosenX;
    private int chosenY;
    private int[][] errorCounter;

    public int[][] getHints() {
        return hints;
    }

    /*public void setHints(int side, int number, int value) {
        hints[side][number] = value;
    }

    public int getErrorCounter(int x, int y) {
        return errorCounter[x][y];

    }*/

    public int[][] getErrorCounters() {
        return errorCounter;
    }

    public void setErrorCounter(int x, int y, int value) {
        this.errorCounter[x][y] = value;
    }

    /*public void setErrorCounter(int[][] errorCounter) {
        this.errorCounter = errorCounter;
    }*/

    public GameBoard(int size) {
        this.size = size;
        tiles = new int[size][];
        for (int i = 0; i < size; i++) {
            tiles[i] = new int[size];
        }
        errorCounter = new int[size][];
        for (int i = 0; i < size; i++) {
            errorCounter[i] = new int[size];
        }
        hints = new int[4][];
        for (int i = 0; i < hints.length; i++)
            hints[i] = new int[size];
    }

    public int[] getRow(int number) {
        int[] row = new int[size];
        System.arraycopy(tiles[number], 0, row, 0, size);
        return row;
    }

    public int[] getColumn(int number) {
        int[] column = new int[size];
        for (int i = 0; i < size; i++)
            column[i] = tiles[i][number];
        return column;
    }

    private int generateLeftHint(int[] line) {
        int result = 0;
        int max = line[0];
        for (int i = 0; i < size; i++) {
            max = Math.max(max, line[i]);
            if (max == line[i])
                result++;
        }
        return result;
    }

    private int generateRightHint(int[] line) {
        for (int i = 0; i < line.length / 2; i++) {
            int tmp = line[i];
            line[i] = line[line.length - 1 - i];
            line[line.length - 1 - i] = tmp;
        }
        return generateLeftHint(line);
    }

    private void generateRowHints() {
        for (int i = 0; i < size; i++) {
            int[] row = getRow(i);
            hints[LEFT][i] = generateLeftHint(row);
            hints[RIGHT][i] = generateRightHint(row);
        }
    }

    private void generateColumnHints() {
        for (int i = 0; i < size; i++) {
            int[] column = getColumn(i);
            hints[UP][i] = generateLeftHint(column);
            hints[DOWN][i] = generateRightHint(column);
        }
    }

    public void generateHints() {
        generateColumnHints();
        generateRowHints();
    }

    private boolean isLineValid(int[] values, int leftHint, int rightHint) {
        Arrays.sort(values);
        if (leftHint != 0 && generateLeftHint(values) != leftHint)
            return false;
        if (rightHint != 0 && generateRightHint(values) != rightHint)
            return false;
        for (int i = 1; i < values.length; i++)
            if (values[i] != 0 && values[i - 1] == values[i])
                return false;
        return true;
    }

    private boolean isRowValid(int number, boolean checkHints) {
        int[] row = getRow(number);
        if (checkHints)
            return isLineValid(row, hints[LEFT][number], hints[RIGHT][number]);
        return isLineValid(row, 0, 0);
    }

    private boolean isColumnValid(int number, boolean checkHints) {
        int[] column = getColumn(number);
        if (checkHints)
            return isLineValid(column, hints[UP][number], hints[DOWN][number]);
        return isLineValid(column, 0, 0);
    }

    public boolean isValid(boolean checkHints) {
        for (int i = 0; i < size; i++)
            if (!isRowValid(i, checkHints))
                return false;
        for (int i = 0; i < size; i++)
            if (!isColumnValid(i, checkHints))
                return false;
        return true;
    }

    public int getSize() {
        return size;
    }

    public int getChosenX() {
        return chosenX;
    }

    public int getChosenY() {
        return chosenY;
    }

    public int getTile(int x, int y) {
        return tiles[x][y];
    }

    public int[][] getTiles() {
        return tiles;
    }

    public int[] getLeftHints() {
        return hints[LEFT];
    }

    public int[] getRightHints() {
        return hints[RIGHT];
    }

    public int[] getUpHints() {
        return hints[UP];
    }

    public int[] getDownHints() {
        return hints[DOWN];
    }

    public void setTile(int x, int y, int value) {
        tiles[x][y] = value;
    }

    private int[] stringToHints(String string) {
        int[] result = new int[string.length()];
        for (int i = 0; i < string.length(); i++) {
            result[i] = (int) string.charAt(i);
        }
        return result;
    }

    private void setHintsSide(int side, int[] newHints) {
        System.arraycopy(newHints, 0, hints[side], 0, newHints.length);
    }

    public void setLeftHints(int[] newHints) {
        setHintsSide(LEFT, newHints);
    }

    public void setLeftHints(String string) {
        setLeftHints(stringToHints(string));
    }

    public void setRightHints(int[] newHints) {
        setHintsSide(RIGHT, newHints);
    }

    public void setRightHints(String string) {
        setRightHints(stringToHints(string));
    }

    public void setUpHints(int[] newHints) {
        setHintsSide(UP, newHints);
    }

    public void setUpHints(String string) {
        setUpHints(stringToHints(string));
    }


    public void setDownHints(int[] newHints) {
        setHintsSide(DOWN, newHints);
    }

    public void setDownHints(String string) {
        setDownHints(stringToHints(string));
    }

    /*private String debugVerticalHints(final int[] source) {
        String result = "-";
        for (int i = 0; i < size; i++)
            result += " " + (char) source[i];
        result += " -\n";
        return result;
    }

    private String debugLine(int number) {
        String result = String.valueOf(hints[LEFT][number]) + " ";
        for (int i = 0; i < size; i++)
            result += String.valueOf(tiles[number][i]) + " ";
        result += String.valueOf(hints[RIGHT][number]) + "\n";
        return result;
    }

    public String debug() {
        String result = "Generated: \n";
        result += debugVerticalHints(hints[UP]);
        for (int i = 0; i < size; i++)
            result += debugLine(i);
        result += debugVerticalHints(hints[DOWN]);
        return result;
    }*/

    public void setChosen(int x, int y) {
        chosenX = x;
        chosenY = y;
    }

    public boolean hasOnlyOneSolution() {
        //TODO
        return false;
    }

}
