package com.example.bikesh.checkerz;

import java.util.ArrayList;
/**
 * Created by Bikesh on 3/7/2018.
 */

public class Piece {
    public static int totalPieces;
    public boolean king;
    public boolean captured;
    public PieceColor color;
    public Position position;
    public ArrayList<Position> neighbors  = new ArrayList<Position>();
    public ArrayList<Position> moves  = new ArrayList<Position>();
    public Piece(int x, int y, PieceColor color){
        king = false;
        captured = false;
        color = color;
        position = new Position(x,y);
        totalPieces++;
    }

    public int getX(){
        return  this.position.row;
    }
    public int getY(){
        return this.position.column;
    }

    public void setPosition(Position newPos){
        this.position = newPos;
    }
    public void setX(int x){this.position.row = x;}

    public void setY(int y){this.position.column = y;}

    public void setKing(){
        king = true;
    }

    public PieceColor getColor(){
        return this.color;
    }

    public ArrayList<Position> findNeighbors(){
        for (int i = 0; i < totalPieces; i++){

        }
        if (this.getX()+1>7 && this.getY()+1 > 7) neighbors.add(new Position(getX()+1, getY()+1));
        if (this.getX()+1>7 && this.getY()-1 < 0) neighbors.add(new Position(getX()+1, getY()-1));
        if (this.getX()-1<0 && this.getY()+1 > 7) neighbors.add(new Position(getX()-1, getY()+1));
        if (this.getX()-1<0 && this.getY()-1 < 0) neighbors.add(new Position(getX()-1, getY()-1));
    return neighbors;

    }
  //  public ArrayList<Position> prospectiveMoves(){

    //}
}
