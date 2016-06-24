package com.example.domsi.sspclient;

import android.content.DialogInterface;
import android.content.Intent;
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
public class ClientThread extends AsyncTask<Void, String, Integer> {

    @Override
    protected Integer doInBackground(Void... params) {
        Socket client;

        int gameWinner=0;

        Log.d("HIT", "In Thread");

        try {
            client = new Socket("10.0.2.2", 9871);

            Log.d("HIT", "Created");

            PrintWriter outStream = new PrintWriter(client.getOutputStream(), true);
            BufferedReader inStream = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String input;

            do{
                input=inStream.readLine();
            }while(input.equals("Queue/Wait"));

            Log.d("HIT", "Match Started");

            int indexPlayerNr=input.lastIndexOf("/");
            indexPlayerNr++;
            input=input.substring(indexPlayerNr, input.length());
            MainActivity.PlayerNr=Integer.parseInt(input);

            Log.d("HIT", "Game Started");

            String inputFromServer;

            do {

                MainActivity.startGame();

                while(MainActivity.selected==false){

                }

                outStream.println(MainActivity.move);

                String answer = inStream.readLine();

                if (answer.startsWith("Match/Winner/")) {
                    int index = answer.lastIndexOf("/");
                    answer = answer.substring(index + 1, answer.length());

                    String[] win = answer.split(":");

                    super.publishProgress(win);
                }

                inputFromServer = inStream.readLine();
            } while (inputFromServer.equals("Match/Next"));

            int index = inputFromServer.lastIndexOf("/");
            index++;
            inputFromServer = inputFromServer.substring(index, inputFromServer.length());
            gameWinner=Integer.parseInt(inputFromServer);

            client.close();
        } catch (UnknownHostException hostEx) {
            hostEx.printStackTrace();
        } catch (IOException IOEx) {
            System.err.println("No Connection");
        }
        return gameWinner;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        String[] points = values[1].split(";");

        String winner=values[0];

        int bla=Integer.parseInt(winner);

        MainActivity.setWinnerTxt(bla, points);
    }

    @Override
    protected void onPostExecute(Integer winner) {
        Log.d("PostExecute", winner+"");
        MainActivity.gameWinner.setTitle("Das Spiel ist vorbei!");
        MainActivity.gameWinner.setMessage("Der Gewinner ist Spieler "+winner+"!");
        MainActivity.gameWinner.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.backToStart();
            }
        });

        MainActivity.gameWinner.show();
    }
}