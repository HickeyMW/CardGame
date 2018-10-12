package UI;

public class ClickableButton extends Clickable{

	//When we mouse down on a button, we store that button here so we can reset it when we mouse up
	public static ClickableButton heldButton;
	
	public boolean locked = false;
	
	//Image to switch to when this button is held down
	String heldImageURL;
	
	//Image to change to when this button is at rest
	String baseImageURL;
	
	//Image to change to when this button is locked
	String lockedImageURL;
	
	public ClickableButton(int x, int y, int w, int h, String imageURL, String heldImageURL, String lockedImageURL ) {
		super(x, y, w, h, imageURL);
		
		this.heldImageURL = heldImageURL;
		this.baseImageURL = imageURL;
		this.lockedImageURL = lockedImageURL;
	}
	
	//Locks this button and changes it's image
	public void lock() {
		
		//Change the image
		this.changeImage( lockedImageURL );
		
		//Lock the button
		//locked = true;
		
	}
	
	//Resets the held button to its normal state
	public static void onMouseUp() {
		
		//Change the image of the held button
		heldButton.changeImage( heldButton.baseImageURL );
		
		//Remove the held button;
		heldButton = null;
	}
	
	//Should be overridden for actual button functionality
	public void onClicked() {}
	
	//When the mouse goes down here, toggle to our held image
	public void onMouseDown() {
		
		//If we're locked, then we can't be clicked
		if( locked ) {
			return;
		}
		
		//Change our image to be held
		this.changeImage( heldImageURL );
		
		//Set ourselves as the held button
		heldButton = this;
		
	}
	
	

}
