package screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class DrawGameMenu extends JComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int rectX=300;
	private int rectY=226;
	private int selectedOption=1; //default
	private int gridSize=4;
	private int gridCheckImageX=335;
	private boolean showOptions=false;
	private Image checkImage = new ImageIcon("images\\check_16x16.png").getImage();
	private int timeCheckImageX=295;//default time check image position: 3 mins
	private int gameTimeLimit=2;//3 default time -> 0 unlimited (2min+60secs)


	public void drawMenu(Graphics g2){
		
		centerAndFormatText(g2, "Word Constructor", 800, 100, Color.ORANGE, "Arial", 1, 50);//game title / window width /  height for placing the string on screen
		
		g2.setColor(Color.blue);
		g2.fillRoundRect(rectX, rectY, 195, 30, 20, 20);
		//g2.fillRoundRect(rectX, 267, 195, 30, 20, 20);
		
	    centerAndFormatText(g2, "Start Game", 800, 250, Color.YELLOW, "Arial", 1, 25);//game option / window width /  height for placing the string on screen
	    centerAndFormatText(g2, "Options", 800, 290, Color.YELLOW, "Arial", 1, 25);//game option / window width /  height for placing the string on screen
		
	    if(showOptions){
	    	//red border
	    	g2.setColor(Color.red);
			g2.drawRoundRect(180, 330, 450, 170, 30, 30);
			g2.drawRoundRect(181, 331, 448, 168, 30, 30);
			
	    	//grid size options
			centerAndFormatText(g2, "Grid size", 800, 360, Color.WHITE, "Arial", 1, 16);//game option / window width /  height for placing the string on screen
			centerAndFormatText(g2, "4x4            5x5", 800, 390, Color.WHITE, "Arial", 1, 15);//game option / window width /  height for placing the string on screen
			g2.drawImage(checkImage, gridCheckImageX, 377, null);
			
			//time options
			centerAndFormatText(g2, "Time limit", 800, 450, Color.WHITE, "Arial", 1, 16);//game option / window width /  height for placing the string on screen
			centerAndFormatText(g2, "2 Minutes         3 Minutes          5 Minutes          Unlimited", 810, 480, Color.WHITE, "Arial", 1, 15);//game option / window width /  height for placing the string on screen
			g2.drawImage(checkImage, timeCheckImageX, 467, null);
	
	    }
	    
	    //credits
	    centerAndFormatText(g2, "Word Constructor beta V0.10  Bruno Silva - 2013", 800, 567, Color.WHITE, "Arial", 1, 13);//game option / window width /  height for placing the string on screen
	}
	
	public void moveOptionCursor(int x, int y){
		moveCursor(x, y);
	}
	
	
	private void moveCursor(int x, int y){
		
		if(x >= rectX && x <= (rectX+195) && y >= 267 && y <= (267+30) ){//options
			rectY=267;
			selectedOption=2;
				
		} else if(x >= rectX && x <= (rectX+195) && y >= 226 && y <= (226+30) ){//start game
			rectY=226;
			selectedOption=1;
			
		}
		
	}
	
	
	public void selectedOptionXY(int x, int y){
		selectedOption(x, y);
	}
	
	private void selectedOption(int x, int y){
		
		if(x >= 345 && x <= (345+38) && y >= 375 && y <= (375+18) ){// 4x4 grid size: default
			gridCheckImageX=335;
			gridSize=4;
			
		} else if(x >= 417 && x <= (417+38) && y >= 375 && y <= (375+18) ){// 5x5 grid size
			gridCheckImageX=407;
			gridSize=5;
			
		} 
		
		/*  g2.drawRect(207, 464, 71, 20);			
			g2.drawRect(308, 464, 77, 20);			
			g2.drawRect(417, 464, 77, 20);			
			g2.drawRect(525, 464, 77, 20);
		*/
		else if(x >= 207 && x <= (207+71) && y >= 464 && y <= (464+20) ){// 2 min
			timeCheckImageX=195;
			gameTimeLimit=1;
			
		} else if(x >= 308 && x <= (308+77) && y >= 464 && y <= (464+20) ){// 3 mins: default
			timeCheckImageX=295;
			gameTimeLimit=2;
			
		} else if(x >= 417 && x <= (417+77) && y >= 464 && y <= (464+20) ){// 5 mins
			timeCheckImageX=405;
			gameTimeLimit=4;
			
		} else if(x >= 525 && x <= (525+77) && y >= 464 && y <= (464+20) ){// unlimited
			timeCheckImageX=515;
			gameTimeLimit=0;
			
		}
		
		
	}
	
	public int getSelectedOption() {
		return selectedOption;
	}
	
	public int getGridSize() {
		return gridSize;
	}

	public void setShowOptions(boolean showOptions) {
		this.showOptions = showOptions;
	}
	
	public int getGameTimeLimit() {
		return gameTimeLimit;
	}
	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}

	public void setGridCheckImageX(int gridCheckImageX) {
		this.gridCheckImageX = gridCheckImageX;
	}

	public void setTimeCheckImageX(int timeCheckImageX) {
		this.timeCheckImageX = timeCheckImageX;
	}

	public void setGameTimeLimit(int gameTimeLimit) {
		this.gameTimeLimit = gameTimeLimit;
	}
	
	private void centerAndFormatText(Graphics g2, String s, int width, int height, Color color, String fontName, int fontStyle, int fontSize){
		g2.setColor(color);
		g2.setFont(new Font(fontName, fontStyle, fontSize));
		int stringLetterLength = (int) g2.getFontMetrics().getStringBounds(s, g2).getWidth();
	    int start = width/2 - stringLetterLength/2;
	    g2.drawString(s, start , height);
	    
	}
	
}
