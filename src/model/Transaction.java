package model;

public class Transaction {

	private int id;
	private int userId;
	private String date;
	private int total;
	public Transaction(int id, int userId, String date, int total) {
		super();
		this.id = id;
		this.userId = userId;
		this.date = date;
		this.total = total;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	

	
}
