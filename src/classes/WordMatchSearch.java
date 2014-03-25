package classes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;


public class WordMatchSearch {

	private HashSet<String> dictionary;
	private ArrayList<String> letterMix = new ArrayList<String>();
	private ArrayList<String> allPossibleWords = new ArrayList<String>();
	private int wordCounter;
	

	//CONSTRUCTOR
	public WordMatchSearch(HashSet<String> dictionary){
		this.dictionary=dictionary;
		
	}
	
	//create all possible words list
	public void createAllPossibleWordsList(ArrayList<String> letterMix){
		this.letterMix=letterMix;
		calculateAllWords();
	}

	private void calculateAllWords(){
		wordCounter=0;
		for (String s : dictionary) {
			if (s.length()>=3){ // only process words with 3 or more characters
				String[] splittedWord = s.split("(?!^)");//split word
				boolean inMix = true;	
				for (int i = 0;i < splittedWord.length; i++){
					
					if(!letterMix.contains(splittedWord[i]) ){
						inMix=false;
					}	
					
					if (!countLetters(splittedWord[i], splittedWord, letterMix)){
						inMix=false;
					}

				}

				if(inMix==true){ //if the word is in the mixed grid add it to the possible word list
					wordCounter++;//count the possible words
					allPossibleWords.add(s);//add to list	
				}
			}
		}

		Collections.sort(allPossibleWords);//sort words

		//System.out.println(wordCounter);//number of matching words found
		/*
		//print list
		int arrayListSize = allPossibleWords.size();
		for(int i = 0; i < arrayListSize; i++)
		{
			System.out.print(allPossibleWords.get(i)+" ");
		}
		*/
	}
	
	//method to match the number of duplicated letters in the grid
	private boolean countLetters(String letter, String[] splittedWord, ArrayList<String> letterMix){
		
		int letterCount=0;//count number of times the letter is in the word being processed  
		for (int i = 0; i < splittedWord.length; i++) {
			if(letter.equals(splittedWord[i])){
				letterCount++;
			}
		}
		
		int stringCount=0; //count the number of times the letter from the word being processed is in the grid
		for (String string : letterMix) {
			if(letter.equals(string)){
				stringCount++;
			}
		}
		
		if(letterCount<=stringCount){ //if the letter appears the same number of times in the word and in the grid return true
			return true;
		}
		return false;
		
	}
	
	public int getWordCounter() {
		return wordCounter;
	}
	
	public ArrayList<String> getAllPossibleWords() {
		return allPossibleWords;
	}

}
