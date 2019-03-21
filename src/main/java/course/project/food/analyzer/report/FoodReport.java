package course.project.food.analyzer.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import course.project.food.analyzer.enums.Banner;
import course.project.food.analyzer.enums.Delimiter;
import course.project.food.analyzer.enums.messages.ErrorMessage;

public class FoodReport {

	@SerializedName("report")
	@Expose
	private Report report;

	public FoodReport() {
	}

	public FoodReport(Report report) {
		this.report = report;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	@Override
	public String toString() {
		StringBuilder foodReportBuilder = new StringBuilder();
		foodReportBuilder.append(Banner.REPORT_BANNER.getBanner());
		foodReportBuilder.append((report == null)
				? ErrorMessage.INFORMATION_NOT_PROVIDED_BY_DATABASE.getMessage() + Delimiter.NEW_LINE.getDelimiter()
				: report);
		foodReportBuilder.append(Banner.FOOTER.getBanner());

		return foodReportBuilder.toString();
	}

}
