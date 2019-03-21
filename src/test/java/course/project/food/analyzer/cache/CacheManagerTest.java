package course.project.food.analyzer.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import course.project.food.analyzer.enums.messages.ErrorMessage;
import course.project.food.analyzer.mocks.ReportMock;

public class CacheManagerTest {

	private static CacheManager cacheManager;

	@BeforeClass
	public static void init() {

		cacheManager = new CacheManager();

	}

	@Test
	public void testCachedCheckForHit() throws IOException {

		final String alwaysCached = "raffaello";
		assertTrue(cacheManager.checkForHit(alwaysCached));

	}

	@Test
	public void testNotCachedCheckForHit() throws IOException {

		final String neverToBeCached = "this is not in the cache";
		assertFalse(cacheManager.checkForHit(neverToBeCached));

	}

	@Test
	public void testGetByBarcodeWithInvalidUpc() throws IOException {

		final String invalidUpc = "this is not a upc";
		assertEquals(ErrorMessage.NOT_CACHED.getMessage(), cacheManager.getByBarcode(invalidUpc));

	}

	@Test
	public void testGetByBarcodeWithValidUpc() throws IOException {

		final String upc = "009800146130";
		assertEquals(ReportMock.PRODUCT_MOCK.getMock(), cacheManager.getByBarcode(upc));

	}

}
