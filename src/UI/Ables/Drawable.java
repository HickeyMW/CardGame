package UI.Ables;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.Driver;
import UI.GuiPanel;
import UI.GameLauncher;

public class Drawable {
	
	public int x;
	public int y;
	
	public int w;
	public int h;
	
	Image image;
	
	String imageURL;
	
	public Drawable( int x, int y, int w, int h, String imageURL ) {
		this.x = x;
		this.y = y;
		
		this.w = w;
		this.h = h;
		
		this.imageURL = imageURL;
		
		try {
			this.image = ImageIO.read( new File( imageURL ) );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		//Register us as drawable
		GuiPanel.drawables.add( this );
		
	}
	
	//Draws the image at the X and Y
	public void draw( Graphics g ) {
		g.drawImage( this.image, x, y, w, h, null );
	}
	
}
