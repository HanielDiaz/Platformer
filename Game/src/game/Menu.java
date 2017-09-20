package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Menu {
	private static int width = Game.width, height = Game.height;
	public Menu() {
		
	}
	
	public void render(Graphics g){
		Graphics2D g2D = (Graphics2D) g ;
		g2D.setColor(new Color(100,132,183,200));
		//g2D.rotate(-45);
		g2D.fillRect(0, 0, width, height);
		g = g2D;
		 }
}
