package at.domsi;

public class Match {
	Player p1;
	Player p2;
	int winsP1;
	int winsP2;
	
	public Match(Player p1, Player p2) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		winsP1=0;
		winsP2=0;
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
}