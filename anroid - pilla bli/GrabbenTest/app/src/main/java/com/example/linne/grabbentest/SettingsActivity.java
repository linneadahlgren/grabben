package com.example.linne.grabbentest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
/*
 * Author : Linnea Dahlgren
 * */
public class SettingsActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.android.twoactivities.extra.MESSAGE";
    private EditText ipReply;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ipReply = findViewById(R.id.editText_IP);

    }

    public void returnReply(View view){
        Intent replyIntent = new Intent();
        String reply =  ipReply.getText().toString();

        replyIntent.putExtra(EXTRA_MESSAGE, reply);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}
