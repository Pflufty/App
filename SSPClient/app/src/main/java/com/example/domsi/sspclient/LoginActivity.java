package com.example.domsi.sspclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity{

    EditText editUsername;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUsername= (EditText) findViewById(R.id.editUsername);
    }

    public void btnJoinQueueClicked(View view) {
        String username="";
        username=editUsername.getText().toString();
        if(!(username.equalsIgnoreCase(""))){
            Intent loggedIn= new Intent(this, OverviewActivtiy.class);
            loggedIn.putExtra("Username", username);
            startActivity(loggedIn);
        }
    }
}
