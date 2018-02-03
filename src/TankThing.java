import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class TankThing extends JPanel{
	
	Dimension screenDim = new Dimension(700,500);
	
	Thread gameLoop;
	Runnable gameEngine;
	boolean gameIsRunning;
	
	int currentFPS;
	int MAX_FPS = 60;
	
	ArrayList<GameObject> sprites = new ArrayList<GameObject>();
	Terrain terrain;
	
	final int GRAVITY = 2;
	final int MAX_GRAVITY = 20;
	
	Tank player1;
	
	public TankThing(){
		panel();
		start();
		keys();
		this.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				sprites.add(new Projectile(e.getX(),e.getY(),0,ProjectileType.SIMPLE));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
	
	void keys(){
		getInputMap().put(KeyStroke.getKeyStroke("A"), "A");		
		getInputMap().put(KeyStroke.getKeyStroke("D"), "D");
		getInputMap().put(KeyStroke.getKeyStroke("released A"), "rA");		
		getInputMap().put(KeyStroke.getKeyStroke("released D"), "rD");
		
		getActionMap().put("A", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(player1.canMove){
				player1.dx = -player1.t.speed;
				}
				
			}
			
		});
		getActionMap().put("D", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(player1.canMove){
				player1.dx = player1.t.speed;
				}
				
			}
			
		});
		getActionMap().put("rA", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				player1.dx = 0;
				player1.canMove = true;
			}
			
		});
		getActionMap().put("rD", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				player1.dx = 0;
				player1.canMove = true;
				
			}
			
		});

	}
	
	synchronized void start(){
		
		gameEngine = () -> gameLoop();
		gameLoop = new Thread(gameEngine);
		gameIsRunning = true;
		gameLoop.start();
		
		terrain = new Terrain(screenDim, (int) screenDim.getHeight() / 2, 1);
		player1 = new Tank((int) screenDim.getWidth() / 10 , 0 ,terrain,TankType.SIMPLE);
		sprites.add(terrain);
		sprites.add(player1);
	}
	
	 void gameLoop(){
		long previousTime = System.currentTimeMillis();
		long currentTime = previousTime;
		long elapsedTime;
		long totalElapsedTime = 0;
		int frameCount = 0;

		while(gameIsRunning)
		{
			currentTime = System.currentTimeMillis();
			elapsedTime = (currentTime - previousTime); 
			totalElapsedTime += elapsedTime;

			if (totalElapsedTime > 1000)
			{
				currentFPS = frameCount;
				frameCount = 0;
				totalElapsedTime = 0;
			}

			update();

			try
			{
				Thread.sleep(getFpsDelay(MAX_FPS));
			} catch (Exception e) {
				e.printStackTrace();
			}

			previousTime = currentTime;
			frameCount++;

		}

	}
	int getFpsDelay(int desiredFps)
	{
		return 1000 / desiredFps;
	}
	
	
	void update(){
		handleObjects();
		physics();
		repaint();
	}
	
	void handleObjects(){
		final int BUFFER = 100;
		for(int i = 0; i < sprites.size(); i++){
			GameObject o = sprites.get(i);
			
			//checking for termination
			if(o.setForTermination) { 
				sprites.remove(i);
				i--;
			}
			
			//checking contacts
			o.checkingContacts(sprites);
			
			//checking if out of bounds
			if(o.x < -BUFFER || o.x > screenDim.getWidth() + BUFFER || o.y > screenDim.getHeight() + BUFFER){
				o.setForTermination = true;
			}
			
			//updating the hitboxes of objects
			o.updateHitbox();
			
		}
	}
	
	void physics(){
		
		for(int i = 0 ; i < sprites.size(); i++){
			GameObject o = sprites.get(i); //grabbing sprite
			
			
			//applying gravity
			if(o.affectedByGravity){
				for(int i2 = 0; i2 < o.hitboxes.size(); i2++){
					Hitbox box = o.hitboxes.get(i2);
					if(!box.inContact){ //apply gravity
						if(o.dy < MAX_GRAVITY){
						o.dy += GRAVITY;
						}
					}else{
						o.dy = 0;
					}
				}
			}
			
			
			
			
			//moving according to velocity
			if(o.canMove){
			o.x += o.dx;
			o.y += o.dy;
			}
		}
	
		
	}
	
	
	void panel(){
		JFrame frame = new JFrame();
		frame.add(this);
		frame.setPreferredSize(screenDim);
		this.setBackground(Color.BLUE);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawSprites(g);
		drawStats(g);
	}
	
	void drawStats(Graphics g){
		g.setColor(Color.WHITE);
		g.setFont(new Font("Aerial",Font.BOLD,20));
		g.drawString("FPS: " + currentFPS, 10, 40);
	}
	void drawSprites(Graphics g){
		for(int index = 0; index < sprites.size(); index++){
			GameObject o = sprites.get(index);
			o.draw(g);
		}
	}
	
	
	
}
