package com.example.linne.grabbentest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class launchNewUser extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.twoactivites.extra.REPLY";
    private EditText userInput;
    private Intent intent;
    private int launchedIndex;
    private TextView textView;
    private String[] addUserStrings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_new_user);
        intent = getIntent();
        userInput = findViewById(R.id.editText_newUser);
        textView = findViewById(R.id.textView_addUser);
        launchedIndex = 0;
        addUserStrings = getResources().getStringArray(R.array.enter_Username);
    }


    public void onStop(){
        super.onStop();
        setResult(RESULT_CANCELED);

    }
    public void returnUser(View view) {
            String reply = userInput.getText().toString();

            if(reply.isEmpty()){

                if(launchedIndex < addUserStrings.length) {
                    launchedIndex++;
                    textView.setText(addUserStrings[launchedIndex]);
                }
            }else {
                Intent replyIntent = new Intent();
                replyIntent.putExtra(EXTRA_REPLY, reply);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
    }
}