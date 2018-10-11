package UI;

public class ClickableButton extends Clickable{

	//When we mouse down on a button, we store that button here so we can reset it when we mouse up
	public static ClickableButton heldButton;
	
	//Image to switch to when this button is held down
	String heldImageURL;
	
	String baseImageURL;
	
	public ClickableButton(int x, int y, int w, int h, String imageURL, String heldImageURL ) {
		super(x, y, w, h, imageURL);
		
		this.heldImageURL = heldImageURL;
		this.baseImageURL = imageURL;
	}
	
	//Resets the held button to its normal state
	public void onMouseUp() {
		StartGame.print("Button up");
		//Change the image of the held button
		heldButton.changeImage( heldButton.baseImageURL );
		
		//Remove the held button;
		heldButton = null;
	}
	
	public void onClicked() {}
	
	
	//When the mouse goes down here, toggle to our held image
	public void onMouseDown() {
		StartGame.print("Button down");
		//Change our image to be held
		this.changeImage( heldImageURL );
		StartGame.print("Image changed");
		//Set ourselves as the held image
		heldButton = this;
		StartGame.print("Button held");
		
	}
	
	

}
