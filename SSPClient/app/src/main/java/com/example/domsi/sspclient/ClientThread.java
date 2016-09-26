package com.example.domsi.sspclient;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by User on 21.06.2016.
 */
public class ClientThread extends AsyncTask<Void, String, String[]> {

    @Override
    protected String[] doInBackground(Void... params) {
        String[] gameStats=new String[4];

        try {

            OverviewActivtiy.outStream.println("Search/"+OverviewActivtiy.username);
            String input="";
            do {
                input =OverviewActivtiy.inStream.readLine();
            } while (!(input.startsWith("Queue/Go/")));

            MainActivity.matchId=Integer.parseInt(input.split("/")[2]);

            String[] gameInfos=input.split("/");
            String playerNr=gameInfos[3];
            String[] playerNames=gameInfos[4].split(";");

            MainActivity.ownPlayerNr = Integer.parseInt(playerNr);

            if(MainActivity.ownPlayerNr==2){
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
                Log.d("Move Output", "Match/Move/"+MainActivity.matchId+"/"+OverviewActivtiy.username+"/"+MainActivity.move);
                OverviewActivtiy.outStream.println("Match/Move/"+MainActivity.matchId+"/"+OverviewActivtiy.username+"/"+MainActivity.move);

                String answer =OverviewActivtiy.inStream.readLine();

                if (answer.startsWith("Match/Winner/")) {

                    String[] allInfos = answer.split("/");
                    String winner = allInfos[2];
                    String[] win = {"postMove", winner, allInfos[3]};

                    super.publishProgress(win);
                }

                inputFromServer =OverviewActivtiy.inStream.readLine();
            } while (inputFromServer.equals("Match/Next"));

            String[] finishedGame=inputFromServer.split("/");
            gameStats[0]=finishedGame[2];
            gameStats[1]=finishedGame[3];
            gameStats[2]=finishedGame[4];
            gameStats[3]=finishedGame[5];

            OverviewActivtiy.client.close();
        } catch (UnknownHostException hostEx) {
            hostEx.printStackTrace();
        } catch (IOException IOEx) {
            System.err.println("No Connection");
        }
        return gameStats;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        if(values[0].equals("postMove")){
            String winner = values[1];

            String[] otherInfos = values[2].split(";");
            String[] infosP1 = otherInfos[0].split(":");
            String[] infosP2 = otherInfos[1].split(":");
            String[] points = {infosP1[0], infosP2[0]};

            int moveOtherPLayer;

            if (MainActivity.ownPlayerNr == 1) {
                moveOtherPLayer = Integer.parseInt(infosP2[1]);
            } else {
                moveOtherPLayer = Integer.parseInt(infosP1[1]);
            }

            MainActivity.setWinnerTxt(winner, points, moveOtherPLayer);
        }

        if(values[0].equals("postPlayername")){
            MainActivity.txtP2.setText(values[1]);
        }
    }

    @Override
    protected void onPostExecute(final String[] stats) {
        MainActivity.gameWinner.setTitle("Das Spiel ist vorbei!");
        MainActivity.gameWinner.setMessage(stats[0]+" hat das Match gewonnen!");
        MainActivity.gameWinner.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int[] newPlayerStats=new int[3];

                newPlayerStats[0]=Integer.parseInt(stats[1]);
                newPlayerStats[1]=Integer.parseInt(stats[2]);
                newPlayerStats[2]=Integer.parseInt(stats[3]);

                MainActivity.backToStart(newPlayerStats);
            }
        });
        MainActivity.gameWinner.show();
    }
}