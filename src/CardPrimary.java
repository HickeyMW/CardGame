
public class CardPrimary {
	public static void main(String[] args) {
		
		Card card = new Card( Card.JACK, Card.HEARTS );
		
		card.setValue( 5 );
		
		System.out.println( card );
		//Starts the game UI
		StartGame info = new StartGame();

		
	}
}
