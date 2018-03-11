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
        redPiece.add(new Piece(0,1, PieceColor.RED));
        redPiece.add(new Piece(0,3, PieceColor.RED));
        redPiece.add(new Piece(0,5, PieceColor.RED));
        redPiece.add(new Piece(0,7, PieceColor.RED));
        redPiece.add(new Piece(1,0, PieceColor.RED));
        redPiece.add(new Piece(1,2, PieceColor.RED));
        redPiece.add(new Piece(1,4, PieceColor.RED));
        redPiece.add(new Piece(1,6, PieceColor.RED));
        redPiece.add(new Piece(2,1, PieceColor.RED));
        redPiece.add(new Piece(2,3, PieceColor.RED));
        redPiece.add(new Piece(2,5, PieceColor.RED));
        redPiece.add(new Piece(2,7, PieceColor.RED));
        blackPiece.add(new Piece(5,0, PieceColor.BLACK));
        blackPiece.add(new Piece(5,2, PieceColor.BLACK));
        blackPiece.add(new Piece(5,4, PieceColor.BLACK));
        blackPiece.add(new Piece(5,6, PieceColor.BLACK));
        blackPiece.add(new Piece(6,1, PieceColor.BLACK));
        blackPiece.add(new Piece(6,3, PieceColor.BLACK));
        blackPiece.add(new Piece(6,5, PieceColor.BLACK));
        blackPiece.add(new Piece(6,7, PieceColor.BLACK));
        blackPiece.add(new Piece(7,0, PieceColor.BLACK));
        blackPiece.add(new Piece(7,2, PieceColor.BLACK));
        blackPiece.add(new Piece(7,4, PieceColor.BLACK));
        blackPiece.add(new Piece(7,6, PieceColor.BLACK));

    }

    public ArrayList<Piece> getReds(){
        return redPiece;
    }
    public ArrayList<Piece> getBlacks(){
        return blackPiece;
    }

}
