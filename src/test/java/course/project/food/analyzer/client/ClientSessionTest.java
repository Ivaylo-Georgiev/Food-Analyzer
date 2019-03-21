package course.project.food.analyzer.client;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ClientSessionTest {

	private static ClientSession session;

	@BeforeClass
	public static void init() throws IOException {
		Socket socket = mock(Socket.class);
		InputStream streamReader = mock(InputStream.class);

		doReturn(streamReader).when(socket).getInputStream();

		session = spy(new ClientSession(socket));
	}

	@AfterClass
	public static void restore() {
		System.setOut(System.out);
	}

	@Test // (expected = IOException.class)
	public void testRun() throws IOException {
		session.run();
		verify(session).print(any());
	}

	@Test
	public void testPrint() throws IOException {
		final String line = "mock";

		BufferedReader reader = mock(BufferedReader.class);
		when(reader.readLine()).thenReturn(line, (String) null);

		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(byteOut));

		session.print(reader);

		byteOut.flush();
		String allWrittenLines = new String(byteOut.toByteArray());
		assertTrue(allWrittenLines.contains(line));

	}

}
