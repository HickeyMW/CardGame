package Main;
import java.io.IOException;
import java.util.ArrayList;

import UI.GameLauncher;
import UI.Ables.Clickable;
import UI.Ables.Drawable;

public class Driver {

	public static final int PORT = 4544;
	
	public static GameLauncher gameLauncher;
	
	public static void main(String[] args) throws IOException {
		
		//Start the launcher
		gameLauncher = new GameLauncher();
		
	}

}
