package course.project.food.analyzer.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import course.project.food.analyzer.enums.LogFilePath;
import course.project.food.analyzer.enums.messages.ErrorMessage;
import course.project.food.analyzer.enums.messages.ServerMessage;

public class FoodAnalyzerServer implements AutoCloseable {

	private static ServerSocket serverSocket;
	private static Logger LOGGER;
	private static FileHandler fileHandler;

	private static final int PORT = 8080;

	public FoodAnalyzerServer() {
		LOGGER = Logger.getLogger(FoodAnalyzerServer.class.getName());
		setupLogger();
	}

	public static void setupLogger() {
		try {
			fileHandler = new FileHandler(LogFilePath.SERVER_ERROR_LOGS.getPath());
			fileHandler.setLevel(Level.SEVERE);
			fileHandler.setFormatter(new SimpleFormatter());
			LOGGER.addHandler(fileHandler);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		} catch (SecurityException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	public void start(ServerSocket serverSocket) throws IOException {
		LOGGER.info(ServerMessage.RUNNING.getMessage() + PORT);

		while (!serverSocket.isClosed()) {
			Socket newClientSocket = serverSocket.accept();

			LOGGER.info(ServerMessage.CLIENT_CONNECTED.getMessage());

			ClientConnectionSession clientConnectionSession = new ClientConnectionSession(newClientSocket);
			new Thread(clientConnectionSession).start();
		}
	}

	@Override
	public void close() throws IOException {
		serverSocket.close();
		LOGGER.info(ServerMessage.CLOSED_SOCKET.getMessage() + PORT);
	}

	public static void main(String[] args) {
		try (FoodAnalyzerServer foodAnalyzerServer = new FoodAnalyzerServer();
				ServerSocket serverSocket = new ServerSocket(PORT);) {

			foodAnalyzerServer.start(serverSocket);

		} catch (IOException e) {
			LOGGER.severe(ErrorMessage.SERVER_ERROR.getMessage() + PORT);
		} finally {
			fileHandler.flush();
			fileHandler.close();
		}
	}
}
