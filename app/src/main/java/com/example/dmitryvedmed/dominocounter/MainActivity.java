package com.example.dmitryvedmed.dominocounter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
    private static final String  PREFERENCES = "domino_prefs";
    private static final String  NUMBER_OF_PLAYERS = "number_of_players";
    private static final String  WIN_VALUE = "win_value";

    private TextView certainScore;

    private ArrayList<TextView> finalScoreList;

    private ImageButton btn_cancel;

    private List<String> firstList, secondList, thirdList, fourthList;

    private List<List<String>> adaptersListList;

    private List<ListView> listViewList;

    private List<ArrayAdapter<String>> adaptersList;

    private int certainValue;

    private int[] values;

    private boolean gameIsOver;

    private byte lastAddition;

    private SharedPreferences sharedPreferences;

    private int numberOfPlayers;
    private int winValue;


    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        numberOfPlayers = sharedPreferences.getInt(NUMBER_OF_PLAYERS, 2);
        winValue = sharedPreferences.getInt(WIN_VALUE, 101);

        if(numberOfPlayers == 2){
            setContentView(R.layout.activity_main);
        } else
        if(numberOfPlayers == 3){
            setContentView(R.layout.activity_for_three_players);
        } else
        if(numberOfPlayers == 4){
            setContentView(R.layout.activity_for_four_players);
        }

        firstList = new ArrayList<>();
        secondList = new ArrayList<>();
        thirdList = new ArrayList<>();
        fourthList = new ArrayList<>();

        adaptersList = new ArrayList<>();

        adaptersListList = new ArrayList<>();

        adaptersListList.add(firstList);
        adaptersListList.add(secondList);
        adaptersListList.add(thirdList);
        adaptersListList.add(fourthList);

        finalScoreList = new ArrayList<>();
        listViewList = new ArrayList<>();

        adaptersList = new ArrayList<>();

        values = new int[4];


        initView();

        final EditText et2 = (EditText) findViewById(R.id.lable_second);


        et2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    et2.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }

                if((keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK)){
                    et2.clearFocus();
                    return true;
                }
                return false;
            }
        });



        et = (EditText) findViewById(R.id.lable_first);

        et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                System.out.println("KEEEEY");
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    et.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }

                if((keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK)){
                    et.clearFocus();
                    System.out.println("CATCH BACK");
                    return true;
                }
                return false;
            }
        });



       // LinearLayout linearLayout = findViewById(R.id.)


     /*   LinearLayout myLayout = (LinearLayout) findViewById(R.id.scoreboard);
        myLayout.requestFocus();
*/
    }

    /*@Override
    public void onBackPressed() {
        System.out.println("bback");
    }*/

    private void initView() {
        certainScore = (TextView) findViewById(R.id.certain_score);

        initTwoPlayers();
        if(numberOfPlayers > 3) {
            initThirdPlayer();
            initFourthPlayer();
        } else
        if(numberOfPlayers > 2){
            initThirdPlayer();
        }

        btn_cancel = (ImageButton) findViewById(R.id.cancel);
        btn_cancel.getDrawable().setAlpha(ALPHA_87);
        btn_cancel.setClickable(false);

    }

    private void initTwoPlayers() {
        TextView firstFinalScore = (TextView) findViewById(R.id.first_final_score);
        finalScoreList.add(firstFinalScore);

        TextView secondFinalScore = (TextView) findViewById(R.id.second_final_score);
        finalScoreList.add(secondFinalScore);

        ListView firstListView = (ListView) findViewById(R.id.first_list_view);
        listViewList.add(firstListView);

        List<String> d = adaptersListList.get(0);
        ArrayAdapter<String> firstAdapter = new ArrayAdapter(this,
                R.layout.list_item, d);
        firstListView.setAdapter(firstAdapter);

        adaptersList.add(firstAdapter);

        firstListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                listClick(0);
                return false;
            }
        });

        ListView secondListView = (ListView) findViewById(R.id.second_list_view);
        listViewList.add(secondListView);

        ArrayAdapter<String> secondAdapter = new ArrayAdapter(this,
                R.layout.list_item, adaptersListList.get(1));
        secondListView.setAdapter(secondAdapter);

        adaptersList.add(secondAdapter);

        secondListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                listClick(1);
                return false;
            }
        });
    }

    private void initThirdPlayer() {
        TextView third = (TextView) findViewById(R.id.third_final_score);
        finalScoreList.add(third);

        ListView thirdListView = (ListView) findViewById(R.id.third_list_view);
        listViewList.add(thirdListView);


        ArrayAdapter<String> thirdAdapter = new ArrayAdapter(this,
                R.layout.list_item, adaptersListList.get(2));
        thirdListView.setAdapter(thirdAdapter);

        adaptersList.add(thirdAdapter);

        thirdListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                listClick(2);
                return false;
            }
        });

    }

    private void initFourthPlayer() {
        TextView fourth = (TextView) findViewById(R.id.fourth_final_score);
        finalScoreList.add(fourth);

        ListView fourthListView = (ListView) findViewById(R.id.fourth_list_view);
        listViewList.add(fourthListView);


        ArrayAdapter<String> fourthAdapter = new ArrayAdapter(this,
                R.layout.list_item, adaptersListList.get(3));
        fourthListView.setAdapter(fourthAdapter);

        adaptersList.add(fourthAdapter);

        fourthListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                listClick(3);
                return false;
            }
        });

    }

    private void listClick(int pos){

        if(gameIsOver||certainScore.getText().equals(ZERO)){
            return;
        }

        String certainText = (String) certainScore.getText();
        certainValue = Integer.valueOf(certainText);
        values[pos] = values[pos] + certainValue;
        lastAddition = (byte) (pos+1);
        btn_cancel.getDrawable().setAlpha(ALPHA_87);
        btn_cancel.setClickable(true);

        adaptersListList.get(pos).add(certainText);
        finalScoreList.get(pos).setText(String.valueOf(values[pos]));
        certainScore.setText(ZERO);

        adaptersList.get(pos).notifyDataSetChanged();
        listViewList.get(pos).setSelection(adaptersListList.get(pos).size()-1);

        if(values[pos] >= winValue){
            certainScore.setText(getString(R.string.enough));
            gameIsOver = true;
        }

    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Значение выигрыша");
        View v = getLayoutInflater().inflate(R.layout.dialog, null);

        final EditText editText = (EditText) v.findViewById(R.id.editText);

        builder.setView(v);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String s = String.valueOf(editText.getText());
                if(s.equals("")){
                    return;
                }
                int value = Integer.valueOf(s);
                editor.putInt(WIN_VALUE, value);
                editor.commit();
                winValue = value;
            }
        });
        Dialog dialog = builder.create();
        dialog.show();

    }


    public void onClick(View v){

        if(v.getId() == R.id.btn_setting){
            PopupMenu popupMenu = new PopupMenu(this, v);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    switch (item.getItemId()){
                        case R.id.two_players:
                            if(numberOfPlayers == 2)
                                break;
                            editor = sharedPreferences.edit();
                            editor.putInt(NUMBER_OF_PLAYERS, 2);
                            editor.commit();
                            MainActivity.this.recreate();
                            break;
                        case R.id.three_players:
                            if(numberOfPlayers == 3)
                                break;
                            editor = sharedPreferences.edit();
                            editor.putInt(NUMBER_OF_PLAYERS, 3);
                            editor.commit();
                            MainActivity.this.recreate();
                            break;
                        case R.id.four_players:
                            if(numberOfPlayers == 4)
                                break;
                            editor = sharedPreferences.edit();
                            editor.putInt(NUMBER_OF_PLAYERS, 4);
                            editor.commit();
                            MainActivity.this.recreate();
                            break;
                        case R.id.set_win_value:
                            showDialog();

                            break;

                    }
                    return false;
                }
            });

            popupMenu.show();
            return;
        }

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
        for (TextView ft:finalScoreList
                ) {
            ft.setText(ZERO);
        }
        for (List<String> l:adaptersListList
                ) {
            l.clear();
        }

        for (ArrayAdapter<String> aa:adaptersList
                ) {
            aa.notifyDataSetChanged();
        }

        gameIsOver = false;
    }

    public void cancelLastInput(View v){

        if(lastAddition != 0){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(R.string.question_cancel_last_enter);
            dialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(lastAddition == 1){
                        int lastValue = Integer.valueOf(adaptersListList.get(0).get(adaptersListList.get(0).size()-1));
                        values[0] = values[0] - lastValue;
                        finalScoreList.get(0).setText(String.valueOf(values[0]));
                        adaptersListList.get(0).remove(adaptersListList.get(0).get(adaptersListList.get(0).size()-1));
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
                        int lastValue = Integer.valueOf(adaptersListList.get(1).get(adaptersListList.get(1).size()-1));
                        values[1] = values[1] - lastValue;
                        finalScoreList.get(1).setText(String.valueOf(values[1]));
                        adaptersListList.get(1).remove(adaptersListList.get(1).get(adaptersListList.get(1).size()-1));
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
                        int lastValue = Integer.valueOf(adaptersListList.get(2).get(adaptersListList.get(2).size()-1));
                        values[2] = values[2] - lastValue;
                        finalScoreList.get(2).setText(String.valueOf(values[2]));
                        adaptersListList.get(2).remove(adaptersListList.get(2).get(adaptersListList.get(2).size()-1));
                        adaptersList.get(2).notifyDataSetChanged();
                        if(gameIsOver){
                            certainScore.setText(ZERO);
                            gameIsOver = false;
                        }
                        lastAddition = 0;
                        btn_cancel.getDrawable().setAlpha(ALPHA_54);
                        btn_cancel.setClickable(false);
                    }
                    if(lastAddition == 4){
                        int lastValue = Integer.valueOf(adaptersListList.get(3).get(adaptersListList.get(3).size()-1));
                        values[3] = values[3] - lastValue;
                        finalScoreList.get(3).setText(String.valueOf(values[3]));
                        adaptersListList.get(3).remove(adaptersListList.get(3).get(adaptersListList.get(3).size()-1));
                        adaptersList.get(3).notifyDataSetChanged();
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
