package com.example.dmitryvedmed.dominocounter;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private TextView certainScore, firstFinalScore, secondFinalScore, firstScoreBoard, secondScoreBoard;

    private int certainValue;

    private int firstValue, secondValue;

    private ScrollView firstScroll, secondScroll;

    private MediaPlayer mp;

    private int[] sounds;

    private boolean gameIsOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sounds = new int[]{R.raw.igrulia,R.raw.leha,R.raw.leha_vania,R.raw.vania};
        initView();

    }

    private void initView() {
        certainScore = (TextView) findViewById(R.id.certain_score);
        firstFinalScore = (TextView) findViewById(R.id.first_final_score);
        secondFinalScore = (TextView) findViewById(R.id.second_final_score);

        firstScoreBoard = (TextView) findViewById(R.id.first_score_board);
        secondScoreBoard = (TextView) findViewById(R.id.second_score_board);

        //  firstScroll = (ScrollView) findViewById(R.id.first_scroll);
 /*       firstScoreBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                certainScore.setText("sdfgh");
            }
        });*/
    }

    public void onClick(View v){


        System.out.println("C Score " + certainScore.getText());

        if(gameIsOver)
            return;

        if(certainScore.getText().equals("0")){
            certainScore.setText("");
        }


        switch (v.getId()){

            case R.id.btn_0:
                certainScore.setText(certainScore.getText()+"0");
                break;
            case R.id.btn_1:
                certainScore.setText(certainScore.getText()+"1");
                break;
            case R.id.btn_2:
                certainScore.setText(certainScore.getText()+"2");
                break;
            case R.id.btn_3:
                certainScore.setText(certainScore.getText()+"3");
                break;
            case R.id.btn_4:
                certainScore.setText(certainScore.getText()+"4");
                break;
            case R.id.btn_5:
                certainScore.setText(certainScore.getText()+"5");
                break;
            case R.id.btn_6:
                certainScore.setText(certainScore.getText()+"6");
                break;
            case R.id.btn_7:
                certainScore.setText(certainScore.getText()+"7");
                break;
            case R.id.btn_8:
                certainScore.setText(certainScore.getText()+"8");
                break;
            case R.id.btn_9:
                certainScore.setText(certainScore.getText()+"9");
                break;
            case R.id.tuda:
                certainScore.setText("21");
                break;
            case R.id.first_score_board:
                if(certainScore.getText().equals("")){
                    certainScore.setText("0");
                    return;
                }

                certainValue = Integer.valueOf((String) certainScore.getText());
                if(certainValue == 21){
                    playSound();
                }
                firstValue = firstValue + certainValue;
                firstFinalScore.setText(String.valueOf(firstValue));

                certainScore.setText("0");

                if(firstScoreBoard.getText().equals("")){
                    System.out.println("EQ");
                    firstScoreBoard.setText(String.valueOf(firstValue));
                }
                else {
                    System.out.println("NE EQ");
                    System.out.println(firstFinalScore);
                    System.out.println(firstFinalScore.length());
                    firstScoreBoard.setText(firstScoreBoard.getText() +"\r\n " + certainValue);
                }

                if(firstValue>101){
                    certainScore.setText("ХВАТАЕТ!");
                    gameIsOver = true;
                }
                break;
            case R.id.second_score_board:
                if(certainScore.getText().equals("")){
                    certainScore.setText("0");
                    return;
                }

                certainValue = Integer.valueOf((String) certainScore.getText());
                if(certainValue == 21){
                    playSound();
                }
                secondValue = secondValue + certainValue;
                secondFinalScore.setText(String.valueOf(secondValue));
                certainScore.setText("0");

                if(secondScoreBoard.getText().equals("")){
                    secondScoreBoard.setText(String.valueOf(secondValue));
                }
                else {
                    secondScoreBoard.setText(secondScoreBoard.getText() +"\r\n " + certainValue);
                }

                if(secondValue>101){
                    certainScore.setText("ХВАТАЕТ!");
                    gameIsOver = true;
                }
                break;
        }

        if(!certainScore.getText().equals("")&&!certainScore.getText().equals("ХВАТАЕТ!")){
            certainValue = Integer.valueOf((String) certainScore.getText());
            if(certainValue > 101){
                certainScore.setText(((String) certainScore.getText()).substring(0, certainScore.getText().length()-1));
                return;
            }
        }
        if(certainScore.getText().equals(""))
            certainScore.setText("0");
    }

    private void playSound(){
        int i = new Random().nextInt(4);
        System.out.println(i);
        mp = MediaPlayer.create(MainActivity.this, sounds[i]);
        mp.start();
    }

    public void newGame(View v){
        if(gameIsOver){
            firstValue = 0;
            secondValue = 0;
            certainScore.setText("");
            certainValue = 0;
            firstFinalScore.setText("0");
            secondFinalScore.setText("0");
            firstScoreBoard.setText("");
            secondScoreBoard.setText("");
            gameIsOver = false;
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Новая игра?");
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    firstValue = 0;
                    secondValue = 0;
                    certainScore.setText("");
                    certainValue = 0;
                    firstFinalScore.setText("0");
                    secondFinalScore.setText("0");
                    firstScoreBoard.setText("");
                    secondScoreBoard.setText("");
                    gameIsOver = false;
                }
            });
            dialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialog.show();

        }
    }

}
