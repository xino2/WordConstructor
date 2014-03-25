package classes;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;

public class Dictionary {

	private HashSet<String> dictionary = new HashSet<>();
	private String filename = "Eng.txt";
	
	private void fillDictionary(HashSet<String> dictionary, String filename) 
			throws UnsupportedEncodingException, FileNotFoundException, IOException {

		File dictionaryFile = new File(filename);
		Reader reader = new InputStreamReader(new FileInputStream(dictionaryFile), "UTF-8");
		BufferedReader input = new BufferedReader(reader);
		input.readLine();//ignore the first line
		String readLine;
		
		while ((readLine = input.readLine()) != null){
			String[] line = readLine.split(" ");
			dictionary.add(line[0].toUpperCase());
		}
		input.close();
	}
	
	public Dictionary()
			throws UnsupportedEncodingException, FileNotFoundException, IOException{
		fillDictionary(dictionary, filename);
	}
	
	public HashSet<String> getDictionary() {
		return dictionary;
	}
	
}
