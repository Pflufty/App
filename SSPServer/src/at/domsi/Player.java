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
	
	public Player(Socket client) {
		super();
		this.client = client;
		try {
			this.inStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
			this.outStream = new PrintWriter(client.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
}