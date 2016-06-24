package com.example.domsi.sspclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by domsi on 24.06.2016.
 */
public class PreGameActivity extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregame);
    }

    public void btnJoinQueueClciked(View view) {
        Intent queueStarted= new Intent(this, MainActivity.class);
        startActivity(queueStarted);
    }
}
