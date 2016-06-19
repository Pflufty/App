package at.domsi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		try {
			ServerSocket socket = new ServerSocket(9812);
			System.out.println("ServerSocket Created!");
			
			Socket client = socket.accept();
			System.out.println("Client Connected!");
			
			BufferedReader inStream =
		            new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			String in=inStream.readLine();
			
			PrintWriter outStream =
                    new PrintWriter(client.getOutputStream(), true);
			
			outStream.println(in);
			
			socket.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}