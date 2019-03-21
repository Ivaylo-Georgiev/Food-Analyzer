package course.project.food.analyzer.enums;

public enum RequiredNutrient {
	ENERGY("Energy"), PROTEINS("Protein"), CARBOHYDRATES("Carbohydrate, by difference"), FATS("Total lipid (fat)"),
	FIBERS("Fiber, total dietary");

	RequiredNutrient(String nutrient) {
		this.nutrient = nutrient;
	}

	private final String nutrient;

	public String getNutrient() {
		return nutrient;
	}
}
