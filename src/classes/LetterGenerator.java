package classes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;


public class LetterGenerator {

	private String[] allVowels = {"A","E","I","O","U","Y"};
	private String[] allConsonants = {"B", "C", "D", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "V", "W", "X", "Y", "Z", "Qu"};
	private int vowels;
	private int consonants;
	private Random r = new Random();
	private ArrayList<String> letterMix = new ArrayList<String>();
	private HashMap<String, Integer> letterValues = new HashMap<>(); 

	//CONSTRUCTOR
	public LetterGenerator(int gridSize){//grid size
		if(gridSize==4){
			this.vowels=6;//vowels for 4x4 grid
			this.consonants=10;//consonants for 4x4 grid
		}
		if(gridSize==5){
			this.vowels=8;//vowels for 5x5 grid
			this.consonants=17;//consonants for 5x5 grid
		}
		setLetterValues();
	}
	
	public void generateLetters(){
		randomizeLetters(allVowels, allConsonants);
		
	}
	

	private void randomizeLetters(String[] allVowels, String[] allConsonants) {
		//add random vowels in temp array
	    for (int j = 0; j < vowels; j++) {
	    	int v = r.nextInt(allVowels.length-1);
	    	letterMix.add(allVowels[v]);
	    }
	    
	    //add random consonants in temp array
	    for (int j = 0; j < consonants; j++) {
	    	int c = r.nextInt(allConsonants.length-1);
	    	letterMix.add(allConsonants[c]);
	    }
	    
	    //shuffle letterMix array
	    Collections.shuffle(letterMix);   
	}	
	
	public ArrayList<String> getLetterMix() {
		return letterMix;
	}
	
	//load HashMap with the letter values
	private void setLetterValues(){
		
		//25 point letters
		String[] p25 = {"A","E","I","O","U","S","T","N","R","L"};
		for (int i = 0; i < p25.length; i++) {
			letterValues.put(p25[i], 25);
		}
		
		//50 points letters
		String[] p50 = {"B","D","P","C","M","G"};
		for (int i = 0; i < p50.length; i++) {
			letterValues.put(p50[i], 50);
		}
		
		//75 points letters
		String[] p75 = {"V","F","H","Y","W"};
		for (int i = 0; i < p75.length; i++) {
			letterValues.put(p75[i], 75);
		}
		
		//100 points letters
		letterValues.put("K", 100);
		
		//150 points letters
		letterValues.put("X", 150);
		letterValues.put("J", 150);
		
		//200 points letters
		letterValues.put("Q", 200);
		letterValues.put("Z", 200);
		letterValues.put("Qu", 200);
		
	}
	
	public Integer getLetterValues(String l) {
		return letterValues.get(l);
	}
	


}
