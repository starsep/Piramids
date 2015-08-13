package com.starsep.piramids;

public class GameBoard {
    private final int[][] tiles;

    public GameBoard(int size) {
        tiles = new int[size][];
        for (int i = 0; i < size; i++) {
            tiles[i] = new int[size];
        }
    }

    public int getSize() {
        return tiles.length;
    }

    public int getTile(int x, int y) {
        return tiles[x][y];
    }

    public void setTile(int x, int y, int value) {
        tiles[x][y] = value;
    }
}
