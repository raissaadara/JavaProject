package model;

public class Flower {
	private int id;
	private String name;
	private int price;
	private int stock;
	private int typeId;
	private String typeName;
	public Flower(int id, String name, int price, int stock, int typeId, String typeName) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.typeId = typeId;
		this.typeName = typeName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
}
