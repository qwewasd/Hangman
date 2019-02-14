package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class State {
	
	private StateType type;
	private String name;
	private List<State> visibleStates = new ArrayList<>();
	
	/**
	 * Constructor of a new State with a unique StateType t, unique name,
	 * and an array of visible states which the state can be transitioned to.
	 * Assume there are two states, A and B. In order for B to be visible for A,
	 * A must be visible for B and conversely. Ex: From the main menu to Playing the game and 
	 * from playing the game to the main menu.
	 * 
	 * Thus for a new state to be created, it must have an unique StateType and must be
	 * visible for at least one other state not itself.
	 *  
	 * @param t Unique StateType 
	 * @param visibility Visibility of other states that the current state object can transition back and forth to.
	 */
	public State(StateType t) {
		if(isUnique(t)) {
			setState(t);
			setName();
			States.add(this);
		}else {
			System.out.println("StateType is not unique");
		}
	}

	/**
	 * Returns the state type StateType. 
	 * @return StateType
	 */
	public StateType getStateType() {
		return type;
	}
	
	private void setState(StateType t) {
		type = t;
	}
	
	private void setName() {
		if(type != null) {
			name = type.toString();
		}
	}
	
	private boolean isUnique(StateType t) {
		for(State x : States) {
			if(x.getStateType() == t) {
				return false;
			}
		}
		return true;
	}
	
	
	public void addVisibleState(State t) {
		if(States.contains(t) && !visibleStates.contains(t)) {
			visibleStates.add(t);
		}
	}
	
	public void removeVisibleState(State t) {
		visibleStates.remove(t);
	}
	
	public State getVisibleState(int i) {
		return visibleStates.get(i);
	}
	
	public void render(final Rendering r) {
		if(CurrentState == this) {
			r.render();	
		}else {
			System.out.println("Unable to render " + toString() + " state.");
		}
	}
	
	public void render() {
		if(CurrentState == this) {
			System.out.println("You are currently in " + toString() + ". Options: ");
			for(State x : visibleStates) {
				System.out.println("\"" + x.toString() + "\"");
			}
		}
	}
	
	public void inputEvent() {
		if(CurrentState == this) {
			Scanner scanner = new Scanner(System.in);
			System.out.println();
			if(scanner.hasNext()) {
				String input = scanner.nextLine();
				for(State x : visibleStates) {
					if(x.toString().toLowerCase().equals(input.toLowerCase())) {
						State.SetCurrentState(x);
					}
					
				}
				
				
			}
			
			
		}
		
		
	}
	
	public void inputEvent(final Event e) {
		if(CurrentState == this) {
			e.inputEvent();	
		}
	}
		
	/**
	 * Returns the name of the State
	 * @return name
	 */
	public String toString() {
		return name;
	}
	
	/* Static methods */
	/* -------------- */
	
	/* Every state in the game is stored in this list. 
	 * Hence for every state object, the object is added to this list. 
	 * There is no way to change this list other than to create new objects of the class State.
	 */
	private static List<State> States = new ArrayList<State>();
	private static State CurrentState;
	
	/**
	 * Returns the object from the list of all states on the index given by the parameter
	 * @param i Is the index 
	 * @return Object of class State from the list of all states.
	 */
	public static State GetState(int i) throws IndexOutOfBoundsException {
		
		// list is not empty and index i is in interval [0, States.size()-1]
		if( (States.size() > 0 && i <= States.size()-1 && i >= 0) ) {
			return States.get(i);
		}else {
			throw new IndexOutOfBoundsException();
		}
	}
	
	
	/**
	 * Returns the size of the list States.
	 * This list contains all the states created
	 * @return Number of created states in the game. 
	 */
	public static int GetStateSize() {
		return States.size();
	}
	
	public static void SetCurrentState(State t) {
		if(States.contains(t)) {
			CurrentState = t;
		}
	}
	
	public static State GetCurrentState() {
		return CurrentState;
	}
	
	
}
