package at.domsi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Server {
	
	public static Queue<Player> q = new LinkedList<>();
	public static HashMap<String, Player> players=new HashMap<>();
	public static Player p=null;
	
	public static void main(String[] args) throws UnknownHostException{

		ServerSocket socket = null;
		Socket client=null;

		try {
			socket = new ServerSocket(9871);
			System.out.println("ServerSocket Created!");
		
			while(true){	
				client = socket.accept();
				p=new Player(client);
				MatchmakingThread waitingThread=new MatchmakingThread();
				waitingThread.start();
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