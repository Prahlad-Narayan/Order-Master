package item;

import java.util.Objects;

public class OrderItem {
  private String itemName;
  private double price;


  public OrderItem(String itemName, double price) {
    this.itemName = itemName;
    this.price = price;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "OrderItem{" +
            "itemName='" + itemName + '\'' +
            ", price=" + price +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OrderItem orderItem = (OrderItem) o;
    return Objects.equals(itemName, orderItem.itemName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemName);
  }
}