package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GameLogic.GUIEvents;
import GameLogic.MainControl;
import Main.Card;
import Main.Driver;

public class GuiPanel extends JPanel implements MouseListener, GUIInterface{
	
	
	//Tracks whether or not it is currently our turn
	public static boolean isOurTurn = false;
	
	public static ArrayList<Drawable> drawables = new ArrayList<Drawable>();
	public static ArrayList<Clickable> clickables = new ArrayList<Clickable>();
	
	//Player scores
	private int P1Score = 0;
	private int P2Score = 0;
	private int P3Score = 0;
	
	//Our hand of ClickableCards
	public static ArrayList<ClickableCard> hand = new ArrayList<ClickableCard>();
	
	//The playable cards list
	public static ArrayList<Card> playableCards = new ArrayList<Card>();
	
	//UI elements
	
	//Play card button
	ClickableButton playCard;
	
	//Start round button
	ClickableButton startRound;
	
	//Generates own cards. Used for testing.
	public GuiPanel() throws IOException {
		
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(1000,800));
		setFont(new Font("Arial", Font.BOLD, 16));
		addMouseListener(this);
		
		//Create the main UI elements
		//Play card button
		playCard = new ClickableButton( 700, 700, 300, 100, "GUIImages/PlayCard.png", "GUIImages/PlayCardDown.png", "GUIImages/PlayCardLocked.png" ) {
			@Override
			public void onClicked() {
				
				this.lock();
				
			}
		};
		
	}
	
	
	//General methods
	
	//Called when it becomes our turn
	//Unlocks the UI and lets us play a card
	public void ourTurn() {
		
	}
	
	
	//JPanel methods
	public void paintComponent(Graphics page){
		super.paintComponent(page);
		
		for( Drawable img : drawables ) {
			img.draw( page );
		}
		
	}
	
	//Mouse methods
	
	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	//When the mouse is pressed down, check it against clickable objects
	public void mousePressed(MouseEvent mouseEvent) {
		
		//Go through every clickable
		for( Clickable clickable : clickables ) {
			
			//Check if we just clicked inside of its bounds
			if( clickable.pointWithin( mouseEvent.getX(), mouseEvent.getY() ) ) {
				
				//If we did, then start clicking on this object.
				clickable.onMouseDown();
				
				//Stop checking
				break;
				
			}
			
		}
		
	}
	
	@Override
	//When the mouse is released
	public void mouseReleased( MouseEvent mouseEvent ) {
		
		//If there is a clickable button that is held down, check if we just released inside of it
		if( ClickableButton.heldButton != null ) {
			
			//If we just released in the held button
			if( ClickableButton.heldButton.pointWithin( mouseEvent.getX(), mouseEvent.getY() ) ) {
				
				//That counts as a full click
				ClickableButton.heldButton.onClicked();
				
				//We are no longer holding that button
				ClickableButton.onMouseUp();
				
			}
			
		}
		
	}
	
	
	//Game logic methods
	
	@Override
	//Someone has started the game so we should start our UI
	public void gameStarted()  {
		
		try {
			StartGame.clientPlay();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void roundStarted() {}

	@Override
	public void roundWinner(int playerId) {}

	@Override
	public void gameWinner(int playerId) {}

	@Override
	public void error(String error) {}
	
	@Override
	//Server deals us our starting cards
	public void startingHand(ArrayList<Card> cards) {
		
		//Build ClickableCards from the arraylist of cards
		for( Card card : cards ) {
			
			//Create a new ClickableCard and add it to our hand
			hand.add( new ClickableCard( -1000, -1000, 56, 256, card ) );
			
		}
		
	}

	@Override
	//Server alerts us what cards we can play, which also alerts us that it is our turn
	public void playableCards(ArrayList<Card> cards) {
		
		//Make sure we know that it is our turn
		isOurTurn = true;
		
		//Save the playable cards list
		playableCards = (ArrayList<Card>) cards.clone();
		
		//Take our turn
		ourTurn();
	}

	@Override
	public void cardPlayed(int player, Card card) {
		
		//If we just played a card, then it must not be our turn anymore
		if( player == StartGame.mainControl.playerId ) {
			isOurTurn = false;
		}
		
		//TODO - Most of this
	}

	@Override
	//When the server updates our player scores, save them and draw them
	public void updateScores( int P1Score, int P2Score, int P3Score ) {
		
		//Update scores
		this.P1Score = P1Score;
		this.P2Score = P2Score;
		this.P3Score = P3Score;
		
		//TODO - Draw them
		
	}
	
}
