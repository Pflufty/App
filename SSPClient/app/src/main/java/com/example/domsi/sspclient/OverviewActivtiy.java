package com.example.domsi.sspclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by domsi on 15.08.2016.
 */
public class OverviewActivtiy extends Activity{
    public static String username;
    public static int elo=0;
    public static int won=0;
    public static int played=0;

    public static TextView txtUsername;
    public static TextView txtElo;
    public static TextView txtWon;
    public static TextView txtPlayed;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        txtUsername= (TextView) findViewById(R.id.txtUsername);
        txtElo= (TextView) findViewById(R.id.txtElo);
        txtWon= (TextView) findViewById(R.id.txtWon);
        txtPlayed= (TextView) findViewById(R.id.txtPlayed);

        if(getIntent().getExtras().containsKey("Username")) {
            Bundle params = getIntent().getExtras();
            username = params.getString("Username");
        }else if(getIntent().getExtras().containsKey("Stats")) {
            Bundle params = getIntent().getExtras();
            int[] stats = params.getIntArray("Stats");

            elo=stats[0];
            played=stats[1];
            won=stats[2];

            Log.d("StatsGot", stats[0] + "," + stats[1] + "," + stats[2]);
            Log.d("StatsGot", elo + "," +played + "," + won);
        }

        txtUsername.setText(username);
        txtElo.setText("A");
        txtPlayed.setText(played+"");
        txtWon.setText(won+"");

        new LogInThread().execute();
    }

    public void btnPlayClicked(View view) {
        Intent queueStarted= new Intent(this, MainActivity.class);
        startActivity(queueStarted);
    }
}
