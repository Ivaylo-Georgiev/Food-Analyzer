package course.project.food.analyzer.validators;

import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class InvalidCommandValidatorTest {
	private CommandValidator commandValidator;
	private String command;

	@Parameters
	public static Collection<Object[]> patterns() {
		return Arrays.asList(new Object[][] { { "" }, { "get food raffaello" }, { "get-food" }, { "get-food-report" },
				{ "get food report 123" }, { "get-food-by-barcode upc=123" }, { "get-food-by-barcode img=path" },
				{ "get-food-by-barcode --upc=0000000000000" }, { "get-food-by-barcode --upc=000" },
				{ "get-food-by-barcode 123" }, { "get-food-by-barcode --upc=1230000000000000000000000 --img=path" },
				{ "get-food-by-barcode --upc=123 --img=path" }, { "get-food-by-barcode --imgpath --upc123" }, 
				{ "get-food-by-barcode --img=C:\\Users\\resources\\barcode.png --upc=123" },});
	}

	public InvalidCommandValidatorTest(String command) {
		this.command = command;
		commandValidator = new CommandValidator();
	}

	@Test
	public void testCommandValidation() {
		assertFalse(commandValidator.validateCommand(command));
	}
}
