package at.domsi;

import java.io.IOException;

public class MatchThread extends Thread {

	public MatchThread() {
	}

	public void run() {

		int roundsToPlay = 3;
		
		Player p1 = Server.q.poll();
		Player p2 = Server.q.poll();
		Match m = new Match(p1, p2);
		
		m.getP1().getOutStream().println("Queue/Go/1/" + m.getP1().getName() + ";" + m.getP2().getName());
		m.getP2().getOutStream().println("Queue/Go/2/" + m.getP1().getName() + ";" + m.getP2().getName());

		try {
			int moveP1;
			int moveP2;

			do {
				System.out.println(m.getP1().getName());
				moveP1 = Integer.parseInt(m.getP1().getInStream().readLine().split("/")[2]);
				moveP2 = Integer.parseInt(m.getP2().getInStream().readLine().split("/")[2]);
				
				int winner = checkWinner(moveP1, moveP2);
				String winnerName = "NoWinner";

				if (winner == 1) {
					int newWins = m.getWinsP1();
					newWins++;
					m.setWinsP1(newWins);
					int totalWins = m.getP1().getWon();
					totalWins += 1;
					m.getP1().setWon(totalWins);
					roundsToPlay--;
					winnerName = m.getP1().getName();
				} else if (winner == 2) {
					int newWins = m.getWinsP2();
					newWins++;
					m.setWinsP2(newWins);
					int totalWins = m.getP2().getWon();
					totalWins += 1;
					m.getP2().setWon(totalWins);
					roundsToPlay--;
					winnerName = m.getP2().getName();
				}

				System.out.println("Hey");

				int p1Played = m.getP1().getPlayed();
				p1Played += 1;
				m.getP1().setPlayed(p1Played);

				int p2Played = m.getP2().getPlayed();
				p2Played += 1;
				m.getP2().setPlayed(p2Played);

				System.out.println("Hey");
				m.getP1().getOutStream().println("Match/Winner/" + winnerName + "/" + m.getWinsP1() + ":" + moveP1 + ";"
						+ m.getWinsP2() + ":" + moveP2);
				m.getP2().getOutStream().println("Match/Winner/" + winnerName + "/" + m.getWinsP1() + ":" + moveP1 + ";"
						+ m.getWinsP2() + ":" + moveP2);

				if (roundsToPlay > 0 && m.getWinsP1() != 2 && m.getWinsP2() != 2) {
					m.getP1().getOutStream().println("Match/Next");
					m.getP2().getOutStream().println("Match/Next");
				}
			} while (roundsToPlay > 0 && m.getWinsP1() != 2 && m.getWinsP2() != 2);

			String winPlayer;
			if (m.getWinsP1() > m.getWinsP2()) {
				winPlayer = m.getP1().getName();
			} else {
				winPlayer = m.getP2().getName();
			}

			m.getP1().getOutStream().println("Match/Finish/" + winPlayer + "/" + m.getP1().getElo() + "/"
					+ m.getP1().getPlayed() + "/" + m.getP1().getWon());
			m.getP2().getOutStream().println("Match/Finish/" + winPlayer + "/" + m.getP2().getElo() + "/"
					+ m.getP2().getPlayed() + "/" + m.getP2().getWon());
		} catch (

		IOException e) {
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
