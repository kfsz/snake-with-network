package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Timer;

import javax.swing.JPanel;

import game.keyListener;

public class mainGame extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private Rectangle snake;
	private Rectangle enemy;
	private Rectangle ui;
	private score score;
	private int highscore;
	private file scoretable;
	int time; int misc = 0;
	
	ArrayList<Rectangle> enemyB = new ArrayList<Rectangle>();
	ArrayList<Rectangle> snakeB = new ArrayList<Rectangle>();
	ArrayList<Rectangle> snakeC = new ArrayList<Rectangle>();
	ArrayList<Integer> snakeDirection = new ArrayList<Integer>();
	
	public static int safety = 20; //out of bounds bonus
	public static int velX, velY;
	public int speedIterator = 0, speedIterator2 = 0, tempX = 0, tempY = 0;
	public double diff = 0.25;
	public double speed = 8; //8 or 4
	public boolean running = true;
		
	public mainGame(){			//init
		snake = new Rectangle(10,10,30,30);
		ui = new Rectangle(1024,0,200,768);
		
		snakeB.clear();
		
		setBackground(Color.pink);
		setPreferredSize(new Dimension(1024+200,768));
		addKeyListener(new keyListener());
		setFocusable(true);
		}
	
	public void paintComponent(Graphics graphics){
		super.paintComponent(graphics);
		Graphics2D graphics2d=(Graphics2D) graphics;
		
		graphics2d.setColor(Color.blue);
		graphics2d.fill(snake);

		
			graphics2d.setColor(Color.green); misc=0;
			for (Rectangle object: snakeB) {misc++;
				graphics2d.setColor(Color.green);
				if(misc==2){graphics2d.setColor(Color.lightGray); misc=0;}
				graphics2d.fill(object);
			}
		
		graphics2d.setColor(Color.red);
		graphics2d.fill(enemy);
		
			for (Rectangle object: enemyB) {
				if(object.getHeight()==36){
					graphics2d.setColor(Color.black);
					graphics2d.fill(object);
					graphics2d.setColor(Color.red);
				}	else		
				graphics2d.fill(object);
			}
		
		graphics2d.setColor(Color.gray);
		graphics2d.fill(ui);
		
		// text
		
		graphics2d.setColor(Color.pink);	
		graphics2d.setFont(new Font("TimesRoman", Font.ITALIC, 50)); 
		
		graphics2d.drawString("score", 1024+10, 60);	
			graphics2d.drawString(Integer.toString(score.retScore()), 1024+10, 120);	
		graphics2d.drawString("time", 1024+10, 180);
			graphics2d.drawString(Integer.toString(time/60), 1024+10, 240);	
	
		graphics2d.drawString("body", 1024+10, 460);
		graphics2d.drawString("parts", 1024+10, 510);
			graphics2d.drawString(Integer.toString(snakeB.size() + 1), 1024+10, 570);	
		
		
			graphics2d.drawString(Integer.toString(time/60), 1024+10, 240);		
			
			if(running==false){
					if(score.retScore()>highscore) graphics2d.drawString("!new!", 1024+10, 660);
				graphics2d.drawString("end", 1024+10, 720);
			} else {
			
				graphics2d.drawString("hscore", 1024+10, 660);
				if(score.retScore()>highscore){
					  graphics2d.drawString(Integer.toString(score.retScore()), 1024+10, 720);}
				else {graphics2d.drawString(Integer.toString(highscore), 1024+10, 720);}
				
			}
		
	}
	
	public void moveX(double move){		
	if(snake.getX()+move<1024-snake.getWidth()+safety && snake.getX()+move>0-safety){
		snake.setBounds((int)snake.getX()+(int)move, 
				(int)snake.getY(), 
			(int)snake.getWidth(),(int)snake.getHeight());
		}else{running=false;} //wall collision
	}
	
	public void moveY(double move){
	if(snake.getY()+move<768-snake.getHeight()+safety && snake.getY()+move>0-safety){	
		snake.setBounds((int)snake.getX(),  
				(int)snake.getY()+(int)move, 
			(int)snake.getWidth(),(int)snake.getHeight());
		}else{running=false;} //wall collision
	}
	
	public void go(int velX, int velY){
		moveX((double)velX*speed);
		moveY((double)velY*speed);
	};	
	
	public void goB(int velX, int velY){
		snakeC.add(new Rectangle( (int)snake.getX(), (int)snake.getY(), 30, 30));	
			int iii= snakeC.size() - 2;
			
		for (Rectangle object: snakeB) {		
			object.setLocation(snakeC.get(iii).getLocation());
			iii--;
		}

		if(snakeC.size()>snakeB.size() + 10){
			for (int i = snakeC.size()-snakeB.size() - 5; i >= 0; i--){snakeC.remove(i);}			
			}		
		};	
	
	public void checkCollision(double X, double Y){
		if(snake.getX()>enemy.getX()-enemy.getWidth() && snake.getX()<enemy.getX()+enemy.getWidth()		   
			&& snake.getY()>enemy.getY()-enemy.getHeight() && snake.getY()<enemy.getY()+enemy.getHeight())
		{
				int scoreTest=(int)speed;
				speed=speed+0.2; 
				if(scoreTest<(int)speed){score.incMulti(0.5);}
			spawn();
			snakeB.add(new Rectangle(-20, -20, 30, 30)); // -20 for safety
			score.incScore(5);
		} //ate food
		
		int toRemove = -1;
		for (Rectangle object: enemyB) {
			if(snake.getX()>object.getX()-object.getWidth() && snake.getX()<object.getX()+object.getWidth()		   
			&& snake.getY()>object.getY()-object.getHeight() && snake.getY()<object.getY()+object.getHeight())
				{
						int scoreTest=(int)speed;
						speed=speed+0.3; 
						if(scoreTest<(int)speed){score.incMulti(0.5);}
								snakeB.add(new Rectangle(-20, -20, 30, 30));
								snakeB.add(new Rectangle(-20, -20, 30, 30));
								snakeB.add(new Rectangle(-20, -20, 30, 30));
						if(object.getHeight()==36){
							running=false;
						} else
					score.incScore(25);
					toRemove = enemyB.lastIndexOf(object);
				}
		}	if(toRemove!=-1){enemyB.remove(toRemove); toRemove=-1;}
		
		
		for (int i=0; i<snakeB.size()-1; i++) { Rectangle object = snakeB.get(i);
			if(snake.getX()>object.getX()-object.getWidth() && snake.getX()<object.getX()+object.getWidth()		   
				&& snake.getY()>object.getY()-object.getHeight() && snake.getY()<object.getY()+object.getHeight())
			{
				running = false;
			}	
		}
	};	
	
	public void spawn()
	{
		double spawnX, spawnY;
		spawnX = Math.random()*968 + 24;
		spawnY = Math.random()*726 + 24;
		
		enemy = new Rectangle((int)spawnX, (int)spawnY, 20, 20);
	}
	
