package com.example.bikesh.checkerz.model;

import android.util.Log;

import java.util.HashSet;

/**
 * Created by Bikesh on 3/7/2018.
 */

public class GameBoard{

    private Square[][] grid = new Square[8][8];

    //Used for counting pieces faster
    private HashSet<Piece> redPieces = new HashSet<>();
    private HashSet<Piece> blackPieces = new HashSet<>();

    /**
     * Constructor for creating the initial configuration of the game board. All other game
     * boards will result from modifying an existing game board.
     */
    public GameBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i <= 2) {   // The first three rows
                    // If the sum of the indices is odd, the square gets a red piece
                    if ((i + j) % 2 == 1) {
                        Piece redPiece = new Piece(PieceColor.RED);
                        redPiece.setPosition(new Position(i,j));
                        grid[i][j] = new Square( new Position(i,j), redPiece );
                        redPieces.add(redPiece);
                    } else { // The square does not get a piece
                        grid[i][j] = new Square( new Position(i,j), null );
                    }
                } else if (i >= 5) {    // The last three rows
                    // If the sum of the indices is odd, the square gets a black piece
                    if ((i + j) % 2 == 1) {
                        Piece blackPiece = new Piece(PieceColor.BLACK);
                        blackPiece.setPosition(new Position(i,j));
                        grid[i][j] = new Square( new Position(i,j), blackPiece );
                        blackPieces.add(blackPiece);
                    } else { // The square does not get a piece
                        grid[i][j] = new Square( new Position(i,j), null );
                    }
                } else { // The remaining squares in the middle rows get no pieces
                    grid[i][j] = new Square( new Position(i,j), null );
                }
            }
        }
    }

    private GameBoard(Square[][] squares, HashSet<Piece> reds, HashSet<Piece> blacks) {
        this.grid = squares;
        this.redPieces = reds;
        this.blackPieces = blacks;
    }

    public GameBoard cloneBoard() {
        Square[][] gridClone = new Square[8][8];
        HashSet<Piece> reds = new HashSet<>();
        HashSet<Piece> blacks = new HashSet<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square squareToBeCopied = this.grid[i][j];
                Square copiedSquare = null;
                if (squareToBeCopied.isEmpty()){
                    copiedSquare = new Square(squareToBeCopied.getPosition(), null);
                } else {
                    Piece pieceToBeCopied = squareToBeCopied.getPiece();
                    Piece copiedPiece = new Piece(pieceToBeCopied.color);
                    copiedPiece.setPosition(pieceToBeCopied.getPosition());
                    copiedPiece.setCaptured(pieceToBeCopied.isCaptured());
                    copiedPiece.setKing(pieceToBeCopied.isKing());
                    copiedSquare = new Square(squareToBeCopied.getPosition(), copiedPiece);
                    if (copiedPiece.color == PieceColor.BLACK)
                        blacks.add(copiedPiece);
                    else
                        reds.add(copiedPiece);
                }
                gridClone[i][j] = copiedSquare;
            }
        }

        return new GameBoard(gridClone, reds, blacks);
    }

    /**
     * Moves a piece currently at one position to a different position as the
     * result of a Valid move. There must be a piece at that current position
     * on the Board.
     * @param currentPosition the row and column of the piece to be moved
     * @param newPosition the row and column of the desired new position
     * @return a GameBoard where the piece is located at its new position
     * @throws IllegalArgumentException
     */
    public GameBoard movePiece (Position currentPosition, Position newPosition)
            throws IllegalArgumentException {
        int cRow = currentPosition.row;
        int cCol = currentPosition.column;
        int nRow = newPosition.row;
        int nCol = newPosition.column;
        /* Most of the move validation should be handled in the CALLER */

        // Get piece at current position
        Piece pieceToMove = grid[cRow][cCol].getPiece();
        if(pieceToMove == null)
            throw new IllegalArgumentException("No piece exists at specified currentPosition");

        // Remove piece from current position
        grid[cRow][cCol].setPiece(null);

        // Move piece to new position
        if (!grid[nRow][nCol].isEmpty())
            throw new IllegalArgumentException("Already a piece at specified newPosition");
        pieceToMove.setPosition(new Position(nRow, nCol));
        grid[nRow][nCol].setPiece(pieceToMove);
        return this;
    }

    public Square[][] getGrid() {
        return grid;
    }

    public int numberOfBlackPieces() {
        return blackPieces.size();
    }

    public int numberOfRedPieces() {
        return redPieces.size();
    }
    public void removePiece(Piece x){

        if (x.color == PieceColor.RED) {
            redPieces.remove(x);
//            String s = Integer.toString(redPieces.size());
//            Log.d("ready", s);
        }
        else {
            blackPieces.remove(x);
        }
        for(int i = 0; i<8;i++){
            for (int j=0; j < 8; j++){

                if (grid[i][j].getPiece() == x) grid[i][j] = new Square( new Position(i,j), null );
            }
        }
    }
    public Position getMid(Position oldPos, Position newPos){

        return new Position((oldPos.getX()+newPos.getX())/2, (oldPos.getY()+newPos.getY())/2);
    }

    /**
     * This method will be used to determine the moves that are available to a selected piece.
     *
     * @param positionOfPiece the coordinates of the Piece whose moves are being considered
     * @return A collection of Positions representing the locations this piece could move
     */
    public HashSet<Position> getAvailableMoves (Position positionOfPiece) {
        //TODO: Implement this method. Will be used to display available moves in the View
        int cRow = positionOfPiece.row;
        int cCol = positionOfPiece.column;
        Piece pieceToMove = grid[cRow][cCol].getPiece();

        HashSet<Position> neighbors = new HashSet<Position>();

        if (pieceToMove.isKing() == true){
            if ((cRow+1 < 8) && (cCol+1 < 8)){
                if (grid[cRow+1][cCol+1].isEmpty()) {
                    neighbors.add(grid[cRow + 1][cCol + 1].getPosition());
                }
                else{
                    if (cRow+2<8 && cCol+2<8){
                        if (grid[cRow+2][cCol+2].isEmpty()) {
                            if (grid[cRow + 1][cCol + 1].getPiece().color != pieceToMove.color) {
                                neighbors.add(grid[cRow + 2][cCol + 2].getPosition());
                            }
                        }
                    }
                }
            }


            if ((cRow-1 > -1) && (cCol-1 > -1)){
                if (grid[cRow-1][cCol-1].isEmpty()) {
                    neighbors.add(grid[cRow - 1][cCol - 1].getPosition());
                }
                else{
                    if (cRow-2>-1 && cCol-2>-1){
                        if (grid[cRow-2][cCol-2].isEmpty()) {
                            if (grid[cRow - 1][cCol - 1].getPiece().color != pieceToMove.color) {
                                neighbors.add(grid[cRow - 2][cCol - 2].getPosition());
                            }
                        }
                    }
                }
            }

            if ((cRow+1 < 8) && (cCol-1 > -1)){
                if (grid[cRow+1][cCol-1].isEmpty()) {
                    neighbors.add(grid[cRow + 1][cCol - 1].getPosition());
                }
                else{
                    if (cRow+2<8 && cCol-2>-1){
                        if (grid[cRow+2][cCol-2].isEmpty()) {
                            if (grid[cRow + 1][cCol - 1].getPiece().color != pieceToMove.color) {
                                neighbors.add(grid[cRow + 2][cCol - 2].getPosition());
                            }
                        }
                    }
                }
            }

            if ((cRow-1 < 8) && (cCol+1 < 8)){
                if (grid[cRow-1][cCol+1].isEmpty()) {
                    neighbors.add(grid[cRow - 1][cCol + 1].getPosition());
                }
                else{
                    if (cRow-2>-1&& cCol+2<8){
                        if (grid[cRow-2][cCol+2].isEmpty()) {
                            if (grid[cRow - 1][cCol + 1].getPiece().color != pieceToMove.color) {
                                neighbors.add(grid[cRow - 2][cCol + 2].getPosition());
                            }
                        }
                    }
                }
            }
        }

       else if (pieceToMove.isKing() == false){
            if (pieceToMove.color == PieceColor.RED){
                if ((cRow+1 < 8) && (cCol+1 < 8)){
                    if (grid[cRow+1][cCol+1].isEmpty()) {
                        neighbors.add(grid[cRow + 1][cCol + 1].getPosition());
                    }
                    else{
                        if (cRow+2<8 && cCol+2<8){
                            if (grid[cRow+2][cCol+2].isEmpty()) {
                                if (grid[cRow + 1][cCol + 1].getPiece().color != pieceToMove.color) {
                                    neighbors.add(grid[cRow + 2][cCol + 2].getPosition());
                                }
                            }
                        }
                    }
                }

                if ((cRow+1 < 8) && (cCol-1 > -1)){
                    if (grid[cRow+1][cCol-1].isEmpty()) {
                        neighbors.add(grid[cRow + 1][cCol - 1].getPosition());
                    }
                    else{
                        if (cRow+2<8 && cCol-2>-1){
                            if (grid[cRow+2][cCol-2].isEmpty()) {
                                if (grid[cRow + 1][cCol - 1].getPiece().color != pieceToMove.color) {
                                    neighbors.add(grid[cRow + 2][cCol - 2].getPosition());
                                }
                            }
                        }
                    }
                }

            }

            else if (pieceToMove.color == PieceColor.BLACK){

                if ((cRow-1 > -1) && (cCol-1 > -1)){
                    if (grid[cRow-1][cCol-1].isEmpty()) {
                        neighbors.add(grid[cRow - 1][cCol - 1].getPosition());
                    }
                    else{
                        if (cRow-2>-1 && cCol-2>-1){
                            if (grid[cRow-2][cCol-2].isEmpty()) {
                                if (grid[cRow - 1][cCol - 1].getPiece().color != pieceToMove.color) {
                                    neighbors.add(grid[cRow - 2][cCol - 2].getPosition());
                                }
                            }
                        }
                    }
                }

                if ((cRow-1 < 8) && (cCol+1 < 8)){
                    //TODO: Fix ArrayIndexOutOfBoundsException here (length = 4, index = -1)
                    if (grid[cRow-1][cCol+1].isEmpty()) {
                        neighbors.add(grid[cRow - 1][cCol + 1].getPosition());
                    }
                    else{
                        if (cRow-2>-1&& cCol+2<8){
                            if (grid[cRow-2][cCol+2].isEmpty()) {
                                if (grid[cRow - 1][cCol + 1].getPiece().color != pieceToMove.color) {
                                    neighbors.add(grid[cRow - 2][cCol + 2].getPosition());
                                }
                            }
                        }
                    }
                }


            }
        }
        return neighbors;
    }

    public HashSet<Piece> getBlackPieces() {
        return blackPieces;
    }

    public HashSet<Piece> getRedPieces() {
        return redPieces;
    }
}
