package com.example.domsi.sspclient;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by User on 21.06.2016.
 */
public class ClientThread extends AsyncTask<Void, String, String> {

    int ownPlayerNr = 0;

    @Override
    protected String doInBackground(Void... params) {
        Socket client = null;
        String gameWinner="";

        try {
            client = new Socket("10.0.2.2", 9871);

            PrintWriter outStream = new PrintWriter(client.getOutputStream(), true);
            BufferedReader inStream = new BufferedReader(new InputStreamReader(client.getInputStream()));

            outStream.println(MainActivity.username);

            String input;
            do {
                input = inStream.readLine();
            } while (input.equals("Queue/Wait"));

            String[] gameInfos=input.split("/");
            String playerNr=gameInfos[2];
            String[] playerNames=gameInfos[3].split(";");

            ownPlayerNr = Integer.parseInt(playerNr);

            if(ownPlayerNr==2){
                String[] names={"postPlayername", playerNames[0]};
                super.publishProgress(names);
            }else{
                String[] names={"postPlayername", playerNames[1]};
                super.publishProgress(names);
            }

            String inputFromServer;

            do {
                MainActivity.startGame();

                while (MainActivity.selected == false) {

                }

                outStream.println(MainActivity.move);

                String answer = inStream.readLine();

                if (answer.startsWith("Match/Winner/")) {

                    String[] allInfos = answer.split("/");
                    String winner = allInfos[2];
                    String[] win = {"postMove", winner, allInfos[3]};

                    super.publishProgress(win);
                }

                inputFromServer = inStream.readLine();
            } while (inputFromServer.equals("Match/Next"));

            Log.d("GameWinner",inputFromServer);
            String[] finishedGame=inputFromServer.split("/");
            gameWinner=finishedGame[2];
            Log.d("GameWinner",gameWinner);

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
        if(values[0].equals("postMove")){
            int winner = Integer.parseInt(values[1]);

            String[] otherInfos = values[2].split(";");
            String[] infosP1 = otherInfos[0].split(":");
            String[] infosP2 = otherInfos[1].split(":");
            String[] points = {infosP1[0], infosP2[0]};

            int moveOtherPLayer;

            if (ownPlayerNr == 1) {
                moveOtherPLayer = Integer.parseInt(infosP2[1]);
            } else {
                moveOtherPLayer = Integer.parseInt(infosP1[1]);
            }

            MainActivity.setWinnerTxt(winner, points, moveOtherPLayer, ownPlayerNr);
        }

        if(values[0].equals("postPlayername")){
            MainActivity.txtP2.setText(values[1]);
        }
    }

    @Override
    protected void onPostExecute(String winner) {
        MainActivity.gameWinner.setTitle("Das Spiel ist vorbei!");
        MainActivity.gameWinner.setMessage(winner+" hat die Parite gewonnen!");
        MainActivity.gameWinner.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.backToStart();
            }
        });
        MainActivity.gameWinner.show();
    }
}