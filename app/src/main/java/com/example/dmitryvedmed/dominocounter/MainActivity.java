package com.example.dmitryvedmed.dominocounter;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private TextView certainScore, firstFinalScore, secondFinalScore, firstScoreBoard, secondScoreBoard;

    private int certainValue;

    private int firstValue, secondValue;

    private MediaPlayer mp;

    private int[] sounds;

    private boolean gameIsOver;

    List<String> firstList, secondList;

    ListView firstListView, secondListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstList = new ArrayList<>();
        secondList = new ArrayList<>();


        initView();

        sounds = new int[]{R.raw.igrulia, R.raw.leha, R.raw.leha_vania, R.raw.vania};


    }

    private void initView() {
      //  Typeface typeface = Typeface.createFromAsset(getAssets(),"font/LHANDW.TTF");


        certainScore = (TextView) findViewById(R.id.certain_score);
        firstFinalScore = (TextView) findViewById(R.id.first_final_score);
        secondFinalScore = (TextView) findViewById(R.id.second_final_score);

        //firstScoreBoard = (TextView) findViewById(R.id.first_score_board);
        //secondScoreBoard = (TextView) findViewById(R.id.second_score_board);

        firstListView = (ListView) findViewById(R.id.firstListView);

        final ArrayAdapter<String> adapter = new ArrayAdapter(this,
                R.layout.list_item, firstList);
        firstListView.setAdapter(adapter);

        firstListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){

                    if(gameIsOver)
                        return false;











           /*         if(gameIsOver)
                        return false;

                    System.out.println("Touch");
                    if(certainScore.getText().equals("")){
                        certainScore.setText("0");
                        return false;
                    }

                    certainValue = Integer.valueOf((String) certainScore.getText());

                    firstValue = firstValue + certainValue;
                    firstFinalScore.setText(String.valueOf(firstValue));

                    certainScore.setText("0");

                    if(certainValue != 0) {
                        firstList.add(String.valueOf(certainValue));
                        adapter.notifyDataSetChanged();
                        //countriesList.scrollListBy(list.size()-1);
                        firstListView.setSelection(firstList.size()-1);
                    }

                    if(firstValue>=101){
                        certainScore.setText("ХВАТАЕТ!");
                        gameIsOver = true;
                    }*/

                }
                return false;
            }
        });

    }

    public void onClick(View v){

        System.out.println("C Score " + certainScore.getText());

        if(v.getId() == R.id.backspace){
            if(!certainScore.getText().equals("")&&!certainScore.getText().equals("ХВАТАЕТ!")){
                certainScore.setText(((String) certainScore.getText()).substring(0, certainScore.getText().length()-1));
            }
            if(certainScore.getText() == "")
                certainScore.setText("0");
            return;
        }

        if(gameIsOver)
            return;

        switch (v.getId()) {
            case R.id.btn_0:
                certainScore.setText(certainScore.getText() + "0");
                break;
            case R.id.btn_1:
                certainScore.setText(certainScore.getText() + "1");
                break;
            case R.id.btn_2:
                certainScore.setText(certainScore.getText() + "2");
                break;
            case R.id.btn_3:
                certainScore.setText(certainScore.getText() + "3");
                break;
            case R.id.btn_4:
                certainScore.setText(certainScore.getText() + "4");
                break;
            case R.id.btn_5:
                certainScore.setText(certainScore.getText() + "5");
                break;
            case R.id.btn_6:
                certainScore.setText(certainScore.getText() + "6");
                break;
            case R.id.btn_7:
                certainScore.setText(certainScore.getText() + "7");
                break;
            case R.id.btn_8:
                certainScore.setText(certainScore.getText() + "8");
                break;
            case R.id.btn_9:
                certainScore.setText(certainScore.getText() + "9");
                break;
        }

        String certainText = (String) certainScore.getText();
        if(certainText.startsWith("0")&&certainText.length()>1){
            certainText = certainText.substring(1, certainText.length());
            certainScore.setText(certainText);
            System.out.println("after" + certainScore.getText());
        }


       if(!certainScore.getText().equals("")&&!certainScore.getText().equals("ХВАТАЕТ!")){
            certainValue = Integer.valueOf((String) certainScore.getText());
            if(certainValue >= 101){
                certainScore.setText(((String) certainScore.getText()).substring(0, certainScore.getText().length()-1));
                return;
            }
        }

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
            firstList.clear();
            secondList.clear();

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

    public void onClickCancel(View v){
        System.out.println("WORK!!");

    }

}
