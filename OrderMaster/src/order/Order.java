package order;

import item.OrderItem;

import java.util.ArrayList;

import item.CustomItem;

public class Order {
    private Node head;
    private static int lastId=1000;
    private String status;
    private int id;

    public Order() {
        this.head = null;
        this.status = "Pending";
        this.id = lastId++;
    }

    public static int getLastId() {
        return lastId;
    }

    public static void setLastId(int lastId) {
        Order.lastId = lastId;
    }


    private static class Node {
        private OrderItem orderItem;
        private int quantity;
        private Node next;

        public Node(OrderItem orderItem, int quantity) {
            this.orderItem = orderItem;
            this.quantity = quantity;
            this.next = null;
        }

        @Override
        public String toString() {
            return "{" +
                    "orderItem=" + orderItem +
                    ", quantity=" + quantity +
                    '}';
        }
    }

    public void addItem(OrderItem orderItem, int quantity) {
        Node newNode = new Node(orderItem, quantity);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public void removeItem(OrderItem orderItem) {
        if (head == null) {
            return;
        }
        if (head.orderItem.equals(orderItem)) {
            head = head.next;
        } else {
            Node current = head;
            while (current.next != null) {
                if (current.next.orderItem.equals(orderItem)) {
                    current.next = current.next.next;
                    break;
                }
                current = current.next;
            }
        }
    }

    public void modifyQuantity(OrderItem orderItem, int newQuantity) {
        if (head == null) {
            return;
        } else if (newQuantity == 0) {
        	removeItem(orderItem);
        } else {
            Node current = head;
            while (current != null) {
                if (current.orderItem.equals(orderItem)) {
                    current.quantity = newQuantity;
                    break;
                }
                current = current.next;
            }
        }
    }


    public String getItems() {
        String items="";
        Node current = head;
        while (current != null) {
            items += current.orderItem.getItemName() + " x " +  current.quantity + "\n";
            current = current.next;
        }
        return items;
    }

    public ArrayList<CustomItem> toArray() {
    	ArrayList<CustomItem> arr = new ArrayList<CustomItem>();
        Node current = head;
        while (current != null) {
        	CustomItem item = new CustomItem(current.orderItem, current.quantity);
        	arr.add(item);
        	current = current.next;
        }
        return arr;
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        Node current = head;
        while (current != null) {
            totalPrice += current.orderItem.getPrice() * current.quantity;
            current = current.next;
        }
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node current = head;

        while (current != null) {
            sb.append(current.toString());
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }

        sb.append("]");
        return sb.toString();
    }

	public int getId() {
		return id;
	}
}