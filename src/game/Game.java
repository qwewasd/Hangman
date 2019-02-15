package game;

import java.util.Scanner;

public class Game {
	public static boolean isRunning = true;
	/* Create new word */
	public static Word word = new Word();
	
	public static void main(String[] args) {
		/* Initializing states */
		State mainMenu = new State(StateType.MAIN_MENU, "main menu");
		State playGame = new State(StateType.PLAY_GAME, "play");
		State terminate = new State(StateType.QUIT_GAME, "quit");
		State addWord = new State(StateType.ADD_WORD, "add word");
		State deleteWord = new State(StateType.DELETE_WORD, "delete word");
		State scoreboard = new State(StateType.SCOREBOARD, "scoreboard");
		
		mainMenu.setDescription("This is where you start the game from.");
		playGame.setDescription("You start a game of hangman");
		terminate.setDescription("You quit the game");
		addWord.setDescription("This is where you add a word to the game");
		deleteWord.setDescription("This is where you delete a word from the game");
		scoreboard.setDescription("This is the top ten highest scores. ");
		
		/* Adding visibilities */
		mainMenu.addVisibleState(playGame);
		mainMenu.addVisibleState(terminate);
		mainMenu.addVisibleState(addWord);
		mainMenu.addVisibleState(deleteWord);
		mainMenu.addVisibleState(scoreboard);
		
		playGame.addVisibleState(mainMenu);
		playGame.addVisibleState(terminate);
		
		terminate.addVisibleState(mainMenu);
		terminate.addVisibleState(playGame);
		
		addWord.addVisibleState(mainMenu);
		
		deleteWord.addVisibleState(mainMenu);
		
		scoreboard.addVisibleState(mainMenu);
		
		/* Setting current state */
		State.SetCurrentState(mainMenu);
		

		
		Hangman hangman = new Hangman();

		/* The game */
		while(isRunning) {
			
			State.GetCurrentState().render(new Rendering() {
				@Override
				public void render() {
					System.out.println("=------------------=");
					System.out.println("| HANGMAN THE GAME |");
					System.out.println("=------------------=");
					System.out.println("This is a text based game of hangman. To navigate around"
							+ " you type one of the options shown below.");
					
				}
				
			});
			if(State.GetCurrentState().getStateType() == StateType.PLAY_GAME) {
				/* Load words from file */
				
				
				/* State transition rendering and input events */
				State.GetCurrentState().render(new Rendering() {
					@Override
					public void render() {
						System.out.println("You are in a game of hangman! To return to main menu type 'main menu', to quit the game type 'quit'. ");
						System.out.println("Guess this word: ");
						System.out.println(word.toString());
						System.out.println();
						System.out.print("Words that have already been guessed: ");
						for(int i = 0; i < word.getIncorrectlyGuessedLettersAmount(); i++) {
							System.out.print(word.getIncorrectlyGuessedLetters(i) + " ");
						}
						System.out.println();
						System.out.println("Attempts left: " + Hangman.AttemptsLeft);
						System.out.println("Score: " + Hangman.Score);
						System.out.println("Goodluck!");
					}
				});
				State.GetCurrentState().event(new InputEvent() {

					@Override
					public void create() {
						if(word.areEqual()) {
							System.out.println("You guessed the word! A new word has been generated. ");
							hangman.restartWonGame();
							return;
						}
						
						addInput();
						if(matchesPattern("^" + mainMenu.toString() + "$")) {
							System.out.println("Are you sure you want to go back to the main menu(All progress will be lost)? (yes/no)");
							addInput();
							
							if(matchesPattern("^yes$")) {
								State.SetCurrentState(mainMenu);
							}else if(matchesPattern("^no$")) {
								return;
							}else {
								System.out.println("Could not recognize input. ");
							}
							
						}else if(matchesPattern("^" + terminate.toString() + "$")) {
							State.SetCurrentState(terminate);
						}else if(matchesPattern("^[a-zA-Z]$")){
							if(word.isLetterInWord(getInput().charAt(0))) {
								System.out.println("You guessed correctly!");
								Hangman.Score += 10;
								word.updateDisplayWord();
							}else {
								Hangman.AttemptsLeft--;
								
								if(Hangman.AttemptsLeft == 0) {
									hangman.tryAddHighscore();
									System.out.println("You lost the game");
									State.SetCurrentState(mainMenu);
								}
							}
						}else if(matchesPattern("^[a-zA-Z]{2,}$")) {
							if(word.isTheWord(getInput())) {
								System.out.println("You guessed a word correctly! A new word has been generated. ");
								Hangman.Score += 50;
								hangman.restartWonGame();
							}else {
								hangman.tryAddHighscore();
								System.out.println("You lost the game");
								State.SetCurrentState(mainMenu);
							}
						}
					}
					
					
				});
			}else if(State.GetCurrentState().getStateType() == StateType.MAIN_MENU) {
				/* State transition rendering and input events */
				State.GetCurrentState().render();
				State.GetCurrentState().inputEvent();
				
				hangman.restartLostGame();
				
			}else if(State.GetCurrentState().getStateType() == StateType.QUIT_GAME) {
				State.GetCurrentState().render(new Rendering() {

					@Override
					public void render() {
						System.out.println("Are you sure you want to quit the game(all progress will be lost)? (yes/no)");
					}
					
				});
				State.GetCurrentState().event(new InputEvent() {

					@Override
					public void create() {
						// TODO Auto-generated method stub
						addInput();
						
						if(matchesPattern("^yes$")) {
							
							System.exit(0);
						
						}else if(matchesPattern("^no$")) {
							
							State.SetCurrentState(State.GetState(1));
						
						}else {
							System.out.println("Could not recognize the input. It can either be 'yes' or 'no'.");
						}
						
					}
	
				
					
				});
				
			}else if(State.GetCurrentState().getStateType() == StateType.ADD_WORD) {
				State.GetCurrentState().render(new Rendering() {
					@Override
					public void render() {
						System.out.println("You are adding a new word. Type 'main menu' if you want to go back to the main menu. The list of existing words are shown below:");
						System.out.print("[ ");
						for(int i = 0; i < word.getWordFromFileSize(); i++) {
							System.out.print(word.getWordFromFile(i) +  " ");
						}
						System.out.println("]");
						
					}
					
					
				});
				
				State.GetCurrentState().event(new InputEvent() {
					@Override
					public void create() {
						addInput();
						if(matchesPattern("^" + mainMenu.toString() + "$")) {
							State.SetCurrentState(mainMenu);
						}else if(matchesPattern("^[a-zA-Z]{2,}$")) {
							word.addWord(getInput());
							word.updateFile();
						}else {
							System.out.println("Word could not be added. The word must only contain letters(no whitespaces) and be atleast 2 letters long.");
						}
					}
				});
				
			}else if(State.GetCurrentState().getStateType() == StateType.DELETE_WORD) {
				State.GetCurrentState().render(new Rendering() {
					@Override
					public void render() {
						System.out.println("You are deleteing a word. Type 'main menu' if you want to go back to the main menu. The list of existing words are shown below:");
						System.out.print("[ ");
						for(int i = 0; i < word.getWordFromFileSize(); i++) {
							System.out.print(word.getWordFromFile(i) +  " ");
						}
						System.out.println("]");
						
					}
					
					
				});
				
				State.GetCurrentState().event(new InputEvent() {
					@Override
					public void create() {
						addInput();
						if(matchesPattern("^" + mainMenu.toString() + "$")) {
							State.SetCurrentState(mainMenu);
						}else if(matchesPattern("^[a-zA-Z]{2,}$")) {
							word.deleteWord(getInput());
							word.updateFile();
							
						}else {
							System.out.println("Word could not be deleted. The word must only contain letters(no whitespaces) and be atleast 2 letters long.");
						}
					}
				});
				
			}else if(State.GetCurrentState().getStateType() == StateType.SCOREBOARD) {
				State.GetCurrentState().render(new Rendering() {
					@Override
					public void render() {
						System.out.println("You are viewing the scoreboard. The scoreboard shows top ten highscores. To go back to main menu, type 'main menu'. ");
						System.out.println("If the scoreboard is empty then you have not played a game of hangman..");
						
						for(int i = 0; i < hangman.getHighscoresAmount(); i++) {
							System.out.println((i+1) + ": " + hangman.getHighscore(i));							
						}
					}
					
					
				});
				
				State.GetCurrentState().event(new InputEvent() {
					@Override
					public void create() {
						addInput();
						if(matchesPattern("^" + mainMenu.toString() + "$")) {
							State.SetCurrentState(mainMenu);
						}
					}
				});
			}
			

		}
	

		
	}
}
