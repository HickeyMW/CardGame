package GameLogic;

import Client.ClientEvents;
import Main.Card;
import Main.CardGamePrimary;
import Server.ServerEvents;

public class GameLogicClient implements ClientEvents{
	
	//@Override
	public void connectedToServerOnClient() {
		CardGamePrimary.ui.print( "Client connected to server event!" );
		
		CardGamePrimary.client.startRound();
	}

	//@Override
	public void cardPlayedOnClient(int playedByID, Card card) {
		CardGamePrimary.ui.print( "Player " + playedByID + " just played " + card );
	}

	//@Override
	public void roundStartedOnClient(int startedByID) {
		CardGamePrimary.ui.print( "Player " + startedByID + " just started the round");
	}

	//@Override
	public void gameStartedOnClient(int startedByID) {
		CardGamePrimary.ui.print( "Player " + startedByID + " just started the game");		
	}

	//@Override
	public void cardDealtOnClient(Card card) {
		CardGamePrimary.ui.print( "Server dealt us " + card );
		
	}

	//@Override
	public void error( String error ) {
		// TODO Auto-generated method stub
		
	}
}
