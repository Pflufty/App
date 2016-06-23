package com.example.domsi.sspclient;

import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by User on 21.06.2016.
 */
public class ClientThread extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
        Socket client;

        Log.d("HIT", "In Thread");

        try {
            client = new Socket("10.0.2.2", 9871);

            Log.d("HIT", "Created");

            PrintWriter outStream = new PrintWriter(client.getOutputStream(), true);
            BufferedReader inStream = new BufferedReader(new InputStreamReader(client.getInputStream()));

            System.out.println(inStream.readLine());

            while (inStream.readLine().equals("Queue/Wait")) {

            }

            Log.d("HIT", "Game Started");

            String inputFromServer;

            do {

                MainActivity.startGame();

                while(MainActivity.selected==false){

                }

                outStream.println(MainActivity.move);

                String answer = inStream.readLine();

                int winner = 0;

                if (answer.startsWith("Match/Winner/")) {
                    int index = answer.lastIndexOf("/");
                    answer = answer.substring(index + 1, answer.length());

                    String[] win = answer.split(":");
                    String[] points = win[1].split(";");

                    winner = Integer.parseInt(win[0]);

                    MainActivity.setWinnerTxt(winner, points);
                }

                inputFromServer = inStream.readLine();
            } while (inputFromServer.equals("Match/Next"));

            int index = inputFromServer.lastIndexOf("/");
            index++;
            inputFromServer = inputFromServer.substring(index, inputFromServer.length());



            client.close();
        } catch (UnknownHostException hostEx) {
            hostEx.printStackTrace();
        } catch (IOException IOEx) {
            System.err.println("No Connection");
        }
        return null;
    }
}
