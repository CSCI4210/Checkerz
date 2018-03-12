package com.example.bikesh.checkerz;

import android.app.Fragment;
import android.media.Image;
import android.widget.ImageButton;
import java.util.ArrayList;
/**
 * Created by Bikesh on 3/7/2018.
 */

public class GameBoard{
    public ArrayList<Piece> redPiece = new ArrayList<Piece>();
    public ArrayList<Piece> blackPiece = new ArrayList<Piece>();

    public boolean redTurn;

    public GameBoard(){
        redPiece.add(new Piece(0,1,true));
        redPiece.add(new Piece(0,3,true));
        redPiece.add(new Piece(0,5, true));
        redPiece.add(new Piece(0,7, true));
        redPiece.add(new Piece(1,0, true));
        redPiece.add(new Piece(1,2, true));
        redPiece.add(new Piece(1,4, true));
        redPiece.add(new Piece(1,6, true));
        redPiece.add(new Piece(2,1, true));
        redPiece.add(new Piece(2,3, true));
        redPiece.add(new Piece(2,5, true));
        redPiece.add(new Piece(2,7, true));
        blackPiece.add(new Piece(5,0, false));
        blackPiece.add(new Piece(5,2, false));
        blackPiece.add(new Piece(5,4, false));
        blackPiece.add(new Piece(5,6, false));
        blackPiece.add(new Piece(6,1, false));
        blackPiece.add(new Piece(6,3, false));
        blackPiece.add(new Piece(6,5, false));
        blackPiece.add(new Piece(6,7, false));
        blackPiece.add(new Piece(7,0, false));
        blackPiece.add(new Piece(7,2, false));
        blackPiece.add(new Piece(7,4, false));
        blackPiece.add(new Piece(7,6, false));

    }

    public ArrayList<Piece> getReds(){
        return redPiece;
    }
    public ArrayList<Piece> getBlacks(){
        return blackPiece;
    }

}
