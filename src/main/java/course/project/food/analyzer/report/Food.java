package course.project.food.analyzer.report;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import course.project.food.analyzer.enums.Delimiter;
import course.project.food.analyzer.enums.FoodCharacteristic;
import course.project.food.analyzer.enums.RequiredNutrient;
import course.project.food.analyzer.enums.ProductCharacteristic;
import course.project.food.analyzer.enums.messages.ErrorMessage;

public class Food {

	@SerializedName("ndbno")
	@Expose
	private String ndbno;

	@SerializedName("name")
	@Expose
	private String name;

	@SerializedName("ing")
	@Expose
	private Ingredient ing;

	@SerializedName("nutrients")
	@Expose
	private List<Nutrient> nutrients;

	public Food() {
	}

	public Food(String ndbno, String name, Ingredient ing, List<Nutrient> nutrients) {
		this.ndbno = ndbno;
		this.name = name;
		this.ing = ing;
		this.nutrients = nutrients;
	}

	public String getNdbno() {
		return ndbno;
	}

	public void setNdbno(String ndbno) {
		this.ndbno = ndbno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Ingredient getIng() {
		return ing;
	}

	public void setIng(Ingredient ing) {
		this.ing = ing;
	}

	public List<Nutrient> getNutrients() {
		return nutrients;
	}

	public void setNutrients(List<Nutrient> nutrients) {
		this.nutrients = nutrients;
	}

	@Override
	public String toString() {

		StringBuilder foodBuilder = new StringBuilder();
		foodBuilder.append(ProductCharacteristic.NAME.getProductCharacteristic());
		foodBuilder.append(Delimiter.NEW_LINE.getDelimiter());
		foodBuilder.append(name);
		foodBuilder.append(Delimiter.NEW_LINE.getDelimiter());
		foodBuilder.append(ProductCharacteristic.NDBNO.getProductCharacteristic());
		foodBuilder.append(Delimiter.NEW_LINE.getDelimiter());
		foodBuilder.append(ndbno);
		foodBuilder.append(Delimiter.NEW_LINE.getDelimiter());
		foodBuilder.append(Delimiter.NEW_LINE.getDelimiter());
		foodBuilder.append(FoodCharacteristic.INGREDIENTS.getFoodCharacteristic());
		foodBuilder.append(Delimiter.NEW_LINE.getDelimiter());
		foodBuilder.append((ing == null) ? ErrorMessage.MISSING_INFORMATION.getMessage() : ing);
		foodBuilder.append(Delimiter.NEW_LINE.getDelimiter());
		foodBuilder.append(FoodCharacteristic.NUTRIENTS.getFoodCharacteristic());

		for (Nutrient nutrient : nutrients) {
			final String energyUnit = "kcal";
			if ((nutrient.getName().equals(RequiredNutrient.ENERGY.getNutrient())
					&& nutrient.getUnit().equals(energyUnit))
					|| nutrient.getName().equals(RequiredNutrient.PROTEINS.getNutrient())
					|| nutrient.getName().equals(RequiredNutrient.FATS.getNutrient())
					|| nutrient.getName().equals(RequiredNutrient.CARBOHYDRATES.getNutrient())
					|| nutrient.getName().equals(RequiredNutrient.FIBERS.getNutrient())) {

				foodBuilder.append(nutrient);

			}
		}

		return foodBuilder.toString();
	}

}
