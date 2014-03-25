package classes;

import java.io.Serializable;

public class GamerInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int bestScore=0;
	private int bestNumberOfWordsFound=0;
	private int gameTimeLimit=2;//3 default time -> 0 unlimited (2min+60secs)
	private int timeCheckImageX=295;//user default check image position on time menu
	private int gridSize=4;
	private int gridCheckImageX=335;
	
	
	public int getGridSize() {
		return gridSize;
	}

	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
		
		switch (gridSize) {
	        case 4:  gridCheckImageX = 335; //4x4 Grid
	                 break;
	        case 5:  gridCheckImageX = 407; //5x5 Grid
	                 break;
	        default: gridCheckImageX = 335; //default: 4x4
	        break;
	    }
	}
	
	public int getGridCheckImageX() {
		return gridCheckImageX;
	}
	
	public int getGameTimeLimit() {
		return gameTimeLimit;
	}
	
	public void setGameTimeLimit(int gameTimeLimit) {
		this.gameTimeLimit = gameTimeLimit;

        switch (gameTimeLimit) {
            case 1:  timeCheckImageX = 195; //2min
                     break;
            case 2:  timeCheckImageX = 295; //3min
                     break;
            case 4:  timeCheckImageX = 405; //5min
                     break;
            case 0:  timeCheckImageX = 515; //unlimited
                     break;
            default: timeCheckImageX = 295; //3min
            break;
        }   
	}
	
	public int getBestScore() {
		return bestScore;
	}

	public void setBestScore(int bestScore) {
		if(this.bestScore < bestScore){
			this.bestScore = bestScore;
		}	
	}

	public int getBestNumberOfWordsFound() {
		return bestNumberOfWordsFound;
	}

	public void setBestNumberOfWordsFound(int bestNumberOfWordsFound) {
		if(this.bestNumberOfWordsFound < bestNumberOfWordsFound){
			this.bestNumberOfWordsFound = bestNumberOfWordsFound;
		}	
	}

	public int getTimeCheckImageX() {
		return timeCheckImageX;
	}

}