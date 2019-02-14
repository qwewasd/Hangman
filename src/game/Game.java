package game;

public class Game {
	public static boolean isRunning = true;
	public static void main(String[] args) {
		/* Initializing states */
		State mainMenu = new State(StateType.MAIN_MENU);
		State playGame = new State(StateType.PLAY_GAME);
		State terminate = new State(StateType.QUIT_GAME);
		
		/* Adding visibilities */
		mainMenu.addVisibleState(playGame);
		mainMenu.addVisibleState(terminate);

		playGame.addVisibleState(mainMenu);
		playGame.addVisibleState(terminate);
		
		terminate.addVisibleState(mainMenu);
		terminate.addVisibleState(playGame);
		
		/* Setting current state */
		State.SetCurrentState(mainMenu);
		
		/* The game */
		while(isRunning) {
			State.GetCurrentState().render();
			State.GetCurrentState().inputEvent();
		}
	

		
	}
}
