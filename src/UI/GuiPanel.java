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
	
	
	Boolean isTurn = true;
	MainControl ctrl;
	
	
	ClickableButton roundButton = 	new ClickableButton( 700, 600, 300, 100, "GUIImages/NextRound.png", "GUIImages/NextRoundNot.png" ) {
		public void onClicked() {
			System.out.println( "Clicked round change" );
			//endGame();
			
			//TODO 
			//startRound();
			
			
		}
	};
	
	ClickableButton playButton = 	new ClickableButton( 700, 700, 300, 100, "GUIImages/PlayCard.png", "GUIImages/PlayCardNot.png" ) {
		public void onClicked() {
			System.out.println( "Clicked play" );
			
			//Detects if there is a selected card and passes it to game logic
			if(ClickableCard.selectedCard!=null) {
				System.out.println(ClickableCard.selectedCard.card.toString());
				ctrl.playCard(ClickableCard.selectedCard.card);
				showPlayedCard(ctrl.playerId, ClickableCard.selectedCard.card);
				
				ClickableCard.cardPlayed();
				
			}
			//TODO Uncomment
			
			
			
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

		
		/*
		//Generate some cards
		for (int i = 0; i < 17; i++) {
		
			Card card = new Card( 1 + i%13, (int) ( Math.random() * 3 ) );
			
			new ClickableCard( 50 + 30 * i, 500, 156, 256, card ) {
				public void onClicked() {
					//System.out.println( "Clicked " + this.card );
				}
			};
			
		}
		*/
		
		
		
		
	}
	
	public void createCtrl(MainControl newCtrl) {
		ctrl = newCtrl;
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
	
	public void updateScores(int player1, int player2, int player3){
		p1Score = player1;
		p2Score = player2;
		p3Score = player3;
	}
	
	
	public void changeTurn(int player) {
		
		if(player == 2 /*TODO playerID*/)
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
	Drawable p1Card = new Drawable(150, 125, 156, 256, "GUIImages/Cards/temp.png");
	Drawable p2Card = new Drawable(400, 125, 156, 256, "GUIImages/Cards/temp.png");
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
	
	public void endGame() {
		StartGame.frame.dispose();
		JLabel winnerLabel;
	    JButton restartButton;
	    JLabel p2;
	    JLabel p3;
	    JLabel p1;
	    JLabel p1Score;
	    JLabel p2Score;
	    JLabel p3Score;
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
        p2 = new JLabel ("Player 2:");
        p3 = new JLabel ("Player 3:");
        p1 = new JLabel ("Player 1:");
        p1Score = new JLabel ("####");
        p2Score = new JLabel ("####");
        p3Score = new JLabel ("####");

        //adjust size and set layout
        end.setPreferredSize (new Dimension (187, 235));
        end.setLayout (null);

        //add components
        end.add (winnerLabel);
        end.add (restartButton);
        end.add (p2);
        end.add (p3);
        end.add (p1);
        end.add (p1Score);
        end.add (p2Score);
        end.add (p3Score);

        //set component bounds (only needed by Absolute Positioning)
        winnerLabel.setBounds (20, 20, 100, 25);
        restartButton.setBounds (20, 170, 100, 25);
        p2.setBounds (20, 90, 60, 25);
        p3.setBounds (20, 125, 60, 25);
        p1.setBounds (20, 55, 60, 25);
        p1Score.setBounds (100, 55, 40, 25);
        p2Score.setBounds (100, 90, 40, 25);
        p3Score.setBounds (100, 125, 45, 20);
		
        end.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        end.pack();
		end.setVisible(true);
		
		
		
		
		
	}
	
	int p1Score=0;
	int p2Score=0;
	int p3Score=0;
	
	public void paintComponent(Graphics page)
	{
		super.paintComponent(page);
		
		for( Drawable img : Driver.drawables ) {
			img.draw( page );
		}
		
		page.drawString("Scores:", 0, 80);
		page.drawString(Integer.toString(p1Score), 200, 80);
		page.drawString(Integer.toString(p2Score), 450, 80);
		page.drawString(Integer.toString(p3Score), 700, 80);
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}
	
	@Override
	public void mouseReleased( MouseEvent mouseEvent ) {
		
		//If there's something held, let it go
		if( ClickableButton.heldButton != null ) {
			
			//If we just released over the held button then we did a full click on it
			if( ClickableButton.heldButton.pointWithin( mouseEvent.getX() ,  mouseEvent.getY() ) ){
			
				//ClickableButton.heldButton.onClicked();
				
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
				clickable.onClicked();
				clickable.onMouseDown();
				break;
			}
			
		}
		
		//Redraw everything
		this.repaint();
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	
		
	}

	
	@Override
	public void gameStarted()  {
		
		
		try {
			StartGame.play();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void roundStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void roundWinner(int playerId) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void gameWinner(int playerId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(String error) {
		// TODO Auto-generated method stub
		
	}
	static ArrayList<ClickableCard> hand;
	@Override
	public void startingHand(ArrayList<Card> cards) {
		System.out.println("Receiving Hand");
		
		
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
		// TODO Auto-generated method stub
		
	}
	
}
