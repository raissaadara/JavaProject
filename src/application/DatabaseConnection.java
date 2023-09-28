package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;
import model.Cart;
import model.Flower;
import model.FlowerType;
import model.Transaction;
import model.TransactionDetail;

public class DatabaseConnection {

    static private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static private final String DB_URL = "jdbc:mysql://localhost/flowger_store";
    static private final String USER = "root";
    static private final String PASS = "";
    
    private static DatabaseConnection instance;
    static private Connection conn = null;
    static private Statement stmt;
    static private ResultSet rs;
	
	private DatabaseConnection() {
        try {
        	// register driver yang akan dipakai
			Class.forName(JDBC_DRIVER);
	        // buat koneksi ke database
	        conn = DriverManager.getConnection(DB_URL, USER, PASS);
	        
	        stmt = conn.createStatement();
	        String sql = "SELECT * FROM user";
	        rs = stmt.executeQuery(sql);
	        while(rs.next()){
	            //System.out.println("email: " + rs.getString("UserEmail"));
	            //System.out.println("password: " + rs.getString("UserPassword"));
	        }
		} catch (Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Database connection failed");
			alert.show();
		}

	}
	
    public Connection getConnection() {
        return conn;
    }
    
    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }

        return instance;
    }
    
    public static Boolean validType(String name) {
        String sql = "SELECT * FROM flower_type WHERE typeName='"+name+"'";

        try {
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
            	return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return true;
    }
    
    public static void getAllCarts(TableView<Cart> tableView, int userId) {
        try {
        	tableView.getItems().clear();
        	
	        String sql = "SELECT * FROM `cart` as c LEFT JOIN flower as f ON c.flowerID = f.flowerID WHERE c.userID = " + userId;
	        rs = stmt.executeQuery(sql);
	        while(rs.next()){
	        	tableView.getItems().add(new Cart(
		        			rs.getInt("flowerID"),
		        			rs.getInt("quantity")
	        			));
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void getAllFlowers(TableView<Flower> tableView) {
        try {
        	tableView.getItems().clear();
        	
	        String sql = "SELECT * FROM `flower` as f LEFT JOIN flower_type as ft ON f.typeID = ft.typeID";
	        rs = stmt.executeQuery(sql);
	        while(rs.next()){
	        	tableView.getItems().add(new Flower(
		        			rs.getInt("flowerID"),
		        			rs.getString("flowerName"),
		        			rs.getInt("flowerPrice"),
		        			rs.getInt("flowerStock"),
		        			rs.getInt("typeId"),
		        			rs.getString("typeName")
	        			));
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void getAllType(ComboBox<FlowerType> comboBox) {
        try {
            ObservableList<FlowerType> data = FXCollections.observableArrayList();
        	
	        String sql = "SELECT * FROM `flower_type`";
	        rs = stmt.executeQuery(sql);
	        while(rs.next()){
	        	data.add(new FlowerType(
	        			rs.getInt("typeID"),
	        			rs.getString("typeName")
        			));
	        }
	        
	        comboBox.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void getAllTypes(TableView<FlowerType> tableView) {
        try {
        	tableView.getItems().clear();
        	
	        String sql = "SELECT * FROM `flower_type`";
	        rs = stmt.executeQuery(sql);
	        while(rs.next()){
	        	tableView.getItems().add(new FlowerType(
		        			rs.getInt("typeID"),
		        			rs.getString("typeName")
	        			));
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void getAllTransaction(TableView<Transaction> tableView) {
        try {
        	tableView.getItems().clear();
        	
	        String sql = "SELECT t.transactionID, t.userID, t.transactionDate, SUM(f.flowerPrice) AS total FROM `transaction` t LEFT JOIN `transaction_detail` td ON t.transactionID = td.transactionID JOIN flower f ON td.flowerID = f.flowerID GROUP BY t.transactionID";
	        rs = stmt.executeQuery(sql);
	        while(rs.next()){
	        	tableView.getItems().add(new Transaction(
		        			rs.getInt("transactionID"),
		        			rs.getInt("userId"),
		        			rs.getString("transactionDate"),
		        			rs.getInt("total")
	        			));
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void getAllDetailTransaction(TableView<TransactionDetail> tableView, int transactionId) {
        try {
        	tableView.getItems().clear();
        	
	        String sql = "SELECT * FROM `transaction_detail` as td JOIN `flower` as f ON td.flowerID = f.flowerID JOIN flower_type ft ON f.typeID = ft.typeID WHERE td.transactionID = %d";
	        sql = String.format(sql, transactionId);
	        rs = stmt.executeQuery(sql);
	        while(rs.next()){
	        	tableView.getItems().add(new TransactionDetail(
		        			rs.getInt("flowerID"),
		        			rs.getString("flowerName"),
		        			rs.getInt("flowerPrice"),
		        			rs.getString("typeName"),
		        			rs.getInt("quantity")
	        			));
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
	public static int getQuantity(int id) {
        try {
	        String sql = "SELECT * FROM `flower` WHERE flowerID = %d";
	        sql = String.format(sql, id);
	        rs = stmt.executeQuery(sql);
	        if(rs.next()){
	        	return rs.getInt("flowerStock");
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return -1;
	}
    
    public static Boolean checkoutCart(int userId) {
        int transactionId = 1;
		String sqlCount = "SELECT * FROM transaction";

        try {
            rs = stmt.executeQuery(sqlCount);
            while (rs.next()) {
            	transactionId++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
	    try {
	    	String sql = "INSERT INTO transaction (transactionID, userID, transactionDate) VALUES (%d, %d, ";
	        sql = String.format(sql, transactionId, userId);
	        sql += "DATE_FORMAT(current_timestamp,'%d-%m-%Y'))";
	        int count = stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
	        if(count > 0) {
   		        String sql2 = "INSERT INTO transaction_detail (transactionID, flowerID, quantity) SELECT '%d',flowerID, quantity FROM cart";
		        sql2 = String.format(sql2, transactionId);
		        int count2 = stmt.executeUpdate(sql2, transactionId);
		        if(count2 > 0) {
		        	
	   		        String sql3 = "UPDATE flower join cart ON flower.flowerID=cart.flowerID SET flower.flowerStock=flower.flowerStock-cart.quantity WHERE cart.userID=%d";
			        sql3 = String.format(sql3, userId);
			        int count3 = stmt.executeUpdate(sql3);
			        if(count3 > 0) {
			        	String sql4 = "DELETE FROM cart WHERE userID = %d";
				        sql4 = String.format(sql4, userId);
				        int count4 = stmt.executeUpdate(sql4);
				        if(count4 > 0) return true;
				        return false;
			        }
			        return false;
		        }
		        return false;
	        }
	        return false;
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
    }
    
    public static Boolean addCart(int userId, int flowerId, int cartQty) {
	    try {  
	        String sql = "INSERT INTO cart (userID, flowerID, quantity) VALUE('%d','%d','%d')";
	        sql = String.format(sql, userId, flowerId, cartQty);
	        int count = stmt.executeUpdate(sql);
	        if(count > 0) return true;
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
    }
    
    public static Boolean insertFlower(String name, int typeId, int stock, int price) {
        int lastId = 1;
		String sqlCount = "SELECT * FROM flower";

        try {
            rs = stmt.executeQuery(sqlCount);
            while (rs.next()) {
            	lastId++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    	try {  
	        String sql = "INSERT INTO flower (flowerID, typeID, flowerName, flowerPrice, flowerStock) VALUE('%d', '%d', '%s', '%d', '%d')";
	        sql = String.format(sql, lastId, typeId, name, price, stock);
	        int count = stmt.executeUpdate(sql);
	        if(count > 0) return true;
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
    }
    
	public static Boolean updateFlower(int id, String name, int typeId, int stock, int price) {
        try {
        	String sql = "UPDATE flower SET flowerName = '%s',  typeID = %d, flowerStock = %d, flowerPrice = %d  WHERE flowerID = %d";
	        sql = String.format(sql, name, typeId, stock, price, id);
	        int count = stmt.executeUpdate(sql);
	        if(count > 0) return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
	}
	
	public static Boolean deleteFlower(int id) {
        try {
        	String sql = "DELETE FROM flower WHERE flowerID = %d";
	        sql = String.format(sql, id);
	        int count = stmt.executeUpdate(sql);
	        if(count > 0) return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
	}
    
    public static Boolean insertType(String name) {
        int lastId = 1;
		String sqlCount = "SELECT * FROM flower_type";

        try {
            rs = stmt.executeQuery(sqlCount);
            while (rs.next()) {
            	lastId++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
	    try {  
	        String sql = "INSERT INTO flower_type (typeID, typeName) VALUE('%d', '%s')";
	        sql = String.format(sql, lastId, name);
	        int count = stmt.executeUpdate(sql);
	        if(count > 0) return true;
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
    }
    
	public static Boolean updateType(int id, String name) {
        try {
        	String sql = "UPDATE flower_type set typeName = '%s' WHERE typeID = %d";
	        sql = String.format(sql, name, id);
	        int count = stmt.executeUpdate(sql);
	        if(count > 0) return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
	}
	
	public static Boolean deleteType(int id) {
        try {
        	String sql = "DELETE FROM flower_type WHERE typeID = %d";
	        sql = String.format(sql, id);
	        int count = stmt.executeUpdate(sql);
	        if(count > 0) return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
	}
    
	public static Boolean registerUser(String fullName, String email, String password, String gender, String role) {
        int lastId = 1;
		String sqlCount = "SELECT * FROM user";

        try {
            rs = stmt.executeQuery(sqlCount);
            while (rs.next()) {
            	lastId++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		try {  
	        String sql = "INSERT INTO user (userID, userName, userEmail, userPassword, userGender, userRole) VALUE('%s', '%s', '%s', '%s', '%s', '%s')";
	        sql = String.format(sql, lastId, fullName, email, password, gender, role);
	        int count = stmt.executeUpdate(sql);
	        if(count > 0) return true;
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	public static Boolean loginUser(String email, String password) {
        String sql = "SELECT * FROM user WHERE userEmail='%s' AND userPassword='%s'";
        sql = String.format(sql, email, password);
        
        try {
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
            	Main.userId = rs.getInt("userID");
            	Main.userFullName = rs.getString("userName");
	        	Main.role = rs.getString("userRole");
            	return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
	}

}
