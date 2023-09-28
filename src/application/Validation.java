package application;

import javafx.scene.control.Alert.AlertType;

public class Validation {

	public Validation() {
	}

	public static Boolean isAlphaNumeric(String str) {
		Boolean isAlpha = false;
		Boolean isNumeric = false;
		
		for (int i = 0, len = str.length(); i < len; i++) {
			char chr = str.charAt(i);
			if (chr > 47 && chr < 58) // numeric (0-9)
				isNumeric = true;
			if (chr > 64 && chr < 91) // upper alpha (A-Z)
				isAlpha = true;
			if (chr > 96 && chr < 123) // lower alpha (a-z))
				isAlpha = true;
		}
		
		if(!isAlpha || !isNumeric) return false;
		
		return true;
	};
	
	public static boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}
	
	public static Boolean validateRegister(String fullName, String email, String pass, String gender, String role) {
		
		if(fullName.length() < 5 || fullName.length() > 30) {
			AlertWindow.show(AlertType.ERROR, "Name must be between 5 - 30 characters!");
			return false;
		}
		
		if(email.isEmpty()
			|| email.charAt(0) == '@'
			|| !email.contains("@")
			|| !email.endsWith(".com")
			) {
			AlertWindow.show(AlertType.ERROR, "Invalid email format!");
			return false;
		}
		
		if(pass.length()<5 || pass.length()>25) {
			AlertWindow.show(AlertType.ERROR, "Password must be between 5 - 25 characters!");
			return false;
		}
		
		if(gender == "") {
			AlertWindow.show(AlertType.ERROR, "Gender must be choosed!");
			return false;
		}
		
		if(role == null) {
			AlertWindow.show(AlertType.ERROR, "Role must be choosed!");
			return false;
		}
		
		return true;
	}
	
	public static Boolean validateType(String name) {
		if(name.length() < 10 || name.length() > 20) {
			AlertWindow.show(AlertType.ERROR, "Type Name must be between 10 - 20 characters!");
			return false;
		}
		
		if(!DatabaseConnection.validType(name)) {
			AlertWindow.show(AlertType.ERROR, "Type already exists in database!");
			return false;
		}
		
		return true;
	}
	
	public static Boolean validateFlower(String name, String price, String type, int stock) {
		if(name.length() < 15) {
			AlertWindow.show(AlertType.ERROR, "Flower name length minimal 15 characters!");
			return false;
		}
		
		if(!isNumeric(price)) {
			AlertWindow.show(AlertType.ERROR, "Price must be numeric!");
			return false;
		}
		
		if(Integer.valueOf(price) <= 0) {
			AlertWindow.show(AlertType.ERROR, "Price must more than 0!");
			return false;
		}
		
		if(type.isEmpty()) {
			AlertWindow.show(AlertType.ERROR, "Type must be choosed!");
			return false;
		}
		
		if(stock == 0) {
			AlertWindow.show(AlertType.ERROR, "Stock must more than 0!");
			return false;
		}
		
		return true;
	}

	public static boolean checkQuantity(int id, int qty) {

		if(DatabaseConnection.getQuantity(id) < qty) {
			AlertWindow.show(AlertType.ERROR, "Quantity can't exceed the available stock");
			return false;
		}
		
		return true;
	}
	
}
