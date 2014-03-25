package screen;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyKeyListener extends KeyAdapter {
	StartGame gui;
	LetterBlock[][] blocks;
	int currPositionX;
	int currPositionY;
	int newX;
	int newY;
	
	public MyKeyListener(StartGame gui){
		this.gui = gui;
		//this.blocks = gui.panel.getBlocks();
		this.newX=0;
		this.newY=0;
	}
	
	public void keyPressed(KeyEvent e){
			
		//int keyCode = e.getKeyCode();
		/*
		if(keyCode==e.VK_LEFT){
			currPositionX = blocks[gui.selectedItem][0].getX();
			newX--;
			blocks[gui.selectedItem].setX(currPositionX+(newX));
			gui.panel.repaint();
		}
		if(keyCode==e.VK_RIGHT){
			currPositionX = blocks[gui.selectedItem].getX();
			newX++;
			blocks[gui.selectedItem].setX(currPositionX+(newX));
			gui.panel.repaint();
		}
		if(keyCode==e.VK_UP){
			currPositionY = blocks[gui.selectedItem].getY();
			newY--;
			blocks[gui.selectedItem].setY(currPositionY+(newY));
			gui.panel.repaint();
		}
		if(keyCode==e.VK_DOWN){
			currPositionY = blocks[gui.selectedItem].getY();
			newY++;
			blocks[gui.selectedItem].setY(currPositionY+(newY));
			gui.panel.repaint();
		}
		*/
	}
	
}
