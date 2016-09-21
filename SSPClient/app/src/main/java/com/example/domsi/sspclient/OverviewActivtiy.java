package com.example.domsi.sspclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by domsi on 15.08.2016.
 */
public class OverviewActivtiy extends Activity{
    public static String username="";
    public static int elo=0;
    public static int won=0;
    public static int played=0;

    public static TextView txtUsername;
    public static TextView txtElo;
    public static TextView txtWon;
    public static TextView txtPlayed;

    public static Socket client=null;
    public static PrintWriter outStream=null;
    public static BufferedReader inStream=null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        txtUsername= (TextView) findViewById(R.id.txtUsername);
        txtElo= (TextView) findViewById(R.id.txtElo);
        txtPlayed= (TextView) findViewById(R.id.txtPlayed);
        txtWon= (TextView) findViewById(R.id.txtWon);

        if(getIntent().getExtras().containsKey("Username")) {
            Bundle params = getIntent().getExtras();
            username = params.getString("Username");

            txtUsername.setText(username);
        }else if(getIntent().getExtras().containsKey("Stats")) {
            Bundle params = getIntent().getExtras();
            int[] stats = params.getIntArray("Stats");

            elo=stats[0];
            played=stats[1];
            won=stats[2];

            txtElo.setText(elo+"");
            txtPlayed.setText(played+"");
            txtWon.setText(won+"");
        }

        new LogedInThread().execute();
    }

    public void btnPlayClicked(View view) {
        Intent queueStarted= new Intent(this, MainActivity.class);
        startActivity(queueStarted);
    }
}
