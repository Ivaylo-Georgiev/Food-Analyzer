package course.project.food.analyzer.converters;

import com.google.gson.Gson;

import course.project.food.analyzer.product.Product;
import course.project.food.analyzer.report.FoodReport;

public class JsonConverter {

	public String toProductString(String jsonProduct) {
		Gson gson = new Gson();
		Product product = gson.fromJson(jsonProduct, Product.class);
		
		return product.toString();
	}
	
	public String toReportString(String jsonReport) {
		Gson gson = new Gson();
		FoodReport report = gson.fromJson(jsonReport, FoodReport.class);

		return report.toString();
	}
	
}
