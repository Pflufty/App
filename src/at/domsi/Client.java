package at.domsi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		try {
			Socket client = new Socket("127.0.0.1", 9812);

			Scanner sc = new Scanner(System.in);
			System.out.println("Gib eine Nummer ein:");
			int nr = sc.nextInt();
			sc.close();
			
			PrintWriter inStream =
                    new PrintWriter(client.getOutputStream(), true);
			inStream.println(nr);
			
			BufferedReader outStream =
		            new BufferedReader(new InputStreamReader(client.getInputStream()));
			
		    String answer = outStream.readLine();
		    
		    System.out.print(answer);
		    
		    client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}