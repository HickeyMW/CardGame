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

public class StartGame implements GUIStartInterface{
	
	//Holds everything
	public static JFrame window = new JFrame("Jack or Better Launcher");
	static JFrame frame = new JFrame( "Jacks or Better" );

	private JButton host;
    private JButton connect;
    private static JTextArea ipInField;
    private JLabel playerInfoLabel;
    private JLabel p1;
    private JLabel p2;
    private JLabel p3;
    private JLabel ipAdLabel;
    private JLabel hostNameLabel;
    private JLabel ipInfoLabel;
    private JButton startButton;
    
    public static GuiPanel panel;
    
    static MainControl ctrl;
	
	public StartGame() throws IOException {
		
		panel = new GuiPanel();
		
		ctrl = new MainControl( panel, this );
		
		window.setSize(400, 400);
		window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		//construct components
        host = new JButton ("Host");
        host.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae) {
        		//Obtains IP info and displays it
        		try {
					ipPull();
					ctrl.hostGame();
					
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
        		
        		//Names the start window to player 1 if we are the host
        		//TODO remove this
        		StartGame.window.setTitle( "Player 1" );
        		
        		
        	}
        });
        connect = new JButton ("Connect");
        connect.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae) {
        		//Sends IP info to networking
        		System.out.println(getIPFromText());
        		ctrl.joinGame(getIPFromText());
        	}
        });
        startButton = new JButton ("Start Game");
        startButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae) {
        		//Starts the game if all players are connected
        		if(true)
					try {
						play();
					} catch (IOException e) {
						e.printStackTrace();
					}
        	}
        });

        ipInField = new JTextArea ();
        //ipInField.setSize( 500 , 10 );
        playerInfoLabel = new JLabel ("Connected Players");
        p1 = new JLabel ("Player 1: Not Connected");
        p2 = new JLabel ("Player 2: Not Connected");
        p3 = new JLabel ("Player 3: Not Connected");
        ipAdLabel = new JLabel ("############");
        hostNameLabel = new JLabel ("############");
        ipInfoLabel = new JLabel ("Your Connection Info");
        

        //adjust size and set layout
        window.setPreferredSize (new Dimension (350, 400));
        window.setLayout (null);

        
        JScrollPane scroll = new JScrollPane (ipInField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds (230, 100, 150, 230 );
        
        //add components
        window.add (host);
        window.add (connect);
        window.add ( scroll );
        window.add (playerInfoLabel);
        window.add (p1);
        window.add (p2);
        window.add (p3);
        window.add (ipAdLabel);
        window.add (hostNameLabel);
        window.add (ipInfoLabel);
        window.add (startButton);

        //set component bounds (only needed by Absolute Positioning)
        host.setBounds (55, 335, 100, 25);
        connect.setBounds (230, 335, 100, 25);
        playerInfoLabel.setBounds (50, 20, 145, 25);
        p1.setBounds (50, 60, 175, 20);
        p2.setBounds (50, 90, 140, 25);
        p3.setBounds (50, 125, 145, 25);
        ipAdLabel.setBounds (55, 240, 200, 25);
        hostNameLabel.setBounds (55, 280, 200, 25);
        ipInfoLabel.setBounds (55, 200, 130, 25);
        startButton.setBounds (225, 25, 100, 25);
        
        ipInField.setText( "127.0.0.1" );
		
        ipInField.setText("127.0.0.1");
		window.setVisible(true);
	}
	
	public static void print( String str ){
		ipInField.setText( ipInField.getText() + str + "\n" );
	}
	
	private String getIPFromText() {
		return ipInField.getText();
	}
	
	public void playerConnected(int player) {
		
		if(player==1)
			p1.setText("Player 1: Connected");
		else if(player == 2)
			p2.setText("Player 2: Connected");
		else if(player == 3)
			p3.setText("Player 3: Connected");
		//TODO Get this info
		
	}
	
	
	private void ipPull() throws UnknownHostException{
		InetAddress inetAddress = InetAddress.getLocalHost();
		ipAdLabel.setText("IP: " + inetAddress.getHostAddress());
		hostNameLabel.setText("Host: " + inetAddress.getHostName());
	}
	
	public static void play() throws IOException {
		
		ctrl.startGame();
		
		
		frame.setSize( 1000, 800 );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		panel = new GuiPanel();
		panel.createCtrl(ctrl);
		frame.getContentPane().add( panel );
		
		frame.pack();
		frame.setVisible( true );
		//window.dispose();
		
	}
	
	
	

	

	@Override
	public void connectedToServer( int playerID ) {
		
		//Names the start window AKA the console window to the player number
		//TODO remove this
		StartGame.window.setTitle( "Player " + playerID );
		
		if( playerID==1 )
			p1.setText("Player 1: Connected");
		else if( playerID == 2 )
			p2.setText("Player 2: Connected");
		else if( playerID == 3 )
			p3.setText("Player 3: Connected");
		
	}

	

	
	
	
}
