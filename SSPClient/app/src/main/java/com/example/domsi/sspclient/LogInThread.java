package com.example.domsi.sspclient;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by domsi on 15.08.2016.
 */

    public class LogInThread extends AsyncTask<Void, Void, Void>{
    @Override
    protected Void doInBackground(Void... params) {

        try {
            MainActivity.client = new Socket("10.0.2.2", 9871);

            MainActivity.outStream = new PrintWriter(MainActivity.client.getOutputStream(), true);
            MainActivity.inStream = new BufferedReader(new InputStreamReader(MainActivity.client.getInputStream()));

            MainActivity.outStream.println("Connection/"+OverviewActivtiy.username);

            String input=MainActivity.inStream.readLine();
            if(input.startsWith("Game/Stats/")){
                String[] stats=input.split("/");
                OverviewActivtiy.elo=Integer.parseInt(stats[2]);
                OverviewActivtiy.played=Integer.parseInt(stats[3]);
                OverviewActivtiy.won=Integer.parseInt(stats[4]);
                super.publishProgress();
                this.cancel(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onProgressUpdate(Void... values) {
        OverviewActivtiy.txtUsername.setText(OverviewActivtiy.username);
        OverviewActivtiy.txtElo.setText(OverviewActivtiy.elo+"");
        OverviewActivtiy.txtPlayed.setText(OverviewActivtiy.played+"");
        OverviewActivtiy.txtWon.setText(OverviewActivtiy.won+"");
    }
}
