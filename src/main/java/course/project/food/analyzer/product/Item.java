package course.project.food.analyzer.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import course.project.food.analyzer.enums.Delimiter;
import course.project.food.analyzer.enums.ProductCharacteristic;

public class Item {

	@SerializedName("name")
	@Expose
	private String name;

	@SerializedName("ndbno")
	@Expose
	private String ndbno;

	@SerializedName("manu")
	@Expose
	private String manu;

	public Item() {
	}

	public Item(Integer offset, String group, String name, String ndbno, String ds, String manu) {
		this.name = name;
		this.ndbno = ndbno;
		this.manu = manu;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNdbno() {
		return ndbno;
	}

	public void setNdbno(String ndbno) {
		this.ndbno = ndbno;
	}

	public String getManu() {
		return manu;
	}

	public void setManu(String manu) {
		this.manu = manu;
	}

	@Override
	public String toString() {

		StringBuilder productDetails = new StringBuilder();
		productDetails.append(ProductCharacteristic.NAME.getProductCharacteristic());
		productDetails.append(Delimiter.NEW_LINE.getDelimiter());

		if (name.indexOf(", UPC:") == -1) {
			productDetails.append(name);
		} else {
			productDetails.append(name.substring(0, name.indexOf(", UPC")));
			productDetails.append(Delimiter.NEW_LINE.getDelimiter());
			productDetails.append(name.substring(name.indexOf("UPC"), name.length()));
		}

		productDetails.append(Delimiter.NEW_LINE.getDelimiter());
		productDetails.append(ProductCharacteristic.NDBNO.getProductCharacteristic());
		productDetails.append(Delimiter.NEW_LINE.getDelimiter());
		productDetails.append(ndbno);
		productDetails.append(Delimiter.NEW_LINE.getDelimiter());
		productDetails.append(ProductCharacteristic.MANUFACTURER.getProductCharacteristic());
		productDetails.append(Delimiter.NEW_LINE.getDelimiter());
		productDetails.append(manu);
		productDetails.append(Delimiter.NEW_LINE.getDelimiter());
		productDetails.append(Delimiter.NEW_LINE.getDelimiter());

		return productDetails.toString();
	}

}
