package com.example.domsi.sspclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    TextView txtP1;
    TextView txtP2;
    TextView txtScoreP1;
    TextView txtScoreP2;
    TextView txtWinner;
    ImageView imgP1;
    ImageView imgP2;
    Button btnSchere;
    Button btnStein;
    Button btnPapier;

    int move=0;
    boolean selected=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtP1= (TextView) findViewById(R.id.txtP1);
        txtP2= (TextView) findViewById(R.id.txtP2);
        txtScoreP1= (TextView) findViewById(R.id.txtScore1);
        txtScoreP2= (TextView) findViewById(R.id.txtScore2);
        txtWinner= (TextView) findViewById(R.id.txtWinner);
        imgP1= (ImageView) findViewById(R.id.imgP1);
        imgP2= (ImageView) findViewById(R.id.imgP2);
        btnSchere= (Button) findViewById(R.id.btnSchere);
        btnStein= (Button) findViewById(R.id.btnStein);
        btnPapier= (Button) findViewById(R.id.btnPapier);

        btnPapier.setClickable(false);
        btnSchere.setClickable(false);
        btnStein.setClickable(false);



        try {
            Socket client = new Socket("localhost", 9812);

            PrintWriter outStream = new PrintWriter(client.getOutputStream(), true);
            BufferedReader inStream = new BufferedReader(new InputStreamReader(client.getInputStream()));

            System.out.println(inStream.readLine());

            while (inStream.readLine().equals("Queue/Wait")) {

            }

            startGame();

            outStream.println(move);

            String inputFromServer;

            do {

                int input;

                while(selected==false){

                }
                outStream.println(move);

                String answer = inStream.readLine();

                int winner = 0;

                if (answer.startsWith("Match/Winner/")) {
                    int index = answer.lastIndexOf("/");
                    answer = answer.substring(index + 1, answer.length());

                    String[] win = answer.split(":");
                    String[] points = win[1].split(";");

                    winner = Integer.parseInt(win[0]);

                    switch (winner) {
                        case 0:
                            System.out.println("Unentschieden");
                            break;
                        case 1:
                            System.out.println("Spieler 1 hat gewonnen!");
                            break;
                        case 2:
                            System.out.println("Spieler 2 hat gewonnen!");
                            break;
                    }

                    System.out.println(points[0] + " : " + points[1]);
                }

                inputFromServer = inStream.readLine();
            } while (inputFromServer.equals("Match/Next"));

            int index = inputFromServer.lastIndexOf("/");
            index++;
            inputFromServer = inputFromServer.substring(index, inputFromServer.length());
            System.out.println();
            System.out.println("Spieler " + inputFromServer + " hat die Partie gewonnen!");

            btnPapier.setClickable(false);
            btnSchere.setClickable(false);
            btnStein.setClickable(false);

            client.close();
        } catch (UnknownHostException hostEx) {
            hostEx.printStackTrace();
        } catch (IOException IOEx) {
            System.err.println("No Connection");
        }
    }

    private void startGame() {
        btnPapier.setClickable(true);
        btnSchere.setClickable(true);
        btnStein.setClickable(true);
    }

    public void btnSchereClicked(View view) {
        move=1;
        selected=true;
    }

    public void btnSteinClicked(View view) {
        move=2;
        selected=true;
    }

    public void btnPapierClicked(View view) {
        move=3;
        selected=true;
    }
}
