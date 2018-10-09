package UI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import Main.Card;
import Main.Driver;

public class ClickableCard extends Clickable {

	//The card object that this object represents
	public Card card;
	
	//Which ClickableCard is currently selected from the hand
	public static ClickableCard selectedCard = null;
	
	//How much to slide the card up when it is selected
	public static int selectedCardHeightModifier = 75;
	
	
	//Constructor
	public ClickableCard(int x, int y, int w, int h, Card card ) {
		super( x, y, w, h, getAddress( card ) );
		
		this.card = card;
	}
	
	//Converts the card object into a valid image filepath for that same card
	public static String getAddress( Card card ) {
		return("GUIImages/cards/" + card.toString().replaceAll(" ", "_") + ".png");
	}
	
	//Cards don't have a full click cycle so this is never called
	public void onClicked() {}
	
	//Called when this card is clicked (Mouse down, specifically)
	public void onMouseDown() {
		
		//You can only click on cards when it is your turn.
		if(StartGame.panel.isTurn) {
			
			//You can only click on playable cards
			if(StartGame.panel.playableCardsVar.contains(this.card)) {
				
				//If we just clicked on the card we already have selected, we should unselect that card
				if( selectedCard == this ){
					
					//Move the selected card down into the hand
					selectedCard.y += selectedCardHeightModifier;
					
					//Make sure we are no longer selecting this card
					selectedCard = null;
					
					//Stop
					return;
				}
				
				//If we have a card already selected, we should move it back down into the hand
				if( selectedCard != null ) {
					selectedCard.y += selectedCardHeightModifier;
				}
				
				//This is now the selected card
				selectedCard = this;
				
				//Move the selected card up out of the hand to show that it is selected
				y -= selectedCardHeightModifier;
			}
			
		}
		
	}
	
	public void draw( Graphics g ) {
	    
	    g.drawImage( image, x, y, w, h, null );
	    
	    //If it isn't our turn, gray out the cards
		if( !GuiPanel.isTurn ) {
			Color disableColor = new Color( 127, 127, 127, 200 );
		    
			Graphics2D g2d = (Graphics2D) g;
			
			g2d.setComposite( AlphaComposite.SrcAtop );
			g2d.setColor( disableColor );
			g2d.fillRect( x, y, w, h );
			
		}
	}
	
	//Once a card has been played from the hand, it is removed from the hand and destroyed
	public static void cardPlayed() {
		
		//Remove it from the drawables list
		Driver.drawables.remove(selectedCard);
		
		//Remove it from the clickables list
		Driver.clickables.remove(selectedCard);
		
		//It is no longer the selected card
		selectedCard = null;
	}
	
	
}