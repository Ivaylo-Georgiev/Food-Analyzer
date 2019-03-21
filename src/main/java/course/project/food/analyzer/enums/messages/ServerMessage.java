package course.project.food.analyzer.enums.messages;

public enum ServerMessage {
	RUNNING("Server is running on localhost:"), CLIENT_CONNECTED("A new client is connected to the server. "),
	CLOSED_SOCKET("Server socket is closed. "), CLIENT_DISCONNECTED("A client disconnected from the server. "),
	MISSING_BARCODE_IMAGE("Barcode file not found. "),
	REQUESTED("The requested information is retrieved from the REST API. It is now loaded in the cache. "),
	CACHED("The requested information is retrieved from the cache. ");

	ServerMessage(String message) {
		this.message = message;
	}

	private final String message;

	public String getMessage() {
		return message;
	}
}
