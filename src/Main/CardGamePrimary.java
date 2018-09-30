package Main;
import Client.ClientThread;
import GameLogic.GameLogic;
import Server.ServerThread;
import UI.UI;


public class CardGamePrimary {
	
	public static UI ui;
	
	public static GameLogic gameLogic;
	
	public static ServerThread server;
	public static ClientThread client;
	
	public static final int PORT = 6879;
	
	public static void main(String[] args){
		
		//UI start
		//UI Starts networking
		ui = new UI();
		
		//Logic start
		gameLogic = new GameLogic();
		
		
		
		
	}
}
