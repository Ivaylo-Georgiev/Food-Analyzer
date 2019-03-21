package course.project.food.analyzer.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandValidator {

	private static final String[] COMMAND_PATTERNS = { "get-food .+", "get-food-report .+",
			"get-food-by-barcode --upc=.{12}$", "get-food-by-barcode --upc=.{12} --img=(.+.(png|jpg|gif|bmp)$)",
			"get-food-by-barcode --img=(.+.(png|jpg|gif|bmp)) --upc=.{12}$",
			"get-food-by-barcode --img=(.+.(png|jpg|gif|bmp)$)", "disconnect" };

	public boolean validateCommand(String command) {

		for (String commandPattern : COMMAND_PATTERNS) {

			Pattern pattern = Pattern.compile(commandPattern);
			Matcher matcher = pattern.matcher(command);

			if (matcher.matches()) {
				return true;
			}

		}

		return false;

	}

}
