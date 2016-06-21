package at.domsi;

import java.io.IOException;
import java.net.Socket;

public class QueuingThread extends Thread {

	Socket client;
	int players;

	public QueuingThread(Socket client, int nr) {
		super();
		this.client = client;
		this.players = nr;
	}

	public void run() {

		int roundsToPlay = 3;

		try {

			int size = Server.q.size();
			System.out.println(size);

			Player p = new Player(client);

			if (Server.q.size() % 2 == 0) {
				Player p1 = Server.q.poll();
				Player p2 = Server.q.poll();
				Match m = new Match(p1, p2);

				m.getP1().getOutStream().println("Queue/Go");
				m.getP2().getOutStream().println("Queue/Go");

				String inP1;
				String inP2;
				int moveP1;
				int moveP2;

				do {

					System.out.println("Rounds Left: " + roundsToPlay);
					inP1 = m.getP1().getInStream().readLine();

					moveP1 = Integer.parseInt(inP1);

					inP2 = m.getP2().getInStream().readLine();
					moveP2 = Integer.parseInt(inP2);

					int winner = checkWinner(moveP1, moveP2);

					if (winner == 1) {
						int newWins = m.getWinsP1();
						newWins++;
						m.setWinsP1(newWins);
						roundsToPlay--;
					} else if (winner == 2) {
						int newWins = m.getWinsP2();
						newWins++;
						m.setWinsP2(newWins);
						roundsToPlay--;
					}

					m.getP1().getOutStream()
							.println("Match/Winner/" + winner + ":" + m.getWinsP1() + ";" + m.getWinsP2());
					m.getP2().getOutStream()
							.println("Match/Winner/" + winner + ":" + m.getWinsP1() + ";" + m.getWinsP2());

					if (roundsToPlay > 0 && m.getWinsP1() != 2 && m.getWinsP2() != 2) {
						m.getP1().getOutStream().println("Match/Next");
						m.getP2().getOutStream().println("Match/Next");
					}
				} while (roundsToPlay > 0 && m.getWinsP1() != 2 && m.getWinsP2() != 2);

				int winPlayer;
				if (m.getWinsP1() > m.getWinsP2()) {
					winPlayer = 1;
				} else {
					winPlayer = 2;
				}

				m.getP1().getOutStream().println("Match/Finish/" + winPlayer);
				m.getP2().getOutStream().println("Match/Finish/" + winPlayer);

			} else {
				p.getOutStream().println("Queue/Wait");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static int checkWinner(int moveP1, int moveP2) {
		int winner = 0;

		switch (moveP1) {
		case 1:
			if (moveP2 == 2) {
				winner = 2;
			} else if (moveP2 == 3) {
				winner = 1;
			}
			break;
		case 2:
			if (moveP2 == 3) {
				winner = 2;
			} else if (moveP2 == 1) {
				winner = 1;
			}
			break;
		case 3:
			if (moveP2 == 1) {
				winner = 2;
			} else if (moveP2 == 2) {
				winner = 1;
			}
			break;
		}
		return winner;
	}
}
