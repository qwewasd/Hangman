package game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StateTest {

	@Test
	void addVisibleStateShouldThrowExceptionOnAddingSameStateTwice() {
		/* The first parameter of the State object is chosen from the existing StateTypes */
		State t = new State(StateType.MAIN_MENU, "menu"); 	
		State t2 = new State(StateType.PLAY_GAME, "play"); // dummy object
		t.addVisibleState(t2); // adding t2 to t
	
		// add t2 again
		assertThrows(IllegalArgumentException.class, () -> t.addVisibleState(t2)); 

	}
	
	@Test
	void addVisibleStateShouldThrowExceptionOnAddingItself(){
		State tx = new State(StateType.ADD_WORD, "add word");
		assertThrows(IllegalArgumentException.class, () -> tx.addVisibleState(tx));
		
	}
	
	/*
	 * The newly added method(removeVisibleState) that is bugged.
	 * This one does not throw an IllegalArgumentException thus fails the test
	 */
	@Test
	void removeVisibleStateShouldThrowExceptionOnNonVisibleState() {
		State tx = new State(StateType.DELETE_WORD, "delete word");
		State t2 = new State(StateType.QUIT_GAME, "quit"); // dummy object
		// trying to delete t2 from t1's visible states
		assertThrows(IllegalArgumentException.class, () -> tx.removeVisibleState(t2));
	}
	
	@Test
	void removeVisibleStateShouldThrowExceptionOnItself() {
		State tx = new State(StateType.SCOREBOARD, "scoreboard");
		assertThrows(IllegalArgumentException.class, () -> tx.removeVisibleState(tx));
	}
	
	State tx = new State(StateType.TEST, "delete word");
	
	@Test
	void setDescriptionShouldThrowExceptionOnCharacterLength256() {
		
		StringBuilder sb = new StringBuilder();
		for(int x = 0; x < 256; x++) {
			sb.append("x");
		}
		
		assertThrows(IllegalArgumentException.class, () -> tx.setDescription(sb.toString()));
		
		
	}
	
	@Test
	void setDescriptionShouldNotThrowExceptionOnCharacterLength255() {
		StringBuilder sb = new StringBuilder();
		for(int x = 0; x < 255; x++) {
			sb.append("x");
		}
		
		tx.setDescription(sb.toString());
		
	}
	
	
	
}
