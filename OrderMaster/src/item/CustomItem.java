package item;

public class CustomItem {
	private OrderItem item;
	private int quantity;
	
	public CustomItem(OrderItem item, int quantity) {
		this.item = item;
		this.quantity = quantity;
	}

	public OrderItem getItem() {
		return item;
	}

	public int getQuantity() {
		return quantity;
	}
}
