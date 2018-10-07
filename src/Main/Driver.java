package Main;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import UI.Clickable;
import UI.Drawable;
import UI.GuiPanel;
import UI.StartGame;

public class Driver {

	public static final int PORT = 4544;
	
	public static ArrayList<Drawable> drawables = new ArrayList<Drawable>();
	public static ArrayList<Clickable> clickables = new ArrayList<Clickable>();
	
	
	public static StartGame info;
	public static GuiPanel panel;
	
	public static void main(String[] args) throws IOException {
		info = new StartGame();
		
	}

}
