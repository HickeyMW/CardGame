package UI;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import GameLogic.MainControl;
import Main.Card;

public class TestGUI extends JFrame implements GUIStartInterface, GUIInterface {
	
	JTextField jTextField;
	JTextArea jTextArea;
	ArrayList<Card> availableCards;
	MainControl mainControl;
	boolean autoPlay = true;
	
	public TestGUI() {
		mainControl = new MainControl(this, this);
		JPanel jPanel = new JPanel();
		// TODO Auto-generated constructor stub
		setSize(600, 600);
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		JButton btnStart = new JButton ("Start Game");
		
        btnStart.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae) {
        		mainControl.startGame();
        		System.out.println("Start");
        		
        	}
        });
        JButton btnHost = new JButton ("Host");
        btnHost.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae) {
        		mainControl.hostGame();
        		
        	}
        });
        JButton btnJoin = new JButton ("Join");
        btnJoin.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae) {
        		mainControl.joinGame("127.0.0.1");
        		
        	}
        });
        JButton btnPlayCard = new JButton ("Play Card");
        btnPlayCard.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae) {
        		int num = 0;
        		if (!jTextField.getText().equals("")) {
        			Integer.parseInt(jTextField.getText());
				}        		
        		mainControl.playCard(availableCards.get(num));
        		
        	}
        });
        JButton btnStartRound = new JButton ("Start Round");
        btnStartRound.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae) {
        		mainControl.startRound();
        		
        	}
        });
        JButton btnClear = new JButton ("Clear");
        btnClear.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae) {
        		jTextArea.setText("");
        		
        	}
        });
        JButton btnAuto = new JButton ("Auto");
        btnAuto.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae) {
        		autoPlay = !autoPlay;
        		
        	}
        });
        
        
        jTextField = new JTextField();
        jTextField.setPreferredSize(new Dimension(200, 50));
        jTextArea = new JTextArea();
        jTextArea.setPreferredSize(new Dimension(150, 200));
        JScrollPane scroll = new JScrollPane (jTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds (230, 100, 150, 230 );
        jTextArea.setPreferredSize(new Dimension(150, 20000));
        scroll.setPreferredSize(new Dimension(300, 500));
        
        btnStart.setSize(100, 50);
        btnHost.setSize(100, 50);
        btnJoin.setSize(100, 50);
        btnPlayCard.setSize(100, 50);
        btnStartRound.setSize(100, 50);
        btnClear.setSize(100, 50);
        btnAuto.setSize(100, 50);
        
        jPanel.add(btnHost);
        jPanel.add(btnStart);
        jPanel.add(btnJoin);
        jPanel.add(btnPlayCard);
        jPanel.add(btnStartRound);
        jPanel.add(btnClear);
        jPanel.add(btnAuto);
        jPanel.add(jTextField);
        jPanel.add(scroll);
        
        add(jPanel);
        
		setVisible(true);
		
		
	}
	
	

	@Override
	public void roundStarted() {
		// TODO Auto-generated method stub
		jTextArea.append("\nRound Started");
		
	}

	@Override
	public void roundWinner(int playerId) {
		// TODO Auto-generated method stub
		jTextArea.append("\nRound winner " + playerId);
		if (playerId == mainControl.playerId) {
			jTextArea.append("\n\n@@@@@@@@@@@@@@@@@");
		}
		if (autoPlay && playerId == mainControl.playerId) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mainControl.startRound();
		}
	}

	@Override
	public void gameStarted() {
		// TODO Auto-generated method stub
		jTextArea.append("\nGame Started");
		
	}

	@Override
	public void gameWinner(int playerId) {
		// TODO Auto-generated method stub
		jTextArea.append("\nGame winner " + playerId);
	}

	@Override
	public void error(String error) {
		// TODO Auto-generated method stub
		jTextArea.append("\nError " + error);
		
	}

	@Override
	public void startingHand(ArrayList<Card> cards) {
		// TODO Auto-generated method stub
		jTextArea.append("\nStarting Hand");
		for (Card card : cards) {
			jTextArea.append("\nStarting: " + card.toString());
		}
		
	}

	@Override
	public void playableCards(ArrayList<Card> cards) {
		// TODO Auto-generated method stub
		availableCards = cards;
		
		jTextArea.append("\n\nPlayable cards");
		int i = 0;
		for (Card card : cards) {
			jTextArea.append("\n" + i + ":    " + card.toString());
			i++;
		}
		if (autoPlay) {
			mainControl.playCard(availableCards.get(0));
		}
	}

	@Override
	public void updateScores(int[] scores) {
		// TODO Auto-generated method stub
		jTextArea.append("\nPlayer 1 " + scores[0] + " Player 2 " + scores[1] + " Player 3 " + scores[2]);
	}

	@Override
	public void cardPlayed(int player, Card card) {
		// TODO Auto-generated method stub
		jTextArea.append("\nPlayer " + player + " played " + card.toString());
		
	}

	@Override
	public void playerConnected(int playerId) {
		// TODO Auto-generated method stub
		jTextArea.append("\nPlayer " + playerId + " connected");
	}

	@Override
	public void connectedToServer(int playerId) {
		// TODO Auto-generated method stub
		jTextArea.append("\nConnected to server with id " + playerId);
		
	}

}
