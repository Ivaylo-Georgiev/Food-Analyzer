package course.project.food.analyzer.client;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;

public class ClientTest {
	
	private static FoodAnalyzerClient client;
	
	@BeforeClass
	public static void init() {
		client = spy(new FoodAnalyzerClient());
	}
	
	@AfterClass
	public static void restore() {
		System.setIn(System.in);
	}
	
	@Test
	public void testConnect() {
		final String connectCommand = "connect localhost 8080\n";
		final String host = "localhost";
		final int port = 8080;
		
		System.setIn(new ByteArrayInputStream(connectCommand.getBytes()));
		
		client.run();
		
		verify(client).connect(host, port);
	}

}
