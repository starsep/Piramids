package com.starsep.piramids;

public class GameBoard {
    private final int[][] tiles;
    private final int[] leftHints;
    private final int[] rightHints;
    private final int[] upHints;
    private final int[] downHints;

    public GameBoard(int size) {
        tiles = new int[size][];
        for (int i = 0; i < size; i++) {
            tiles[i] = new int[size];
        }
        leftHints = new int[size];
        rightHints = new int[size];
        upHints = new int[size];
        downHints = new int[size];
    }

    public int getSize() {
        return tiles.length;
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
}
