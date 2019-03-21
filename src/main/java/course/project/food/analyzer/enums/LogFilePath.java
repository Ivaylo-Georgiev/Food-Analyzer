package course.project.food.analyzer.enums;

public enum LogFilePath {
	SERVER_ERROR_LOGS("resources\\logs\\ServerErrorLogs.log"),
	SERVER_SESSION_ERROR_LOGS("resources\\logs\\ServerSessionErrorLogs.log"),
	CLIENT_ERROR_LOGS("resources\\logs\\ClientErrorLogs.log"),
	CLIENT_SESSION_ERROR_LOGS("resources\\logs\\ClientSessionErrorLogs.log");

	LogFilePath(String path) {
		this.path = path;
	}

	private final String path;

	public String getPath() {
		return path;
	}
}
