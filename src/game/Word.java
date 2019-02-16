package game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

public class Word {
	private String word;
	
	private String displayWord;
	
	private List<Character> correctlyGuessedLetters = new ArrayList<>();
	private List<Character> incorrectlyGuessedLetters = new ArrayList<>();
	
	
	
	private String[] predefinedWords = {"awkward", "bagpipes", "banjo", "croquet", "crypt", 
			"dwarves","fervid","fishhook","fjord","gazebo","gypsy","haphazard",
			"hyphen","ivory","jazzy","kiosk","klutz","oxygen","pajama",
			"mystify","sphinx","rogue","quad", "yacht", "zigzag","zombie",
			"ostracize","numbskull","rhythmic",   
	};
	
	private List<String> words = new ArrayList<>();
	
	public Word() {
		loadFile();
		setRandomWord();
		updateDisplayWord();
		
	}
	
	private void setRandomWord() {
		Random r = new Random();
		if(!words.isEmpty()) {

			if(words.size() > 1) {
				
				this.word = words.get(r.nextInt(words.size()));
			}else {
				this.word = words.get(0);
			}
			
			
		}else {
			this.word = "";
		}

	}
	
	public void updateDisplayWord() {

		StringBuffer sb = new StringBuffer();
		
		for(int i = 0; i < word.length(); i++) {
			
			
			if(correctlyGuessedLetters.contains(word.charAt(i))) {
				sb.append(word.charAt(i));
			}else {
				sb.append("_");
			}
			sb.append(" ");
			
			
		}
		displayWord = sb.toString();
	}
	
	public boolean isLetterInWord(char inputLetter) {
		char[] lettersInWord = word.toCharArray();
		for(char x : lettersInWord) {
			if(x == inputLetter && !correctlyGuessedLetters.contains(x) && x != ' ') {
				correctlyGuessedLetters.add(x);
				
				return true;
			}
			
		}
		if(!incorrectlyGuessedLetters.contains(inputLetter)) {
			incorrectlyGuessedLetters.add(inputLetter);	
		}
		return false;
	}
	

	public void loadFile() {
		File file = new File(System.getProperty("user.dir") + "\\words2.dat");
		
		try {
			if(file.createNewFile()) {
				StringBuffer x = new StringBuffer();
				for(int i = 0; i < predefinedWords.length; i++) {
					if(i==(predefinedWords.length-1)) {
						x.append(predefinedWords[i]);
					}else {
						x.append(predefinedWords[i] + ",");
					}
				}
				FileWriter write = new FileWriter(file);
				write.write(x.toString());
				write.close();
			}
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
			
				String[] wordsSeparated = line.split(",");
				for(String q : wordsSeparated) {
					words.add(q);
				}
					
			}
			scanner.close();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void addWord(String word) {
		
		if(Pattern.matches("^[a-zA-Z]{2,}$", word)) {
			if(!words.contains(word)) {
				words.add(word.toLowerCase());	
				System.out.println("Word added!");
			}else {
				System.out.println("The word is already in the game!");
			}
		}else {
			System.out.println("Word could not be added. The word must only contain letters(no whitespaces) and be atleast 2 letters long.");
		}
	}
	public void deleteWord(String word) {
		if(Pattern.matches("^[a-zA-Z]{2,}$", word)) {
			if(words.contains(word)) {
				words.remove(word.toLowerCase());	
				System.out.println("Word removed! ");
			}else {
				System.out.println("The word is not in the game! You can only delete a word that is in the game.");
			}
		}else {
			System.out.println("Word could not be removed. The word must only contain letters(no whitespaces) and be atleast 2 letters long.");
		}
	}
	public void updateFile() {
		File file = new File(System.getProperty("user.dir") + "\\words2.dat");
	
		try {
			StringBuffer x = new StringBuffer();
			for(int i = 0; i < words.size(); i++) {
				if(i==(words.size()-1)) {
					x.append(words.get(i));
				}else {
					x.append(words.get(i) + ",");
				}
			}
			FileWriter write = new FileWriter(file);
			write.write(x.toString());
			write.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isTheWord(String input) {
		if(word.equals(input)) {
			displayWord = input;
			return true;
		}else {
			return false;
		}
	}
	
	public String toString() {
		return displayWord;
	}
	
	public char getIncorrectlyGuessedLetters(int i) {
		return incorrectlyGuessedLetters.get(i);
	}
	public int getIncorrectlyGuessedLettersAmount() {
		return incorrectlyGuessedLetters.size();
	}
	public boolean areEqual() {
		int q = 0;
		if(word != "") {
			for(int i = 0; i < toString().length(); i+=2) {
				if(toString().charAt(i) != word.charAt(q)) {
					return false;
				}
				q++;
			}
			return true;	
		}else {
			return false;
		}
		
	}
	
	public String getWordFromFile(int i) {
		return words.get(i);
	}
	public int getWordFromFileSize() {
		return words.size();
	}
}
