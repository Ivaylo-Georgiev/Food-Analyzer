package course.project.food.analyzer.mocks;

public enum ReportMock {
	PRODUCT_ERROR_MOCK(
			"\n-------------------------------------\nPRODUCT DETAILS\n-------------------------------------\n\nThe requested information is not provided by the data base. \n\n-------------------------------------\n"),
	REPORT_ERROR_MOCK(
			"\n-------------------------------------\nFOOD REPORT\n-------------------------------------\n\nThe requested information is not provided by the data base. \n\n-------------------------------------\n"),
	PRODUCT_MOCK(
			"\n-------------------------------------\nPRODUCT DETAILS\n-------------------------------------\n\nName: \nRAFFAELLO, ALMOND COCONUT TREAT\nUPC: 009800146130\nUnique Number in Data Base (NDBNO): \n45142036\nManufacturer: \nFerrero U.S.A., Incorporated\n\n\n-------------------------------------\n"),
	REPORT_MOCK(
			"\n-------------------------------------\nFOOD REPORT\n-------------------------------------\n\nName: \nRAFFAELLO, ALMOND COCONUT TREAT, UPC: 009800146130\nUnique Number in Data Base (NDBNO): \n45142036\n\nIngredients: \nVEGETABLE OILS (PALM AND SHEANUT). DRY COCONUT, SUGAR, ALMONDS, SKIM MILK POWDER, WHEY POWDER (MILK), WHEAT FLOUR, NATURAL AND ARTIFICIAL FLAVORS, LECITHIN AS EMULSIFIER (SOY), SALT, SODIUM BICARBONATE AS LEAVENING AGENT.\n\nNutrients: Energy: 633kcal\nProtein: 6.67g\nTotal lipid (fat): 50.00g\nCarbohydrate, by difference: 40.00g\nFiber, total dietary: 3.3g\n\n-------------------------------------\n");

	ReportMock(String mock) {
		this.mock = mock;
	}

	private final String mock;

	public String getMock() {
		return mock;
	}

}
