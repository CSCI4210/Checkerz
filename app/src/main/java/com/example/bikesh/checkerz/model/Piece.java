package com.example.bikesh.checkerz.model;

import java.util.ArrayList;
/**
 * Created by Bikesh on 3/7/2018.
 */

public class Piece {
    private boolean king;
    private boolean captured;
    public PieceColor color;

    public ArrayList<Position> neighbors  = new ArrayList<Position>();
    public ArrayList<Position> moves  = new ArrayList<Position>();

    public Piece(PieceColor color){
        this.king = false;
        this.captured = false;
        this.color = color;
    }

    public boolean isKing() {
        return king;
    }

    public void setKing(boolean king) {
        this.king = king;
    }

    public boolean isCaptured() {
        return captured;
    }

    public void setCaptured(boolean captured) {
        this.captured = captured;
    }
}
