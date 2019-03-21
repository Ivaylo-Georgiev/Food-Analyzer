package course.project.food.analyzer.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import course.project.food.analyzer.enums.Delimiter;

public class Ingredient {

	@SerializedName("desc")
	@Expose
	private String desc;

	public Ingredient() {
	}

	public Ingredient(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		StringBuilder ingBuilder = new StringBuilder();
		ingBuilder.append(desc);
		ingBuilder.append(Delimiter.NEW_LINE.getDelimiter());

		return ingBuilder.toString();
	}

}
