package course.project.food.analyzer.converters;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import course.project.food.analyzer.mocks.JsonMock;
import course.project.food.analyzer.mocks.ReportMock;

public class JsonConverterTest {

	private static final String JSON_PRODUCT = JsonMock.PRODUCT_MOCK.getMock();
	private static final String JSON_REPORT = JsonMock.REPORT_MOCK.getMock();
	private static final String JSON_ERROR = JsonMock.ERROR_MOCK.getMock();

	private static JsonConverter converter;

	@BeforeClass
	public static void init() {
		converter = new JsonConverter();
	}

	@Test
	public void testToProductstring() {
		assertEquals(ReportMock.PRODUCT_MOCK.getMock(), converter.toProductString(JSON_PRODUCT));
	}

	@Test
	public void testToProductStringInvalid() {
		assertEquals(ReportMock.PRODUCT_ERROR_MOCK.getMock(), converter.toProductString(JSON_ERROR));
	}

	@Test
	public void testToReportString() {
		assertEquals(ReportMock.REPORT_MOCK.getMock(), converter.toReportString(JSON_REPORT));
	}

	@Test
	public void testToReportStringInvalid() {
		assertEquals(ReportMock.REPORT_ERROR_MOCK.getMock(), converter.toReportString(JSON_ERROR));
	}

}
