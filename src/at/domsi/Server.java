package at.domsi;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;

public class Server {
	
	public static void main(String[] args) throws UnknownHostException {
		
		ServerSocket socket=null;
		
		try {
			socket = new ServerSocket(9812);
			System.out.println("ServerSocket Created!");
			
			Queue<Player> q = new LinkedList<>();
			
			while(true){
				Socket client = socket.accept();
				Player p=new Player(client);
				q.add(p);
				System.out.println("Client "+q.size()+" Connected!");
				
				p.getOutStream().println("Connected: Player "+q.size());
				
				if(q.size()%2==0){
					Match m=new Match(q.poll(), q.poll());
					
					m.getP1().getOutStream().println("GO");
					m.getP2().getOutStream().println("GO");
					
					String inP1;
					String inP2;
					int moveP1;
					int moveP2;
					
					boolean inputP1;
					do{
						inP1=m.getP1().getInStream().readLine();
						moveP1=Integer.parseInt(inP1);
						inputP1=checkP1(moveP1);
						
						if(inputP1==false){
							m.getP1().getOutStream().println("Wrong");
						}
					}while(inputP1==false);
					m.getP1().getOutStream().println("Ok");
					
					boolean inputP2;
					do{
						inP2=m.getP2().getInStream().readLine();
						moveP2=Integer.parseInt(inP2);
						inputP2=checkP2(moveP2);
						
						if(inputP2==false){
							m.getP2().getOutStream().println("Wrong");
						}
					}while(inputP2==false);
					m.getP2().getOutStream().println("Ok");
					
					int winner=checkWinner(moveP1, moveP2);
					
					m.getP1().getOutStream().println("The Winner is Player "+winner);
					m.getP2().getOutStream().println("The Winner is Player "+winner);
				}else{
					p.getOutStream().println("Wait");
				}
			}
		} catch (IOException IOEx) {
			IOEx.printStackTrace();
		}
		finally{
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static boolean checkP2(int moveP2) {
		if(moveP2>=0&&moveP2<=3){
			return true;
		}
		return false;
	}

	private static boolean checkP1(int moveP1) {
		if(moveP1>=0&&moveP1<=3){
			return true;
		}
		return false;
	}

	private static int checkWinner(int moveP1, int moveP2) {
		int winner=1;
		
		switch(moveP1){
			case 1:
				if(moveP2==2){
					winner=2;
				}
				break;
			case 2:
				if(moveP2==3){
					winner=2;
				}
				break;
			case 3:
				if(moveP2==1){
					winner=2;
				}
				break;
		}
		return winner;
	}
}