package com.example.bikesh.checkerz.model;

public class Human implements IPlayer {
    private String name;
    private Square selectedSquare;

    public Human (String name){
        this.name = name;
        this.selectedSquare = null;
    }

    /**
     * Choose move in the human will assume the choice has been made and validated
     * through click events handled by the ViewModel.
     *
     * @param state the already chosen next state
     * @return the chosen state
     */
    @Override
    public GameState chooseMove(GameState state) {
        /* This is admittedly bad design. The method should expect the same input
         * regardless of if it is a Human or a Bot */
        return state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Square getSelectedSquare() {
        return selectedSquare;
    }

    public void setSelectedSquare(Square selectedSquare) {
        this.selectedSquare = selectedSquare;
    }

    @Override
    public String toString() {
        return this.name.toUpperCase();
    }
}
