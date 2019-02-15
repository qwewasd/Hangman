package game;

public class Keyword {
	private String pattern;
	private String description;
	public Keyword(String pattern, String description) {
		this.pattern = pattern;
		this.description = description;
	}
	
	public String getPattern() {
		return pattern;
	}
	
	public String getDescription() {
		return description;
	}
	
}
