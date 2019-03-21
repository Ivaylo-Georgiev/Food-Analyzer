package course.project.food.analyzer.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;

import course.project.food.analyzer.cache.CacheManager;
import course.project.food.analyzer.enums.StatusCode;
import course.project.food.analyzer.enums.messages.ErrorMessage;
import course.project.food.analyzer.http.HttpRequestHandler;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.anyString;

public class ClientConnectionSessionTest {

	private static final String FOOD_NAME = "Mocked food";
	private CacheManager cacheManager;
	private HttpRequestHandler requestHandler;
	private PrintWriter writer;

	private ClientConnectionSession session;

	@Before
	public void init() throws UnsupportedEncodingException, IOException, InterruptedException {
		cacheManager = mock(CacheManager.class);
		writer = mock(PrintWriter.class);

		doNothing().when(writer).println(anyString());

		Socket mockSocket = mock(Socket.class);
		session = new ClientConnectionSession(mockSocket);
	}

	@Test
	public void testGetFood_200() throws IOException, InterruptedException, ExecutionException {

		requestHandler = mock(HttpRequestHandler.class);
		doReturn(FOOD_NAME).when(requestHandler).getFoodHttpRequest(anyString());
		doNothing().when(cacheManager).cache(anyString(), anyString());

		int getFoodStatus = session.getFood(FOOD_NAME, cacheManager, requestHandler, writer);
		assertEquals(StatusCode.SUCCESS.getStatusCode(), getFoodStatus);

	}

	@Test
	public void testGetFood_404() throws IOException, InterruptedException, ExecutionException {

		requestHandler = mock(HttpRequestHandler.class);
		doReturn(ErrorMessage.INFORMATION_NOT_PROVIDED_BY_DATABASE.getMessage()).when(requestHandler)
				.getFoodHttpRequest(anyString());
		doNothing().when(cacheManager).cache(anyString(), anyString());

		int statusCode = session.getFood(FOOD_NAME, cacheManager, requestHandler, writer);
		assertEquals(StatusCode.NOT_FOUND.getStatusCode(), statusCode);

	}

	@Test
	public void testGetCachedFood() throws IOException, InterruptedException {

		doReturn(FOOD_NAME).when(cacheManager).getCachedResponse(anyString());

		int statusCode = session.getCachedFood(FOOD_NAME, cacheManager, writer);
		assertEquals(StatusCode.SUCCESS.getStatusCode(), statusCode);

	}

	@Test
	public void testGetFoodReport_200() throws IOException, InterruptedException, ExecutionException {

		requestHandler = mock(HttpRequestHandler.class);
		doReturn(FOOD_NAME).when(requestHandler).getFoodReportHttpRequest(anyString());
		doNothing().when(cacheManager).cache(anyString(), anyString());

		int getReportStatus = session.getFoodReport(FOOD_NAME, cacheManager, requestHandler, writer);
		assertEquals(StatusCode.SUCCESS.getStatusCode(), getReportStatus);

	}

	@Test
	public void testGetFoodReport_404() throws IOException, InterruptedException, ExecutionException {

		requestHandler = mock(HttpRequestHandler.class);
		doReturn(ErrorMessage.INFORMATION_NOT_PROVIDED_BY_DATABASE.getMessage()).when(requestHandler)
				.getFoodReportHttpRequest(anyString());
		doNothing().when(cacheManager).cache(anyString(), anyString());

		int getReportStatus = session.getFoodReport(FOOD_NAME, cacheManager, requestHandler, writer);
		assertEquals(StatusCode.NOT_FOUND.getStatusCode(), getReportStatus);

	}

	@Test
	public void testGetCachedFoodReport() throws IOException, InterruptedException {

		doReturn(FOOD_NAME).when(cacheManager).getCachedResponse(anyString());

		int getReportStatus = session.getCachedFoodReport(FOOD_NAME, cacheManager, writer);
		assertEquals(StatusCode.SUCCESS.getStatusCode(), getReportStatus);

	}

	@Test
	public void testGetCachedFoodByBarcode_200() throws IOException, InterruptedException {

		doReturn(FOOD_NAME).when(cacheManager).getByBarcode(anyString());

		int status = session.getFoodByBarcode(FOOD_NAME, cacheManager, writer);
		assertEquals(StatusCode.SUCCESS.getStatusCode(), status);

	}

	@Test
	public void testGetCachedFoodByBarcode_404() throws IOException, InterruptedException {

		doReturn(ErrorMessage.NOT_CACHED.getMessage()).when(cacheManager).getByBarcode(anyString());

		int status = session.getFoodByBarcode(FOOD_NAME, cacheManager, writer);
		assertEquals(StatusCode.NOT_FOUND.getStatusCode(), status);

	}
}
