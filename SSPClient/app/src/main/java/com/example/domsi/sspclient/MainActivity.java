package com.example.domsi.sspclient;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

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
    static int ownPlayerNr=0;

    static AlertDialog.Builder gameWinner;

    private static MainActivity instance;

    public static Socket client=null;
    public static PrintWriter outStream=null;
    public static BufferedReader inStream=null;

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

        gameWinner = new AlertDialog.Builder(this);

        setNotClickable();
        setWaitingMark();

        txtP1.setText(OverviewActivtiy.username);

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

    public static void setWaitingMark() {
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

    public static void setWinnerTxt(String winner, String[] points, int moveOtherPlayer) {

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

        if(winner.equals("NoWinner")){
            txtWinner.setText("Unentschieden!");
            txtWinner.setBackgroundResource(R.color.grey);
        }else if(winner.equals(OverviewActivtiy.username)){
            txtWinner.setText(winner + " hat gewonnen!");
            txtWinner.setBackgroundResource(R.color.green);
        }else{
            txtWinner.setText(winner + " hat gewonnen!");
            txtWinner.setBackgroundResource(R.color.red);
        }

        if(ownPlayerNr==1){
            txtScoreP1.setText(points[0]);
            txtScoreP2.setText(points[1]);
        }else{
            txtScoreP1.setText(points[1]);
            txtScoreP2.setText(points[0]);
        }
    }

    public static void backToStart(int[] newStats) {
        Intent backToOverview = new Intent(instance, OverviewActivtiy.class);
        backToOverview.putExtra("Stats", newStats);
        instance.startActivity(backToOverview);
    }
}