package com.example.domsi.sspclient;

import android.content.DialogInterface;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by User on 21.06.2016.
 */
public class ClientThread extends AsyncTask<Void, String, int[]> {

    int ownPlayerNr = 0;

    @Override
    protected int[] doInBackground(Void... params) {
        Socket client = null;
        int[] gameWinner = new int[2];

        try {
            client = new Socket("10.0.2.2", 9871);

            PrintWriter outStream = new PrintWriter(client.getOutputStream(), true);
            BufferedReader inStream = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String input;

            do {
                input = inStream.readLine();
            } while (input.equals("Queue/Wait"));

            int indexPlayerNr = input.lastIndexOf("/");
            indexPlayerNr++;
            input = input.substring(indexPlayerNr, input.length());
            ownPlayerNr = Integer.parseInt(input);

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
                    String[] win = {winner, allInfos[3]};

                    super.publishProgress(win);
                }

                inputFromServer = inStream.readLine();
            } while (inputFromServer.equals("Match/Next"));

            int index = inputFromServer.lastIndexOf("/");
            index++;
            inputFromServer = inputFromServer.substring(index, inputFromServer.length());
            gameWinner[0] = ownPlayerNr;
            gameWinner[1] = Integer.parseInt(inputFromServer);

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

        int winner = Integer.parseInt(values[0]);

        String[] otherInfos = values[1].split(";");
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

    @Override
    protected void onPostExecute(int[] winnerInfo) {
        MainActivity.gameWinner.setTitle("Das Spiel ist vorbei!");
        if ((winnerInfo[0] == 1 && winnerInfo[1] == 1) || (winnerInfo[0] == 2 && winnerInfo[1] == 1)) {
            MainActivity.gameWinner.setMessage("Der Gewinner ist Spieler " + 2 + "!");
        } else if ((winnerInfo[0] == 2 && winnerInfo[1] == 2) || (winnerInfo[0] == 1 && winnerInfo[1] == 2)) {
            MainActivity.gameWinner.setMessage("Der Gewinner ist Spieler " + 1 + "!");
        }
        MainActivity.gameWinner.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.backToStart();
            }
        });
        MainActivity.gameWinner.show();
    }
}