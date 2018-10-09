package GameLogic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import Client.ClientEvents;
import Client.ClientThread;
import Main.Card;
import Server.ServerEvents;
import Server.ServerThread;
import UI.GUIInterface;
import UI.GUIStartInterface;
import UI.StartGame;

public class MainControl implements ClientEvents, ServerEvents, GUIEvents {
	
	public int playerId = 0;

	private ArrayList<ArrayList<Card>> playersHands = new ArrayList<ArrayList<Card>>();
	private Card[] playedCards = new Card[3];
	private int[] playerScores = new int[3];
	private int[] tieBreakerScores = new int[3];
	private ArrayList<Card> myCards = new ArrayList<Card>();

	private ClientThread clientThread;
	private ServerThread serverThread;
	private GUIInterface guiInterface;
	private GUIStartInterface guiStartInterface;
	
	private int numberOfCardsPlayed = 0;
	private int currentPlayerTurn = 1;
	private int leadingPlayer = 1;

	public MainControl( GUIInterface guiInterface, GUIStartInterface guiStartInterface ) {
		
		//Update the GUIInterface to be a real one
		this.guiInterface = guiInterface;
		
		//Update guiStartInterface to be a real one
		this.guiStartInterface = guiStartInterface;
		
		for (int i = 0; i < 3; i++) {
			playersHands.add(new ArrayList<Card>());
		}
	}

	//Client methods

	//Called by GUI
	public void hostGame() {
		serverThread = new ServerThread( this );
		playerId = 1;
	}
	
	public void joinGame(String ip) {
		StartGame.print(ip);
		clientThread = new ClientThread( this, ip);
	}
	


	public void playCard(Card card) {
		
		StartGame.print( "Player " + playerId + " played " + card );
		
		if (playerId == 1) {
			serverThread.broadcastCardPlayed(1, card);
			cardPlayedOnClient(1, card);
		} else {
			clientThread.playCard(card);
		}
	}

	public void startRound() {
		if (playerId == 1) {
			serverThread.broadcastRoundStart(1);
		} else {
			clientThread.startRound();
		}
	}

	public void startGame() {
		
		StartGame.print( "Startgame" );
		
		if (playerId == 1) {
			
			StartGame.print( "Starting game" );
			
			serverThread.broadcastGameStart(playerId);
			
			dealCards();
		} else {
			
			clientThread.startGame();
		}
	}
	
	private void dealCards() {
		ArrayList<Card> deck = new ArrayList<Card>();
		
		for (int i = 0; i < 4; i++) {
			for (int j = 2; j < 15; j++) {
				deck.add(new Card(j, i));
			}
		}
		Collections.shuffle(deck);
		
		for (int i = 0; i < 17; i++) {
			for (int j = 1; j < 4; j++) {
				Card nextCard = deck.get(0);
				deck.remove(0);
				playersHands.get(j - 1).add(nextCard);
				if (j == 1) {
					myCards.add(nextCard);
				} else {
					serverThread.sendCardDealt(j, nextCard);
				}
			}
		}
		
		guiInterface.startingHand( myCards );
		guiInterface.playableCards( myCards );
	}

	public void cardPlayedOnServer(int player, Card card) {
		Card hasCard = playerHasCard(playersHands.get(player - 1), card);
		if (hasCard != null) {
			serverThread.broadcastCardPlayed(player, card);
			playersHands.get(player - 1).remove(hasCard);
			cardPlayedOnClient(player, hasCard);
		}
	}



	//Called by Network

	public void gameStartedOnClient(int startedByID) {
		playerId = clientThread.playerID;
		guiInterface.gameStarted();
		
		clientThread.listenForDealtCard();
	}

	public void cardDealtOnClient(Card card) {
		myCards.add(card);
		if (myCards.size() != 17) {
			clientThread.listenForDealtCard();
		} else {
			guiInterface.startingHand(myCards);
			clientThread.listenForPlayedCard();
		}
	}
	
	private Card playerHasCard(ArrayList<Card> playerCards, Card card) {
		for (Card pCard : playerCards) {
			if (pCard.getSuit() == card.getSuit() && pCard.getValue() == card.getValue()) {
				return pCard;
			}
		}
		return null;
	}


	//Shared methods

