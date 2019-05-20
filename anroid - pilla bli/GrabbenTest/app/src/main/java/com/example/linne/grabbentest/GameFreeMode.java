package com.example.linne.grabbentest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class GameFreeMode extends AppCompatActivity implements UpdateUser{
    public static final String EXTRA_MESSAGE = "com.example.android.twoactivities.extra.MESSAGE";

    private TextView firstText;
    private TextView userNameText;
    private Button btnForward;
    private Button btnBack;
    private Button btnRight;
    private Button btnLeft;
    private Button btnDown;
    private Button btnUp;
    private Button btnClose;
    private Button btnOpen;
    private ClientController client;
    private String active_User;
    public static final int TEXT_REQUEST = 1;
    private CountDownTimer countdown;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_free_mode);
        initializeComponents();
        registerListeners();
        Intent intent = getIntent();
        String ip = intent.getStringExtra("ip");

        String temp = intent.getStringExtra("active_User");
        Log.e("myinfo", "tog emot användarnamn : " + temp + " från mainActivity" );
        client = new ClientController(ip, this, temp);




    }

    public void time(){


        Log.e("myinfo", "ny timer");


        countdown = new CountDownTimer(1000000, 1000) {

            public void onTick(long millisUntilFinished) {
                firstText.setText(" " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        disableButtons();
                        alertDialogLoadFile();
                    }
                });


            }
        }.start();

    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.e("myinfo", "on stop");
        if(countdown != null) {
            countdown.cancel();
            Log.e("myinfo", "countdown cancel");
        }


    }
    public void setActiveUser(String user){
        active_User = user;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userNameText.setText(active_User);

                Log.e("myinfo", active_User);
            }
        });
    }

    public void noUser(){
        Log.e("myinfo", "no user, launching new user activity");
        Intent intent = new Intent(this, launchNewUser.class);
        startActivityForResult(intent, TEXT_REQUEST);
    }

    private void initializeComponents(){
        firstText = (TextView)findViewById(R.id.textView_Title);
        userNameText = (TextView)findViewById(R.id.user_textview);
        btnForward = (Button) findViewById(R.id.btnForward);
        btnBack = (Button) this.findViewById(R.id.btnBack);
        btnDown = (Button) this.findViewById(R.id.btnDown);
        btnRight = (Button) this.findViewById(R.id.btnRight);
        btnLeft = (Button) this.findViewById(R.id.btnLeft);
        btnOpen = (Button) findViewById((R.id.btnOpen));
        btnClose = (Button) findViewById((R.id.btnClose));
        btnUp = (Button) findViewById(R.id.btnUp);


    }

    private void registerListeners(){
        Log.e("myinfo", "registrera lyssnare");

        btnForward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e("myinfo", "forward pressed");
                    new GameFreeMode.Sender("FORWARD\n").start();
                   disableButtons(btnForward);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.e("myinfo", "forward released");
                    sendReleased();
                }
                return true;
            }
        });

        btnBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e("myinfo", "back pressed");
                    new GameFreeMode.Sender("BACK\n").start();
                    disableButtons(btnBack);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.e("myinfo", "back released");
                    sendReleased();
                }
                return true;
            }
        });
        btnLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e("myinfo", "left pressed");
                    new GameFreeMode.Sender("LEFT\n").start();
                    disableButtons(btnLeft);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.e("myinfo", "left released");
                    sendReleased();
                }
                return true;
            }
        });
        btnRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e("myinfo", "right pressed");
                    new GameFreeMode.Sender("RIGHT\n").start();
                    disableButtons(btnRight);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.e("myinfo", "right released");
                    sendReleased();
                }
                return true;
            }
        });
        btnDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e("myinfo", "down pressed");
                    disableButtons(btnDown);
                    new GameFreeMode.Sender("DOWN\n").start();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.e("myinfo", "down released");
                    sendReleased();
                }
                return true;
            }
        });
        btnUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e("myinfo", "up pressed");
                    disableButtons(btnUp);

                    new GameFreeMode.Sender("UP\n").start();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.e("myinfo", "UP released");
                    sendReleased();
                }
                return true;
            }
        });
        btnOpen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e("myinfo", "open pressed");
                    disableButtons(btnOpen);


                        new GameFreeMode.Sender("OPEN\n").start();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.e("myinfo", "open released");
                    sendReleased();
                }
                return true;
            }
        });
        btnClose.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e("myinfo", "close pressed");
                    disableButtons(btnClose);

                    new GameFreeMode.Sender("CLOSE\n").start();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.e("myinfo", "close released");
                    sendReleased();
                }

                return true;
            }
        });


    }

    public void disableButtons(){
        btnBack.setEnabled(false);
        btnDown.setEnabled(false);
        btnLeft.setEnabled(false);
        btnRight.setEnabled(false);
        btnDown.setEnabled(false);
        btnUp.setEnabled(false);
        btnOpen.setEnabled(false);
        btnClose.setEnabled(false);
        btnForward.setEnabled(false);
    }
    public void disableButtons(Button pressedButton){
        switch (pressedButton.getId()){

            case R.id.btnForward:
                btnBack.setEnabled(false);
                btnDown.setEnabled(false);
                btnLeft.setEnabled(false);
                btnRight.setEnabled(false);
                btnDown.setEnabled(false);
                btnUp.setEnabled(false);
                btnOpen.setEnabled(false);
                btnClose.setEnabled(false);
                break;
            case R.id.btnBack:
                btnRight.setEnabled(false);
                btnForward.setEnabled(false);
                btnDown.setEnabled(false);
                btnLeft.setEnabled(false);
                btnDown.setEnabled(false);
                btnUp.setEnabled(false);
                btnOpen.setEnabled(false);
                btnClose.setEnabled(false);
                break;
            case R.id.btnLeft:
                btnRight.setEnabled(false);
                btnForward.setEnabled(false);
                btnDown.setEnabled(false);
                btnBack.setEnabled(false);
                btnDown.setEnabled(false);
                btnUp.setEnabled(false);
                btnOpen.setEnabled(false);
                btnClose.setEnabled(false);
                break;
            case R.id.btnRight:
                btnLeft.setEnabled(false);
                btnForward.setEnabled(false);
                btnBack.setEnabled(false);
                btnDown.setEnabled(false);
                btnUp.setEnabled(false);
                btnOpen.setEnabled(false);
                btnClose.setEnabled(false);
                break;
            case R.id.btnOpen:
                btnRight.setEnabled(false);
                btnForward.setEnabled(false);
                btnDown.setEnabled(false);
                btnBack.setEnabled(false);
                btnLeft.setEnabled(false);
                btnUp.setEnabled(false);
                btnClose.setEnabled(false);
                break;
            case R.id.btnDown:
                btnOpen.setEnabled(false);
                btnClose.setEnabled(false);
                btnRight.setEnabled(false);
                btnForward.setEnabled(false);
                btnBack.setEnabled(false);
                btnUp.setEnabled(false);
                btnLeft.setEnabled(false);
                break;
            case R.id.btnUp:
                btnRight.setEnabled(false);
                btnForward.setEnabled(false);
                btnDown.setEnabled(false);
                btnBack.setEnabled(false);
                btnOpen.setEnabled(false);
                btnClose.setEnabled(false);
                btnLeft.setEnabled(false);
                break;
            case R.id.btnClose:
                btnRight.setEnabled(false);
                btnForward.setEnabled(false);
                btnDown.setEnabled(false);
                btnBack.setEnabled(false);
                btnOpen.setEnabled(false);
                btnUp.setEnabled(false);
                btnLeft.setEnabled(false);
                break;
        }
    }

    public void sendReleased(){

        btnDown.setEnabled(true);
        btnRight.setEnabled(true);
        btnLeft.setEnabled(true);
        btnBack.setEnabled(true);
        btnForward.setEnabled(true);
        btnOpen.setEnabled(true);
        btnClose.setEnabled(true);
        btnUp.setEnabled(true);
        new GameFreeMode.Sender("RELEASE\n").start();
    }


    public void setController(ClientController cc){
        client = cc;

    }

    @Override
    public void newUser(String user) {
        active_User = user;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userNameText.setText(active_User);

                Log.e("myinfo", "new active user: " + active_User);
                time();


            }
        });


    }

    private class Sender extends Thread{
        public String stringToSend;


        public Sender(String stringToBeSent){
         //   firstText.setText(stringToBeSent);
            stringToSend=stringToBeSent;
        }
        public void run(){
            client.send(stringToSend);
        }

    }

    private void alertDialogLoadFile(){
        sendReleased();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Game Over");
        alert.setCancelable(false);
        disableButtons();

        alert.setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



                Intent replyIntent = new Intent();
                replyIntent.putExtra(EXTRA_MESSAGE,  active_User);
                Log.e("myinfo", "skickar med användarnamn till mainactivity" + active_User);
                setResult(RESULT_OK, replyIntent);

                finish();
            }
        });

        alert.setNegativeButton("Next Player", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent replyIntent = new Intent();
                replyIntent.putExtra(EXTRA_MESSAGE, "-1");
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });

        AlertDialog ad = alert.create();
        ad.show();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_CANCELED){
            disableButtons();
        }
        if(requestCode == TEXT_REQUEST){
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra(launchNewUser.EXTRA_REPLY);
                newUser(result);
                client.send("NEWUSER:"+ result + "\n");
            }
        }

    }
}