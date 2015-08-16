package com.starsep.piramids;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class GameBoard {
    public static final int MIN_SIZE = 3;
    public static final int MAX_SIZE = 7;
    private final int[][] tiles;
    private final int[] leftHints;
    private final int[] rightHints;
    private final int[] upHints;
    private final int[] downHints;
    private final int size;

    public GameBoard(int size) {
        this.size = size;
        tiles = new int[size][];
        for (int i = 0; i < size; i++) {
            tiles[i] = new int[size];
        }
        leftHints = new int[size];
        rightHints = new int[size];
        upHints = new int[size];
        downHints = new int[size];
    }

    public int[] getRow(int number) {
        int[] row = new int[size];
        for (int i = 0; i < size; i++)
            row[i] = tiles[number][i];
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
            leftHints[i] = generateLeftHint(row);
            rightHints[i] = generateRightHint(row);
        }
    }

    private void generateColumnHints() {
        for (int i = 0; i < size; i++) {
            int[] column = getColumn(i);
            upHints[i] = generateLeftHint(column);
            downHints[i] = generateRightHint(column);
        }
    }

    public void generateHints() {
        generateColumnHints();
        generateRowHints();
    }

    private boolean isLineValid(int[] values, int leftHint, int rightHint) {
        Arrays.sort(values);
        if(leftHint != 0 && generateLeftHint(values) != leftHint)
            return false;
        if(rightHint != 0 && generateRightHint(values) != rightHint)
            return false;
        for (int i = 1; i < values.length; i++)
            if (values[i] != 0 && values[i - 1] == values[i])
                return false;
        return true;
    }

    private boolean isRowValid(int number, boolean checkHints) {
        int[] row = getRow(number);
        if(checkHints)
            return isLineValid(row, leftHints[number], rightHints[number]);
        return isLineValid(row, 0, 0);
    }

    private boolean isColumnValid(int number, boolean checkHints) {
        int[] column = getColumn(number);
        if(checkHints)
            return isLineValid(column, upHints[number], downHints[number]);
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

    public int getTile(int x, int y) {
        return tiles[x][y];
    }

    public int[][] getTiles() {
        return tiles;
    }

    public int[] getLeftHints() {
        return leftHints;
    }

    public int[] getRightHints() {
        return rightHints;
    }

    public int[] getUpHints() {
        return upHints;
    }

    public int[] getDownHints() {
        return downHints;
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

    public void setLeftHints(int[] hints) {
        for (int i = 0; i < hints.length; i++)
            leftHints[i] = hints[i];
    }

    public void setLeftHints(String string) {
        setLeftHints(stringToHints(string));
    }

    public void setRightHints(int[] hints) {
        for (int i = 0; i < hints.length; i++)
            rightHints[i] = hints[i];
    }

    public void setRightHints(String string) {
        setRightHints(stringToHints(string));
    }

    public void setUpHints(int[] hints) {
        for (int i = 0; i < hints.length; i++)
            upHints[i] = hints[i];
    }

    public void setUpHints(String string) {
        setUpHints(stringToHints(string));
    }

    public void setDownHints(int[] hints) {
        for (int i = 0; i < hints.length; i++)
            downHints[i] = hints[i];
    }

    public void setDownHints(String string) {
        setDownHints(stringToHints(string));
    }

    private String debugVerticalHints(int[] source) {
        String result = "-";
        for (int i = 0; i < size; i++)
            result += " " + source[i];
        result += " -\n";
        return result;
    }

    private String debugLine(int number) {
        String result = String.valueOf(leftHints[number]) + " ";
        for (int i = 0; i < size; i++)
            result += String.valueOf(tiles[number][i]) + " ";
        result += String.valueOf(rightHints[number]) + "\n";
        return result;
    }

    public String debug() {
        String result = "Generated: \n";
        result += debugVerticalHints(upHints);
        for (int i = 0; i < size; i++)
            result += debugLine(i);
        result += debugVerticalHints(downHints);
        return result;
    }
}
