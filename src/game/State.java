package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class State {
	
	private StateType type;
	private String name;
	private List<State> visibleStates = new ArrayList<>();
	private List<Keyword> keywords = new ArrayList<>();
	
	private String description;

	 /** @param t Unique StateType 
	 * @param visibility Visibility of other states that the current state object can transition back and forth to.
	 */
	public State(StateType t, String name) {
		if(isUnique(t)) {
			setStateType(t);
			setName(name);
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
	
	private void setStateType(StateType t) {
		type = t;
	}
	
	private void setName(String name) {
		this.name = name;
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
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
				System.out.println(x.toString() + ": " + x.getDescription());
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
	
	public void event(final Event e) {
		if(CurrentState == this) {
			
			e.create();	
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
			List<State> tempList = new ArrayList<>();
			tempList.add(t);
			tempList.add(CurrentState);
			for(State x : States) {
				if(x != CurrentState && x != t) {
					tempList.add(x);
				}
				
			}
			States = tempList;
			CurrentState = t;
		
			
			
		}
	}
	
	public static State GetCurrentState() {
		return CurrentState;
	}
	
	
}
