package course.project.food.analyzer.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import course.project.food.analyzer.enums.LogFilePath;
import course.project.food.analyzer.enums.messages.ClientMessage;
import course.project.food.analyzer.enums.messages.ErrorMessage;
import course.project.food.analyzer.server.ClientConnectionSession;

public class ClientSession implements Runnable {

	private Socket clientSocket;

	private static final Logger LOGGER = Logger.getLogger(ClientConnectionSession.class.getName());
	private FileHandler fileHandler;

	public ClientSession(Socket clientSocket) {
		this.clientSocket = clientSocket;
		setupLogger();
	}

	private void setupLogger() {
		try {
			fileHandler = new FileHandler(LogFilePath.CLIENT_SESSION_ERROR_LOGS.getPath());
			fileHandler.setLevel(Level.SEVERE);
			fileHandler.setFormatter(new SimpleFormatter());
			LOGGER.addHandler(fileHandler);
		} catch (IOException e) {
			LOGGER.severe(e.getMessage());
		} catch (SecurityException e) {
			LOGGER.severe(e.getMessage());
		}
	}

	@Override
	public void run() {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
			print(reader);
		} catch (IOException e) {
			LOGGER.severe(ErrorMessage.CLOSED_SOCKET_ERROR.getMessage());
		} finally {
			fileHandler.flush();
			fileHandler.close();
		}
	}

	public void print(BufferedReader reader) throws IOException {
		String line;

		while (clientSocket.isClosed() == false) {
			line = reader.readLine();
			if (line == null) {
				clientSocket.close();
				LOGGER.info(ClientMessage.CLOSED_SOCKET.getMessage());
				return;
			}

			System.out.println(line);
		}
	}
}
