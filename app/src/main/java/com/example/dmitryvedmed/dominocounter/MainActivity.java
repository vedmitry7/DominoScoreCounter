package com.example.dmitryvedmed.dominocounter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private TextView certainScore, firstFinalScore, secondFinalScore, firstScoreBoard, secondScoreBoard;

    private int certainValue;

    private int firstValue, secondValue;

    private ScrollView firstScroll, secondScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        certainScore = (TextView) findViewById(R.id.certain_score);
        firstFinalScore = (TextView) findViewById(R.id.first_final_score);
        secondFinalScore = (TextView) findViewById(R.id.second_final_score);

        firstScoreBoard = (TextView) findViewById(R.id.first_score_board);
        firstFinalScore.setText("");
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
                if(certainScore.getText().equals(""))
                    return;

                certainValue = Integer.valueOf((String) certainScore.getText());
                firstValue = firstValue + certainValue;
                firstFinalScore.setText(String.valueOf(firstValue));

                certainScore.setText("0");

                if(firstFinalScore.getText().equals("")){
                    firstScoreBoard.setText(String.valueOf(firstValue));
                }
                    else {
                    firstScoreBoard.setText(firstScoreBoard.getText() +"\r\n " + firstValue);
                }
                break;
            case R.id.second_score_board:
                if(certainScore.getText().equals(""))
                    return;

                certainValue = Integer.valueOf((String) certainScore.getText());
                secondValue = secondValue + certainValue;
                secondFinalScore.setText(String.valueOf(secondValue));
                certainScore.setText("0");

                if(secondFinalScore.getText().equals("")){
                    secondScoreBoard.setText(secondValue);
                }
                else {
                    secondScoreBoard.setText(secondScoreBoard.getText() +"\r\n " + secondValue);
                }
                break;
        }
    }
}
