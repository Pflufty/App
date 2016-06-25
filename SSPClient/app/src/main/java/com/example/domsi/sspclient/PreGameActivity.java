package com.example.domsi.sspclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * Created by domsi on 24.06.2016.
 */
public class PreGameActivity extends AppCompatActivity{

    EditText editUsername;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregame);

        editUsername= (EditText) findViewById(R.id.editUsername);
    }

    public void btnJoinQueueClicked(View view) {
        String username="";
        username=editUsername.getText().toString();
        Log.d("Username",username);
        if(!(username.equalsIgnoreCase(""))){
            Intent queueStarted= new Intent(this, MainActivity.class);
            queueStarted.putExtra("Username",username);
            startActivity(queueStarted);
        }
    }
}
