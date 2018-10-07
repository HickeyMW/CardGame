package UI;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Images {
	
	
	
	private int xPos;
	private int yPos;
	private BufferedImage pic;
	
	
	public Images (String filePath,int cNum, int newxPos, int newyPos) throws IOException {
		if (filePath.toString().equals("GUIimages\\TempCard-50-50-.png")) {
			System.out.println(filePath.toString());
			xPos = cNum;
			yPos = 625;
			pic = ImageIO.read(new File(filePath));
		}else {
		System.out.println(filePath.toString());
		xPos = Integer.parseInt(filePath.split("-")[1]);
		yPos = Integer.parseInt(filePath.split("-")[2]);
		pic = ImageIO.read(new File(filePath));
		}
	}
	
	private void setxPos(int newxPos) {
		xPos = newxPos;
	}
	
	private void setyPos(int newyPos) {
		yPos = newyPos;
	}
	
	
	public int getxPos() {
		return(xPos);
	}

	public int getyPos() {
		return(yPos);
	}
	
	public BufferedImage getImage() {
		return(pic);
	}
	
	public File[] imagePull() {
		File folder = new File("GUIimages/");
		File[] listOfFiles = folder.listFiles();
		
		
		return(listOfFiles);
		
	}
	
}

