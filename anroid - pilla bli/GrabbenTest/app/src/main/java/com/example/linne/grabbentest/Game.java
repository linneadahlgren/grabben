package com.example.linne.grabbentest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Game extends AppCompatActivity implements UpdateUser{
    public static final String EXTRA_MESSAGE = "com.example.android.twoactivities.extra.MESSAGE";

    private TextView firstText;
    private TextView userNameText;
    private Button btnForward;
    private Button btnBack;
    private Button btnRight;
    private Button btnLeft;
    private Button btnGrab;
    private ClientController client;
    public static final int TEXT_REQUEST = 1;

    private String active_User = "-1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initializeComponents();
        registerListeners();

        Intent intent = getIntent();
        String ip = intent.getStringExtra("ip");
        Log.e("myinfo", ip);

        String temp = intent.getStringExtra("active_User");
        active_User = temp;
        client = new ClientController(ip, this,temp);


    }
    private void initializeComponents(){
        firstText = (TextView)findViewById(R.id.textTest);
        userNameText = (TextView)findViewById(R.id.user_textview);
        btnForward = (Button) findViewById(R.id.btnForward);
        btnBack = (Button) this.findViewById(R.id.btnBack);
        btnGrab = (Button) this.findViewById(R.id.btnGrab);
        btnRight = (Button) this.findViewById(R.id.btnRight);
        btnLeft = (Button) this.findViewById(R.id.btnLeft);

    }

    protected void onStop(){
        super.onStop();
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_MESSAGE,  active_User);
        Log.e("myinfo", "skickar med användarnamn till mainactivity" + active_User);
        setResult(RESULT_OK, replyIntent);
    }
    private void registerListeners(){
        Log.e("myinfo", "registrera lyssnare");

        btnForward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e("myinfo", "FORWARD pressed");
                    new Sender("FORWARD\n").start();
                    disableButtons(btnForward);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.e("myinfo", "FORWARD released");
                    sendReleased();
                }
                return true;
            }
        });

        btnBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e("myinfo", "BACK pressed");
                    new Sender("BACK\n").start();
                    disableButtons(btnBack);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.e("myinfo", "BACK released");
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
                    new Sender("LEFT\n").start();
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
                    new Sender("RIGHT\n").start();
                    disableButtons(btnRight);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.e("myinfo", "right released");
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
                    new Sender("GRAB\n").start();
                    disableButtons(btnGrab);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.e("myinfo", "grab released");
                    sendReleased();
                }
                return true;
            }
        });
    }

    public void disableButtons(Button pressedButton) {
        switch (pressedButton.getId()) {

            case R.id.btnForward:
                btnBack.setEnabled(false);
                btnLeft.setEnabled(false);
                btnRight.setEnabled(false);
                btnGrab.setEnabled(false);
                break;
            case R.id.btnBack:
                btnRight.setEnabled(false);
                btnForward.setEnabled(false);
                btnLeft.setEnabled(false);
                btnGrab.setEnabled(false);
                break;
            case R.id.btnLeft:
                btnRight.setEnabled(false);
                btnForward.setEnabled(false);
                btnBack.setEnabled(false);
                btnGrab.setEnabled(false);
                break;
            case R.id.btnRight:
                btnLeft.setEnabled(false);
                btnForward.setEnabled(false);
                btnBack.setEnabled(false);
                btnGrab.setEnabled(false);
                break;
            case R.id.btnGrab:
                btnRight.setEnabled(false);
                btnForward.setEnabled(false);
                btnLeft.setEnabled(false);
                btnBack.setEnabled(false);
                break;
        }
    }
    public void sendReleased(){

        btnGrab.setEnabled(true);
        btnRight.setEnabled(true);
        btnLeft.setEnabled(true);
        btnBack.setEnabled(true);
        btnForward.setEnabled(true);
        new Sender("RELEASE\n").start();
    }

    public void setUser(String user){
        userNameText.setText(user);
    }

    public void setController(ClientController cc){
        client = cc;

    }

    @Override
    public void newUser(String user) {
        active_User = user;
/*
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userNameText.setText(active_User);

                Log.e("myinfo", active_User);
            }
        });
*/
    }
    public void disableButtons(){
        btnGrab.setEnabled(false);
        btnRight.setEnabled(false);
        btnLeft.setEnabled(false);
        btnBack.setEnabled(false);
        btnForward.setEnabled(false);
    }
    public void noUser(){
       // Intent intent = new Intent(this, launchNewUser.class);
        //startActivityForResult(intent, TEXT_REQUEST);

        active_User = "-1";
        Log.e("myinfo", "no user == -1");
        btnGrab.setEnabled(true);
        btnRight.setEnabled(true);
        btnLeft.setEnabled(true);
        btnBack.setEnabled(true);
        btnForward.setEnabled(true);

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){

     /*
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_CANCELED){
            finish();
        }
        if(requestCode == TEXT_REQUEST){
            if(resultCode == RESULT_OK){
                String result =data.getStringExtra(launchNewUser.EXTRA_REPLY);
                newUser(result);
                client.send("NEWUSER:"+ result + "\n");
            }
        }
        */
    }
    private class Sender extends Thread{
        public String stringToSend;


        public Sender(String stringToBeSent){
            firstText.setText(stringToBeSent);
            stringToSend=stringToBeSent;
        }
        public void run(){
            client.send(stringToSend);
        }

    }
}
