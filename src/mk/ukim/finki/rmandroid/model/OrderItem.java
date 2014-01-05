package mk.ukim.finki.rmandroid.model;

import java.io.Serializable;

public class OrderItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int quantity;
	private float price;
	private String title;

	public OrderItem() {
		super();
	}

	public OrderItem(int quantity, float price, String title) {
		super();
		this.quantity = quantity;
		this.price = price;
		this.title = title;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public float getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getTitle() {
		return title;
	}
}