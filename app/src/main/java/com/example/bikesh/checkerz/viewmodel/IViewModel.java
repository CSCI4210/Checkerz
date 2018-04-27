package com.example.bikesh.checkerz.viewmodel;

import android.support.v7.app.AppCompatActivity;

public interface IViewModel {
    void onCreate(AppCompatActivity activity);
    void onPause();
    void onResume();
    void onDestroy();
}
