package com.example.linne.da339a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    private void registerListeners(){

        btnTop.setOnClickListener(new ButtonListener());
        btnBottom.setOnClickListener(new ButtonListener());
        btnLeft.setOnClickListener(new ButtonListener());
        btnRight.setOnClickListener(new ButtonListener());
        btnDown.setOnClickListener(new ButtonListener());
    }
    private void initializeComponents(){
        firstText = (TextView)findViewById(R.id.textTest);
        btnTop = (Button) findViewById(R.id.btnTop);
        btnBottom = (Button) findViewById(R.id.btnBottom);
        btnDown = (Button) findViewById(R.id.btnDown);
        btnRight = (Button) findViewById(R.id.btnRight);
        btnLeft = (Button) findViewById(R.id.btnLeft);

    }

    private class ButtonListener implements View.OnClickListener{
        public void onClick(View v ){
           if(v.getId() == R.id.btnTop){

                new Sender('U').start();
                firstText.setText("Top");
                Log.e("myinfo", "top pressed");
           }else if(v.getId() == R.id.btnBottom){
               new Sender('D').start();
               firstText.setText("Bottom");
               Log.e("myinfo", "bottom pressed");
           }else if(v.getId() == R.id.btnLeft){
               firstText.setText("Left");
               new Sender('L').start();
               Log.i("myinfo", "left pressed");
            }else if(v.getId() == R.id.btnRight){
               firstText.setText("Right");
               new Sender('R').start();
               Log.e("myinfo", "right pressed");
           }else if(v.getId() == R.id.btnDown){
               firstText.setText("Down");
               new Sender('G').start();
               Log.e("myinfo", "down pressed");
           }

        }
    }
    private class Sender extends Thread{
        public char charToSend;

        public Sender(char charToBeSent){
            charToSend=charToBeSent;
        }
        public void run(){
            client.send(charToSend);
        }

    }
}
