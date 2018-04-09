package com.example.bikesh.checkerz.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.example.bikesh.checkerz.model.Game;
import com.example.bikesh.checkerz.model.IPlayer;

public class CheckersViewModel implements IViewModel {

    private Game model;

    public final ObservableInt blackCaptures = new ObservableInt();
    public final ObservableInt redCaptures = new ObservableInt();
    public final ObservableField<IPlayer> winner = new ObservableField<>();

    public CheckersViewModel() { }

    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }
}
