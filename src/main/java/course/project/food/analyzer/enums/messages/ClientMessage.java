package course.project.food.analyzer.enums.messages;

public enum ClientMessage {
	COMMAND_EXECUTED("Your command was processed by the server. "),
	CONNECTED("A new client is connected to the server. "),
	CLOSED_SOCKET("Client socket is closed, stop waiting for server messages. ");

	ClientMessage(String message) {
		this.message = message;
	}

	private final String message;

	public String getMessage() {
		return message;
	}
}
