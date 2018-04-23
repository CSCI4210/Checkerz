package com.example.bikesh.checkerz.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class GameBoardTest {

    // Test fixtures
    private GameBoard gameBoard;

    private Piece pieceBeingMoved;
    private Square currentSquareBeforeMove;
    private Square currentSquareAfterMove;
    private Square desiredSquareBeforeMove;
    private Square desiredSquareAfterMove;
    private Square square;


    @Before
    public void setUp() throws Exception {
        // Creates a new game board with pieces in their initial positions
        this.gameBoard = new GameBoard();
        this.pieceBeingMoved = null;
        this.currentSquareBeforeMove = null;
        this.currentSquareAfterMove = null;
        this.desiredSquareBeforeMove = null;
        this.desiredSquareAfterMove = null;
        this.square = null;

    }

    @Test
    public void movePiece_HappyPath() {

        GameBoard gameBoardAfterMove = null;
        Piece movedPiece = null;

        // Before Move
        currentSquareBeforeMove = gameBoard.getGrid()[2][3];
        assertFalse(currentSquareBeforeMove.isEmpty());
        pieceBeingMoved = currentSquareBeforeMove.getPiece();

        desiredSquareBeforeMove = gameBoard.getGrid()[3][4];
        assertTrue(desiredSquareBeforeMove.isEmpty());

        // Move
        gameBoardAfterMove = gameBoard.movePiece(
                currentSquareBeforeMove.getPosition(),
                desiredSquareBeforeMove.getPosition());

        // After Move
        currentSquareAfterMove = gameBoardAfterMove.getGrid()[2][3];
        assertTrue(currentSquareAfterMove.isEmpty());

        desiredSquareAfterMove = gameBoardAfterMove.getGrid()[3][4];
        assertFalse(desiredSquareAfterMove.isEmpty());
        movedPiece = desiredSquareAfterMove.getPiece();

        assertTrue(pieceBeingMoved.equals(movedPiece));
    }

    @Test(expected = IllegalArgumentException.class)
    public void movePiece_NoPieceAtLocationBeingMovedFrom() {

        GameBoard gameBoardAfterMove = null;
        Piece movedPiece = null;

        // Before Move
        currentSquareBeforeMove = gameBoard.getGrid()[3][0];
        assertTrue(currentSquareBeforeMove.isEmpty());
        pieceBeingMoved = currentSquareBeforeMove.getPiece();

        desiredSquareBeforeMove = gameBoard.getGrid()[4][1];
        assertTrue(desiredSquareBeforeMove.isEmpty());

        // Move
        gameBoardAfterMove = gameBoard.movePiece(
                currentSquareBeforeMove.getPosition(),
                desiredSquareBeforeMove.getPosition());
    }

    @Test(expected = IllegalArgumentException.class)
    public void movePiece_PieceAlreadyAtLocationBeingMovedTo() {
        GameBoard gameBoardAfterMove = null;
        Piece movedPiece = null;

        // Before Move
        currentSquareBeforeMove = gameBoard.getGrid()[1][4];
        assertFalse(currentSquareBeforeMove.isEmpty());
        pieceBeingMoved = currentSquareBeforeMove.getPiece();

        desiredSquareBeforeMove = gameBoard.getGrid()[2][3];
        assertFalse(desiredSquareBeforeMove.isEmpty());

        // Move
        gameBoardAfterMove = gameBoard.movePiece(
                currentSquareBeforeMove.getPosition(),
                desiredSquareBeforeMove.getPosition());
    }

    @Test
    public void numberOfBlackPieces() {
        assertEquals(12, gameBoard.numberOfBlackPieces());
    }

    @Test
    public void numberOfBlackPieces_afterCapture() {
        //TODO: Write this test after implementing Capture logic
    }

    @Test
    public void numberOfRedPieces() {
        assertEquals(12, gameBoard.numberOfRedPieces());
    }

    @Test
    public void numberOfRedPiecesAfterCapture() {
        //TODO: Write this test after implementing Capture logic
    }

    @Test
    public void getAvailableMoves_FrontRowPiece() {
        square = gameBoard.getGrid()[2][1];
        HashSet<Position> resultSet1 = gameBoard.getAvailableMoves(square.getPosition());
        assertEquals(2, resultSet1.size());
        assertTrue(resultSet1.contains(new Position(3,0)));
        assertTrue(resultSet1.contains(new Position(3,2)));

        square = gameBoard.getGrid()[5][4];
        HashSet<Position> resultSet2 = gameBoard.getAvailableMoves(square.getPosition());
        assertEquals(2, resultSet2.size());
        assertTrue(resultSet2.contains(new Position(4,3)));
        assertTrue(resultSet2.contains(new Position(4,5)));
    }

    @Test
    public void getAvailableMoves_PieceFreeOnAllSides() {
        gameBoard.movePiece(new Position(2,5), new Position(3,4));
        square = gameBoard.getGrid()[3][4];
        HashSet<Position> resultSet1 = gameBoard.getAvailableMoves(square.getPosition());
        assertEquals(2, resultSet1.size());
        assertTrue(resultSet1.contains(new Position(4,3)));
        assertTrue(resultSet1.contains(new Position(4,5)));
    }

    @Test
    public void getAvailableMoves_KingFreeOnAllSides() {
        /* Move piece at 50 to 41
         * Move piece at 52 to 43
         * Make piece at 41 a king
         * Get available moves of 41
         * Should be size 4 with positions 30, 32, 50, 52 */
        gameBoard.movePiece(new Position(5,0), new Position(4,1));
        gameBoard.movePiece(new Position(5,2), new Position(4,3));
        gameBoard.getGrid()[4][1].getPiece().setKing(true);

        square = gameBoard.getGrid()[4][1];
        HashSet<Position> resultSet1 = gameBoard.getAvailableMoves(square.getPosition());
        assertEquals(4,resultSet1.size());
        assertTrue(resultSet1.contains(new Position(3,0)));
        assertTrue(resultSet1.contains(new Position(3,2)));
        assertTrue(resultSet1.contains(new Position(5,0)));
        assertTrue(resultSet1.contains(new Position(5,2)));
    }

    @Test
    public void getAvailableMoves_PieceWithPossibleJump() {
        /* Move piece at 56 to 34
         * Get available moves of 23
         * Should be size 2 with positions 32 and 45
         * Get available moves of 25
         * Should be size 2 with positions 43 and 36 */
        gameBoard.movePiece(new Position(5,6), new Position(3,4));

        square = gameBoard.getGrid()[2][3];
        HashSet<Position> resultSet1 = gameBoard.getAvailableMoves(square.getPosition());
        assertEquals(2,resultSet1.size());
        assertTrue(resultSet1.contains(new Position(4,5)));
        assertTrue(resultSet1.contains(new Position(3,2)));

        square = gameBoard.getGrid()[2][5];
        HashSet<Position> resultSet2 = gameBoard.getAvailableMoves(square.getPosition());
        assertEquals(2,resultSet2.size());
        assertTrue(resultSet2.contains(new Position(4,3)));
        assertTrue(resultSet2.contains(new Position(3,6)));
    }

    @Test
    public void getAvailableMoves_KingWithPossibleJumpBackwards() {
        /* Move piece at 52 to 32
         * Move piece at 21 to  30
         * Move piece at 25 to 43
         * Make piece at 43 a king
         * Get available moves of 43
         * Should be size 3 with positions 52, 34, and 21 */
        gameBoard.movePiece(new Position(5,2), new Position(3,2));
        gameBoard.movePiece(new Position(2,1), new Position(3,0));
        gameBoard.movePiece(new Position(2,5), new Position(4,3));
        gameBoard.getGrid()[4][3].getPiece().setKing(true);

        square = gameBoard.getGrid()[4][3];
        HashSet<Position> resultSet1 = gameBoard.getAvailableMoves(square.getPosition());
        assertEquals(3,resultSet1.size());
        assertTrue(resultSet1.contains(new Position(5,2)));
        assertTrue(resultSet1.contains(new Position(3,4)));
        assertTrue(resultSet1.contains(new Position(2,1)));
    }
}