package com.example.bikesh.checkerz.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.bikesh.checkerz.model.GameBoard;
import com.example.bikesh.checkerz.Piece;
import com.example.bikesh.checkerz.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton buttons [][] = new ImageButton[8][8];
    private boolean redTurn = false;
    private int redKills = 0;
    private int blackKills = 0;
    public GameBoard gb = new GameBoard();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j ++){
                String buttonID = "button_" + i + j;
                int intID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(intID);
                buttons[i][j].setOnClickListener(this);
                if ((i+j) % 2 == 0) {
                    buttons[i][j].setBackground(getResources().getDrawable(R.drawable.board1, null));
                }
                if ((i+j) % 2 == 1) {
                    buttons[i][j].setBackground(getResources().getDrawable(R.drawable.board2, null));
                }


            }
        }

        for (Piece i: gb.getReds()){
            if ((i.getX() + i.getY()) % 2 == 0) buttons[i.getX()][i.getY()].setBackground(getResources().getDrawable(R.drawable.board1wr, null));
            else buttons[i.getX()][i.getY()].setBackground(getResources().getDrawable(R.drawable.board2wr, null));
        }
        for (Piece i: gb.getBlacks()){
            if ((i.getX() + i.getY()) % 2 == 0) buttons[i.getX()][i.getY()].setBackground(getResources().getDrawable(R.drawable.board1wb, null));
            else buttons[i.getX()][i.getY()].setBackground(getResources().getDrawable(R.drawable.board2wb, null));
        }
    }



    @Override
    public void onClick(View v){
        if ((((ImageButton) v).getBackground().equals(getResources().getDrawable(R.drawable.board1,null))) || (((ImageButton) v).getBackground().equals(getResources().getDrawable(R.drawable.board2,null)))) {
            return;
        }
        if (redTurn == false) {
            if ((((ImageButton) v).getBackground().equals(getResources().getDrawable(R.drawable.board1wr,null))) || (((ImageButton) v).getBackground().equals(getResources().getDrawable(R.drawable.board2wr,null)))) {
                return;
            }

        }
        return;

    }
}
