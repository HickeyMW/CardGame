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

import GameLogic.GUIEvents;
import GameLogic.MainControl;
import Main.Card;
import Main.Driver;

public class GuiPanel extends JPanel implements MouseListener, GUIInterface{
	
	
	static Boolean isTurn = true;
	MainControl ctrl;
	
	 ArrayList<Card> playableCardsVar;
	
	
	ClickableButton roundButton = 	new ClickableButton( 700, 600, 300, 100, "GUIImages/NextRound.png", "GUIImages/NextRoundNot.png" ) {
		public void onClicked() {
			
			System.out.println( "Clicked round change" );
			if(isTurn) {
				//endGame();
				
				//TODO 
				ctrl.startRound();
			}
			
		}
	};
	
	ClickableButton playCard = 	new ClickableButton( 700, 700, 300, 100, "GUIImages/PlayCard.png", "GUIImages/PlayCardNot.png" ) {
		public void onClicked() {
			if(isTurn) {
				//Detects if there is a selected card and passes it to game logic
				if( ClickableCard.selectedCard != null ) {
					
					//Tells gamelogic to play the card
					ctrl.playCard( ClickableCard.selectedCard.card );
					
					//Tells the UI to play the card
					showPlayedCard( ctrl.playerId, ClickableCard.selectedCard.card );
					
					
					//Let the card know it was played
					ClickableCard.cardPlayed();
					//Ends turn
					isTurn = false;
				}
			}
			
			
		}
	};
	
	//Drawable handTable = new Drawable(0,400,400,300, "GUIImages/TableTop.png");
	Drawable pokerTable = new Drawable(0,0,1000,400, "GUIImages/PokerTable.png");

	
	
