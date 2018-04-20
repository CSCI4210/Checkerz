package com.example.bikesh.checkerz.model;

/**
 * Created by Bikesh on 3/7/2018.
 */

public class Position {
    public int row;
    public int column;

    public Position(){
        row =0;
        column = 0;
    }

    public Position(int x, int y){
        row = x;
        column = y;
    }

    @Override
    public String toString() {
        return "" + String.valueOf(row) + String.valueOf(column);
    }
}
