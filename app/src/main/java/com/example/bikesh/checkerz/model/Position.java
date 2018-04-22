package com.example.bikesh.checkerz.model;

import java.util.Objects;

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

    public int getX(){
        return row;
    }
    public int getY(){
        return column;
    }


    @Override
    public String toString() {
        return "" + String.valueOf(row) + String.valueOf(column);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row &&
                column == position.column;
    }

    @Override
    public int hashCode() {

        return Objects.hash(row, column);
    }
}
