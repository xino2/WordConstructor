package screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import classes.GamerInfo;
import classes.LetterGenerator;
import classes.LoadSave;
import classes.WordChecker;
import classes.WordCreator;
import classes.Dictionary;
import classes.WordMatchSearch;


public class GameDrawPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private int gridSize;
	private int gapSize;
	private int blockSize;
	private LetterGenerator letters;
	private LetterBlock[][] blocks;
	private Image bgImage;
	private WordCreator wordCreator = new WordCreator();
	private Dictionary dic;
	private WordMatchSearch wordMatchSearch;
	private WordChecker wordChecker;
	private ArrayList<String> constructedWords = new ArrayList<String>();
	private int totalScore=0;
	private int wordTempScore=0;
	private boolean inGame = false;
	private LetterBlock newButton = new LetterBlock(710, 20, 72);
	private LetterBlock endButton = new LetterBlock(710, 102, 72);
	private LetterBlock pauseButton = new LetterBlock(710, 184, 72);
	private DrawGameMenu drawGameMenu=null;
	private Clock clock = new Clock(this);
	private Thread clockThread;
	private GamerInfo gamerInfo;

	public GameDrawPanel() throws UnsupportedEncodingException, FileNotFoundException, IOException{	
		dic = new Dictionary();
		wordMatchSearch = new WordMatchSearch(dic.getDictionary());
		wordChecker = new WordChecker(dic.getDictionary());
		bgImage=new ImageIcon("images\\bg1_960x600.png").getImage();
		loadGameInfo();
		
	}
	
	private void loadGameInfo(){
		//load info
		gamerInfo=LoadSave.load();
		//set info
		drawGameMenu=new DrawGameMenu();//create gameMenu
		drawGameMenu.setTimeCheckImageX(gamerInfo.getTimeCheckImageX());//set values
		drawGameMenu.setGameTimeLimit(gamerInfo.getGameTimeLimit());//set values
		drawGameMenu.setGridSize(gamerInfo.getGridSize());//set values
		drawGameMenu.setGridCheckImageX(gamerInfo.getGridCheckImageX());//set values
	}
	
	public void saveGameInfo(){
		gamerInfo.setBestScore(totalScore);//set total score
		gamerInfo.setBestNumberOfWordsFound(constructedWords.size());//set max words found
		LoadSave.save(gamerInfo);//save info to file
	}
	
	private void saveSettingsInfo(){
		gamerInfo.setGameTimeLimit(drawGameMenu.getGameTimeLimit());
		gamerInfo.setGridSize(drawGameMenu.getGridSize());
		LoadSave.save(gamerInfo);//save info to file
	}
	
	//set the grid size
	public void setGrid(int size){
		if(size==4){
			gridSize=size;
			gapSize = 6;
			blockSize=100;
		} else {//size 5
			gridSize=size;
			gapSize = 4;
			blockSize=80;
		}
		letters = new LetterGenerator(gridSize);
	}
	
	public void startGame(){
		saveSettingsInfo();
		inGame=true;
		clock.setGameOver(false);
		setGrid(drawGameMenu.getGridSize());
		buildBlockGrid();
		populateBlocks();
		
		if(drawGameMenu.getGameTimeLimit()!=0){//if game limit selected in the menu is not set to 0, start clock thread
			clock.setTimeLimit(drawGameMenu.getGameTimeLimit());//set user selected time from menu
			clockThread = new Thread(clock);
			clockThread.start();
		}	
		
	}
	
	public void resetGrid(){
		populateBlocks();
	}
	
	private void populateBlocks(){
		letters.generateLetters();
		ArrayList<String> letterMix = letters.getLetterMix();
		int letterMixIndexCounter=0;
		for(int i=0; i < blocks.length; i++){
			for(int j=0; j < blocks.length; j++){
				//set letter in block object
				blocks[i][j].setS(letterMix.get(letterMixIndexCounter));
				//set letter points in block object
				blocks[i][j].setPoints(letters.getLetterValues(letterMix.get(letterMixIndexCounter)));
				letterMixIndexCounter++;
			}	
		}
		//System.out.println("Calculating all possible words...");
		wordMatchSearch.createAllPossibleWordsList(letterMix);
		//System.out.println("Done!");
	}
	
	private void buildBlockGrid(){
		blocks = new LetterBlock[gridSize][gridSize];
		int acumulatorX=20;
		int acumulatorY=20;
		for (int i = 0; i < blocks.length; i++) {	
			for (int j = 0; j < blocks.length; j++) {
				blocks[i][j] = new LetterBlock( acumulatorX, acumulatorY, blockSize);
				acumulatorY+=blockSize+gapSize;//block size + gap between blocks
			}
			acumulatorY=20;
			acumulatorX+=blockSize+gapSize;
		}
	}
	
	//paint objects on screen
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(g instanceof Graphics2D){
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);//set smooth text on
			g2.drawImage(bgImage, 0, 0, null);//draw background
			if(inGame){
				drawGame(g2);//in the game				
			} else {	
				drawGameMenu.drawMenu(g2);//in the menu
			}
		}
	}
	
	
	public void resetBlockColors(){
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks.length; j++) {
				blocks[i][j].setUsed(false);
			}		
		}
	}
	
	//Identify selected blocks
	public void checkSelectedArea(int x, int y){
		
		if(x >= 340 && x <= (340+96) && y >= 445 && y <= (445+22) && !clock.isPause() ){//clear button area
			//clear word
			wordTempScore=0;//reset temp var
			resetBlockColors();//reset the selected blocks
			wordCreator.resetWordValue();//reset value of the word maker
						
		} else if(x >= 20 && x <= (20+418) && y >= 20 && y <= (20+418) && !clock.isPause() && !clock.isGameOver()) { //letter blocks area 418X418
			identifySelectedBlock(x, y);//selected letter block
			
		} else if(x >= 710 && x <= (710+72) && y >= 20 && y <= (20+72) ){//restart button area
			restartGame();
			clock.resetTime();
			if(clock.isPause()){
				clock.setPause(false);
			}
			
				
		} else if(x >= 710 && x <= (710+72) && y >= 102 && y <= (102+72) ){//end button area 
			inGame=false;
			wordCreator.resetWordValue();
			saveGameInfo();
			constructedWords.clear();
			totalScore=0;
			wordTempScore=0;
			drawGameMenu.setShowOptions(false);
			if(clock.isPause()){
				clock.setPause(false);
			}
			if(drawGameMenu.getGameTimeLimit()!=0){
				clockThread.interrupt();//stop clock
			}
			//TODO:
			//show possible words
			//collect points and add to leaderBoard...
						
		} else if(x >= 710 && x <= (710+72) && y >= 184 && y <= (184+72) && drawGameMenu.getGameTimeLimit()!=0){//pause button area
			
			if(clock.isPause()){
				clock.setPause(false);
			} else {
				clock.setPause(true);
			}
				
		}		
	}
	
	//Identify selected blocks, add letters to word, verify words
	private void identifySelectedBlock(int x, int y){
		//LetterBlock[][] blocks = panel.getBlocks();//get the screen blocks to perform checks
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks.length; j++) {
				//check if the clicked coordinate belongs to some block
				if(x >= blocks[i][j].getX() && x <= (blocks[i][j].getX()+100) && y >= blocks[i][j].getY() && y <= (blocks[i][j].getY()+100) ){
					//block found
					//System.out.println("Block letter: "+blocks[i][j].getS());
					if(!blocks[i][j].isUsed()){ //check if the block was already selected -- block not used
						wordCreator.addLetter(blocks[i][j].getS());//add block letter to the word creator class
						blocks[i][j].setUsed(true);//set block as selected
						wordTempScore+=letters.getLetterValues(blocks[i][j].getS());//add points to the temp var
						//Verify if the word as more than 3 characters and verify if the word is in the dictionary
						if(wordCreator.getWord().length() >= 3 && wordChecker.wordCheck(wordCreator.getWord())){//more than 3 letters and in dic
							//Add word to found list if not there yet
							if(!constructedWords.contains(wordCreator.getWord())){//check if the word is not already in the found list
								constructedWords.add(wordCreator.getWord());//add word to found word list
								totalScore+=wordTempScore;//calculate word points and add to sum
								wordTempScore=0;//reset temp var
								resetBlockColors();//reset the selected blocks
								wordCreator.resetWordValue();//reset value of the word maker
							} //else {//if already in list skip adding to list and do not reset blocks
								//System.out.println("ALREADY FOUND!");
							//}
						}
						
					} else {//block already used
						//if the block is selected again the letter will be removed from the word being built but ONLY if the selected
						//block contains the last letter in the word
						if(wordCreator.removeLastLetter(blocks[i][j].getS())){
							wordTempScore-=letters.getLetterValues(blocks[i][j].getS());//subtract points to the temp var
							blocks[i][j].setUsed(false);
						}
					}					
				} 
			}	
		}
			
	}
	
	public void restartGame(){
		resetGrid();
		resetBlockColors();
		wordCreator.resetWordValue();
		constructedWords.clear();
		totalScore=0;
		wordTempScore=0;
		if(clock.isGameOver()){//check if the game is over
			clockThread = new Thread(clock);
			clockThread.start();
		}
	}
	
	/*
	private void drawMenu(Graphics g2){
		
		g2.setColor(Color.YELLOW);
		g2.setFont(new Font("Arial", Font.BOLD, 25));
		
		//Center letter in block
		int stringLetterLength = (int) g2.getFontMetrics().getStringBounds("Start Game", g2).getWidth();
	    int start = 800/2 - stringLetterLength/2;
	    g2.drawString("Start Game", start , 250);
	    
	    stringLetterLength = (int) g2.getFontMetrics().getStringBounds("Options", g2).getWidth();
	    start = 800/2 - stringLetterLength/2;
	    g2.drawString("Options", start , 290);
		
		
	}
	*/
	
	private void drawGame(Graphics g2){
		
		if(drawGameMenu.getGameTimeLimit()!=0){ //if game limit is not set to 0, show clock
			clock.paint(g2);
		}
		
		if(blocks != null){
			
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("Arial", Font.BOLD, 14));
			
			//draw best score
			g2.drawString("Best score: "+ gamerInfo.getBestScore(), 500, 35);
			g2.drawString("Max words found: "+ gamerInfo.getBestNumberOfWordsFound(), 500, 53);
			
			g2.setColor(Color.YELLOW);
			g2.setFont(new Font("Arial", Font.BOLD, 16));
			
			//draw score
			g2.drawString("Score: "+ totalScore, 500, 75);
			
			//draw words found string
			g2.drawString("Words found: "+ constructedWords.size() + " / " + wordMatchSearch.getWordCounter(), 500, 95);
			
			//draw blocks
			for (int i = 0; i < blocks.length; i++) {
				for (int j = 0; j < blocks.length; j++) {
					blocks[i][j].paint(g2);
				}		
			}
		}
		
		
		if(wordCreator.getWord() != ""){
			//draw created word
			g2.setColor(new Color(255,255,255));
			g2.setFont(new Font("Arial", Font.PLAIN, 43));
			g2.drawString(wordCreator.getWord(), 20, 530);
			
			
			/*
			//draw blocks with letters instead of just letters	
			for (int i=0; i < wordCreator.getSelectedLetters().size(); i++) {
				LetterBlock letter = new LetterBlock(20+(42*i), 490, 40);//x y with
				letter.setS(wordCreator.getSelectedLetters().get(i));
				letter.paint(g2);
			} 
			*/
			
			
			//g2.drawString(wordCreator.getWord(), 20, 530);
			
			//draw word points
			g2.setColor(Color.YELLOW);
			g2.setFont(new Font("Arial", Font.BOLD, 12));
			g2.drawString("Word points: "+ wordTempScore, 20, 550);
		}
		
		//draw word list
		if(!constructedWords.isEmpty()){
			g2.setColor(new Color(255,255,255));
			g2.setFont(new Font("Arial", Font.BOLD, 14));
			int pos=115;
			for (int i = constructedWords.size()-1; i >= 0; i--) {//print last word first
				g2.drawString(constructedWords.get(i), 505, pos);
				pos+=17;
			}
		}	
		
		//draw word clear button
		g2.setColor(Color.DARK_GRAY);
		g2.fillRect(340, 445, 96, 22);
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Arial", Font.BOLD, 16));
		g2.drawString("Clear", 368, 462);
		
		//draw restart game button
		newButton.setS("Restart");
		newButton.paint(g2);
		
		//draw end game button
		endButton.setS("End");
		endButton.paint(g2);
		
		//draw pause game button
		if(drawGameMenu.getGameTimeLimit()!=0 && !clock.isGameOver()){ //if time limit is not 0, draw pause button
			pauseButton.setS("Pause");
			if(clock.isPause()){
				pauseButton.setUsed(true);
			} else {
				pauseButton.setUsed(false);
			}
			pauseButton.paint(g2);
		}
		
	}
	
	public boolean isInGame() {
		return inGame;
	}

	public void setMenuCursor(int x, int y){
		drawGameMenu.moveOptionCursor(x, y);
	}
	
	public void setMenuSelectGridSize(int x, int y){
		drawGameMenu.selectedOptionXY(x, y);
	}
	
	
	public int getSelectedOption(){
		return drawGameMenu.getSelectedOption();
	}
	
	public void setShowOptions(boolean showOptions) {
		drawGameMenu.setShowOptions(showOptions);
	}

}

