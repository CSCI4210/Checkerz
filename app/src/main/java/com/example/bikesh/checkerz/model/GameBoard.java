package com.example.bikesh.checkerz.model;

import java.util.ArrayList;
/**
 * Created by Bikesh on 3/7/2018.
 */

public class GameBoard{

    public Square[][] grid = new Square[8][8];

    // Constructor for the starting configuration of the board
    public GameBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i <= 2) {
                    if ((i + j) % 2 == 1) {
                        grid[i][j] = new Square(
                                new Position(i,j),
                                new Piece(PieceColor.RED));
                    }
                } else if (i >= 5) {
                    if ((i + j) % 2 == 1) {
                        grid[i][j] = new Square(
                                new Position(i,j),
                                new Piece(PieceColor.BLACK));
                    }
                } else {
                    grid[i][j] = new Square(
                            new Position(i,j),
                            null);
                }
            }
        }
    }

    /**
     * Moves a piece currently at one position to a different position as the
     * result of a Valid move. There must be a piece at that current position
     * on the Board.
     * @param currentPos the row and column of the piece to be moved
     * @param newPos the row and column of the desired new position
     * @return a GameBoard where the piece is located at its new position
     */
    public GameBoard movePiece (Position currentPos, Position newPos) {
        return null;
    }
}
