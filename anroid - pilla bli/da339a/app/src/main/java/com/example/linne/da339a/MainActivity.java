package com.example.linne.da339a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    private TextView firstText;
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

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.e("myinfo", "top released");
                    new Sender("RELEASED\n").start();
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

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.e("myinfo", "Down released");
                    new Sender("RELEASED\n").start();
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

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.e("myinfo", "left released");
                    new Sender("RELEASED\n").start();
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

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.e("myinfo", "right released");
                    new Sender("RELEASED\n").start();
                }
                return true;
            }
        });
        btnDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e("myinfo", "grab pressed");
                    new Sender("GRAB\n").start();

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.e("myinfo", "grab released");
                    new Sender("RELEASED\n").start();
                }
                return true;
            }
        });
    }

/*
    private class ButtonListener implements View.OnClickListener{
        public void onClick(View v ){
           if(v.getId() == R.id.btnTop){

                firstText.setText("Top");
                Log.e("myinfo", "top pressed");
           }else if(v.getId() == R.id.btnBottom){
               new Sender("DOWN\n").start();
               firstText.setText("Bottom");
               Log.e("myinfo", "bottom pressed");
           }else if(v.getId() == R.id.btnLeft){
               new Sender("LEFT\n").start();
               firstText.setText("Left");
               Log.i("myinfo", "left pressed");
            }else if(v.getId() == R.id.btnRight){
               new Sender("RIGHT\n").start();
               firstText.setText("Right");
               Log.e("myinfo", "right pressed");
           }else if(v.getId() == R.id.btnDown){
               new Sender("GRAB\n").start();
               firstText.setText("Down");
               Log.e("myinfo", "down pressed");
           }

        }
    }*/
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
