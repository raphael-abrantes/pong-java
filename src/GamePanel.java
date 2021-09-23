import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable{

	static final double FPS = 60.0;
	static final int SCREEN_WIDTH = 1000;
	static final int SCREEN_HEIGHT = (int)(SCREEN_WIDTH * (0.555555));
	static final Dimension SCREEN_SIZE = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
	static final int BALL_DIAMETER = 20;
	static final int PADDLE_WIDTH = 25;
	static final int PADDLE_HEIGHT = 100;
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle1;
	Paddle paddle2;
	Ball ball;
	Score score;
	
	GamePanel () {
		newPaddles();
		newBall();
		score = new Score(SCREEN_WIDTH, SCREEN_HEIGHT);
		this.setFocusable(true);
		this.addKeyListener(new ActionListener());
		this.setPreferredSize(SCREEN_SIZE);
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void newBall () {
		random = new Random();
		ball = new Ball((SCREEN_WIDTH/2)-(BALL_DIAMETER/2), random.nextInt(SCREEN_HEIGHT- BALL_DIAMETER), BALL_DIAMETER, BALL_DIAMETER);
	}
	
	public void newPaddles () {
		paddle1 = new Paddle(0, ((SCREEN_HEIGHT/2) - (PADDLE_HEIGHT/2)), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
		paddle2 = new Paddle((SCREEN_WIDTH - PADDLE_WIDTH), ((SCREEN_HEIGHT/2) - (PADDLE_HEIGHT/2)), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
	}
	
	public void paint (Graphics g) {
		image = createImage(getWidth(),getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image, 0, 0, this);
	}
	
	public void draw (Graphics g) {
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g);
	}
	
	public void move () {
		paddle1.move();
		paddle2.move();
		ball.move();
	}
	
	public void checkCollision () {
		//BorderChecksForBall
		if (ball.y <= 0) {
			ball.setYDirection(-ball.yVelocity);			
		}
		if (ball.y >= (SCREEN_HEIGHT - BALL_DIAMETER)) {
			ball.setYDirection(-ball.yVelocity);			
		}
		
		//BorderChecksfBallTouchingPaddles
		if (ball.intersects(paddle1)) {
			ball.xVelocity = (-ball.xVelocity);
			ball.xVelocity++;
			if (ball.yVelocity > 0) {
				ball.yVelocity++;
			} else {
				ball.yVelocity--;
			}
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		
		if (ball.intersects(paddle2)) {
			ball.xVelocity = (ball.xVelocity);
			ball.xVelocity++;
			if (ball.yVelocity > 0) {
				ball.yVelocity++;
			} else {
				ball.yVelocity--;
			}
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}

		//BorderChecksForPaddles
		if (paddle1.y < 0) {
			paddle1.y = 0;
		}
		if (paddle1.y >= (SCREEN_HEIGHT - PADDLE_HEIGHT)) {
			paddle1.y = (SCREEN_HEIGHT - PADDLE_HEIGHT);
		}
		if (paddle2.y < 0) {
			paddle2.y = 0;
		}
		if (paddle2.y >= (SCREEN_HEIGHT - PADDLE_HEIGHT)) {
			paddle2.y = (SCREEN_HEIGHT - PADDLE_HEIGHT);
		}
		
		
		//Score + Reset Ball/Paddles
		if (ball.x >= SCREEN_WIDTH - BALL_DIAMETER) {
			score.player1++;
			newPaddles();
			newBall();
		}
		if (ball.x <= 0) {
			score.player2++;
			newPaddles();
			newBall();
		}
		
	}
	
	public void run () {
		//GameLoop
		long lastTime = System.nanoTime();
		double amountOfTicks = FPS;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		
		while(true) {
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			if (delta >= 1) {
				move();
				checkCollision();
				repaint();
				delta--;
			}
		}	
	}
	
	public class ActionListener extends KeyAdapter {
		public void keyPressed (KeyEvent e) {
			paddle1.keyPressed(e);
			paddle2.keyPressed(e);
		}
		public void keyReleased (KeyEvent e) {
			paddle1.keyReleased(e);
			paddle2.keyReleased(e);			
		}
	}
}