	Drawable p1 = new Drawable(150, 20, 90, 40, "GUIImages/Player1.png");
	Drawable p2 = new Drawable(400, 20, 90, 40, "GUIImages/Player2.png");
	Drawable p3 = new Drawable(650, 20, 90, 40, "GUIImages/Player3.png");
	
	
	
	
	
	
	//Receives 3D array for ints containing 17 rows of 2 values.
	//First value is the face and the second value is the suit.
	
	
	//Generates own cards. Used for testing.
	public GuiPanel( ) throws IOException {
		
		
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(1000,800));
		setFont(new Font("Arial", Font.BOLD, 16));
		addMouseListener(this);
		
	}
	
	public void createCtrl(MainControl newCtrl) {
		ctrl = newCtrl;
		
		//Sets window title to indicated which player they are.
		StartGame.frame.setTitle("Player " + ctrl.playerId );
		
	}
	
	public void ReceiveCards(int[][] dealt) {
		
		//Receive cards from host
		for (int i = 0; i < dealt.length; i++) {
			Card card = new Card(dealt[i][0],dealt[i][1]);
			
			new ClickableCard( 50 + 30 * i, 500, 156, 256, card );
		}
		
		//Redraw everything
		this.repaint();
		
	}
	
	public void updateScores(){
		pScores = ctrl.getScores();
		
		repaint();
	}
	
	
	public void changeTurn(int player) {
		
		if(player == ctrl.playerId)
			isTurn = true;
		else
			isTurn = false;
		
		
		switch(player) {
		
		case 1: player = 1;
			p3.changeImage("GUIImages/Player3.png");
			p1.changeImage("GUIImages/Player1Turn.png");
			break;
		case 2: player = 2;
			p1.changeImage("GUIImages/Player1.png");
			p2.changeImage("GUIImages/Player2Turn.png");
			break;
		case 3: player = 3;
			p2.changeImage("GUIImages/Player2.png");
			p3.changeImage("GUIImages/Player3Turn.png");
			break;
		
		}
		
		//Redraw everything
		this.repaint();
		
	}
	Drawable p1Card = new Drawable(150, 125, 137, 200, "GUIImages/Cards/temp.png");
	Drawable p2Card = new Drawable(400, 125, 137, 200, "GUIImages/Cards/temp.png");
	Drawable p3Card = new Drawable(650, 125, 137, 200, "GUIImages/Cards/temp.png");
	
	public void showPlayedCard(int player, Card card) {
		
		if(player == 1) {
			p1Card.changeImage("GUIImages/cards/" + card.toString().replaceAll(" ", "_") + ".png");
		}else if(player == 2) {
			p2Card.changeImage("GUIImages/cards/" + card.toString().replaceAll(" ", "_") + ".png");
		}else if(player == 3) {
			p3Card.changeImage("GUIImages/cards/" + card.toString().replaceAll(" ", "_") + ".png");
		}
		
	}
	
	
	
	public void endGame(int player) {
		StartGame.frame.dispose();
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
					StartGame.play();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        });
        
        winnerLabel.setText("Player " + player + "Wins!");
        
        
        
        
        p1 = new JLabel ("Player 1:");
        p2 = new JLabel ("Player 2:");
        p3 = new JLabel ("Player 3:");
        
        p1ScoreLabel = new JLabel (Integer.toString(pScores[1]));
        p2ScoreLabel = new JLabel (Integer.toString(pScores[2]));
        p3ScoreLabel = new JLabel (Integer.toString(pScores[3]));
        
        

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
	
	
	int[] pScores = {-1,0,0,0};
	
	public void paintComponent(Graphics page)
	{
		super.paintComponent(page);
		
		for( Drawable img : Driver.drawables ) {
			img.draw( page );
		}
		
		
		
		page.setColor(Color.WHITE);
		//page.drawString("Scores:", 0, 80);
		page.drawString(Integer.toString(pScores[1]), 200, 80);
		page.drawString(Integer.toString(pScores[2]), 450, 80);
		page.drawString(Integer.toString(pScores[3]), 700, 80);
		
		
		
	}

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
			}
			
			//Release the held button
			ClickableButton.onMouseUp();
			
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
		for(  int i = Driver.clickables.size()-1 ; i>= 0; i--) {
			Clickable clickable = Driver.clickables.get(i);
			
			
			if( clickable instanceof ClickableCard ) {
				ClickableCard card = (ClickableCard) clickable;
			}
			
			if( clickable.pointWithin( mouseEvent.getX() ,  mouseEvent.getY() ) ){
				clickable.onMouseDown();
				break;
			}
			
		}
		
		//Redraw everything
		this.repaint();
		
	}
	
	@Override
	public void gameStarted()  {
		
		
		try {
			StartGame.play();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void roundStarted() {
		//Resets the images to be blank
		p1Card.changeImage("GUIImages/Cards/temp.png");
		p2Card.changeImage("GUIImages/Cards/temp.png");
		p3Card.changeImage("GUIImages/Cards/temp.png");
		
		
	}

	@Override
	public void roundWinner(int playerId) {
		
		changeTurn(playerId);
		//TODO updateScores(, player2, player3);
	}

	

	@Override
	public void gameWinner(int playerId) {
		endGame(playerId);
		
	}

	@Override
	public void error(String error) {
		// TODO Auto-generated method stub
		
	}
	static ArrayList<ClickableCard> hand;
	@Override
	public void startingHand(ArrayList<Card> cards) {
		System.out.println("Receiving Hand");
		
		//Receives the cards and turns them into clickable cards
		for (int i = 0; i < cards.size()-1; i++) {
			new ClickableCard( 50 + 30 * i, 500, 156, 256, cards.get(i) ){
				public void onClicked() {
					//System.out.println( "Clicked " + this.card );
				}
			};
			
			
		}
		
	}

	@Override
	public void playableCards(ArrayList<Card> cards) {
		//Sets playable cards as local variable
		playableCardsVar = cards;
		System.out.println("Cards received");
		isTurn = true;
		
	}

	@Override
	public void cardPlayed(int player, Card card) {
		// Plays the given card for the given player
		showPlayedCard(player, card);
		
		
	}

	
	
}
