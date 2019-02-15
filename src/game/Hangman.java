package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Hangman {
	public static int AttemptsLeft = 8;
	public static int Score = 0;
	private List<Integer> highscores = new ArrayList<>();
	
	public Hangman() {
		File file = new File(System.getProperty("user.dir") + "\\scoreboard.dat");
		
		try {
			if(!file.createNewFile() ) {
				FileInputStream fileI = new FileInputStream(System.getProperty("user.dir") + "\\scoreboard.dat");
				if(fileI.available() != 0) {
					ObjectInputStream objectInFile = new ObjectInputStream(fileI);
					
					highscores  = (ArrayList<Integer>) objectInFile.readObject();
					objectInFile.close();
				}
				
			}
		}catch(IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}
	public void restartWonGame() {
		AttemptsLeft = 8;
		Game.word = new Word();
		
	}
	public void restartLostGame() {
		restartWonGame();
		Score = 0;
	}

	
	public void tryAddHighscore() {
		if(highscores.isEmpty()) {
			highscores.add(Score);
		
		}else if(highscores.size() < 10){
			highscores.add(Score);
		}else {
			if(highscores.get(highscores.size()-1) < Score) {
				highscores.set(9, Score);
			}
		}
		Collections.sort(highscores, (a, b) -> a > b ? -1 : a == b ? 0 : 1);
		
		try {
			
			FileOutputStream file = new FileOutputStream(System.getProperty("user.dir") + "\\scoreboard.dat");
			ObjectOutputStream fileToObject = new ObjectOutputStream(file);
			fileToObject.writeObject(highscores);
			fileToObject.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getHighscore(int i) {
		return highscores.get(i);
	}
	
	public int getHighscoresAmount() {
		return highscores.size();
	}
	
}
