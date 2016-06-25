package at.domsi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {

		try {
			Socket client = new Socket("localhost", 9871);

			PrintWriter outStream = new PrintWriter(client.getOutputStream(), true);
			BufferedReader inStream = new BufferedReader(new InputStreamReader(client.getInputStream()));

			System.out.println("Connected");

			while (inStream.readLine().equals("Queue/Wait")) {

			}

			Scanner sc = new Scanner(System.in);
			String inputFromServer;

			do {

				int input;

				do {
					sc = new Scanner(System.in);
					System.out.println();
					System.out.println("Gib eine Nummer ein<1,2,3>:");
					input = sc.nextInt();
					outStream.println(input);
				} while (input > 3);

				String answer = inStream.readLine();

				if (answer.startsWith("Match/Winner/")) {
					String[] allInfos=answer.split("/");
                    String won=allInfos[2];
                    String[] win={won, allInfos[3]};
                    
                    String[] otherInfos=win[1].split(";");
                    String[] infosP1=otherInfos[0].split(":");
                    String[] infosP2=otherInfos[1].split(":");

					System.out.println(infosP1[0] + " : " + infosP2[0]);
				}

				inputFromServer = inStream.readLine();
			} while (inputFromServer.equals("Match/Next"));

			int index = inputFromServer.lastIndexOf("/");
			index++;
			inputFromServer = inputFromServer.substring(index, inputFromServer.length());
			System.out.println();
			System.out.println("Spieler " + inputFromServer + " hat die Partie gewonnen!");

			sc.close();
			client.close();
		} catch (UnknownHostException hostEx) {
			hostEx.printStackTrace();
		} catch (IOException IOEx) {
			System.err.println("No Connection");
		}
	}
}