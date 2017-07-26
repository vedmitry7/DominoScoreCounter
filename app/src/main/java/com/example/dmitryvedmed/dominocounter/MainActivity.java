package com.example.dmitryvedmed.dominocounter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {


    private static final String ZERO = "0";
    private static final long VIBRATION_TIME = 25L;
    private static final int ALPHA_30 = 76;
    private static final int ALPHA_60 = 153;
    private static final int DEFAULT_WIN_VALUE = 101;
    private static final String PREFERENCES = "domino_prefs";
    private static final String NUMBER_OF_PLAYERS = "number_of_players";
    private static final String WIN_VALUE = "win_value";
    private static final String SHOW_NAMES = "show_names";
    private static final String LOCK_SCREEN = "lock_screen";
    private static final String RATE = "rate";
    private static final String RESTART_AMOUND = "restart_amound";

    private ArrayList<TextView> finalScoreList;
    private List<String> firstList, secondList, thirdList, fourthList;
    private List<List<String>> adaptersListList;
    private List<ListView> listViewList;
    private List<ArrayAdapter<String>> adaptersList;

    private int certainValue;
    private int[] values;
    private int numberOfPlayers;
    private int winValue;
    private byte lastAddition;
    private boolean gameIsOver, showName, disableLock, wasRate;
    private int restartCount;

    private SharedPreferences sharedPreferences;

    private AdView mAdView;

    private TextView certainScore;
    private ImageButton btn_cancel;

    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;
    private LinearLayout linearLayout4;

    private EditText et1;
    private EditText et2;
    private EditText et3;
    private EditText et4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        numberOfPlayers = sharedPreferences.getInt(NUMBER_OF_PLAYERS, 2);
        wasRate = sharedPreferences.getBoolean(RATE, false);
        restartCount = sharedPreferences.getInt(RESTART_AMOUND, 0);
        if (numberOfPlayers == 2) {
            setContentView(R.layout.activity_main);
        } else {
            if (numberOfPlayers == 3) {
                setContentView(R.layout.activity_for_three_players);
            } else {
                if (numberOfPlayers == 4) {
                    setContentView(R.layout.activity_for_four_players);
                }
            }
        }


        //  MobileAds.initialize(this, "ca-app-pub-2209532931509792~6001598897");

        winValue = sharedPreferences.getInt(WIN_VALUE, DEFAULT_WIN_VALUE);
        showName = sharedPreferences.getBoolean(SHOW_NAMES, false);
        disableLock = sharedPreferences.getBoolean(LOCK_SCREEN, false);

        if (disableLock) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }


        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setVisibility(View.GONE);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (mAdView.getVisibility() == View.GONE) {
                    mAdView.setVisibility(View.VISIBLE);
                }
            }
        });

        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);


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

        wasRate = false;

    }

    private static long back_pressed;

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            //super.onBackPressed();
            this.finish();
        } else
            Toast.makeText(getBaseContext(), R.string.exit,
                    LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }


    private void initView() {
        certainScore = (TextView) findViewById(R.id.certain_score);

        initTwoPlayers();
        if (numberOfPlayers > 3) {
            initThirdPlayer();
            initFourthPlayer();
        } else if (numberOfPlayers > 2) {
            initThirdPlayer();
        }

        btn_cancel = (ImageButton) findViewById(R.id.cancel);
        btn_cancel.getDrawable().setAlpha(ALPHA_30);
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


        et1 = (EditText) findViewById(R.id.lable_first);

        et1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    et1.clearFocus();
                    return true;
                }
                if ((keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    et1.clearFocus();
                    return true;
                }
                return false;
            }
        });

        et2 = (EditText) findViewById(R.id.lable_second);

        et2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    et2.clearFocus();
                    return true;
                }
                if ((keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    et2.clearFocus();
                    return true;
                }
                return false;
            }
        });

        linearLayout1 = (LinearLayout) findViewById(R.id.name_first_container);
        linearLayout2 = (LinearLayout) findViewById(R.id.name_second_container);


        if (!showName) {
            linearLayout1.setVisibility(View.GONE);
            linearLayout2.setVisibility(View.GONE);
        }

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

        et3 = (EditText) findViewById(R.id.lable_third);
        et3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    et3.clearFocus();
                    return true;
                }
                if ((keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    et3.clearFocus();
                    return true;
                }
                return false;
            }
        });

        linearLayout3 = (LinearLayout) findViewById(R.id.name_third_container);
        if (!showName) {
            linearLayout3.setVisibility(View.GONE);
        }
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

        et4 = (EditText) findViewById(R.id.lable_fourth);
        et4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    et4.clearFocus();
                    return true;
                }

                if ((keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    et4.clearFocus();
                    return true;
                }
                return false;
            }
        });

        linearLayout4 = (LinearLayout) findViewById(R.id.name_fourth_container);
        if (!showName) {
            linearLayout4.setVisibility(View.GONE);
        }
    }

    private void listClick(int pos) {

        if (gameIsOver || certainScore.getText().equals(ZERO)) {
            return;
        }

        String certainText = (String) certainScore.getText();
        certainValue = Integer.valueOf(certainText);
        values[pos] = values[pos] + certainValue;
        lastAddition = (byte) (pos + 1);
        btn_cancel.getDrawable().setAlpha(ALPHA_60);
        btn_cancel.setClickable(true);

        adaptersListList.get(pos).add(certainText);
        finalScoreList.get(pos).setText(String.valueOf(values[pos]));
        certainScore.setText(ZERO);

        adaptersList.get(pos).notifyDataSetChanged();
        listViewList.get(pos).setSelection(adaptersListList.get(pos).size() - 1);

        if (values[pos] >= winValue) {
            certainScore.setText(getString(R.string.enough));
            gameIsOver = true;
        }

    }

    private void showDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(false);
        builder.setTitle(R.string.win_value);
        View v = getLayoutInflater().inflate(R.layout.dialog, null);

        final EditText editText = (EditText) v.findViewById(R.id.editText);
        editText.setHint(String.valueOf(winValue));
        builder.setView(v);
        builder.setPositiveButton("ok",null);

        /*builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String s = String.valueOf(editText.getText());
                if (s.equals("")) {
                    return;
                }
                int value = Integer.valueOf(s);
                if(value<10){
                    Toast.makeText(getApplicationContext(), "ERROR! Minimum 10", Toast.LENGTH_SHORT).show();
                    return;
                }
                editor.putInt(WIN_VALUE, value);
                editor.commit();
                winValue = value;
            }
        });*/
        final Dialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        String s = String.valueOf(editText.getText());
                        if (s.equals("")) {
                            return;
                        }
                        int value = Integer.valueOf(s);
                        if(value<10){
                            Toast.makeText(getApplicationContext(), "ERROR! Minimum 10", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        editor.putInt(WIN_VALUE, value);
                        editor.commit();
                        winValue = value;
                        dialog.dismiss();
                    }
                });
            }
        });
        dialog.show();
    }


    public void onClick(View v) {

        et1.clearFocus();

        if (v.getId() == R.id.btn_setting) {
            PopupMenu popupMenu = new PopupMenu(this, v);
            popupMenu.inflate(R.menu.popup_menu);

            final MenuItem names = popupMenu.getMenu().findItem(R.id.show_names);
            if (showName) {
                names.setChecked(true);
            } else {
                names.setChecked(false);
            }

            final MenuItem lock = popupMenu.getMenu().findItem(R.id.lock);
            if (disableLock) {
                lock.setChecked(true);
            } else {
                lock.setChecked(false);
            }


            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    switch (item.getItemId()) {

                        case R.id.win_value:
                            showDialog();
                            break;
                        case R.id.two_players:
                            if (numberOfPlayers == 2)
                                break;
                            editor = sharedPreferences.edit();
                            editor.putInt(NUMBER_OF_PLAYERS, 2);
                            editor.commit();
                            MainActivity.this.recreate();
                            break;
                        case R.id.three_players:
                            if (numberOfPlayers == 3)
                                break;
                            editor = sharedPreferences.edit();
                            editor.putInt(NUMBER_OF_PLAYERS, 3);
                            editor.commit();
                            MainActivity.this.recreate();
                            break;
                        case R.id.four_players:
                            if (numberOfPlayers == 4)
                                break;
                            editor = sharedPreferences.edit();
                            editor.putInt(NUMBER_OF_PLAYERS, 4);
                            editor.commit();
                            MainActivity.this.recreate();
                            break;
                        case R.id.show_names:
                            if (showName) {
                                editor.putBoolean(SHOW_NAMES, false);
                                names.setChecked(false);
                                editor.commit();
                                showName = false;
                                linearLayout1.setVisibility(View.GONE);
                                linearLayout2.setVisibility(View.GONE);
                                if (linearLayout3 != null)
                                    linearLayout3.setVisibility(View.GONE);
                                if (linearLayout4 != null)
                                    linearLayout4.setVisibility(View.GONE);
                            } else if (!showName) {
                                editor.putBoolean(SHOW_NAMES, true);
                                names.setChecked(true);
                                showName = true;
                                editor.commit();
                                linearLayout1.setVisibility(View.VISIBLE);
                                linearLayout2.setVisibility(View.VISIBLE);
                                if (linearLayout3 != null)
                                    linearLayout3.setVisibility(View.VISIBLE);
                                if (linearLayout4 != null)
                                    linearLayout4.setVisibility(View.VISIBLE);
                            }
                            break;
                        case R.id.lock:
                            if (disableLock) {
                                lock.setChecked(false);
                                disableLock = false;
                                editor.putBoolean(LOCK_SCREEN, false);
                                editor.commit();
                                getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                            } else {
                                lock.setChecked(true);
                                disableLock = true;
                                editor.putBoolean(LOCK_SCREEN, true);
                                editor.commit();
                                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                            }
                            break;
                    }
                    return false;
                }
            });

            popupMenu.show();
            return;
        }

        if (v.getId() == R.id.backspace) {

            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATION_TIME);

            if (!certainScore.getText().equals("") && !certainScore.getText().equals(getString(R.string.enough))) {
                certainScore.setText(((String) certainScore.getText()).substring(0, certainScore.getText().length() - 1));
            }
            if (certainScore.getText().equals(""))
                certainScore.setText(ZERO);
            return;
        }

        if (gameIsOver)
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
        if (certainText.startsWith(ZERO) && certainText.length() > 1) {
            certainText = certainText.substring(1, certainText.length());
            certainScore.setText(certainText);
        }

        if (!certainScore.getText().equals("") && !certainScore.getText().equals(getString(R.string.enough))) {
            certainValue = Integer.valueOf((String) certainScore.getText());
            if (certainValue >= winValue) {
                certainScore.setText(((String) certainScore.getText()).substring(0, certainScore.getText().length() - 1));
                return;
            }
        }

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(VIBRATION_TIME);

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mAdView != null)
            mAdView.resume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mAdView != null)
            mAdView.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mAdView != null)
            mAdView.pause();
    }

    public void restart(View v) {

        if (gameIsOver) {
            setToZero();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(R.string.question_new_game);
            dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    setToZero();
                    btn_cancel.getDrawable().setAlpha(ALPHA_30);
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

    private void setToZero() {
        values = new int[4];
        certainScore.setText(ZERO);
        certainValue = 0;
        for (TextView ft : finalScoreList
                ) {
            ft.setText(ZERO);
        }
        for (List<String> l : adaptersListList
                ) {
            l.clear();
        }

        for (ArrayAdapter<String> aa : adaptersList
                ) {
            aa.notifyDataSetChanged();
        }

        gameIsOver = false;


        SharedPreferences.Editor editor = sharedPreferences.edit();
        restartCount++;
        editor.putInt(RESTART_AMOUND, restartCount);
        editor.commit();
        System.out.println("RETE - " + restartCount);

        if (restartCount % 5 == 0) {
            System.out.println("/5!!!!");
        }

        if (!wasRate) {
            if (restartCount % 5 == 0) {
                rateDialog();
            }
        }
    }

    public void cancelLastInput(View v) {

        if (lastAddition != 0) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(R.string.question_cancel_last_enter);
            dialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (lastAddition == 1) {
                        int lastValue = Integer.valueOf(adaptersListList.get(0).get(adaptersListList.get(0).size() - 1));
                        values[0] = values[0] - lastValue;
                        finalScoreList.get(0).setText(String.valueOf(values[0]));
                        adaptersListList.get(0).remove(adaptersListList.get(0).get(adaptersListList.get(0).size() - 1));
                        adaptersList.get(0).notifyDataSetChanged();
                        if (gameIsOver) {
                            certainScore.setText(ZERO);
                            gameIsOver = false;
                        }
                        lastAddition = 0;
                        btn_cancel.getDrawable().setAlpha(ALPHA_30);
                        btn_cancel.setClickable(false);
                    }
                    if (lastAddition == 2) {
                        int lastValue = Integer.valueOf(adaptersListList.get(1).get(adaptersListList.get(1).size() - 1));
                        values[1] = values[1] - lastValue;
                        finalScoreList.get(1).setText(String.valueOf(values[1]));
                        adaptersListList.get(1).remove(adaptersListList.get(1).get(adaptersListList.get(1).size() - 1));
                        adaptersList.get(1).notifyDataSetChanged();
                        if (gameIsOver) {
                            certainScore.setText(ZERO);
                            gameIsOver = false;
                        }
                        lastAddition = 0;
                        btn_cancel.getDrawable().setAlpha(ALPHA_30);
                        btn_cancel.setClickable(false);
                    }
                    if (lastAddition == 3) {
                        int lastValue = Integer.valueOf(adaptersListList.get(2).get(adaptersListList.get(2).size() - 1));
                        values[2] = values[2] - lastValue;
                        finalScoreList.get(2).setText(String.valueOf(values[2]));
                        adaptersListList.get(2).remove(adaptersListList.get(2).get(adaptersListList.get(2).size() - 1));
                        adaptersList.get(2).notifyDataSetChanged();
                        if (gameIsOver) {
                            certainScore.setText(ZERO);
                            gameIsOver = false;
                        }
                        lastAddition = 0;
                        btn_cancel.getDrawable().setAlpha(ALPHA_30);
                        btn_cancel.setClickable(false);
                    }
                    if (lastAddition == 4) {
                        int lastValue = Integer.valueOf(adaptersListList.get(3).get(adaptersListList.get(3).size() - 1));
                        values[3] = values[3] - lastValue;
                        finalScoreList.get(3).setText(String.valueOf(values[3]));
                        adaptersListList.get(3).remove(adaptersListList.get(3).get(adaptersListList.get(3).size() - 1));
                        adaptersList.get(3).notifyDataSetChanged();
                        if (gameIsOver) {
                            certainScore.setText(ZERO);
                            gameIsOver = false;
                        }
                        lastAddition = 0;
                        btn_cancel.getDrawable().setAlpha(ALPHA_30);
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

    private void rateDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.rate_dialog_title);
        dialog.setMessage(R.string.rate_dialog_message);
        dialog.setPositiveButton(R.string.rate, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(RATE, true);
                wasRate = true;
                editor.commit();

                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
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