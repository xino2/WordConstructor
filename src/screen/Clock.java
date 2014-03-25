package screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Clock implements Runnable {
	//time 2,3 or 5 min
	private volatile int timeLimit;//default time 3min
	private volatile int minutes;
	private volatile int seconds = 60;
	private volatile int secondsTemp = 0;
	private volatile boolean pause = false;
	private volatile boolean gameOver = false;
	private GameDrawPanel gameDrawPanel;
	private String result="";

	public Clock(GameDrawPanel gameDrawPanel) {
		this.gameDrawPanel=gameDrawPanel;
	}

	@Override
	public void run() {
		runClock();	
	}
	
	private void runClock(){
  
		while(minutes!=-1 && seconds!=0){
			try {
				
				if(seconds!=-1){//game not paused
					seconds--;
					result = String.format(minutes+":"+ "%02d", seconds);
					//System.out.println(result);
					gameDrawPanel.repaint();
					
					if(seconds==0){
						minutes--;
						seconds=60;
					}
					Thread.sleep(1000);
				} 
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//System.out.println("Clock Stoped");
				seconds = 60;
				break;
				
			}
				
		}
		//end game
		result="Game Over";
		gameDrawPanel.saveGameInfo();
		gameOver=true;
		gameDrawPanel.resetBlockColors();
		gameDrawPanel.repaint();
		
	}

	public void paint(Graphics g2){
		//set clock color
		if(minutes==0 && seconds<60 && seconds>30){
			g2.setColor(Color.YELLOW);
		} else if(minutes==0 && seconds<=30){
			g2.setColor(Color.RED);
		} else {
			g2.setColor(Color.WHITE);
		}
		g2.setFont(new Font("Arial", Font.BOLD, 14));
		g2.drawString(result, 25, 460);	
	}

	public void resetTime(){
		gameOver=false;
		minutes=timeLimit;
		seconds=60;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
		if(pause){
			secondsTemp=seconds;
			seconds=-1;
		} else {
			seconds=secondsTemp;
		}
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
		this.minutes = timeLimit;
	}

}
