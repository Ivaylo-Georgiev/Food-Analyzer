package course.project.food.analyzer.enums;

public enum FoodCharacteristic {
	INGREDIENTS("Ingredients: "), NUTRIENTS("Nutrients: ");

	FoodCharacteristic(String characteristic) {
		this.characteristic = characteristic;
	}

	private final String characteristic;

	public String getFoodCharacteristic() {
		return characteristic;
	}
}
