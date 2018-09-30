package UI;

import java.util.ArrayList;

public interface GUIInterface {

    //Server methods

    void playerConnected(int PlayerId);

    //Client methods

    void connectedToServer();

    //General methods

    void roundStarted();

    void gameStarted();

    void error(String error);

    void startingHand(ArrayList<Main.Card> cards);

    void playableCards(ArrayList<Main.Card> cards);

    void updateScores(int player1, int player2, int player3);
}
