package UI;

import java.io.IOException;
import java.util.ArrayList;

public interface GUIInterface {

    //General methods

    void roundStarted();

    void roundWinner(int playerId);

    void gameStarted();
    
    void gameWinner(int playerId);

    void error(String error);

    void startingHand(ArrayList<Main.Card> cards);

    void playableCards(ArrayList<Main.Card> cards);

    void updateScores(int player1, int player2, int player3);
}