	public void cardPlayedOnClient(int player, Card card) {
		
		StartGame.print( "Letting the gui know about player " + player + " playing " + card );
		
		guiInterface.cardPlayed(player, card);
		
		playedCards[player - 1] = card;
		numberOfCardsPlayed++;
		currentPlayerTurn = nextPlayerId();

		if (numberOfCardsPlayed == 3) {
			int winnerId = findRoundWinner();
			guiInterface.roundWinner(winnerId);
			calculateScoring(winnerId);
			guiInterface.updateScores();
			currentPlayerTurn = winnerId;
			if (myCards.size() != 0) {
				if (playerId == 1) {
					serverThread.listenForRoundStart(winnerId);
				} else {
					clientThread.listenForRoundStart();
				}
			} else {
				if (playerId == 1) {
					serverThread.listenForGameStart(findRoundWinner());
				} else {
					clientThread.listenForGameStart();
				}
			}

		} else if (currentPlayerTurn == playerId) {
			guiInterface.playableCards(playableCards());
		} else if (playerId == 1) {
			serverThread.listenForCardPlayed(currentPlayerTurn);
		} else {
			clientThread.listenForPlayedCard();
		}
	}

	private int findRoundWinner() {
		int winner = leadingPlayer;
		if (playedCards[leadingPlayer - 1].getSuit() == playedCards[nextPlayerId() - 1].getSuit() &&
				playedCards[leadingPlayer - 1].getValue() < playedCards[nextPlayerId() - 1].getValue()) {
			winner = nextPlayerId();
		}
		if (playedCards[winner - 1].getSuit() == playedCards[nextNextPlayerId() - 1].getSuit() &&
				playedCards[winner - 1].getValue() < playedCards[nextNextPlayerId() - 1].getValue()) {
			winner = nextPlayerId();
		}
		return winner;
	}

	private int gameWinner() {
		int winner = 1;
		if (playerScores[0] < playerScores[1]) {
			winner = 2;
		} else if (playerScores[0] == playerScores[1] && tieBreakerScores[0] < tieBreakerScores[1]) {
			winner = 2;
		}
		if (playerScores[winner - 1] < playerScores[2]) {
			winner = 3;
		} else if (playerScores[winner - 1] == playerScores[2] && tieBreakerScores[winner - 1] < tieBreakerScores[2]) {
			winner = 3;
		}
		return winner;
	}

	private ArrayList<Card> playableCards() {
		ArrayList<Card> cards = new ArrayList<>();
		if (leadingPlayer == playerId) {
			return myCards;
		} else {
			for (Card card:myCards) {
				if (card.getSuit() == playedCards[leadingPlayer - 1].getSuit()) {
					cards.add(card);
				}
			}
			if (cards.size() == 0) {
				return myCards;
			}
			return cards;
		}
	}

	private void calculateScoring(int winnerId) {
		playerScores[winnerId - 1]++;
		for (int i = 0; i < 3; i++) {
			tieBreakerScores[i] += playedCards[i].getValue();
		}
	}

	private void resetRound() {
		leadingPlayer = findRoundWinner();
	    numberOfCardsPlayed = 0;
    }

    private void resetGame() {
		numberOfCardsPlayed = 0;
		leadingPlayer = 1;
		playerScores = new int[3];
		tieBreakerScores = new int[3];
		currentPlayerTurn = 1;
    }

	private int nextPlayerId() {
		return (currentPlayerTurn % 3) + 1;
	}

	private int nextNextPlayerId() {
		return ((currentPlayerTurn + 1) % 3) + 1;
	}

	public void playerConnectedOnServer(int playerID) {
		guiStartInterface.playerConnected(playerID);
	}

	public void roundStartedOnServer(int startedByID) {
		resetRound();
		guiInterface.roundStarted();
	}

	public void gameStartedOnServer(int startedByID) {
		if (gameWinner() == startedByID) {
			serverThread.broadcastGameStart(startedByID);
		}
		resetGame();
		dealCards();
		guiInterface.gameStarted();
		
	}

	public void connectedToServerOnClient() {
		UI.StartGame.print( "Connectd event" );
		
		//UI.StartGame.print( "Please don't be null " + guiStartInterface );
		
		guiStartInterface.connectedToServer(clientThread.playerID);
		
		//UI.StartGame.print( "Connected on client.  Listening for game start" );
		
		clientThread.listenForGameStart();
		
	}
	//Returns all the players scores
	public int[] getScores() {
		return playerScores;
	}

	public void roundStartedOnClient(int startedByID) {
		if (findRoundWinner() == startedByID) {
			serverThread.broadcastRoundStart(startedByID);
		}
		resetRound();
		guiInterface.roundStarted();
		
	}

	public void error(String error) {
		guiInterface.error(error);
	}
}
