package GameLogic;

import Main.Card;

public interface GUIEvents {
	
	//What the GUI can call on game logic
	
	public void hostGame();

	public void joinGame(String ip);
	
	public void playCard(Card card);
	
	public void startRound();
	
	public void startGame();
}
