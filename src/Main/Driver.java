package Main;
import java.io.IOException;
import java.util.ArrayList;

import UI.Clickable;
import UI.Drawable;
import UI.StartGame;
import UI.TestGUI;

public class Driver {

	public static final int PORT = 4544;
	
	public static StartGame startGame;
	
	public static void main(String[] args) throws IOException {
		boolean testMode = false;
		
		if (testMode) {
			TestGUI testGUI = new TestGUI();
		} else {
			startGame = new StartGame();
		}
	}

}
