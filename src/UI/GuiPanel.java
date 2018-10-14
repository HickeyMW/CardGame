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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GameLogic.MainControl;
import Main.Card;
import Main.Driver;

public class GuiPanel extends JPanel implements MouseListener, GUIInterface{
	
	//Variables
	
	//Arraylists
	public static ArrayList<Drawable> drawables = new ArrayList<Drawable>();
	public static ArrayList<ClickableButton> buttons = new ArrayList<ClickableButton>();
	public static ArrayList<Clickable> clickables = new ArrayList<Clickable>();
	
	//Card hand variables
	//Upper left corner of the leftmost card
	static int cardsX = 40;
	static int cardsY = 497;
	
	//How wide the hand space is
	static int cardsW = 464;
	
	//Tracks whether or not it's our turn to play
	static Boolean isTurn = false;
	
	//Our variable holding a copy of the game logic
	public static MainControl gameLogic;
	
	//Holds the cards that game logic tells us can be played
	public static ArrayList<Card> playableCards;
	
	//Background UI images

	Drawable pokerTable = new Drawable( 0, 0, 1000, 400, "GUIImages/PokerTable2.png" );
	Drawable handBackground = new Drawable( 0, 400, 700, 400, "GUIImages/HandBackground.png" );
	Drawable felt = new Drawable( 700, 400, 400, 400, "GUIImages/felt.png" );
	
	//The images for the player 1, 2, and 3 turn icons
	PlayerTurnIcon p1Icon = new PlayerTurnIcon( 168, 40, 100, 40, "GUIImages/PlayerOneNotTurn.png", "GUIImages/PlayerOneTurn.png" );
	PlayerTurnIcon p2Icon = new PlayerTurnIcon( 451, 40, 100, 40, "GUIImages/PlayerTwoNotTurn.png", "GUIImages/PlayerTwoTurn.png" );
	PlayerTurnIcon p3Icon = new PlayerTurnIcon( 732, 40, 100, 40, "GUIImages/PlayerThreeNotTurn.png", "GUIImages/PlayerThreeTurn.png" );
	
	//Card placeholders for played cards
	PlayedCardImage p1Card = new PlayedCardImage( 116, 97, 169, 252 );
	PlayedCardImage p2Card = new PlayedCardImage( 417, 97, 169, 252 );
	PlayedCardImage p3Card = new PlayedCardImage( 716, 97, 169, 252 );
	
	//Score Display Board
	Drawable pointsDisplay = new Drawable(700, 400, 300, 200, "GUIImages/PointsDisplay.png");
	
	//Our current hand
	public static ArrayList<ClickableCard> hand = new ArrayList<ClickableCard>();
	
	//Player scores
	static int[] scores = { 0, 0, 0 };
	
	//Button declarations
	
	//Start round button
	public static ClickableButton startRoundButton = new ClickableButton(700, 600, 300, 100, "GUIImages/StartRound.png", "GUIImages/StartRoundDown.png", "GUIImages/StartRoundDisabled.png" ) {
		public void onClicked() {
			//gameLogic.startRound();
			//startRoundButton.lock();
			endGame(0);
			
			
		}
	};
	
	//Play card button
	public static ClickableButton playCardButton = new ClickableButton( 700, 700, 300, 100, "GUIImages/PlayCard.png", "GUIImages/PlayCardDown.png", "GUIImages/PlayCardDisabled.png" ) {
		@Override
		public void onClicked() {
			
			//We can only play on our turn
			if(isTurn) {
				
				//Detects if there is a selected card and passes it to game logic
				if( ClickableCard.selectedCard != null ) {
					
					//Tells gamelogic to play the card
					gameLogic.playCard( ClickableCard.selectedCard.card );
					
					//Let the card know it was played
					ClickableCard.selectedCard.remove();
					
					//Reposition the cards in our hand
					positionHand();
					
					//Ends turn
					isTurn = false;
				}
			}
			playCardButton.lock();
		}
	};
	
	//Generates own cards. Used for testing.
	public GuiPanel( ) throws IOException {
		
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(1000,800));
		setFont(new Font("Arial", Font.BOLD, 16));
		addMouseListener(this);
		
