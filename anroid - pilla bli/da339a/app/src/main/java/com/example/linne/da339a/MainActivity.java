package com.example.linne.da339a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    private TextView firstText;
    private TextView userNameText;
    private Button btnTop;
    private Button btnBottom;
    private Button btnRight;
    private Button btnLeft;
    private Button btnDown;
    private ClientController client;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
        registerListeners();
        client = new ClientController();


    }
    private void initializeComponents(){
        firstText = (TextView)findViewById(R.id.textTest);
        userNameText = (TextView)findViewById(R.id.textTest);
        btnTop = (Button) findViewById(R.id.btnTop);
        btnBottom = (Button) findViewById(R.id.btnBottom);
        btnDown = (Button) findViewById(R.id.btnDown);
        btnRight = (Button) findViewById(R.id.btnRight);
        btnLeft = (Button) findViewById(R.id.btnLeft);

    }

    private void registerListeners(){

        btnTop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e("myinfo", "top pressed");
                    new Sender("UP\n").start();
                    btnBottom.setEnabled(false);
                    btnDown.setEnabled(false);
                    btnLeft.setEnabled(false);
                    btnRight.setEnabled(false);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.e("myinfo", "top released");
                    sendReleased();
                }
                return true;
            }
        });

        btnBottom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e("myinfo", "down pressed");
                    new Sender("DOWN\n").start();
                    btnRight.setEnabled(false);
                    btnTop.setEnabled(false);
                    btnDown.setEnabled(false);
                    btnLeft.setEnabled(false);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.e("myinfo", "Down released");
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
                    btnDown.setEnabled(false);
                    btnBottom.setEnabled(false);
                    btnTop.setEnabled(false);
                    btnRight.setEnabled(false);

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
                    btnTop.setEnabled(false);
                    btnBottom.setEnabled(false);
                    btnDown.setEnabled(false);
                    btnLeft.setEnabled(false);

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
                    Log.e("myinfo", "grab pressed");
                    btnRight.setEnabled(false);
                    btnLeft.setEnabled(false);
                    btnBottom.setEnabled(false);
                    btnTop.setEnabled(false);
                    new Sender("GRAB\n").start();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.e("myinfo", "grab released");
                    sendReleased();
                }
                return true;
            }
        });
    }
    public void sendReleased(){

        btnDown.setEnabled(true);
        btnRight.setEnabled(true);
        btnLeft.setEnabled(true);
        btnBottom.setEnabled(true);
        btnTop.setEnabled(true);
        new Sender("RELEASED\n").start();
    }

    public void setUser(String user){
        userNameText.setText(user);
    }
    private class Sender extends Thread{
        public String stringToSend;

        public Sender(String stringToBeSent){
            stringToSend=stringToBeSent;
        }
        public void run(){
            client.send(stringToSend);
        }

    }
}
