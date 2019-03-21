package course.project.food.analyzer.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;

import course.project.food.analyzer.cache.CacheManager;
import course.project.food.analyzer.http.HttpRequestHandler;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class CommandExecutionTest {

	private static final String UPC = "009800146130";
	private static final String DISCONNECT = "disconnect";

	private static PrintWriter writer;
	private static BufferedReader reader;
	private static CacheManager cacheManager;
	private static HttpRequestHandler httpRequestHandler;

	private static ClientConnectionSession session;

	@BeforeClass
	public static void init() {
		Socket socket = mock(Socket.class);
		session = spy(new ClientConnectionSession(socket));

		writer = mock(PrintWriter.class);
		reader = mock(BufferedReader.class);
		cacheManager = mock(CacheManager.class);
		httpRequestHandler = mock(HttpRequestHandler.class);
	}

	@Test
	public void testGetFoodCommandExecutionCached() throws IOException, NotFoundException, ChecksumException,
			FormatException, InterruptedException, ExecutionException {

		final String command = "get-food raffaello";
		when(reader.readLine()).thenReturn(command, DISCONNECT);
		doReturn(true).when(cacheManager).checkForHit(anyString());

		session.runCommands(reader, writer, cacheManager, httpRequestHandler);

		verify(session).getCachedFood("raffaello", cacheManager, writer);

	}

	@Test
	public void testGetFoodCommandExecutionApiRequested() throws IOException, NotFoundException, ChecksumException,
			FormatException, InterruptedException, ExecutionException {

		final String command = "get-food raffaello";
		when(reader.readLine()).thenReturn(command, DISCONNECT);
		doReturn(false).when(cacheManager).checkForHit(anyString());
		doReturn(UPC).when(httpRequestHandler).getFoodHttpRequest(anyString());

		session.runCommands(reader, writer, cacheManager, httpRequestHandler);

		verify(session).getFood("raffaello", cacheManager, httpRequestHandler, writer);

	}

	@Test
	public void testGetFoodReportCommandExecutionCached() throws IOException, NotFoundException, ChecksumException,
			FormatException, InterruptedException, ExecutionException {

		final String command = "get-food-report 009800146130";

		when(reader.readLine()).thenReturn(command, DISCONNECT);
		doReturn(true).when(cacheManager).checkForHit(anyString());

		session.runCommands(reader, writer, cacheManager, httpRequestHandler);

		verify(session).getCachedFoodReport(UPC, cacheManager, writer);

	}

	@Test
	public void testGetFoodReportCommandExecutionApiRequested() throws IOException, NotFoundException,
			ChecksumException, FormatException, InterruptedException, ExecutionException {

		final String command = "get-food-report 009800146130";
		when(reader.readLine()).thenReturn(command, DISCONNECT);
		doReturn(false).when(cacheManager).checkForHit(anyString());
		doReturn(UPC).when(httpRequestHandler).getFoodReportHttpRequest(anyString());

		session.runCommands(reader, writer, cacheManager, httpRequestHandler);

		verify(session).getFoodReport(UPC, cacheManager, httpRequestHandler, writer);

	}

	@Test
	public void testGetFoodByBarcodeCommandExecutionCached() throws IOException, NotFoundException, ChecksumException,
			FormatException, InterruptedException, ExecutionException {

		final String command = "get-food-by-barcode --upc=009800146130";

		when(reader.readLine()).thenReturn(command, DISCONNECT);
		doReturn(UPC).when(cacheManager).getByBarcode(UPC);

		session.runCommands(reader, writer, cacheManager, httpRequestHandler);

		verify(session).getFoodByBarcode(UPC, cacheManager, writer);

	}

}
