package at.domsi;

public class Match {
	Player p1;
	Player p2;
	int winsP1;
	int winsP2;
	int roundsLeft;
	int moveP1;
	int moveP2;
	int timer;
	
	public Match(Player p1, Player p2) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		winsP1=0;
		winsP2=0;
		roundsLeft=3;
		timer=20;
		moveP1=-1;
		moveP2=-1;
	}
	
	public Player getP1() {
		return p1;
	}
	public void setP1(Player p1) {
		this.p1 = p1;
	}
	public Player getP2() {
		return p2;
	}
	public void setP2(Player p2) {
		this.p2 = p2;
	}
	public int getWinsP1() {
		return winsP1;
	}
	public void setWinsP1(int winsP1) {
		this.winsP1 = winsP1;
	}
	public int getWinsP2() {
		return winsP2;
	}
	public void setWinsP2(int winsP2) {
		this.winsP2 = winsP2;
	}
	public int getRoundsLeft() {
		return roundsLeft;
	}
	public void setRoundsLeft(int roundsLeft) {
		this.roundsLeft = roundsLeft;
	}
	public int getMoveP1() {
		return moveP1;
	}
	public void setMoveP1(int moveP1) {
		this.moveP1 = moveP1;
	}
	public int getMoveP2() {
		return moveP2;
	}
	public void setMoveP2(int moveP2) {
		this.moveP2 = moveP2;
	}
	public int getTimer() {
		return timer;
	}
	public void setTimer(int timer) {
		this.timer = timer;
	}
}