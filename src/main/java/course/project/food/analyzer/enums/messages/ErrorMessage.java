package course.project.food.analyzer.enums.messages;

public enum ErrorMessage {
	SERVER_ERROR("Could not run server. Perhaps another server is running on port:"),
	CONNECTION_ERROR("Cannot connect to server. Make sure that the server is started. "),
	RESOURCE_NOT_FOUND_ERROR("The resource you required could not be found on the server. "),
	NOT_CONNECTED_TO_SERVER_ERROR("Invalid command. Connect to the server first. "),
	MISSING_INFORMATION("Missing in database. \n"),
	INFORMATION_NOT_PROVIDED_BY_DATABASE("The requested information is not provided by the data base. "),
	NOT_CACHED("The product you required is not in the cache. "), CLOSED_SOCKET_ERROR("Client socket is closed. "),
	UNRECOGNISED_COMMAND("The server cannot recognise your command: "),
	EXECUTION_ERROR("The request to the server was aborted during execution. "),
	INTERUPTION_ERROR("Current thread was interrupted during execution. "),
	GENERAL_ERROR("A general exception occured. ");

	ErrorMessage(String message) {
		this.message = message;
	}

	private final String message;

	public String getMessage() {
		return message;
	}
}
