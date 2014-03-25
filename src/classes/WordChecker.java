package classes;

import java.util.HashSet;

public class WordChecker {
	
	private HashSet<String> dictionary;
	
	public WordChecker(HashSet<String> dictionary) {
		this.dictionary=dictionary;
	}

	public boolean wordCheck(String word){
		if(dictionary.contains(word)){
			return true;
		}
		return false;
	}

		
}
