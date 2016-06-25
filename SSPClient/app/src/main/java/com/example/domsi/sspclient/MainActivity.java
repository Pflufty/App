package com.example.domsi.sspclient;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    static TextView txtP1;
    static TextView txtP2;
    static TextView txtScoreP1;
    static TextView txtScoreP2;
    static TextView txtWinner;
    static ImageView imgP1;
    static ImageView imgP2;
    static Button btnSchere;
    static Button btnStein;
    static Button btnPapier;

    static int move = 0;
    static boolean selected = false;

    static AlertDialog.Builder gameWinner;

    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtP1 = (TextView) findViewById(R.id.txtP1);
        txtP2 = (TextView) findViewById(R.id.txtP2);
        txtScoreP1 = (TextView) findViewById(R.id.txtScore1);
        txtScoreP2 = (TextView) findViewById(R.id.txtScore2);
        txtWinner = (TextView) findViewById(R.id.txtWinner);
        imgP1 = (ImageView) findViewById(R.id.imgP1);
        imgP2 = (ImageView) findViewById(R.id.imgP2);
        btnSchere = (Button) findViewById(R.id.btnSchere);
        btnStein = (Button) findViewById(R.id.btnStein);
        btnPapier = (Button) findViewById(R.id.btnPapier);

        setNotClickable();
        gameWinner = new AlertDialog.Builder(this);

        setWaitingMark();

        new ClientThread().execute();
    }

    public static void startGame() {
        btnPapier.setClickable(true);
        btnSchere.setClickable(true);
        btnStein.setClickable(true);

        selected = false;
    }

    public static void setNotClickable() {
        btnPapier.setClickable(false);
        btnSchere.setClickable(false);
        btnStein.setClickable(false);
    }

    public static void setWaitingMark(){
        imgP1.setImageResource(R.drawable.waiting);
        imgP2.setImageResource(R.drawable.waiting);
    }

    public void btnSchereClicked(View view) {
        move = 1;
        selected = true;
        setNotClickable();
        setOwnMovePic();
        imgP2.setImageResource(R.drawable.waiting);
    }

    public void btnSteinClicked(View view) {
        move = 2;
        selected = true;
        setNotClickable();
        setOwnMovePic();
        imgP2.setImageResource(R.drawable.waiting);
    }

    public void btnPapierClicked(View view) {
        move = 3;
        selected = true;
        setNotClickable();
        setOwnMovePic();
        imgP2.setImageResource(R.drawable.waiting);
    }

    public void setOwnMovePic() {
        switch (move) {
            case 1:
                imgP1.setImageResource(R.drawable.schere);
                break;
            case 2:
                imgP1.setImageResource(R.drawable.stein);
                break;
            case 3:
                imgP1.setImageResource(R.drawable.papier);
                break;
        }
    }

    public static void setWinnerTxt(int winner, String[] points, int moveOtherPlayer, int playerNr) {

        switch (moveOtherPlayer) {
            case 1:
                imgP2.setImageResource(R.drawable.schere);
                break;
            case 2:
                imgP2.setImageResource(R.drawable.stein);
                break;
            case 3:
                imgP2.setImageResource(R.drawable.papier);
                break;

        }

        switch (winner) {
            case 0:
                txtWinner.setText("Unentschieden!");
                txtWinner.setBackgroundResource(R.color.grey);
                break;
            case 1:
                if(playerNr==1){
                    txtWinner.setText("Player " + 1 + " hat gewonnen!");
                    txtWinner.setBackgroundResource(R.color.green);

                    txtScoreP1.setText(points[0]);
                    txtScoreP2.setText(points[1]);
                }else{
                    txtWinner.setText("Player " + 2 + " hat gewonnen!");
                    txtWinner.setBackgroundResource(R.color.red);

                    txtScoreP1.setText(points[1]);
                    txtScoreP2.setText(points[0]);
                }
                break;
            case 2:
                if(playerNr==2){
                    txtWinner.setText("Player " + 1 + " hat gewonnen!");
                    txtWinner.setBackgroundResource(R.color.green);

                    txtScoreP1.setText(points[1]);
                    txtScoreP2.setText(points[0]);
                }else{
                    txtWinner.setText("Player " + 2 + " hat gewonnen!");
                    txtWinner.setBackgroundResource(R.color.red);

                    txtScoreP1.setText(points[0]);
                    txtScoreP2.setText(points[1]);
                }
                break;
        }
    }

    public static void backToStart() {
        Intent queueStarted = new Intent(instance, PreGameActivity.class);
        instance.startActivity(queueStarted);
    }
}
