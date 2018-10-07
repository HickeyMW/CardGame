package UI;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import Main.Card;

public class Driver {

	public static ArrayList<Drawable> drawables = new ArrayList<Drawable>();
	public static ArrayList<Clickable> clickables = new ArrayList<Clickable>();
	
	
	public static StartGame info;
	public static GuiPanel panel;
	
	public static void main(String[] args) throws IOException {
		info = new StartGame();
		
	}

}
