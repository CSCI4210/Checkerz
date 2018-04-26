package com.example.bikesh.checkerz.model;

import android.support.annotation.NonNull;

import java.util.Comparator;
import java.util.Iterator;

public class Bot implements IPlayer {
    private String name;

    public Bot() {
        this.name = "Checkers Bot";
    }

    @Override
    public GameState chooseMove(GameState state) {
        StateTree currentBestStateTree = null;
        StateTree bestStateTree = null;
        StateTree alphaState = new StateTree(Double.NEGATIVE_INFINITY);
        StateTree betaState = new StateTree(Double.POSITIVE_INFINITY);
        StateTree treeRoot = new StateTree(state);
        int searchDepth = 2;

        while(!state.searchLimitReached()){
            bestStateTree = currentBestStateTree;
            if (state.getCurrentColor() == PieceColor.RED)
                currentBestStateTree = findMax(treeRoot, 0, alphaState, betaState, searchDepth);
            else
                currentBestStateTree = findMin(treeRoot, 0, alphaState, betaState, searchDepth);
            searchDepth += 2;
        }
        if (currentBestStateTree != null) {
            if (bestStateTree != null) {
                return bestStateTree.state;
            }
            return currentBestStateTree.state;
        }
        return null;
    }

    private StateTree findMax(StateTree treeNode,
                              int currentDepth,
                              StateTree alphaState,
                              StateTree betaState,
                              int searchDepth) {
        if (currentDepth > searchDepth)
            return treeNode;

        StateTree maxState = new StateTree(Double.NEGATIVE_INFINITY);
        Iterator<GameState> children = treeNode.state.next().iterator();

        while (!treeNode.state.searchLimitReached() && children.hasNext()) {
            StateTree child = new StateTree(children.next());
            child.score = calculateUtility(
                    findMin(child, currentDepth + 1, alphaState, betaState, searchDepth)
            );
            maxState = (maxState.compareTo(child) > 0) ? maxState : child;
            if (maxState.compareTo(betaState) >= 0)
                return maxState;
            alphaState = (alphaState.compareTo(maxState) > 0) ? alphaState : maxState;
        }
        return maxState;
    }

    private StateTree findMin(StateTree treeNode,
                              int currentDepth,
                              StateTree alphaState,
                              StateTree betaState,
                              int searchDepth) {
        if (currentDepth > searchDepth)
            return treeNode;

        StateTree minState = new StateTree(Double.POSITIVE_INFINITY);
        Iterator<GameState> children = treeNode.state.next().iterator();

        while (!treeNode.state.searchLimitReached() && children.hasNext()) {
            StateTree child = new StateTree(children.next());
            child.score = calculateUtility(
                    findMax(child, currentDepth + 1, alphaState, betaState, searchDepth)
            );
            minState = (minState.compareTo(child) < 0) ? minState : child;
            if (minState.compareTo(alphaState) <= 0)
                return minState;
            betaState = (betaState.compareTo(minState) < 0) ? betaState : minState;
        }
        return minState;
    }

    private double materialScore (GameState state) {
        double blackTotal = 0;
        double redTotal = 0;

        for (Piece rPiece : state.getBoard().getRedPieces()){
            if (rPiece.isKing())
                redTotal += 3;
            else
                redTotal += 1;
        }
        for (Piece bPiece : state.getBoard().getBlackPieces()){
            if (bPiece.isKing())
                blackTotal += 3;
            else
                blackTotal += 1;
        }
        // TODO: Maybe try switching the order if things aren't working right
        return redTotal - blackTotal;
    }

    private double calculateUtility(StateTree node) {
        // Black is trying to Maximize the Utility (Higher Scores are more desired)
        // Red is trying to Minimize the Utility (Lower Scores are more desired)
        GameState currentState = node.state;
        double score = 0;
        if (currentState == null)
            return node.score;
        // Checks if the game is over. If so, use an ultimate utility because we
        // know who won, or if no one won
        if (currentState.isOver()) {
            if (currentState.getCurrentColor() == PieceColor.RED){
                // If the state of the game is over and it is Red's turn, then Red must have no
                // moves left to play. So Black wins.
                score = Double.POSITIVE_INFINITY;
            }
            if (currentState.getCurrentColor() == PieceColor.BLACK){
                // If the state of the game is over and it is Black's turn, then Black must have
                //  no moves left to play. So Red wins.
                score = Double.NEGATIVE_INFINITY;
            }
        } else {
            // The game is not over, so the Material Score Heuristic is used
            score = materialScore(currentState);
        }
        return score;
    }


    private class StateTree implements Comparable<StateTree> {
        final GameState state;
        Double score;

        StateTree(GameState state){
            this.state = state;
        }

        StateTree(Double score){
            this.state = null;
            this.score = score;
        }

        @Override
        public int compareTo(@NonNull StateTree o) {
            int result = 0;
            if (this.score < o.score)
                result = -1;
            if (this.score > o.score)
                result = 1;
            return result;
        }
    }

    private class StateTreeComparator implements Comparator<StateTree> {

        @Override
        public int compare(StateTree o1, StateTree o2) {
            int result = 0;
            if (o1.score < o2.score)
                result = -1;
            if (o1.score > o2.score)
                result = 1;
            return result;
        }
    }
}
