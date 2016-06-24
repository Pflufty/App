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

			sc.close();
			client.close();
		} catch (UnknownHostException hostEx) {
			hostEx.printStackTrace();
		} catch (IOException IOEx) {
			System.err.println("No Connection");
		}
	}
}