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
			Socket client = new Socket("localhost", 9812);
			
			PrintWriter outStream = new PrintWriter(client.getOutputStream(), true);
			BufferedReader inStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			System.out.println(inStream.readLine());
			
			while(inStream.readLine().equals("Wait")){
				
			}
			
			Scanner sc = new Scanner(System.in);
			int input;
			
			System.out.println("Gib eine Nummer ein<1,2,3>:");
			input = sc.nextInt();
			outStream.println(input);
			
			while(inStream.readLine().equals("Wrong")){
				sc = new Scanner(System.in);
				System.out.println("Gib eine Nummer ein<1,2,3>:");
				input = sc.nextInt();
				outStream.println(input);
			}
			
		    String answer = inStream.readLine();
		    
		    System.out.println(answer);
		    
		    sc.close();
		    client.close();
		    System.out.println("Client Disconnected!");
		} catch (UnknownHostException hostEx) {
			hostEx.printStackTrace();
		} catch (IOException IOEx) {
			System.err.println("No Connection");
		}
	}
}