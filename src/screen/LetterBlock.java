package screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;

public class LetterBlock extends JComponent {
	
	private static final long serialVersionUID = -1261694429446412518L;
	private int x;
	private int y;
	private String s ="";
	private Color limeGreen = new Color(50,205,50);//unselected block
	private Color darkOrange = new Color(205,133,0);//selected block
	private Color darkRed = new Color(204,0,0);//selected pause button
	private Color selectedColor=limeGreen;//default selected colour
	private boolean used=false;
	private int points;
	private int blockWidth;
	private int letterFontSize;
	private int letterHeight;
	private int numberFontSize;
	private int numberHeight;

	//CONSTRUCTOR
	public LetterBlock(int x, int y, int blockWidth){
		this.x=x;
		this.y=y;
		this.blockWidth = blockWidth;
		setConfigValues(blockWidth);
	}
	
	private void setConfigValues(int blockWidth){
		if(blockWidth==100){//blockWidth 4x4 grid
			letterFontSize=60;
			letterHeight=73;
			numberFontSize=12;
			numberHeight=87;
			
		}else if(blockWidth==80){ //blockWidth  5x5 grid
			letterFontSize=46;
			letterHeight=57;
			numberFontSize=10;
			numberHeight=67;
			
		} else if(blockWidth==40){ //building word
			letterFontSize=23;
			letterHeight=27;
			numberFontSize=0;
			numberHeight=0;
			
		} else { //buttons
			letterFontSize=13;
			letterHeight=42;
			numberFontSize=0;
			numberHeight=0;
		}
		
	}
	/*
	//center string
	private void printSimpleString(String s, int width, int XPos, int YPos){
        int stringLen = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
        int start = width/2 - stringLen/2;
        g2d.drawString(s, start + XPos, YPos);
	}
	*/
	
	public void paint(Graphics g){
		
		if(g instanceof Graphics2D){
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);//set smooth text on
			
			g2.setColor(new Color(34,139,34));//outside rect --> color: forest green
			g2.fillRect(x, y, blockWidth, blockWidth);//100/80
			
			g2.setColor(selectedColor);////inside rect --> color:lime green
			g2.fillRect(x+10, y+10, blockWidth-20, blockWidth-20);//80/60
			
			g2.setColor(new Color(255,255,255));//letter color
			if(blockWidth==100||blockWidth==80||blockWidth==40){
				g2.setFont(new Font("Arial", Font.PLAIN, letterFontSize));//letter size in letter block
			} else {
				g2.setFont(new Font("Arial", Font.BOLD, letterFontSize));//letter size in restart and end buttons
			}
			
			
			//Center letter in block
			int stringLetterLength = (int) g2.getFontMetrics().getStringBounds(s, g2).getWidth();
		    int start = blockWidth/2 - stringLetterLength/2;
		    g2.drawString(s, start + x, y+letterHeight);
		    g2.setFont(new Font("Arial", Font.BOLD, numberFontSize));
		    g2.drawString(""+points, x+13, y+numberHeight);
        
		}
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getS() {
		return s;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setS(String s) {
		this.s = s;
	}

	public void setSelectedColor(Color selectedColor) {
		this.selectedColor = selectedColor;
	}
	
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		if(used){
			this.used = used;
			this.selectedColor=darkOrange;
			if(blockWidth!=80&&blockWidth!=100){
				this.selectedColor=darkRed;
				
			}
			
		} else {
			this.used = used;
			this.selectedColor=limeGreen;
		}
	}
}
