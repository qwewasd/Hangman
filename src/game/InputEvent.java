package game;

import java.util.Scanner;
import java.util.regex.Pattern;

public abstract class InputEvent implements Event{
	private Scanner scanner = new Scanner(System.in, "UTF-8");
	private String input; 
	
	private boolean inputAdded = false;
	private boolean isRaw = true;
	
	public void addInput() {
		try {
			input = scanner.nextLine().toLowerCase();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean matchesPattern(String p) {
		if(acceptRawResponse()) {
			return Pattern.matches(p, input) ? true : false;
		}else {
			return false;
		}
		
	}
	
	public String getInput() {
		if(acceptRawResponse()) {
			return input;
		}else {
			return "Raw response not acceptable";
		}
	}
	
	public boolean acceptRawResponse() {
		return true;
	}
	public void closeInputStream() {
		scanner.close();
	}
}
