package com.example.bikesh.checkerz.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.bikesh.checkerz.databinding.ActivityMainBinding;
import com.example.bikesh.checkerz.R;
import com.example.bikesh.checkerz.viewmodel.CheckersViewModel;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    CheckersViewModel viewModel = new CheckersViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //Binds the "viewModel" variable declared in activity_main.xml to this.viewModel
        binding.setViewModel(viewModel);
        viewModel.onCreate(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.item_new_human_game) {
            viewModel.onNewHumanGameSelected();
            return true;
        } else if (item.getItemId() == R.id.item_new_bot_game) {
            viewModel.onNewBotGameSelected();
            return true;
        } else if (item.getItemId() == R.id.item_restart_game) {
            viewModel.onRestartGameSelected();
            return true;
        } else if (item.getItemId() == R.id.item_about){
            // Have Instructions Shown
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }
}
