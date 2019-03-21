package course.project.food.analyzer.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Report {

	@SerializedName("food")
	@Expose
	private Food food;

	public Report() {
	}

	public Report(Food food) {
		this.food = food;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	@Override
	public String toString() {
		return food.toString();
	}

}
