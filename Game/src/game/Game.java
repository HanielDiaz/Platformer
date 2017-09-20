package game;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList; 
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private static Box b;
	private static Action moveR = new MoveR();
	private static Action moveL = new MoveL();
	private static Action noR = new NoR();
	private static Action noL = new NoL();
	private static Action moveU = new MoveU();
	private static Action pause = new Pause();
	private static Boolean u = false,r = false,l = false, WallCling = false;
	private static List<Map> map = new ArrayList<Map>();
	private Iterator<Map> it;
	private static int  countTwo = 1, score = 0;
	private int countGoalTwo = 20;
	private static Random random = new Random();
	private static double x = 1,y = 5;
	private static Menu menu;
	
	public static int height;
	public static int width;
	
	
	private enum GameState {
		Paused,
		Active,
		GameOver
		
	}
	static GameState gamestate = GameState.Paused;

	public Game(String title, int width, int height) {
		Game.width = width;
		Game.height = height;
		Timer timer = new Timer(25,this);
		
		JFrame j = new JFrame(title);
		menu = new Menu();
		
		
		/* JFRAME */
		j.setVisible(true);
		j.setSize(width, height + 20);
		j.setLocationRelativeTo(null);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.requestFocusInWindow();
		
		/* KEY BINDINGS */
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"),"MoveR");
		this.getActionMap().put("MoveR", moveR);
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released RIGHT"),"NoR");
		this.getActionMap().put("NoR", noR);
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "MoveL");
		this.getActionMap().put("MoveL", moveL);
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released LEFT"), "NoL");
		this.getActionMap().put("NoL", noL);
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"),"MoveU");
		this.getActionMap().put("MoveU", moveU);
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"),"Pause");
		this.getActionMap().put("Pause", pause);

		j.add(this);
		
		this.setFocusable(true);;
		this.requestFocus();
		this.setDoubleBuffered(true);
		timer.start();
	}
	static class MoveR extends AbstractAction{

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			System.out.println( b.xPos );  
			if(Game.gamestate == Game.GameState.Active)	
				r = true;
		}
		
	}
	static class NoR extends AbstractAction{

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			System.out.println( b.xPos );    
			if(Game.gamestate == Game.GameState.Active)	
				r = false;
		}
		
	}
	static class MoveU extends AbstractAction{

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			System.out.println( b.yPos );  
			if(Game.gamestate == Game.GameState.Active)	
				u = true;
		}
		
	}
	static class MoveL extends AbstractAction{

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			System.out.println( b.xPos );
			if(Game.gamestate == Game.GameState.Active)	
				l = true;
		}
		
	}
	static class NoL extends AbstractAction{

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			System.out.println( b.xPos );
			if(Game.gamestate == Game.GameState.Active)	
				l = false;
		}
		
	}
	static class Pause extends AbstractAction{

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			if(Game.gamestate == Game.GameState.Paused || Game.gamestate == Game.GameState.GameOver){	
				if(Game.gamestate == Game.GameState.GameOver)
					score = 0;
				Game.gamestate = Game.GameState.Active;
			}
			else{
				Game.gamestate = Game.GameState.Paused;
			}
		}
		
	}
	
	private void render(Graphics g){
		b.render(g);
		it = map.iterator();
		while(it.hasNext())
			 it.next().render(g);
		g.drawString(Integer.toString(score), Game.width/2, 30);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(Game.gamestate == Game.GameState.Active)	{
			render(g);
		}
		else{
			render(g);
			menu.render(g);
		}
			
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(Game.gamestate == Game.GameState.Active){
			
			/* Basic Movement */
			if(r == true){
				WallCling = false;
				b.xPos += b.xSpeed;
			}
			if(l == true){
				WallCling = false;
				b.xPos -= b.xSpeed;
			}
			if(u && x > 0 ){
				WallCling = false;
				b.yPos -= Math.pow(x, 2) + 5;
				x -= .5;
			}
			else if(b.yPos < this.getHeight() - b.side && !WallCling){
				b.yPos += Math.pow(y, 2);
				y += 0.5;
				if(y > 4){
					y = 4;
				}
				u = false;
				//b.yContact = true;
			}
			else{
				if(b.yPos > this.getHeight() - b.side && !WallCling){
					b.yPos = this.getHeight() - b.side;
					b.yContact = true;
					gamestate = GameState.GameOver;
				}
				u = false;
				resetFall();		
			}
			if(b.xPos < -b.side || b.xPos > Game.width){
				gamestate = Game.GameState.GameOver;
			}
			if(map.isEmpty()){
				map.add(new Map(new Point(Game.width/2,Game.height)));
				map.add(new Map(new Point(Game.width - 100,Game.height)));
			}
			
			if(countTwo >= countGoalTwo){
				countGoalTwo = (int) (random.nextInt(50) + 100/b.xSpeed);
				map.add(new Map(new Point(Game.width,Game.height)));
				countTwo = 0;
				score++;
				if(score % 5 == 0){
					b.xSpeed++;
					Map.xSpeed--;
				}
			}
			countTwo++;
			
			/* Checks Platform Collision */
			it = map.iterator();
			while(it.hasNext()){
				Map cur = it.next();
				if((b.xPos <= cur.xPos + cur.length ) && //Lets you stand on platforms
				   (b.yPos + b.side >= cur.yPos) && 
				   (b.xPos + b.side >= cur.xPos) && 
				   (b.yPos + b.side <= cur.yPos + b.side && !u)) {
					 
					b.yPos = cur.yPos - b.side;
					b.xPos += Map.xSpeed;
					cur.contact = true;
					b.yContact = true;
					resetFall();
				}
				else if(b.xPos + b.side >= cur.xPos && //checks for left wall collision
						b.xPos <= cur.xPos + b.side && 
						b.yPos > Game.height - cur.height - b.side){
					b.xPos = cur.xPos - b.side;
					WallCling = true;
					cur.contact = true;
					b.yContact = true;

				}
				else if(b.xPos <= cur.xPos + cur.length && //checks for right wall collision
						b.xPos >= cur.xPos + cur.length - b.side && 
						b.yPos > Game.height - cur.height - b.side ){
					b.xPos = cur.xPos + cur.length;
					b.xPos += Map.xSpeed;
					WallCling = true;
					b.yContact = true;
					cur.contact = true;

				}
				else if(cur.contact && (b.yPos < cur.height || b.xPos > cur.xPos + cur.length)){
					WallCling = false;
					cur.contact = false;					
				}
				else{
					b.yContact = false;
				}
				
			}
			/* Moves Platforms */
			it = map.iterator();
			while(it.hasNext()){
				Map cur = it.next();
				cur.xPos += Map.xSpeed;
			}
			if(!map.isEmpty() && map.get(0).xPos + map.get(0).length <= 0)
				map.remove(0);
		}
		else if(gamestate == GameState.GameOver){
			int q = map.size();
			WallCling = false;
			b.xPos = Game.width/2;
			b.yPos = 50;
			r = false; l = false; u = false;
			for(;q > 0; --q){
				map.remove(0);
			}
			Map.xSpeed = -3;
			b.xSpeed = 5;
		}
		this.repaint();
		
		
	}
	private void resetFall(){
		u = false;
		x = 5;
		y = 1;
	}
	public static void main(String[] args){
		new Game("Box", 700, 400);
		b = new Box(Game.width/2, 50);
	}
}
