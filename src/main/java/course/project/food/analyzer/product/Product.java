package course.project.food.analyzer.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import course.project.food.analyzer.enums.Banner;
import course.project.food.analyzer.enums.Delimiter;
import course.project.food.analyzer.enums.messages.ErrorMessage;

public class Product {

	@SerializedName("list")
	@Expose
	private List list;

	public Product() {
	}

	public Product(List list) {
		this.list = list;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	@Override
	public String toString() {
		StringBuilder productBuilder = new StringBuilder();

		productBuilder.append(Banner.PRODUCT_BANNER.getBanner());
		productBuilder.append((list == null)
				? ErrorMessage.INFORMATION_NOT_PROVIDED_BY_DATABASE.getMessage() + Delimiter.NEW_LINE.getDelimiter()
				: list);
		productBuilder.append(Banner.FOOTER.getBanner());

		return productBuilder.toString();
	}
}
