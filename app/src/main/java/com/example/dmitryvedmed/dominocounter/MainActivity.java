package com.example.dmitryvedmed.dominocounter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String ZERO = "0";
    private static final long VIBRATION_TIME = 25L;
    private static final int ALPHA_54 = 138;
    private static final int  ALPHA_87 = 222;

    private TextView certainScore;

    private ArrayList<TextView> finalScoreList;

    private ImageButton btn_cancel;

    private List<String> firstList, secondList, thirdList;

    List<List<String>> adaptersListList;

    private List<ListView> listViewList;

    private List<ArrayAdapter<String>> adaptersList;

    private int certainValue;

    private int[] values;

    private boolean gameIsOver;

    private byte lastAddition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_four_players);

        firstList = new ArrayList<>();
        secondList = new ArrayList<>();
        thirdList = new ArrayList<>();

        finalScoreList = new ArrayList<>();
        listViewList =new ArrayList<>();

        adaptersList = new ArrayList<>();

        adaptersListList = new ArrayList<>();

        values = new int[4];

        initView();
    }

    private void initView() {
        certainScore = (TextView) findViewById(R.id.certain_score);

        initTwoPlayers();

        initThirdPlayer();

        btn_cancel = (ImageButton) findViewById(R.id.cancel);
        btn_cancel.getDrawable().setAlpha(ALPHA_87);
        btn_cancel.setClickable(false);

    }

    private void initThirdPlayer() {
        TextView third = (TextView) findViewById(R.id.third_final_score);
        finalScoreList.add(third);

        ListView thirdListView = (ListView) findViewById(R.id.third_list_view);
        listViewList.add(thirdListView);


        ArrayAdapter<String> thirdAdapter = new ArrayAdapter(this,
                R.layout.list_item, thirdList);
        thirdListView.setAdapter(thirdAdapter);

        adaptersList.add(thirdAdapter);

        thirdListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP){

                    if(gameIsOver || certainScore.getText().equals(ZERO)){
                        return false;
                    }

                    String certainText = (String) certainScore.getText();
                    certainValue = Integer.valueOf(certainText);
                    values[2] = values[2] + certainValue;
                    lastAddition = 3;
                    btn_cancel.getDrawable().setAlpha(ALPHA_87);
                    btn_cancel.setClickable(true);

                    thirdList.add(certainText);
                    finalScoreList.get(2).setText(String.valueOf(values[0]));
                    certainScore.setText(ZERO);

                    adaptersList.get(2).notifyDataSetChanged();
                    listViewList.get(2).setSelection(firstList.size()-1);

                    if(values[2] >= 101){
                        certainScore.setText(getString(R.string.enough));
                        gameIsOver = true;
                    }
                }
                return false;
            }
        });

    }

    private void initTwoPlayers() {
        TextView firstFinalScore = (TextView) findViewById(R.id.first_final_score);
        finalScoreList.add(firstFinalScore);

        TextView secondFinalScore = (TextView) findViewById(R.id.second_final_score);
        finalScoreList.add(secondFinalScore);

        ListView firstListView = (ListView) findViewById(R.id.first_list_view);
        listViewList.add(firstListView);

        ArrayAdapter<String> firstAdapter = new ArrayAdapter(this,
                R.layout.list_item, firstList);
        firstListView.setAdapter(firstAdapter);

        adaptersList.add(firstAdapter);

        firstListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP){

                    if(gameIsOver || certainScore.getText().equals(ZERO)){
                        return false;
                    }

                    String certainText = (String) certainScore.getText();
                    certainValue = Integer.valueOf(certainText);
                    values[0] = values[0] + certainValue;
                    lastAddition = 1;
                    btn_cancel.getDrawable().setAlpha(ALPHA_87);
                    btn_cancel.setClickable(true);

                    firstList.add(certainText);
                    finalScoreList.get(0).setText(String.valueOf(values[0]));
                    certainScore.setText(ZERO);

                    adaptersList.get(0).notifyDataSetChanged();
                    listViewList.get(0).setSelection(firstList.size()-1);

                    if(values[0] >= 101){
                        certainScore.setText(getString(R.string.enough));
                        gameIsOver = true;
                    }
                }
                return false;
            }
        });

        ListView secondListView = (ListView) findViewById(R.id.second_list_view);
        listViewList.add(secondListView);

        ArrayAdapter<String> secondAdapter = new ArrayAdapter(this,
                R.layout.list_item, secondList);
        secondListView.setAdapter(secondAdapter);

        adaptersList.add(secondAdapter);

        secondListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP){

                    if(gameIsOver||certainScore.getText().equals(ZERO)){
                        return false;
                    }

                    String certainText = (String) certainScore.getText();
                    certainValue = Integer.valueOf(certainText);
                    values[1] = values[1] + certainValue;
                    lastAddition = 2;
                    btn_cancel.getDrawable().setAlpha(ALPHA_87);
                    btn_cancel.setClickable(true);

                    secondList.add(certainText);
                    finalScoreList.get(1).setText(String.valueOf(values[1]));
                    certainScore.setText(ZERO);

                    adaptersList.get(1).notifyDataSetChanged();
                    listViewList.get(1).setSelection(secondList.size()-1);

                    if(values[1] >= 101){
                        certainScore.setText(getString(R.string.enough));
                        gameIsOver = true;
                    }
                }
                return false;
            }
        });
    }

    public void onClick(View v){

        if(v.getId() == R.id.backspace){

            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATION_TIME);

            if(!certainScore.getText().equals("") && !certainScore.getText().equals(getString(R.string.enough))){
                certainScore.setText(((String) certainScore.getText()).substring(0, certainScore.getText().length()-1));
            }
            if(certainScore.getText().equals(""))
                certainScore.setText(ZERO);
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
        if(certainText.startsWith(ZERO)&&certainText.length()>1){
            certainText = certainText.substring(1, certainText.length());
            certainScore.setText(certainText);
        }


        if(!certainScore.getText().equals("") && !certainScore.getText().equals(getString(R.string.enough))){
            certainValue = Integer.valueOf((String) certainScore.getText());
            if(certainValue >= 101){
                certainScore.setText(((String) certainScore.getText()).substring(0, certainScore.getText().length()-1));
                return;
            }
        }

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(VIBRATION_TIME);

    }

    public void restart(View v){

        if(gameIsOver){
            setToZero();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(R.string.question_new_game);
            dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    setToZero();
                    btn_cancel.getDrawable().setAlpha(ALPHA_54);
                    btn_cancel.setClickable(false);
                }
            });
            dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            dialog.show();
        }
    }

    private void setToZero(){
        values = new int[4];
        certainScore.setText(ZERO);
        certainValue = 0;
        finalScoreList.get(0).setText(ZERO);
        finalScoreList.get(1).setText(ZERO);
        firstList.clear();
        thirdList.clear();
        adaptersList.get(0).notifyDataSetChanged();
        adaptersList.get(1).notifyDataSetChanged();
        secondList.clear();
        gameIsOver = false;
    }

    public void cancelLastInput(View v){

        if(lastAddition == 1 || lastAddition == 2){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(R.string.question_cancel_last_enter);
            dialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(lastAddition == 1){
                        int lastValue = Integer.valueOf(firstList.get(firstList.size()-1));
                        values[0] = values[0] - lastValue;
                        finalScoreList.get(0).setText(String.valueOf(values[0]));
                        firstList.remove(firstList.get(firstList.size()-1));
                        adaptersList.get(0).notifyDataSetChanged();
                        if(gameIsOver){
                            certainScore.setText(ZERO);
                            gameIsOver = false;
                        }
                        lastAddition = 0;
                        btn_cancel.getDrawable().setAlpha(ALPHA_54);
                        btn_cancel.setClickable(false);
                    }
                    if(lastAddition == 2){
                        int lastValue = Integer.valueOf(secondList.get(secondList.size()-1));
                        values[1] = values[1] - lastValue;
                        finalScoreList.get(1).setText(String.valueOf(values[0]));
                        secondList.remove(secondList.get(secondList.size()-1));
                        adaptersList.get(1).notifyDataSetChanged();
                        if(gameIsOver){
                            certainScore.setText(ZERO);
                            gameIsOver = false;
                        }
                        lastAddition = 0;
                        btn_cancel.getDrawable().setAlpha(ALPHA_54);
                        btn_cancel.setClickable(false);
                    }
                    if(lastAddition == 3){
                        int lastValue = Integer.valueOf(secondList.get(secondList.size()-1));
                        values[1] = values[2] - lastValue;
                        finalScoreList.get(2).setText(String.valueOf(values[0]));
                        thirdList.remove(thirdList.get(thirdList.size()-1));
                        adaptersList.get(2).notifyDataSetChanged();
                        if(gameIsOver){
                            certainScore.setText(ZERO);
                            gameIsOver = false;
                        }
                        lastAddition = 0;
                        btn_cancel.getDrawable().setAlpha(ALPHA_54);
                        btn_cancel.setClickable(false);
                    }
                }
            });
            dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            dialog.show();
        }
    }

}
