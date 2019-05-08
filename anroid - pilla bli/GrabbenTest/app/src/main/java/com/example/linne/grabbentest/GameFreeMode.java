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
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class GameFreeMode extends AppCompatActivity {

    private TextView firstText;
    private TextView userNameText;
    private Button btnForward;
    private Button btnBack;
    private Button btnRight;
    private Button btnLeft;
    private Button btnDown;
    private Button btnUp;
    private Button btnGrab;
    private ClientController client;
    private String activeUser = "-1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_free_mode);
        initializeComponents();
        registerListeners();
        Intent intent = getIntent();
        String ip = intent.getStringExtra("ip");
        String mode = intent.getStringExtra("mode");



        /*if(mode.equals("Classic")){
            intializeClassicComponents();
            registerClassicComponenets();
        }else{
            initializeComponents();
            registerListeners();
            time();

        }*/



        Log.e("myinfo", ip);
        client = new ClientController(ip);



        client.send("GETNEXTUSER");

        time();

    }

    public void time(){


        Log.e("myinfo", "ny timer");


        new CountDownTimer(30000, 1000) {

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

    public void setActiveUser(String user){
        activeUser = user;
    }


    private void initializeComponents(){
        firstText = (TextView)findViewById(R.id.textTest);
        userNameText = (TextView)findViewById(R.id.textTest);
        btnForward = (Button) findViewById(R.id.btnForward);
        btnBack = (Button) this.findViewById(R.id.btnBack);
        btnDown = (Button) this.findViewById(R.id.btnDown);
        btnRight = (Button) this.findViewById(R.id.btnRight);
        btnLeft = (Button) this.findViewById(R.id.btnLeft);
        btnGrab = (Button) findViewById((R.id.btnGrab));
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
        btnGrab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e("myinfo", "grab pressed");
                    disableButtons(btnGrab);

                    new GameFreeMode.Sender("GRAB\n").start();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.e("myinfo", "grab released");
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
        btnGrab.setEnabled(false);
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
                btnGrab.setEnabled(false);
                break;
            case R.id.btnBack:
                btnRight.setEnabled(false);
                btnForward.setEnabled(false);
                btnDown.setEnabled(false);
                btnLeft.setEnabled(false);
                btnDown.setEnabled(false);
                btnUp.setEnabled(false);
                btnGrab.setEnabled(false);
                break;
            case R.id.btnLeft:
                btnRight.setEnabled(false);
                btnForward.setEnabled(false);
                btnDown.setEnabled(false);
                btnBack.setEnabled(false);
                btnDown.setEnabled(false);
                btnUp.setEnabled(false);
                btnGrab.setEnabled(false);
                break;
            case R.id.btnRight:
                btnLeft.setEnabled(false);
                btnForward.setEnabled(false);
                btnBack.setEnabled(false);
                btnDown.setEnabled(false);
                btnUp.setEnabled(false);
                btnGrab.setEnabled(false);
                break;
            case R.id.btnGrab:
                btnRight.setEnabled(false);
                btnForward.setEnabled(false);
                btnDown.setEnabled(false);
                btnBack.setEnabled(false);
                btnLeft.setEnabled(false);
                btnUp.setEnabled(false);
                break;
            case R.id.btnDown:
                btnGrab.setEnabled(false);
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
                btnGrab.setEnabled(false);
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
        btnGrab.setEnabled(true);
        btnUp.setEnabled(true);
        new GameFreeMode.Sender("RELEASE\n").start();
    }

    public void setUser(String user){
        userNameText.setText(user);
    }

    public void setController(ClientController cc){
        client = cc;

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
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Game Over");


        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });

        AlertDialog ad = alert.create();
        ad.show();

    }
}