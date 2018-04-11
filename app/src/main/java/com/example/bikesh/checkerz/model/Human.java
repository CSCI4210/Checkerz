package com.example.bikesh.checkerz.model;

public class Human implements IPlayer {
    private String name;

    public Human (String name){
        this.name = name;
    }

    @Override
    public GameState chooseMove(GameState state) {
        return null;
    }

    @Override
    public String toString() {
        return "Human{" +
                "name='" + name + '\'' +
                '}';
    }
}
