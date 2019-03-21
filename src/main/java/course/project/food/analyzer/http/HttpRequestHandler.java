package course.project.food.analyzer.http;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import course.project.food.analyzer.converters.JsonConverter;
//import course.project.food.analyzer.enums.StatusCode;

public class HttpRequestHandler {

	private static final String API_URL = "https://api.nal.usda.gov/ndb";
	private static final String API_KEY = "iiiW3zrUbOGC2dX03cHuiF9BAwrHhTQHKFiyeKQr";

	private HttpClient httpClient;

	public HttpRequestHandler(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public String getFoodHttpRequest(String food) throws IOException, InterruptedException, ExecutionException {

		String URL = API_URL + "/search/?q=" + URLEncoder.encode(food, "UTF-8") + "&api_key=" + API_KEY;
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();

		CompletableFuture<String> futureJsonResponse = httpClient.sendAsync(request, BodyHandlers.ofString())
				.thenApply(HttpResponse::body);
		String jsonResponse = futureJsonResponse.get();

		JsonConverter converter = new JsonConverter();
		String humanReadableResponse = converter.toProductString(jsonResponse);

		return humanReadableResponse;

	}

	public String getFoodReportHttpRequest(String ndbno) throws IOException, InterruptedException, ExecutionException {

		String URL = API_URL + "/reports/?ndbno=" + ndbno + "&type=f&format=json&api_key=" + API_KEY;
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();

		CompletableFuture<String> futureJsonResponse = httpClient.sendAsync(request, BodyHandlers.ofString())
				.thenApply(HttpResponse::body);
		String jsonResponse = futureJsonResponse.get();

		JsonConverter converter = new JsonConverter();
		String humanReadableResponse = converter.toReportString(jsonResponse);

		return humanReadableResponse;

	}

}
