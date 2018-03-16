package com.example.bikesh.checkerz;

import com.example.bikesh.checkerz.Piece;

/**
 * Created by Bikesh on 3/08/2018.
 */

public class Location {

    public Piece contained;
    public int i;
    public int j;


    public Location(int i, int j){
        this.i = i;
        this.j = j;
        this.contained = null;
    }

    public int getX(){
        return this.i;
    }

    public int getY(){
        return this.j;
    }

    public Position getPosition(){
        return new Position(i,j);
    }

    public Piece getPiece(){
        return contained;
    }
    public  boolean containsPeice(){
        if (this.contained == null) return false;
        else return true;
    }
    public void setPiece(Piece piece){
        this.contained = piece;
        piece.setPosition(this.getPosition());
        if (piece.getColor() == PieceColor.RED && this.i == 7) piece.setKing();
        if (piece.getColor() == PieceColor.BLACK && this.i == 0) piece.setKing();
    }

    public void changeLoc(Location newLoc){
        newLoc.setPiece(this.contained);
        this.contained.setPosition(new Position(this.i, this.j));
        this.contained = null;

    }
}
