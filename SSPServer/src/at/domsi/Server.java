package at.domsi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException ;
import java.util.LinkedList;
import java.util.Queue;

public class Server {
	
	public static Queue<Player> q = new LinkedList<>();
	
	public static void main(String[] args) throws UnknownHostException{

		ServerSocket socket = null;
		Socket client=null;

		try {
			socket = new ServerSocket(9871);
			System.out.println("ServerSocket Created!");
			
			int nr=1;
			
			while(true){
								
				client = socket.accept();
				QueuingThread thread=new QueuingThread(client, nr);
				
				Player p = new Player(client);
				q.add(p);
				System.out.println("Client " + nr + " Connected!");
				p.getOutStream().println("Connected: Player " + nr);
				
				thread.start();
				nr++;
			}
		} catch (IOException IOEx) {
			IOEx.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}