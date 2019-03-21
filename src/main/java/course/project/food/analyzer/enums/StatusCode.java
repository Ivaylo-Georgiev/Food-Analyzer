package course.project.food.analyzer.enums;

public enum StatusCode {
	SUCCESS(200), NOT_FOUND(404);

	StatusCode(int code) {
		this.code = code;
	}

	private final int code;

	public int getStatusCode() {
		return code;
	}
}
