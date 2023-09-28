package model;

public class TransactionDetail {
	private int flowerId;
	private String flowerName;
	private int flowerPrice;
	private String flowerType;
	private int flowerQuantity;
	public TransactionDetail(int flowerId, String flowerName, int flowerPrice, String flowerType, int flowerQuantity) {
		super();
		this.flowerId = flowerId;
		this.flowerName = flowerName;
		this.flowerPrice = flowerPrice;
		this.flowerType = flowerType;
		this.flowerQuantity = flowerQuantity;
	}
	public int getFlowerId() {
		return flowerId;
	}
	public void setFlowerId(int flowerId) {
		this.flowerId = flowerId;
	}
	public String getFlowerName() {
		return flowerName;
	}
	public void setFlowerName(String flowerName) {
		this.flowerName = flowerName;
	}
	public int getFlowerPrice() {
		return flowerPrice;
	}
	public void setFlowerPrice(int flowerPrice) {
		this.flowerPrice = flowerPrice;
	}
	public String getFlowerType() {
		return flowerType;
	}
	public void setFlowerType(String flowerType) {
		this.flowerType = flowerType;
	}
	public int getFlowerQuantity() {
		return flowerQuantity;
	}
	public void setFlowerQuantity(int flowerQuantity) {
		this.flowerQuantity = flowerQuantity;
	}

	
	
	
	
}
