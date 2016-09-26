package at.domsi;

import java.io.IOException;

public class MatchmakingThread extends Thread {
	public MatchmakingThread() {

	}

	public void run() {
		String input = "";
		int matchId = 1;
		
		while (true) {
			try {
				input = Server.p.getInStream().readLine();
				if (input.startsWith("Connection/")) {
					String username = input.split("/")[1];

					Server.p.setName(username);
					Server.p.getOutStream().println(
							"Game/Stats/" + Server.p.getElo() + "/" + Server.p.getPlayed() + "/" + Server.p.getWon());
					Server.players.put(username, Server.p);
					System.out.println(username + " conncected!");
				} else if (input.startsWith("Search/")) {
					String username = input.split("/")[1];

					Server.q.add(Server.players.get(username));
					System.out.println("Player " + username + " is searching an enemy!");

					if (Server.q.size() % 2 == 0 && Server.q.size() > 0) {
						Player p1 = Server.q.poll();
						Player p2 = Server.q.poll();
						Match m = new Match(p1, p2);

						m.getP1().getOutStream().println(
								"Queue/Go/" + matchId + "/1/" + m.getP1().getName() + ";" + m.getP2().getName());
						m.getP2().getOutStream().println(
								"Queue/Go/" + matchId + "/2/" + m.getP1().getName() + ";" + m.getP2().getName());

						Server.matches.put(matchId, m);
						matchId++;
					} else {
						Server.p.getOutStream().println("Queue/Wait/");
					}
				} else if (input.startsWith("Match/Move/")) {
					int id=Integer.parseInt(input.split("/")[2]);
					String username= input.split("/")[3];
					if(Server.matches.containsKey(id)){
						Match m=Server.matches.get(id);
						String move= input.split("/")[4];
						int playedMove=Integer.parseInt(move);
						System.out.println(move+"");
						
						if(m.getP1().getName().equals(username)){
							m.setMoveP1(playedMove);
						}else if(m.getP2().getName().equals(username)){
							m.setMoveP2(playedMove);
						}else{
							System.out.println("Unknown User");
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}