		//Lock the buttons
		playCardButton.lock();
		startRoundButton.lock();
		
	}
	
	//General UI methods
	
	public void ReceiveCards(int[][] dealt) {
		
		//Receive cards from host
		for (int i = 0; i < dealt.length; i++) {
			Card card = new Card(dealt[i][0],dealt[i][1]);
			
			new ClickableCard( 50 + 30 * i, 500, 156, 256, card );
			
			//Redraw
			this.repaint();
		}
	}
	
	public void updateScores(){
		scores = gameLogic.getScores();
		
		repaint();
	}
	
	
	public void changeTurn( int nextPlayerID ) {
		
		if( nextPlayerID == gameLogic.playerId ) {
			isTurn = true;
		} else {
			isTurn = false;
		}
		
		p1Icon.setIsTurn( false );
		p2Icon.setIsTurn( false );
		p3Icon.setIsTurn( false );
		
		if( nextPlayerID == 1 ) {
			p1Icon.setIsTurn( true );
		}else if( nextPlayerID == 2 ) {
			p2Icon.setIsTurn( true );
		}else {
			p3Icon.setIsTurn( true );
		}
		
		//Redraw everything
		this.repaint();
		
	}
	
	public void showPlayedCard(int player, Card card) {
		GameLauncher.print("Player "+ player + " played: "+ card);
		
		if(player == 1) {
			p1Card.changeCard( card );
			p1Card.shouldDraw = true;
		}else if(player == 2) {
			p2Card.changeCard( card );
			p2Card.shouldDraw = true;
		}else if(player == 3) {
			p3Card.changeCard( card );
			p3Card.shouldDraw = true;
		}
		
	}
	
	//If the 2 previous players play different suites player 3 cant play anything
	
	public static void endGame(int player){
		GameLauncher.gameWindow.dispose();
		JLabel winnerLabel;
	    JButton restartButton;
	    JLabel p2;
	    JLabel p3;
	    JLabel p1;
	    JLabel p1ScoreLabel;
	    JLabel p2ScoreLabel;
	    JLabel p3ScoreLabel;
		JFrame end = new JFrame("End of Game");
		winnerLabel = new JLabel ("Player 1 Wins");
        restartButton = new JButton ("Play Again?");
        restartButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae) {
        		//Restarts game
        		try {
					GameLauncher.play();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        });
        
        winnerLabel.setText("Player " + (player + 1) + " Wins!");
        
        p1 = new JLabel ("Player 1:");
        p2 = new JLabel ("Player 2:");
        p3 = new JLabel ("Player 3:");
        
        p1ScoreLabel = new JLabel( Integer.toString( scores[0] ) );
        p2ScoreLabel = new JLabel( Integer.toString( scores[1] ) );
        p3ScoreLabel = new JLabel( Integer.toString( scores[2] ) );

        //adjust size and set layout
        end.setPreferredSize (new Dimension (187, 235));
        end.setLayout (null);

        //add components
        end.add (winnerLabel);
        end.add (restartButton);
        end.add (p2);
        end.add (p3);
        end.add (p1);
        end.add (p1ScoreLabel);
        end.add (p2ScoreLabel);
        end.add (p3ScoreLabel);

        //set component bounds (only needed by Absolute Positioning)
        winnerLabel.setBounds (20, 20, 100, 25);
        restartButton.setBounds (20, 170, 100, 25);
        p2.setBounds (20, 90, 60, 25);
        p3.setBounds (20, 125, 60, 25);
        p1.setBounds (20, 55, 60, 25);
        p1ScoreLabel.setBounds (100, 55, 40, 25);
        p2ScoreLabel.setBounds (100, 90, 40, 25);
        p3ScoreLabel.setBounds (100, 125, 45, 20);
		
        end.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        end.pack();
		end.setVisible(true);
		
	}
	
	//Positions all of the cards in our hand evenly across the hand space
	public static void positionHand(){
		
		//Position every card, from back to front, to their appropriate position
		for( int i = hand.size() - 1; i >= 0; i-- ){
			
			//Find our percentage through the hand
			float percentage = ( (float) i / ( (float) hand.size() - 1 ) );
			
			//Find our X position in the total play space
			int cardX = cardsX + (int) ( percentage * cardsW );
			
			//Reposition this card
			ClickableCard card = hand.get( i );
			card.x = cardX;
			card.y = cardsY;
			
		}
		
		//Redraw
		GameLauncher.gamePanel.repaint();
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent( g );
			
		for( int i = 0; i < drawables.size(); i++ ){
			drawables.get( i ).draw( g );
		}
		
		//Draw all the buttons
		for( ClickableButton button : buttons ) {
			button.draw( g );
		}
		
		g.setColor(Color.BLACK);
		g.drawString(Integer.toString( scores[0]), 910, 460 );
		g.drawString(Integer.toString( scores[1]), 910, 508 );
		g.drawString(Integer.toString( scores[2]), 910, 554 );
		
	}

	//Mouse event methods
	
	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}
	
	@Override
	public void mouseReleased( MouseEvent mouseEvent ) {
		
		//If there's something held, let it go
		if( ClickableButton.heldButton != null ) {
			
			//If we just released over the held button then we did a full click on it
			if( ClickableButton.heldButton.pointWithin( mouseEvent.getX() ,  mouseEvent.getY() ) ){
				
				ClickableButton.heldButton.onClicked();
				
				ClickableButton.onMouseUp();
				
			}
			
		}
		
		//Redraw everything
		this.repaint();
		
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
	
		//Right off the bat, if we are already holding something then we have a problem.
		if( ClickableButton.heldButton != null ) {
			//If this is the case, we'll do some error avoidance and just ignore this event.
			return;
		}
		
		//Check if we just started clicking any buttons
		for(  int i = clickables.size()-1 ; i>= 0; i--) {
			Clickable clickable = clickables.get(i);
			
			if( clickable.pointWithin( mouseEvent.getX() ,  mouseEvent.getY() ) ){
				
				clickable.onMouseDown();
				break;
			}
			
		}
		
		//Redraw everything
		this.repaint();
		
	}
	
	//Methods called by game logic
	
	@Override
	public void gameStarted()  {
		
		
		try {
			GameLauncher.clientPlay();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Redraw everything
		this.repaint();
	}
	
	@Override
	public void roundStarted() {
		//Resets the images to be blank
				p1Card.shouldDraw = false;
				p2Card.shouldDraw = false;
				p3Card.shouldDraw = false;
		
		//Redraw everything
		this.repaint();
	}

	@Override
	public void roundWinner(int playerId) {
		
		startRoundButton.lock();
		
		if(playerId == gameLogic.playerId) {
			startRoundButton.unlock();
		}
		
		changeTurn(playerId);
		
		updateScores();
	}

	@Override
	public void gameWinner(int playerId) {
		endGame(playerId);
		
	}

	@Override
	public void error(String error) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void startingHand(ArrayList<Card> cards) {
		GameLauncher.print("Receiving Hand");
		
		//Receives the cards and turns them into clickable cards
		for (int i = 0; i < cards.size(); i++) {
			hand.add( new ClickableCard( 50 + 30 * i, 500, 156, 256, cards.get(i) ) );
		}
		
		//Position these cards
		positionHand();
		
		//Redraw
		this.repaint();
		
	}

	@Override
	public void playableCards(ArrayList<Card> cards) {
		//Sets playable cards as local variable
		playableCards = cards;
		if( !playableCards.isEmpty() )
			GameLauncher.print( "Cards received" );
		isTurn = true;
		changeTurn( gameLogic.playerId );
		//Redraw
		this.repaint();
		
	}

	@Override
	public void cardPlayed(int player, Card card) {
		// Plays the given card for the given player
		GameLauncher.print("Received Card played from: " + player);
		showPlayedCard(player, card);
		changeTurn((player+1)%3);
	}

	@Override
	//This indicates that the round has ended
	public void updateScores(int[] scores) {
		
		playCardButton.lock();
		//startRoundButton.unlock();
		isTurn = false;
		repaint();
	}

	
}
