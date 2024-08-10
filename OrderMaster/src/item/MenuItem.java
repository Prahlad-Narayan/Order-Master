package item;

public class MenuItem {
	
	public static enum Type {
		ITEM(0, "ITEM"),
		CATEGORY(1, "CATEGORY");
		
		private int typeInt;
		private String typeString;
		
		Type(int typeInt, String typeString) {
			this.typeInt = typeInt;
			this.typeString = typeString;
		}
		
		int getValue() {
			return this.typeInt;
		}
		
		public String getStringValue() {
			return this.typeString;
		}
	}
	
	private String name;
	private Type type;
	private double price;
	
	public MenuItem(String name, Type type) {
		this(name, type, 0);
	}
	
	public MenuItem(String name, Type type, double price) {
		this.name = name;
		this.type = type;
		if(type == MenuItem.Type.ITEM) {
			this.price = price;
		} else {
			this.price = 0;
		}
	}

	public String getName() {
		return this.name;
	}
	
	public Type getType() {
		return this.type;
	}
	
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean equals(MenuItem item) {
		return this.name.equals(item.name) && this.type.typeInt == item.type.typeInt;
	}
	
	@Override
    public String toString() {
        return this.name;
    }
}
