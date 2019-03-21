package course.project.food.analyzer.converters;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;

import course.project.food.analyzer.converters.BarcodeConverter;

public class BarcodeConverterTest {
	private static final String UPC = "009800146130";
	private static FileInputStream barcodeImageStream;
	private static BarcodeConverter barcodeConverter;

	@BeforeClass
	public static void init() throws FileNotFoundException {
		barcodeImageStream = new FileInputStream(new File("resources//barcode.png"));
		barcodeConverter = new BarcodeConverter();
	}

	@Test
	public void testConvertToUpc()
			throws NotFoundException, ChecksumException, FormatException, FileNotFoundException, IOException {
		assertEquals(UPC, barcodeConverter.convertToUpc(barcodeImageStream));
	}

}
