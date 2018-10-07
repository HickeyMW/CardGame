package UI;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.Card;

public class ClickableCard extends Clickable {

	Card card;
	
	public static ClickableCard selectedCard = null;
	
	public static int selectedCardHeightModifier = 50;
	
	public ClickableCard(int x, int y, int w, int h, Card card ) {
		
		super( x, y, w, h, "GUIImages/cards/" + card.toString().replaceAll(" ", "_") + ".png" );
		
		this.card = card;
		
	}
	
	public String getAddress() {
		return("GUIImages/cards/" + card.toString().replaceAll(" ", "_") + ".png");
	}
	
	public void onClicked() {
		
		selectedCard = this;
		
		y -= selectedCardHeightModifier;
		Driver.panel.repaint();
		
	}
	
	public void onMouseDown() {
		
		if(selectedCard!=null) {
			selectedCard.y+=selectedCardHeightModifier;
		}
		
		selectedCard = this;
		y -= selectedCardHeightModifier;
		
		
		Driver.panel.repaint();
	}
	
	public static void cardPlayed() {
		selectedCard.y+=selectedCardHeightModifier;
		selectedCard=null;
	}
	
	
}