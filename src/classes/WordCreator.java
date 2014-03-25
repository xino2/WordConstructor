package classes;

import java.util.ArrayList;

public class WordCreator {
	
	ArrayList<String> selectedLetters = new ArrayList<String>();
	
	public ArrayList<String> getSelectedLetters() {
		return selectedLetters;
	}

	//build word string
	private String word(){
		String word ="";
		for (int i = 0; i < selectedLetters.size(); i++) {
			word+=selectedLetters.get(i);
		}
		return word;
	}
	
	public String getWord() {
		return word();
	}

	public void resetWordValue() {
		this.selectedLetters.clear();
	}


	public void addLetter(String l){
		selectedLetters.add(l);
	}
	
	public boolean removeLastLetter(String l){
		if(checkLastLetter(l)){
			selectedLetters.remove(selectedLetters.size()-1);
			return true;
		}
		return false;
	}
	
	private boolean checkLastLetter(String l){
		if(selectedLetters.get(selectedLetters.size()-1).equals(l)){
			return true;
		}
		return false;
	}

}
