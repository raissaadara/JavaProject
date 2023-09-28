package model;

public class Cart {

	private int flowerId;
	private int qty;
	public Cart(int flowerId, int qty) {
		super();
		this.flowerId = flowerId;
		this.qty = qty;
	}
	public int getFlowerId() {
		return flowerId;
	}
	public void setFlowerId(int flowerId) {
		this.flowerId = flowerId;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	

	
	

}
