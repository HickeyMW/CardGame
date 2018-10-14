package UI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import GameLogic.MainControl;

public class GameLauncher implements GUIStartInterface{
	
	//Holds everything
	public static JFrame launcherWindow = new JFrame("Jack or Better Launcher");
	static JFrame gameWindow = new JFrame( "Jacks or Better" );

	private JButton host;
    private JButton connect;
    private static JTextArea ipInField;
    private JLabel playerInfoLabel;
    private JLabel ipAdLabel;
    private JLabel hostNameLabel;
    private JLabel ipInfoLabel;
    private JButton startButton;
    
    public static GuiPanel gamePanel;
    
    static MainControl gameLogic;
	
	public GameLauncher() throws IOException {
		
		gamePanel = new GuiPanel();
		
		gameLogic = new MainControl( gamePanel, this );
		
		GuiPanel.gameLogic = gameLogic;
		
		launcherWindow.setSize(400, 400);
		launcherWindow.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		gameWindow.setResizable( false );
		
		//construct components
        host = new JButton ("Host");
        host.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae) {
        		//Obtains IP info and displays it
        		try {
					ipPull();
					gameLogic.hostGame();
					
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
        		
        		//Names the start window to player 1 if we are the host
        		//TODO remove this
        		launcherWindow.setTitle( "Player 1" );
        		playerInfoLabel.setText("Hosting");
        		
        		host.setEnabled(false);
        	}
        });
        connect = new JButton ("Connect");
        connect.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae) {
        		//Sends IP info to networking
        		System.out.println(getIPFromText());
        		gameLogic.joinGame(getIPFromText());
        		playerConnected(gameLogic.playerId);
        	}
        });
        startButton = new JButton ("Start Game");
        startButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae) {
        		//Starts the game if all players are connected
        		
					try {
						
						play();
					} catch (IOException e) {
						e.printStackTrace();
					}
        	}
        });
        startButton.setEnabled(false);

        ipInField = new JTextArea ();
        //ipInField.setSize( 500 , 10 );
        playerInfoLabel = new JLabel ("Waiting for Connection");
        ipAdLabel = new JLabel ("############");
        hostNameLabel = new JLabel ("############");
        ipInfoLabel = new JLabel ("Your Connection Info");
        

        //adjust size and set layout
        launcherWindow.setPreferredSize (new Dimension (350, 400));
        launcherWindow.setLayout (null);

        
        JScrollPane scroll = new JScrollPane (ipInField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds (230, 100, 150, 230 );
        
        //add components
        launcherWindow.add (host);
        launcherWindow.add (connect);
        launcherWindow.add ( scroll );
        launcherWindow.add (playerInfoLabel);
        launcherWindow.add (ipAdLabel);
        launcherWindow.add (hostNameLabel);
        launcherWindow.add (ipInfoLabel);
        launcherWindow.add (startButton);

        //set component bounds (only needed by Absolute Positioning)
        host.setBounds (55, 335, 100, 25);
        connect.setBounds (230, 335, 100, 25);
        playerInfoLabel.setBounds (50, 20, 145, 25);
        ipAdLabel.setBounds (55, 240, 200, 25);
        hostNameLabel.setBounds (55, 280, 200, 25);
        ipInfoLabel.setBounds (55, 200, 130, 25);
        startButton.setBounds (225, 25, 100, 25);
        
        ipInField.setText( "127.0.0.1" );
		
        ipInField.setText("127.0.0.1");
		launcherWindow.setVisible(true);
	}
	
	public static void print( String str ){
		//ipInField.setText( ipInField.getText() + str + "\n" );
	}
	
	private String getIPFromText() {
		return ipInField.getText();
	}
	
	public void playerConnected(int player) {
		System.out.println(player);
		if(player==3) {
			playerInfoLabel.setText("All Players Connected");
			startButton.setEnabled(true);
		}
		//TODO Get this info
		
		
		
		
		
	}
	
	
	private void ipPull() throws UnknownHostException{
		InetAddress inetAddress = InetAddress.getLocalHost();
		ipAdLabel.setText("IP: " + inetAddress.getHostAddress());
		hostNameLabel.setText("Host: " + inetAddress.getHostName());
	}
	
	public static void play() throws IOException {
		
		
		
		
		gameWindow.setSize( 1000, 800 );
		gameWindow.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		//gamePanel = new GuiPanel();
		gameWindow.getContentPane().add( gamePanel );
		gameLogic.startGame();
		gameWindow.pack();
		gameWindow.setVisible( true );
		//window.dispose();
		
	}
	
	public static void clientPlay() throws IOException{
		gameWindow.setSize( 1000, 800 );
		gameWindow.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		//gamePanel = new GuiPanel();
		gameWindow.getContentPane().add( gamePanel );
		gameWindow.pack();
		gameWindow.setVisible( true );
		//window.dispose();

		
	}
	

	

	@Override
	public void connectedToServer( int player ) {
		
		//Names the start window AKA the console window to the player number
		//TODO remove this
		GameLauncher.launcherWindow.setTitle( "Player " + player );
		connect.setEnabled(false);
		host.setEnabled(false);
		
		playerInfoLabel.setText("Waiting for Game Start");
		
		
	}

	

	
	
	
}
