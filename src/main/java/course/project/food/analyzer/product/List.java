package course.project.food.analyzer.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class List {

	@SerializedName("item")
	@Expose
	private java.util.List<Item> item = null;

	public List() {
	}

	public List(java.util.List<Item> item) {
		this.item = item;
	}

	public java.util.List<Item> getItem() {
		return item;
	}

	public void setItem(java.util.List<Item> item) {
		this.item = item;
	}

	@Override
	public String toString() {

		StringBuilder list = new StringBuilder();

		for (Item product : item) {
			list.append(product);
		}

		return list.toString();
	}

}
