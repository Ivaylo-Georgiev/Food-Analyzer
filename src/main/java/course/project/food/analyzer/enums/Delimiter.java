package course.project.food.analyzer.enums;

public enum Delimiter {
	SPACE(" "), WHITESPACE("\\s+"), NEW_LINE("\n"), COLON(": "), EQUATION("="), TXT_EXTENSION(".txt");

	Delimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	private final String delimiter;

	public String getDelimiter() {
		return delimiter;
	}
}
