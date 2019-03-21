package course.project.food.analyzer.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import course.project.food.analyzer.enums.Delimiter;

public class Nutrient {

	@SerializedName("name")
	@Expose
	private String name;

	@SerializedName("unit")
	@Expose
	private String unit;

	@SerializedName("value")
	@Expose
	private String value;

	public Nutrient() {
	}

	public Nutrient(String name, String unit, String value) {
		this.name = name;
		this.unit = unit;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuilder nutrientBuilder = new StringBuilder();
		nutrientBuilder.append(name);
		nutrientBuilder.append(Delimiter.COLON.getDelimiter());
		nutrientBuilder.append(value);
		nutrientBuilder.append(unit);
		nutrientBuilder.append(Delimiter.NEW_LINE.getDelimiter());

		return nutrientBuilder.toString();
	}

}
