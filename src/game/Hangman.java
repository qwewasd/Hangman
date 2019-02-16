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
	
	
	protected class Part{
		public String figure;
		public Part(String fig) {
			figure = fig;
		}
		public Part() {}
		
		
	}
	/*  */
	private Part[] parts = new Part[9];
	public Part getPart(int i ) {
		return parts[i];
	}
	public Hangman() {
		File file = new File(System.getProperty("user.dir") + "\\scoreboard.dat");
		
		/* Init parts */
		parts[0] = new Part();
		parts[1] = new Part();
		parts[2] = new Part();
		parts[3] = new Part();
		parts[4] = new Part();
		parts[5] = new Part();
		parts[6] = new Part();
		parts[7] = new Part();
		parts[8] = new Part();
		
		parts[8].figure = "";
		parts[7].figure = " =========\n |\n |\n |\n |\n_|_";
		parts[6].figure = " =========\n |\t |\n |\n |\n |\n_|_";
		parts[5].figure = " =========\n |\t |\n |\t O\n |\n |\n_|_";

		parts[4].figure = " =========\n |\t |\n |\t O\n |\t | \n |\n_|_";

		parts[3].figure = " =========\n |\t |\n |\t O\n |\t/| \n |\n_|_";

		parts[2].figure = " =========\n |\t |\n |\t O\n |\t/|\\ \n |\n_|_";

		parts[1].figure = " =========\n |\t |\n |\t O\n |\t/|\\ \n |\t/ \n_|_";

		parts[0].figure = " =========\n |\t |\n |\t O\n |\t/|\\ \n |\t/ \\ \n_|_";
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
