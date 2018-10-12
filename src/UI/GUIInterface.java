package UI;

import java.awt.CardLayout;
import java.io.IOException;
import java.util.ArrayList;

import Main.Card;

public interface GUIInterface {

    //What game logic sends to UI

    void roundStarted();

    void roundWinner(int playerId);

    void gameStarted();
    
    void gameWinner(int playerId);

    void error(String error);

    void startingHand(ArrayList<Main.Card> cards);

    void playableCards(ArrayList<Main.Card> cards);

    void updateScores( int P1Score, int P2Score, int P3Score );

	void cardPlayed(int player, Card card);
}