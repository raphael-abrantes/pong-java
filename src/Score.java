import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Score extends Rectangle{

	static int SCREEN_WIDTH;
	static int SCREEN_HEIGHT;
	int player1;
	int player2;
	
	Score (int SCREEN_WIDTH, int SCREEN_HEIGHT) {
		Score.SCREEN_WIDTH = SCREEN_WIDTH;
		Score.SCREEN_HEIGHT = SCREEN_HEIGHT;
	}	
	
	public void draw (Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("Arial",Font.PLAIN,60));
		g.drawLine(SCREEN_WIDTH/2, 0, SCREEN_WIDTH/2, SCREEN_HEIGHT);
	
		g.drawString((String.valueOf(player1/10)+String.valueOf(player1%10)), (SCREEN_WIDTH/2) - 100, 60);
		g.drawString((String.valueOf(player2/10)+String.valueOf(player2%10)), (SCREEN_WIDTH/2) + 30, 60);
	}
}
