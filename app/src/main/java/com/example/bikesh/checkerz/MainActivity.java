package com.example.bikesh.checkerz;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton buttons [][] = new ImageButton[8][8];
    private boolean redTurn = false;
    private int redKills = 0;
    private int blackKills = 0;
    public GameBoard gb = new GameBoard();
    private int[] simpleID = new int[64];
    private Location map [][] = new Location[8][8];
    private ImageButton lastTouch;
    private boolean level1 = false;
    private ArrayList<ImageButton> neighCheck = new ArrayList<ImageButton>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int[] simpleID = {R.id.button_00, R.id.button_01, R.id.button_02, R.id.button_03, R.id.button_04, R.id.button_05, R.id.button_06, R.id.button_07, R.id.button_10, R.id.button_11, R.id.button_12, R.id.button_13, R.id.button_14, R.id.button_15, R.id.button_16, R.id.button_17, R.id.button_20, R.id.button_21, R.id.button_22, R.id.button_23, R.id.button_24, R.id.button_25, R.id.button_26, R.id.button_27, R.id.button_30, R.id.button_31, R.id.button_32, R.id.button_33, R.id.button_34, R.id.button_35, R.id.button_36, R.id.button_37, R.id.button_40, R.id.button_41, R.id.button_42, R.id.button_43, R.id.button_44, R.id.button_45, R.id.button_46, R.id.button_47, R.id.button_50, R.id.button_51, R.id.button_52, R.id.button_53, R.id.button_54, R.id.button_55, R.id.button_56, R.id.button_57, R.id.button_60, R.id.button_61, R.id.button_62, R.id.button_63, R.id.button_64, R.id.button_65, R.id.button_66, R.id.button_67, R.id.button_70, R.id.button_71, R.id.button_72, R.id.button_73, R.id.button_74, R.id.button_75, R.id.button_76, R.id.button_77};


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
                map[i][j] = new Location(i,j);


            }
        }
        for (Piece i: gb.getReds()){
            map[i.getX()][i.getY()].setPiece(i);
            if ((i.getX() + i.getY()) % 2 == 0){
                buttons[i.getX()][i.getY()].setBackground(getResources().getDrawable(R.drawable.board1wr, null));

            }
            else buttons[i.getX()][i.getY()].setBackground(getResources().getDrawable(R.drawable.board2wr, null));

        }
        for (Piece i: gb.getBlacks()){
            map[i.getX()][i.getY()].setPiece(i);
            if ((i.getX() + i.getY()) % 2 == 0) buttons[i.getX()][i.getY()].setBackground(getResources().getDrawable(R.drawable.board1wb, null));
            else buttons[i.getX()][i.getY()].setBackground(getResources().getDrawable(R.drawable.board2wb, null));
        }
    }



    @Override
    public void onClick(View v){
        if (!level1)
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (buttons[i][j].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.yellow, null).getConstantState()))
                    buttons[i][j].setBackground(getResources().getDrawable(R.drawable.board2,null));
            }
        }
        /*if ((((ImageButton) v).getBackground().equals(getResources().getDrawable(R.drawable.board1,null))) || (((ImageButton) v).getBackground().equals(getResources().getDrawable(R.drawable.board2,null)))) {
            return;
        }
        if (redTurn == false) {
            if ((((ImageButton) v).getBackground().equals(getResources().getDrawable(R.drawable.board1wr,null))) || (((ImageButton) v).getBackground().equals(getResources().getDrawable(R.drawable.board2wr,null)))) {
                return;
            }

            if ((((ImageButton) v).getBackground().equals(getResources().getDrawable(R.drawable.board1wb,null))) || (((ImageButton) v).getBackground().equals(getResources().getDrawable(R.drawable.board2wb,null)))){
               for (int i = 0; i<8; i++){
                   for (int j = 0; j < 8; j++){
                        if (v == buttons[i][j]){
                            if (i+1<8 && j+1 < 8) {
                                buttons[i+1][j+1].setBackground(getResources().getDrawable(R.drawable.yellow,null));
                            }
                            if (i+1<8 && j-1 > -1) buttons[i+1][j-1].setBackground(getResources().getDrawable(R.drawable.yellow,null));
                            if (i-1>-1 && j+1 < 8) buttons[i-1][j+1].setBackground(getResources().getDrawable(R.drawable.yellow,null));
                            if (i-1>-1 && j-1 > -1  ) buttons[i-1][j-1].setBackground(getResources().getDrawable(R.drawable.yellow,null));

                       }
                   }
               }
            }

        }*/
        if (redTurn == false) {
            if (!level1) {
                if ((v.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.board2wb, null).getConstantState()))) {
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (v == buttons[i][j]) {
                                lastTouch = buttons[i][j];

                       /* if (i + 1 < 8 && j + 1 < 8) {
                            if buttons[i + 1][j + 1].setBackground(getResources().getDrawable(R.drawable.yellow, null));
                        }
                        if (i + 1 < 8 && j - 1 > -1)
                            buttons[i + 1][j - 1].setBackground(getResources().getDrawable(R.drawable.yellow, null));*/
                                if (i - 1 > -1 && j + 1 < 8) {
                                    if (buttons[i - 1][j + 1].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.board2, null).getConstantState())) {
                                        buttons[i - 1][j + 1].setBackground(getResources().getDrawable(R.drawable.yellow, null));

                                        level1 = true;
                                    } else if (buttons[i - 1][j + 1].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.board2wr, null).getConstantState())) {
                                        if(buttons[i-2][j+2].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.board2, null).getConstantState())) {
                                            neighCheck.add(buttons[i - 1][j + 1]);
                                            buttons[i - 2][j + 2].setBackground(getResources().getDrawable(R.drawable.yellow, null));
                                        }
                                    }
                                }
                                if (i - 1 > -1 && j - 1 > -1){
                                    if (buttons[i - 1][j - 1].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.board2, null).getConstantState())) {
                                        buttons[i - 1][j - 1].setBackground(getResources().getDrawable(R.drawable.yellow, null));
                                        //buttons[i-1][j-1].setOnClickListener(this);
                                        level1 = true;
                                    }
                                    else if (buttons[i - 1][j - 1].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.board2wr, null).getConstantState())){
                                        if(buttons[i-2][j-2].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.board2, null).getConstantState())) {
                                            neighCheck.add(buttons[i - 1][j - 1]);
                                            buttons[i - 2][j - 2].setBackground(getResources().getDrawable(R.drawable.yellow, null));
                                        }
                                    }
                                }


                            }
                        }
                    }
                }
            } else {
                if ( (v.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.yellow, null).getConstantState()))) {
                    level1 = false;
                    v.setBackground(getResources().getDrawable(R.drawable.board2wb, null));
                    lastTouch.setBackground(getResources().getDrawable(R.drawable.board2, null));
                    redTurn = true;
                    neighCheck.clear();
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (buttons[i][j].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.yellow, null).getConstantState())) {
                                buttons[i][j].setBackground(getResources().getDrawable(R.drawable.board2, null));

                            }
                        }
                    }


                }//
            }
        }
        if (redTurn == true) {
            if (!level1) {
                if ((v.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.board2wr, null).getConstantState()))) {
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (v == buttons[i][j]) {
                                lastTouch = buttons[i][j];

                       /* if (i + 1 < 8 && j + 1 < 8) {
                            if buttons[i + 1][j + 1].setBackground(getResources().getDrawable(R.drawable.yellow, null));
                        }
                        if (i + 1 < 8 && j - 1 > -1)
                            buttons[i + 1][j - 1].setBackground(getResources().getDrawable(R.drawable.yellow, null));*/
                                if (i + 1 > -1 && j + 1 < 8) {
                                    if (buttons[i + 1][j + 1].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.board2, null).getConstantState())) {
                                        buttons[i + 1][j + 1].setBackground(getResources().getDrawable(R.drawable.yellow, null));
                                        //buttons[i-1][j+1].setOnClickListener(this);
                                        level1 = true;
                                    }
                                    else if (buttons[i + 1][j + 1].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.board2wb, null).getConstantState())){
                                        if(buttons[i+2][j+2].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.board2, null).getConstantState())) {
                                            neighCheck.add(buttons[i + 1][j + 1]);
                                            buttons[i + 2][j + 2].setBackground(getResources().getDrawable(R.drawable.yellow, null));
                                        }
                                    }
                                }
                                if (i + 1 < 8 && j - 1 > -1) {
                                    if (buttons[i + 1][j - 1].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.board2, null).getConstantState())) {
                                        buttons[i + 1][j - 1].setBackground(getResources().getDrawable(R.drawable.yellow, null));
                                        //buttons[i-1][j-1].setOnClickListener(this);
                                        level1 = true;
                                    }
                                    else if (buttons[i + 1][j - 1].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.board2wb, null).getConstantState())){
                                        if(buttons[i+2][j-2].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.board2, null).getConstantState())) {
                                            neighCheck.add(buttons[i + 1][j - 1]);
                                            buttons[i + 2][j - 2].setBackground(getResources().getDrawable(R.drawable.yellow, null));
                                        }
                                    }
                                }

                                //level1 = true;

                            }
                        }
                    }
                }
            } else {
                if ((v.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.yellow, null).getConstantState()))) {
                    level1 = false;
                    v.setBackground(getResources().getDrawable(R.drawable.board2wr, null));
                    lastTouch.setBackground(getResources().getDrawable(R.drawable.board2, null));
                    redTurn = false;
                    neighCheck.clear();
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (buttons[i][j].getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.yellow, null).getConstantState())) {
                                buttons[i][j].setBackground(getResources().getDrawable(R.drawable.board2, null));

                            }
                        }
                    }
                } else return;
            }
        }


        return;

    }
    public int getButtonX(ImageButton but){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (but == buttons[i][j])
                    return i;
            }
        }
        return -1;
    }
    public int getButtonY(ImageButton but){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (but == buttons[i][j])
                    return j;
            }
        }
        return -1;
    }

}
