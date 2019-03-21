package course.project.food.analyzer.enums;

public enum Banner {
	PRODUCT_BANNER(
			"\n-------------------------------------\nPRODUCT DETAILS\n-------------------------------------\n\n"),
	REPORT_BANNER("\n-------------------------------------\nFOOD REPORT\n-------------------------------------\n\n"),
	FOOTER("\n-------------------------------------\n");

	Banner(String banner) {
		this.banner = banner;
	}

	private final String banner;

	public String getBanner() {
		return banner;
	}
}