////////////////////
// main game loop //
////////////////////
	
ActionListener gameLoop = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent event) {
    	
	speedIterator+=speed*velX; speedIterator2+=speed*velY;
	if(speedIterator>=30||speedIterator2>=30||speedIterator<= -30||speedIterator2<= -30)
		{tempX=(int) snake.getX(); tempY=(int) snake.getY();}
		
        go(velX, velY);	time++;
        				//time+=3; speed=30;
        checkCollision(snake.getX(), snake.getY());
        repaint(); 

        		if(speedIterator>=30||speedIterator2>=30||speedIterator<= -30||speedIterator2<= -30){
        			score.penScore(0.05); // penalty for time
        				speedIterator=0;  speedIterator2=0;	 			
        				goB(velX, velY);
      			}
        		
        double spawn = Math.random()*1000;	
        if(spawn>666&&spawn<672){
        	 double x = Math.random()*1024 - 10;	
        	 double y = Math.random()*768 - 10;	
        	 
     		if(!(snake.getX()>(int)x-45 && snake.getX()<(int)x+45	   
     			&& snake.getY()>(int)y-45 && snake.getY()<(int)y+45))
     				// check for snake's vicinity 
     			
        	enemyB.add(new Rectangle((int)x, (int)y, 40, 40));
        }
        if(spawn>994){
        	double x = Math.random()*1024;	
       	 	double y = Math.random()*768;	
       	 	enemyB.add(new Rectangle((int)x, (int)y, 36, 36));
       }
        
		if(time%(Math.ceil(diff)*10*60) == 0) {	
			for (int i = enemyB.size()-1; i >= 0; i--){
			    if (enemyB.get(i).getWidth()==36){
			    		enemyB.remove(i);
			    } diff+=0.25;
			 }
		}
		
        if(running==false)
        {
        	timer.stop();
        	//System.out.print("end");
        	//System.out.print(score.retScore());
        	
	// first April additions
        	scoreNetwork scorenet = new scoreNetwork(score.retScore());
        	
        	try {
				scorenet.test();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        	if(score.retScore()>highscore)
        	{
            	try {
        			scoretable.write(score.retScore());
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        	}
        }
        
   }
};

Timer timer = new Timer(1000/60, gameLoop);
							//20
public void run() {
    spawn(); 
    scoretable = new file();
    
    	highscore = 0;
    	try {
			highscore = scoretable.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    score = new score();
    velX = 1; // can be removed
    timer.start();
    }
}