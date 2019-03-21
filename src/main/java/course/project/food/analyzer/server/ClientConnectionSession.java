package course.project.food.analyzer.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.http.HttpClient;
import java.util.concurrent.ExecutionException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;

import course.project.food.analyzer.cache.CacheManager;
import course.project.food.analyzer.converters.BarcodeConverter;
import course.project.food.analyzer.enums.Delimiter;
import course.project.food.analyzer.enums.LogFilePath;
import course.project.food.analyzer.enums.StatusCode;
import course.project.food.analyzer.enums.messages.ErrorMessage;
import course.project.food.analyzer.enums.messages.ServerMessage;
import course.project.food.analyzer.http.HttpRequestHandler;
import course.project.food.analyzer.validators.CommandValidator;

public class ClientConnectionSession implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(ClientConnectionSession.class.getName());
	private static FileHandler fileHandler;

	private static final String GET_FOOD_COMMAND = "get-food";
	private static final String GET_FOOD_REPORT_COMMAND = "get-food-report";
	private static final String GET_FOOD_BY_BARCODE_COMMAND = "get-food-by-barcode";
	private static final String DISCONNECT_COMMAND = "disconnect";
	private static final String GET_FOOD_FOLDER_RELATIVE_PATH = "\\get-food\\";
	private static final String GET_FOOD_REPORT_FOLDER_RELATIVE_PATH = "\\get-food-report\\";
	private static final int OFFSET = 1;
	private static final int UPC_SIZE = 12;

	private Socket clientSocket;

	public ClientConnectionSession(Socket clientSocket) {
		this.clientSocket = clientSocket;
		setupLogger();
	}

	private void setupLogger() {
		try {
			fileHandler = new FileHandler(LogFilePath.SERVER_SESSION_ERROR_LOGS.getPath());
			fileHandler.setLevel(Level.SEVERE);
			fileHandler.setFormatter(new SimpleFormatter());
			LOGGER.addHandler(fileHandler);

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		} catch (SecurityException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	@Override
	public void run() {

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

			HttpClient httpClient = HttpClient.newHttpClient();
			HttpRequestHandler httpRequestHandler = new HttpRequestHandler(httpClient);
			CacheManager cacheManager = new CacheManager();
			runCommands(reader, writer, cacheManager, httpRequestHandler);

		} catch (NotFoundException e) {
			LOGGER.severe(ErrorMessage.RESOURCE_NOT_FOUND_ERROR.getMessage());
		} catch (ChecksumException e) {
			LOGGER.severe(e.getMessage());
		} catch (FormatException e) {
			LOGGER.severe(e.getMessage());
		} catch (ExecutionException e) {
			LOGGER.severe(ErrorMessage.EXECUTION_ERROR.getMessage());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			LOGGER.severe(ErrorMessage.INTERUPTION_ERROR.getMessage());
		} catch (IOException e) {
			LOGGER.severe(ErrorMessage.CLOSED_SOCKET_ERROR.getMessage());
		} finally {
			fileHandler.flush();
			fileHandler.close();
		}

	}

	public void runCommands(BufferedReader reader, PrintWriter writer, CacheManager cacheManager,
			HttpRequestHandler httpRequestHandler) throws IOException, InterruptedException, NotFoundException,
			ChecksumException, FormatException, ExecutionException {

		CommandValidator commandValidator = new CommandValidator();

		while (!clientSocket.isClosed()) {

			String clientInput = reader.readLine();

			if (!commandValidator.validateCommand(clientInput)) {
				LOGGER.warning(ErrorMessage.UNRECOGNISED_COMMAND.getMessage() + clientInput);
				continue;
			}

			if (clientInput != null) {

				if (clientInput.equals(DISCONNECT_COMMAND)) {
					clientSocket.close();
					LOGGER.info(ServerMessage.CLIENT_DISCONNECTED.getMessage());
					return;
				}

				final String commandType = clientInput.substring(0,
						clientInput.indexOf(Delimiter.SPACE.getDelimiter()));
				final String commandParameter = clientInput
						.substring(clientInput.indexOf(Delimiter.SPACE.getDelimiter()) + OFFSET);

				switch (commandType) {
				case GET_FOOD_COMMAND: {

					if (cacheManager.checkForHit(commandParameter)) {
						getCachedFood(commandParameter, cacheManager, writer);
					} else {
						getFood(commandParameter, cacheManager, httpRequestHandler, writer);
					}
					break;
				}

				case GET_FOOD_REPORT_COMMAND: {

					if (cacheManager.checkForHit(commandParameter)) {
						getCachedFoodReport(commandParameter, cacheManager, writer);
					} else {
						getFoodReport(commandParameter, cacheManager, httpRequestHandler, writer);
					}
					break;

				}
				case GET_FOOD_BY_BARCODE_COMMAND: {

					String upcCode;
					final String upcPrefix = "--upc=";

					if (commandParameter.contains(upcPrefix)) {
						upcCode = commandParameter.substring(commandParameter.indexOf(upcPrefix) + upcPrefix.length(),
								commandParameter.indexOf(upcPrefix) + upcPrefix.length() + UPC_SIZE);
					} else {
						final int barcodeAdressIndex = 1;
						File barcodeImage = new File(
								commandParameter.split(Delimiter.EQUATION.getDelimiter())[barcodeAdressIndex]);

						if (!barcodeImage.exists() && !barcodeImage.isDirectory()) {
							LOGGER.warning(ServerMessage.MISSING_BARCODE_IMAGE.getMessage());
							break;
						}

						BarcodeConverter converter = new BarcodeConverter();
						upcCode = converter.convertToUpc(new FileInputStream(barcodeImage));
					}
					getFoodByBarcode(upcCode, cacheManager, writer);
					break;

				}
				}
			}
		}
	}

	public int getFood(String foodName, CacheManager cacheManager, HttpRequestHandler requestHandler,
			PrintWriter writer) throws IOException, InterruptedException, ExecutionException {

		String foodDetails = requestHandler.getFoodHttpRequest(foodName);

		writer.println(foodDetails);
		writer.flush();

		if (foodDetails.contains(ErrorMessage.INFORMATION_NOT_PROVIDED_BY_DATABASE.getMessage())) {
			LOGGER.warning(ErrorMessage.INFORMATION_NOT_PROVIDED_BY_DATABASE.getMessage());
			return StatusCode.NOT_FOUND.getStatusCode();
		}

		cacheManager.cache(GET_FOOD_FOLDER_RELATIVE_PATH + foodName, foodDetails);
		LOGGER.info(ServerMessage.REQUESTED.getMessage());

		return StatusCode.SUCCESS.getStatusCode();

	}

	public int getCachedFood(String foodName, CacheManager cacheManager, PrintWriter writer) throws IOException {

		String cachedFoodDetails = cacheManager.getCachedResponse(GET_FOOD_FOLDER_RELATIVE_PATH + foodName);
		writer.println(cachedFoodDetails);
		writer.flush();

		LOGGER.info(ServerMessage.CACHED.getMessage());

		return StatusCode.SUCCESS.getStatusCode();

	}

	public int getFoodReport(String ndbno, CacheManager cacheManager, HttpRequestHandler requestHandler,
			PrintWriter writer) throws IOException, InterruptedException, ExecutionException {

		String foodReport = requestHandler.getFoodReportHttpRequest(ndbno);

		writer.println(foodReport);
		writer.flush();

		if (foodReport.contains(ErrorMessage.INFORMATION_NOT_PROVIDED_BY_DATABASE.getMessage())) {
			LOGGER.warning(ErrorMessage.INFORMATION_NOT_PROVIDED_BY_DATABASE.getMessage());
			return StatusCode.NOT_FOUND.getStatusCode();
		}

		cacheManager.cache(GET_FOOD_REPORT_FOLDER_RELATIVE_PATH + ndbno, foodReport);
		LOGGER.info(ServerMessage.REQUESTED.getMessage());

		return StatusCode.SUCCESS.getStatusCode();
	}

	public int getCachedFoodReport(String ndbno, CacheManager cacheManager, PrintWriter writer) throws IOException {

		String cachedReport = cacheManager.getCachedResponse(GET_FOOD_REPORT_FOLDER_RELATIVE_PATH + ndbno);
		writer.println(cachedReport);
		writer.flush();

		LOGGER.info(ServerMessage.CACHED.getMessage());

		return StatusCode.SUCCESS.getStatusCode();

	}

	public int getFoodByBarcode(String upc, CacheManager cacheManager, PrintWriter writer) throws IOException {

		String foodDetails = cacheManager.getByBarcode(upc);
		writer.println(foodDetails);
		writer.flush();

		if (foodDetails.equals(ErrorMessage.NOT_CACHED.getMessage())) {
			LOGGER.info(ErrorMessage.NOT_CACHED.getMessage());
			return StatusCode.NOT_FOUND.getStatusCode();
		}

		LOGGER.info(ServerMessage.CACHED.getMessage());

		return StatusCode.SUCCESS.getStatusCode();
	}

}
