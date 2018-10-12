package UI;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.Driver;

public class Drawable {

	String imageURL;
	
	int x;
	int y;
	
	int w;
	int h;
	
	Image image;
	
	public Drawable( int x, int y, int w, int h, String imageURL ) {
		this.x = x;
		this.y = y;
		
		this.w = w;
		this.h = h;
		
		this.imageURL = imageURL;
		try {
			image = ImageIO.read( new File( imageURL ) );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		//Register us as drawable
		GuiPanel.drawables.add( this );
		
	}
	
	//Changes the image this drawable should draw
	public void changeImage( String newImageURL ) {
		
		StartGame.print( "Drawable - Changing image on " + this );
		
		try {
			image = ImageIO.read( new File( newImageURL ) );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			StartGame.print("Failed to read image");
		}
		
		//Driver.drawables.remove( this );
		//Driver.drawables.add( this );
		
		//Redraw
		StartGame.panel.repaint();
		
		StartGame.print( "Drawable - Finished changing image on " + this );
		
	}
	
	//Draws the image at the X and Y
	public void draw( Graphics g ) {
		g.drawImage( image, x, y, w, h, null );
	}
	
}
