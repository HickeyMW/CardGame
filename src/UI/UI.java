package UI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import Client.ClientThread;
import Main.CardGamePrimary;
import Server.ServerThread;

public class UI {

	public JFrame frame;
	public JButton serverButton;
	public JButton clientButton;

	public JTextArea textArea;

	public UI(){
		frame = new JFrame();
		frame.setSize( 500 , 500 );
		frame.setResizable( false );
		frame.setLayout( new BorderLayout() );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		serverButton = new JButton();
		serverButton.setText( "Start Server" );
		frame.add( serverButton, BorderLayout.NORTH );
		serverButton.addActionListener(
			new ActionListener() { 
				public void actionPerformed(ActionEvent e) { 
					startServerButtonPressed();
				} 
			} 
		);

		textArea = new JTextArea();
		textArea.setSize( 500 , 10 );
		frame.add( textArea, BorderLayout.CENTER );



		clientButton = new JButton();
		clientButton.setText( "Start Client" );
		frame.add( clientButton, BorderLayout.SOUTH );
		clientButton.addActionListener(
			new ActionListener() { 
				public void actionPerformed(ActionEvent e) { 
					startClientButtonPressed();
				} 
			} 
		);
		
		frame.setVisible( true );
	}

	private void startServerButtonPressed(){

		//Create a ServerThread and send that to the driver for access from game logic
		CardGamePrimary.server = new ServerThread();

	}

	private void startClientButtonPressed(){

		//Create a ServerThread and send that to the driver for access from game logic
		CardGamePrimary.client = new ClientThread( "127.0.0.1" );

	}

	public void print( String str ){
		textArea.setText( textArea.getText() + str + "\n" );
	}

}
