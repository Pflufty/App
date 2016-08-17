package at.domsi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player {
	Socket client;
	BufferedReader inStream;
	PrintWriter outStream;
	String name;
	int played;
	int won;
	int elo;
	
	public Player(Socket client) {
		super();
		this.client = client;
		this.name="";
		try {
			this.inStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
			this.outStream = new PrintWriter(client.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.elo=1000;
		this.played=0;
		this.won=0;
	}
	
	public Socket getClient() {
		return client;
	}
	public void setClient(Socket client) {
		this.client = client;
	}
	public BufferedReader getInStream() {
		return inStream;
	}
	public void setInStream(BufferedReader inStream) {
		this.inStream = inStream;
	}
	public PrintWriter getOutStream() {
		return outStream;
	}
	public void setOutStream(PrintWriter outStream) {
		this.outStream = outStream;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getPlayed() {
		return played;
	}

	public void setPlayed(int played) {
		this.played = played;
	}

	public int getWon() {
		return won;
	}

	public void setWon(int won) {
		this.won = won;
	}

	public int getElo() {
		return elo;
	}

	public void setElo(int elo) {
		this.elo = elo;
	}
	
}