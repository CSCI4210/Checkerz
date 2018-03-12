package com.example.bikesh.checkerz;

import java.util.ArrayList;
/**
 * Created by Bikesh on 3/7/2018.
 */

public class Piece {
    public boolean king;
    public boolean captured;
    public boolean red;
    public Position position;
    public ArrayList<Position> neighbors  = new ArrayList<Position>();
    public ArrayList<Position> moves  = new ArrayList<Position>();
    public Piece(int x, int y, boolean z){
        king = false;
        captured = false;
        red = z;
        position = new Position(x,y);
    }

    public int getX(){
        return  position.row;
    }
    public int getY(){
        return position.column;
    }
    public void setKing(boolean z){
        king = z;
    }
    //public ArrayList<Position> neighbors(){


//    }
  //  public ArrayList<Position> prospectiveMoves(){

    //}
}
