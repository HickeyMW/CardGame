package GameLogic;

import Client.ClientEvents;
import Main.Card;
import Main.CardGamePrimary;
import Server.ServerEvents;

public class GameLogicServer implements ServerEvents{

	
	//@Override
	public void playerConnectedOnServer( int playerID ) {
		CardGamePrimary.ui.print( "Server connected to player " + playerID + "  event!" );
		
		CardGamePrimary.server.listenForCardPlayed( 3 );
	}

	//@Override
	public void cardPlayedOnServer(int playedByID, Card card) {
		CardGamePrimary.ui.print( "Player " + playedByID + " played a " + card );
	}

	//@Override
	public void roundStartedOnServer(int startedByID) {
		CardGamePrimary.ui.print( "Round started event!" );
	}

	//@Override
	public void gameStartedOnServer(int startedByID) {
		CardGamePrimary.ui.print( "Game started event!" );
	}
	
	//@Override
	public void error( String error ) {
		CardGamePrimary.ui.print( "There was an error!" );
	}
	
}
