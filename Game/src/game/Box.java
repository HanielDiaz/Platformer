package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Box {
	public float xPos, yPos, xSpeed = 5, side = 25;
	public boolean yContact = false;
	Graphics2D g2D;
	
	public Box(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	public void render(Graphics g){
		g2D = (Graphics2D) g ;
		g2D.setColor(Color.red);
		//g2D.rotate(-45);
		g2D.fillRect((int)xPos, (int)yPos, (int)side, (int)side);
		g = g2D;
	}
}
