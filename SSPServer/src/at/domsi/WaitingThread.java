package at.domsi;

import java.io.IOException;

public class WaitingThread extends Thread {
	public WaitingThread() {

	}

	public void run() {
		String input = "";
		do {
			try {
				input = Server.p.getInStream().readLine();
				String username = "";
				if (input.startsWith("Connection/")) {
					username = input.split("/")[1];

					Server.p.setName(username);
					Server.p.getOutStream().println(
							"Game/Stats/" + Server.p.getElo() + "/" + Server.p.getPlayed() + "/" + Server.p.getWon());
					Server.players.put(username, Server.p);
					System.out.println(username + " conncected!");
				} else if (input.startsWith("Search/")) {
					username = input.split("/")[1];

					Server.q.add(Server.players.get(username));
					System.out.println("Player " + username + " is searching an enemy!");
					
					QueuingThread queuingThread = new QueuingThread();
					queuingThread.start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (!(input.startsWith("Search/")));
	}
}
