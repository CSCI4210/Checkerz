package com.example.bikesh.checkerz.model;

public class Square {
    private Piece piece;
    private Position position; //Should never be updated
    private boolean empty;

    //TODO: add logic to the constructor to set the empty flag based on if there is a piece
    public Square(Position position, Piece startingPiece) {
        this.position = position;
        this.piece = startingPiece;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
