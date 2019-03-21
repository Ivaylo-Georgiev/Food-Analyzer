package course.project.food.analyzer.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import course.project.food.analyzer.enums.Delimiter;
import course.project.food.analyzer.enums.LogFilePath;
import course.project.food.analyzer.enums.messages.ClientMessage;
import course.project.food.analyzer.enums.messages.ErrorMessage;
import course.project.food.analyzer.server.ClientConnectionSession;

public class FoodAnalyzerClient {

	private static final String CONNECT_COMMAND = "connect";
	private static final int COMMAND_TYPE_INDEX = 0;
	private static final int HOST_INDEX = 1;
	private static final int PORT_INDEX = 2;
	private static final int COMMAND_COMPONENTS_COUNT = 3;

	private BufferedReader reader;
	private PrintWriter writer;

	private Logger LOGGER;
	private FileHandler fileHandler;

	public FoodAnalyzerClient() {
		LOGGER = Logger.getLogger(ClientConnectionSession.class.getName());
		setupLogger();
	}

	private void setupLogger() {
		try {
			fileHandler = new FileHandler(LogFilePath.CLIENT_ERROR_LOGS.getPath());
			fileHandler.setLevel(Level.SEVERE);
			fileHandler.setFormatter(new SimpleFormatter());
			LOGGER.addHandler(fileHandler);
		} catch (IOException ex) {
			LOGGER.severe(ex.getMessage());
		} catch (SecurityException ex) {
			LOGGER.severe(ex.getMessage());
		}
	}

	public void run() {
		try (Scanner scanner = new Scanner(System.in)) {
			while (scanner.hasNext()) {
				String userInput = scanner.nextLine();
				String[] commandTokens = userInput.split(Delimiter.WHITESPACE.getDelimiter());

				if (commandTokens[COMMAND_TYPE_INDEX].equals(CONNECT_COMMAND)
						&& commandTokens.length == COMMAND_COMPONENTS_COUNT) {
					final String host = commandTokens[HOST_INDEX];
					final int port = Integer.parseInt(commandTokens[PORT_INDEX]);

					connect(host, port);
				} else {
					if (writer == null) {
						LOGGER.warning(ErrorMessage.NOT_CONNECTED_TO_SERVER_ERROR.getMessage());
					} else {
						writer.println(userInput);
						writer.flush();
						LOGGER.info(ClientMessage.COMMAND_EXECUTED.getMessage());
					}
				}
			}
		}
	}

	public void connect(String host, int port) {
		try {
			Socket newClientSocket = new Socket(host, port);

			this.reader = new BufferedReader(new InputStreamReader(newClientSocket.getInputStream()));
			this.writer = new PrintWriter(newClientSocket.getOutputStream(), true);

			LOGGER.info(ClientMessage.CONNECTED.getMessage());

			ClientSession clientSession = new ClientSession(newClientSocket);
			new Thread(clientSession).start();

		} catch (IOException e) {
			LOGGER.severe(ErrorMessage.CONNECTION_ERROR.getMessage());
		} finally {
			fileHandler.flush();
			fileHandler.close();
		}
	}

	public static void main(String[] args) throws IOException {
		FoodAnalyzerClient foodAnalyzerClient = new FoodAnalyzerClient();
		foodAnalyzerClient.run();
	}
}
