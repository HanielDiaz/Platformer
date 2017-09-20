package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;


public class Map {
	private static BufferedImage img = null;
	private static Graphics2D g2D;
	private Random rand = new Random();
	public int xPos, yPos , length, height ;
	public boolean contact = false;
	public static float xSpeed = -3;
	
	public Map(Point p) {
		length = rand.nextInt(100) + 50;
		height = rand.nextInt(200) + 100;
		xPos = p.x;
		yPos = p.y - height;
		/*	try {
			img = ImageIO.read(name);
			
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	public BufferedImage getMap(){
		if(img != null)
			return img;
		else{
			System.out.println("Image could not be found");
			return null;
		}
			
	}
	public void render(Graphics g){
		if(xPos != -100){
		g2D = (Graphics2D) g ;
		g2D.setColor(Color.darkGray);
		g2D.fillRect(xPos, yPos, length, height);
		}
		
	}
}
