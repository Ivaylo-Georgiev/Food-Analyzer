package course.project.food.analyzer.validators;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ValidCommandValidatorTest {
	private CommandValidator commandValidator;
	private String command;

	@Parameters
	public static Collection<Object[]> patterns() {
		return Arrays.asList(new Object[][] { { "get-food raffaello" }, { "get-food-report 123" },
				{ "get-food-by-barcode --upc=123456789874" }, { "get-food-by-barcode --img=\\resources\\path.jpg" },
				{ "get-food-by-barcode --upc=123456789874 --img=C:\\Users\\resources\\barcode.png" },
				{ "get-food-by-barcode --img=C:\\Users\\resources\\barcode.png --upc=111111111111" } });
	}

	public ValidCommandValidatorTest(String command) {
		this.command = command;
		commandValidator = new CommandValidator();
	}

	@Test
	public void testCommandValidation() {
		assertTrue(commandValidator.validateCommand(command));
	}

}
