package com.example.dmitryvedmed.dominocounter;

import android.content.DialogInterface;
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

public class MainActivity extends AppCompatActivity {


    private TextView certainScore, firstFinalScore, secondFinalScore, firstScoreBoard, secondScoreBoard;

    private int certainValue;

    private int firstValue, secondValue;

    private boolean gameIsOver;

    private byte lastAddition;

    List<String> firstList, secondList;

    ListView firstListView, secondListView;

    ArrayAdapter<String> firstAdapter, secondAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstList = new ArrayList<>();
        secondList = new ArrayList<>();

        initView();

    }

    private void initView() {
        //  Typeface typeface = Typeface.createFromAsset(getAssets(),"font/LHANDW.TTF");

        certainScore = (TextView) findViewById(R.id.certain_score);
        firstFinalScore = (TextView) findViewById(R.id.first_final_score);
        secondFinalScore = (TextView) findViewById(R.id.second_final_score);

        //firstScoreBoard = (TextView) findViewById(R.id.first_score_board);
        //secondScoreBoard = (TextView) findViewById(R.id.second_score_board);

        firstListView = (ListView) findViewById(R.id.first_list_view);

        firstAdapter = new ArrayAdapter(this,
                R.layout.list_item, firstList);
        firstListView.setAdapter(firstAdapter);


        firstListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP){

                    if(gameIsOver||certainScore.getText().equals("0")){
                        return false;
                    }

                    String certainText = (String) certainScore.getText();
                    certainValue = Integer.valueOf(certainText);
                    firstValue = firstValue + certainValue;
                    lastAddition = 1;

                    firstList.add(certainText);
                    firstFinalScore.setText(String.valueOf(firstValue));
                    certainScore.setText("0");

                    firstAdapter.notifyDataSetChanged();
                    firstListView.setSelection(firstList.size()-1);

                    if(firstValue >= 101){
                        certainScore.setText("ХВАТАЕТ!");
                        gameIsOver = true;
                    }
                }
                return false;
            }
        });


        secondListView = (ListView) findViewById(R.id.second_list_view);

        secondAdapter = new ArrayAdapter(this,
                R.layout.list_item, secondList);
        secondListView.setAdapter(secondAdapter);


        secondListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP){

                    if(gameIsOver||certainScore.getText().equals("0")){
                        return false;
                    }

                    String certainText = (String) certainScore.getText();
                    certainValue = Integer.valueOf(certainText);
                    secondValue = secondValue + certainValue;
                    lastAddition = 2;

                    secondList.add(certainText);
                    secondFinalScore.setText(String.valueOf(secondValue));
                    certainScore.setText("0");

                    secondAdapter.notifyDataSetChanged();
                    secondListView.setSelection(secondList.size()-1);

                    if(secondValue >= 101){
                        certainScore.setText("ХВАТАЕТ!");
                        gameIsOver = true;
                    }
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
                System.out.println("\"" + certainScore.getText() + "\"");
            }
            if(certainScore.getText().equals(""))
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

    public void restart(View v){
        if(gameIsOver){
            setToZero();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Новая игра?");
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    setToZero();
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

    private void setToZero(){
        firstValue = 0;
        secondValue = 0;
        certainScore.setText("0");
        certainValue = 0;
        firstFinalScore.setText("0");
        secondFinalScore.setText("0");
        firstList.clear();
        firstAdapter.notifyDataSetChanged();
        secondList.clear();
        gameIsOver = false;

    }

    public void cancelLastInput(View v){
        if(lastAddition==1||lastAddition==2){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Отменить последний ввод?");
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(lastAddition == 1){
                        int lastValue = Integer.valueOf(firstList.get(firstList.size()-1));
                        firstValue = firstValue - lastValue;
                        firstFinalScore.setText(String.valueOf(firstValue));
                        firstList.remove(firstList.get(firstList.size()-1));
                        firstAdapter.notifyDataSetChanged();
                        if(gameIsOver){
                            certainScore.setText("0");
                            gameIsOver = false;
                        }
                        lastAddition = 0;
                    }
                    if(lastAddition == 2){
                        int lastValue = Integer.valueOf(secondList.get(secondList.size()-1));
                        secondValue = secondValue - lastValue;
                        secondFinalScore.setText(String.valueOf(secondValue));
                        secondList.remove(secondList.get(secondList.size()-1));
                        secondAdapter.notifyDataSetChanged();
                        if(gameIsOver){
                            certainScore.setText("0");
                            gameIsOver = false;
                        }
                        lastAddition = 0;
                    }
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
