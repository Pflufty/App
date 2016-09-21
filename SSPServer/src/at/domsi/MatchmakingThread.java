package at.domsi;

import java.io.IOException;

public class MatchmakingThread extends Thread {
	public MatchmakingThread() {

	}

	public void run() {
		String input = "";
		while (true) {
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
					
					if (Server.q.size() % 2 == 0 && Server.q.size() > 0) {
						MatchThread qThread=new MatchThread();
						qThread.start();
					} else {
						Server.p.getOutStream().println("Queue/Wait");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
