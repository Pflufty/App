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
			Scanner sc = new Scanner(System.in);

			System.out.println("Connection");
			System.out.println("Gib deinen Namen ein:");
			String username=sc.next();
			outStream.println("Connection/"+username);
			
			System.out.println(inStream.readLine());
			outStream.println("Search/"+username);
			
			String input;
			do{
				input=inStream.readLine();
			}while (!(input.startsWith("Queue/Go/")));
			
			String inputFromServer;
			int matchId=Integer.parseInt(input.split("/")[2]);
			System.out.println("MatchID: "+matchId);
			
			do {

				int movePlayed;
				do {
					System.out.println();
					System.out.println("Gib eine Nummer ein<1,2,3>:");
					movePlayed = sc.nextInt();
				} while (movePlayed > 4 && movePlayed < 0);
				outStream.println("Match/Move/"+matchId+"/"+username+"/"+movePlayed);

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
			System.out.println(inputFromServer + " hat die Partie gewonnen!");

			sc.close();
			client.close();
		} catch (UnknownHostException hostEx) {
			hostEx.printStackTrace();
		} catch (IOException IOEx) {
			System.err.println("No Connection");
		}
	}
}