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


/*
 * Author : Linnea Dahlgren, Sara Svensson
 *
 * */
public class MainActivity extends AppCompatActivity {
    public static final int TEXT_REQUEST = 1;
    public static final int USER_REQUEST = 2;
    private TextView mReplyTextView;
    private boolean connection = true;
    private Button buttonGame;
    private Button buttonGameMode;
    private String ip = "192.168.0.60";
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
    /**
     * Registrera lyssnare på skärmen av mainActivity så ifall du trycker
     * 5 gånger på skärmen så öppnas en inställningsActivitet
     *
     * */
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

    /*
    *
    * LaunchGame anroppas när man trycker på en av knapparna (GameMode eller Play)
    * Metoden hanterar eventuella val som gjorts av GameMode.
    * */
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

/*
* Starta inställnings aktiviteten för att få resultat.
*
* */
    public void launchSettings(View view) {
        Log.e("myinfo", "settings clicked!");
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, TEXT_REQUEST);
    }




/*
*Denna metoden anropas när en aktivitet avslutas
*
* */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        Log.e("myinfo", "aktivitet avslutad, tillbaka på main");

        /*
        *Första if-satsen hanterar resultatet från inställnings aktiviteten
        * sätter ip-adressen till den nya.
        * */
        if(requestCode == TEXT_REQUEST){
            if(resultCode == RESULT_OK){
                String reply = data.getStringExtra(SettingsActivity.EXTRA_MESSAGE);
               // mReplyTextView.setText(reply);
                Log.e("myinfo", "ny inmatad ip-adress" + reply);
                this.ip = reply;
                buttonGame.setEnabled(true);
            }
        }
        /*
        * denna ifsatsen hanterar användarnamn från aktivitetrna
        * */
        if(requestCode == USER_REQUEST) {
            if (resultCode == RESULT_OK) {
                this.active_User = data.getStringExtra(GameFreeMode.EXTRA_MESSAGE);
                Log.e("myinfo", "tillbaka skickad användare: " + active_User);

            }
        }
    }

    //startar dialogrutan som låter en välja vilket spelmode man vill spela
    public void launchDialgo(View view) {
       alertDialogLoadFile();

    }

    /*
    * alertDialogLoadFile hanterar dialogrutan "Game Mode" som har två singleChoice item (FreeMode och Classic)
    * om man trycker på cancel så blir classic vald.
    *
    *
    * */
    private void alertDialogLoadFile(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        alert.setTitle("Game mode");

        alert.setSingleChoiceItems(gameModes, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pickedMode = gameModes[which];
                buttonGameMode.setText("Game Mode:" + gameModes[which]);
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
                pickedMode = gameModes[0];
                buttonGameMode.setText("Game Mode:" + gameModes[0]);
                dialog.cancel();
            }
        });

        AlertDialog ad = alert.create();
        ad.setCanceledOnTouchOutside(false);
        ad.show();
        ad.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.pink));
        ad.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.pink));




    }


}
