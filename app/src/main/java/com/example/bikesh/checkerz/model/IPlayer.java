package com.example.bikesh.checkerz.model;

import com.example.bikesh.checkerz.GameState;

/**
 * Created by dhemard on 3/10/18.
 */

public interface IPlayer {
    GameState chooseMove(GameState state);
}
