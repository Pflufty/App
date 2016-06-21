package com.example.domsi.sspclient;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by User on 21.06.2016.
 */
public class ClientThread implements Runnable{
    @Override
    public void run() {
        try {
            Log.d("HIT", "Hey");

            MainActivity.client = new Socket("127.0.0.1", 9871);

            Log.d("HIT", MainActivity.client+"");

            MainActivity.inStream= new BufferedReader(new InputStreamReader(MainActivity.client.getInputStream()));
            MainActivity.outStream= new PrintWriter(MainActivity.client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
