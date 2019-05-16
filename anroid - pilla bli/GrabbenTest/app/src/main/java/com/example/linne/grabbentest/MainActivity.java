package com.example.linne.grabbentest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;



public class MainActivity extends AppCompatActivity {
    public static final int TEXT_REQUEST = 1;
    public static final int USER_REQUEST = 2;
    private TextView mReplyTextView;
    private boolean connection = true;
    private Button buttonGame;
    private Button buttonGameMode;
    private String ip = "192.168.0.2";
    private String pickedMode = "Classic";
    private String active_User = "-1";
    private View view;
    private int easteregg = 0;
    private String[] gameModes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mReplyTextView = findViewById(R.id.textView_reply);
        buttonGame = findViewById(R.id.spela);
        buttonGameMode = findViewById(R.id.mode);
        view = findViewById(R.id.mainBody);
        gameModes = getResources().getStringArray(R.array.gameModes);
        registerListeners();


    }
    public void registerListeners(){
        view.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    Log.e("myinfo", "screen pressed" );
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    easteregg++;
                    if(easteregg == 5){
                        launchSettings(v);

                        easteregg = 0;

                    }
                }
                return true;
            }
        }
    );
    }

    public void launchGame(View view){
        Log.e("myinfo", "game is starting");
        Log.e("myinfo", pickedMode.toString());

        if(pickedMode.equals("Classic")) {
            Intent gameIntent = new Intent(this, Game.class);
            gameIntent.putExtra("ip", this.ip);
            gameIntent.putExtra("active_User", active_User);

            startActivityForResult(gameIntent, USER_REQUEST );
        }else{
            Intent freeModeGameIntent = new Intent(this, GameFreeMode.class);
            freeModeGameIntent.putExtra("active_User", active_User);
            freeModeGameIntent.putExtra("ip", this.ip);
            startActivityForResult(freeModeGameIntent, USER_REQUEST);
        }

    }

    public void launchSettings(View view) {
        Log.e("myinfo", "settings clicked!");
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, TEXT_REQUEST);
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);


        Log.e("myinfo", "kommer vi hit???");

        if(requestCode == TEXT_REQUEST){
            if(resultCode == RESULT_OK){
                String reply = data.getStringExtra(SettingsActivity.EXTRA_MESSAGE);
                mReplyTextView.setText(reply);
                //connection = true;
                this.ip = reply;
                buttonGame.setEnabled(true);
            }
        }
        if(requestCode == USER_REQUEST) {
            if (resultCode == RESULT_OK) {
                this.active_User = data.getStringExtra(GameFreeMode.EXTRA_MESSAGE);
                Log.e("myinfo", "tillbaka skickad användare: " + active_User);

            }
        }
    }


    public void launchDialgo(View view) {
       alertDialogLoadFile();

    }

    private void alertDialogLoadFile(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Game mode");

        alert.setSingleChoiceItems(gameModes, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                buttonGameMode.setText("Game Mode:" + gameModes[which]);
                pickedMode = gameModes[which];
            }
        });
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog ad = alert.create();
        ad.show();

    }


}
