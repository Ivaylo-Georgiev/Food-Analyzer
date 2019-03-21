package course.project.food.analyzer.enums;

public enum ProductCharacteristic {
	NAME("Name: "),
	NDBNO("Unique Number in Data Base (NDBNO): "),
	MANUFACTURER("Manufacturer: ");

	ProductCharacteristic(String characteristic) {
		this.characteristic = characteristic;
	}

	private final String characteristic;

	public String getProductCharacteristic() {
		return characteristic;
	}
}
