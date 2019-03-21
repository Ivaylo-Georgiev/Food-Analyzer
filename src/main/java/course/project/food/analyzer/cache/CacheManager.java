package course.project.food.analyzer.cache;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import course.project.food.analyzer.enums.Banner;
import course.project.food.analyzer.enums.Delimiter;
import course.project.food.analyzer.enums.messages.ErrorMessage;

public class CacheManager {

	private static final String CACHE_ROOT_FOLDER = "resources\\cache\\";
	private static final String GET_FOOD = "get-food";
	private static final String UPC_PREFIX = "UPC: ";

	public void cache(String requestDetails, String content) throws UnsupportedEncodingException, IOException {

		Path path = Paths.get(CACHE_ROOT_FOLDER + requestDetails + Delimiter.TXT_EXTENSION.getDelimiter());
		Files.write(path, content.getBytes("utf-8"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

	}

	public String getCachedResponse(String requestDetails) throws IOException {

		Path path = Paths.get(CACHE_ROOT_FOLDER + requestDetails + Delimiter.TXT_EXTENSION.getDelimiter());
		List<String> content = Files.readAllLines(path);

		return String.join(Delimiter.NEW_LINE.getDelimiter(), content);

	}

	public String getByBarcode(String upc) throws IOException {

		try (Stream<Path> paths = Files.walk(Paths.get(CACHE_ROOT_FOLDER + GET_FOOD));) {
			List<Path> cachedFoodDetails = paths.filter(Files::isRegularFile).collect(Collectors.toList());
			for (Path path : cachedFoodDetails) {
				List<String> content = Files.readAllLines(path);
				for (int i = 0; i < content.size(); ++i) {
					if (content.get(i).equals(UPC_PREFIX + upc)) {
						StringBuilder response = new StringBuilder();
						response.append(Banner.PRODUCT_BANNER.getBanner());

						// get 2 lines above upc and 4 beneath to acquire report
						final int beginOffset = 2;
						final int endOffset = 4;
						for (int j = i - beginOffset; j <= i + endOffset; ++j) {
							response.append(content.get(j));
							response.append(Delimiter.NEW_LINE.getDelimiter());
						}
						response.append(Delimiter.NEW_LINE.getDelimiter());
						response.append(Banner.FOOTER.getBanner());

						return response.toString();
					}
				}
			}
		}
		return ErrorMessage.NOT_CACHED.getMessage();
	}

	public boolean checkForHit(String requestDetails) throws IOException {

		List<Path> cachedFiles = getCachedFiles();

		for (Path path : cachedFiles) {
			if (path.endsWith(requestDetails + Delimiter.TXT_EXTENSION.getDelimiter())) {
				return true;
			}
		}

		return false;

	}

	public List<Path> getCachedFiles() throws IOException {

		try (Stream<Path> paths = Files.walk(Paths.get(CACHE_ROOT_FOLDER));) {
			return paths.filter(Files::isRegularFile).collect(Collectors.toList());
		}

	}
}
