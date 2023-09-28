package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import jfxtras.labs.scene.control.window.CloseIcon;
import jfxtras.labs.scene.control.window.Window;
import model.Cart;
import model.Flower;
import model.FlowerType;
import model.Transaction;
import model.TransactionDetail;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Main extends Application {
	
	Stage currentStage;
	Scene sceneLogin;
	Scene sceneRegister;
	Scene sceneMain;
	Window buyFlowerWindow;
	Window historyWindow;
	Window flowerWindow;
	Window typeWindow;
	BorderPane rootLogin;
	BorderPane rootRegister;
	BorderPane rootMain;
	GridPane loginForm;
	GridPane registerForm;
	GridPane mainForm;
	VBox BuyFlowerForm;
	VBox historyForm;
	VBox flowerForm;
	HBox typeForm;
    MenuBar mb;
    Menu menuUser;
    Menu menuStaff;
    Menu menuManage;
    Menu menuTransaction;
    ComboBox<FlowerType> type;
    TextField idInput;
	
	static int userId = 0;
	static String userFullName = null;
	static String role = "user";
	
    TableView<Cart> CartView = new TableView<>();
    TableView<Flower> marketView = new TableView<>();
	TableView<Flower> flowerView = new TableView<>();
	TableView<FlowerType> typeView = new TableView<>();
    TableView<Transaction> historyView = new TableView<>();
    TableView<TransactionDetail> detailView = new TableView<>();
	
	public void init() {
		rootLogin = new BorderPane();
		sceneLogin = new Scene(rootLogin,400,210);
		
		rootRegister = new BorderPane();
		sceneRegister = new Scene(rootRegister,400,390);
		
		rootMain = new BorderPane();
		sceneMain = new Scene(rootMain,1024,650);
		
		loginForm = new GridPane();
		registerForm = new GridPane();
		mainForm = new GridPane();
		BuyFlowerForm = new VBox();
		historyForm = new VBox();
		flowerForm = new VBox();
		typeForm = new HBox();
		
		buyFlowerWindow = new Window();
		historyWindow = new Window();;
		flowerWindow = new Window();
		typeWindow = new Window();
		
	    mb = new MenuBar();
		menuUser = new Menu("User");
		menuStaff = new Menu("Staff");
		menuTransaction = new Menu("Transaction");
		menuManage = new Menu("Manage");
	}
	
	public void initLoginForm() {
		loginForm.setAlignment(Pos.TOP_CENTER);
		loginForm.setHgap(20);
		loginForm.setVgap(10);
		
		Label lblTitle = new Label("Login Form");
	    GridPane.setMargin(lblTitle, new Insets(0, 0, 20, 0));
	    GridPane.setHalignment(lblTitle, HPos.CENTER);
	    loginForm.add(lblTitle, 0, 1, 2, 1);
	    
	    Label lblEmail = new Label("Email");
        loginForm.add(lblEmail, 0, 2);
	    
        TextField emailInput = new TextField();
        emailInput.setPromptText("Input Email");
        emailInput.setPrefWidth(225);
        loginForm.add(emailInput, 1, 2);
        
	    Label lblPassword = new Label("Password");
        loginForm.add(lblPassword, 0, 3);
        
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Input Password");
        loginForm.add(passwordInput, 1, 3);
        
        Button btnLogin = new Button("Login");
         GridPane.setHalignment(btnLogin, HPos.CENTER);
        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				if(DatabaseConnection.loginUser(emailInput.getText(), passwordInput.getText())) {
					emailInput.clear();
					passwordInput.clear();
					
					if(role.equals("Staff")) {
						mb.getMenus().remove(0);
						mb.getMenus().remove(0);
						mb.getMenus().add(menuStaff);
						mb.getMenus().add(menuManage);
					} else {
						mb.getMenus().remove(0);
						mb.getMenus().remove(0);
						mb.getMenus().add(menuUser);
						mb.getMenus().add(menuTransaction);
					}
					
					AlertWindow.show(AlertType.INFORMATION, "Success!");
					
					currentStage.setScene(sceneMain);
				} else {
					AlertWindow.show(AlertType.ERROR, "User Does Not Exist!");
				}
			}
		});
        
        Button btnRegister = new Button("Register");
        GridPane.setHalignment(btnRegister, HPos.CENTER);
        btnRegister.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				currentStage.setMaximized(false);
			    currentStage.setScene(sceneRegister);
			}
		});
        
        HBox hBox = new HBox();
        hBox.getChildren().add(btnLogin);
        hBox.getChildren().add(btnRegister);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.setMargin(btnLogin, new Insets(20, 20, 0, 0));
        loginForm.add(hBox, 0, 4, 2, 1);
	}
	
	public void initRegisterForm() {
		registerForm.setAlignment(Pos.TOP_CENTER);
		registerForm.setHgap(20);
		registerForm.setVgap(10);
		
	    Label lblTitle = new Label("Register Form");
	    GridPane.setMargin(lblTitle, new Insets(0, 0, 60, 0));
	    GridPane.setHalignment(lblTitle, HPos.CENTER);
	    registerForm.add(lblTitle, 0, 1, 3, 1);
	    
	    Label lblFullName = new Label("Name");
	    registerForm.add(lblFullName, 0, 2);
	    
        TextField fullnameInput = new TextField();
        fullnameInput.setPromptText("Input Name");
        registerForm.add(fullnameInput, 1, 2, 2, 1);
	    
	    Label lblEmail = new Label("Email");
	    registerForm.add(lblEmail, 0, 3);
	    
        TextField emailInput = new TextField();
        emailInput.setPromptText("Input Email");
        registerForm.add(emailInput, 1, 3, 2, 1);
        
	    Label lblPassword = new Label("Password");
	    registerForm.add(lblPassword, 0, 4);
        
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Input Password");
        registerForm.add(passwordInput, 1, 4, 2, 1);
        
	    Label lblGender = new Label("Gender");
	    registerForm.add(lblGender, 0, 5);
	    
	    RadioButton maleOption = new RadioButton("");
	    maleOption.setUserData("Male");
	    Label lblMale = new Label("Male");
	    registerForm.add(lblMale, 1, 5);

	    RadioButton femaleOption = new RadioButton("");
	    femaleOption.setUserData("Female");
	    Label lblFemale = new Label("Female");
	    registerForm.add(lblFemale, 1, 6);
	    
	    ToggleGroup gender = new ToggleGroup();
	    maleOption.setToggleGroup(gender);
	    femaleOption.setToggleGroup(gender);
        maleOption.setPrefWidth(100);
        femaleOption.setPrefWidth(100);
	    registerForm.add(maleOption, 2, 5);
	    registerForm.add(femaleOption, 2, 6);
        
	    Label lblRole = new Label("Role");
	    registerForm.add(lblRole, 0, 7);
	    
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("User", "Staff");
	    registerForm.add(comboBox, 1, 7);
	    
        Button btnRegister = new Button("Register");
        btnRegister.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(Validation.validateRegister(
						fullnameInput.getText(),
						emailInput.getText(),
						passwordInput.getText(),
						(RadioButton)gender.getSelectedToggle() != null ? ((RadioButton)gender.getSelectedToggle()).getUserData().toString() : "",
						(String)comboBox.getValue()
				)) {
					if(DatabaseConnection.registerUser(						
							fullnameInput.getText(),
							emailInput.getText(),
							passwordInput.getText(),
							(RadioButton)gender.getSelectedToggle() != null ? ((RadioButton)gender.getSelectedToggle()).getText() : "",
									(String)comboBox.getValue()
							)) {
						AlertWindow.show(AlertType.INFORMATION, "Register Succeed");
						fullnameInput.clear();
						emailInput.clear();
						passwordInput.clear();
						
					    currentStage.setScene(sceneLogin);
					} else {
						AlertWindow.show(AlertType.ERROR, "Oops.. Something went wrong. Please try again later!");
					}
				}
			}
		});
        
        Button btnLogin = new Button("back");
        GridPane.setHalignment(btnLogin, HPos.CENTER);
        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			    currentStage.setScene(sceneLogin);
			}
		});
        
        HBox hBox = new HBox();
        hBox.getChildren().add(btnRegister);
        hBox.getChildren().add(btnLogin);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.setMargin(btnRegister, new Insets(60, 20, 0, 0));
        registerForm.add(hBox, 0, 8, 3, 1);
	}
	
	public void initMainForm() {
	    BackgroundImage myBI = new BackgroundImage(
	    		new Image("file:src/application/assets/bg.jpg", true),
	    		BackgroundRepeat.NO_REPEAT,
	            BackgroundRepeat.NO_REPEAT,
	            BackgroundPosition.DEFAULT,
	            new BackgroundSize(1.0, 1.0, true, true, false, false));
	    mainForm.setBackground(new Background(myBI));
	    
	    MenuItem menuItemLogoutU = new MenuItem("Logout");
	    menuItemLogoutU.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				currentStage.setMaximized(false);
				rootLogin.setCenter(loginForm);
			    currentStage.setScene(sceneLogin);
			    rootMain.setCenter(mainForm);
			}
		});
	    menuUser.getItems().add(menuItemLogoutU);
	    
	    MenuItem menuItemLogoutS = new MenuItem("Logout");
	    menuItemLogoutS.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				currentStage.setMaximized(false);
				rootLogin.setCenter(loginForm);
			    currentStage.setScene(sceneLogin);
			    rootMain.setCenter(mainForm);
			}
		});
	    menuStaff.getItems().add(menuItemLogoutS);
	    
	    MenuItem menuItemBuy = new MenuItem("Order Flower");
	    menuItemBuy.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DatabaseConnection.getAllFlowers(marketView);
				DatabaseConnection.getAllCarts(CartView, userId);
				idInput.setPrefWidth(10);
				
				buyFlowerWindow = new Window();
				buyFlowerWindow.getRightIcons().add(new CloseIcon(buyFlowerWindow));
				buyFlowerWindow.setTitle("Order Flower");
				buyFlowerWindow.setContentPane(BuyFlowerForm);
				
				rootMain.setCenter(buyFlowerWindow);
			}
		});
	    menuTransaction.getItems().add(menuItemBuy);
	    MenuItem menuItemHistory = new MenuItem("Transaction");
	    menuItemHistory.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DatabaseConnection.getAllTransaction(historyView);
				
				historyWindow = new Window();
				historyWindow.getRightIcons().add(new CloseIcon(historyWindow));
				historyWindow.setTitle("Transaction History");
				historyWindow.setContentPane(historyForm);
				
				rootMain.setCenter(historyWindow);
			}
		});
	    menuTransaction.getItems().add(menuItemHistory);
	    
	    MenuItem menuItemFlower = new MenuItem("Manage Flower");
	    menuItemFlower.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DatabaseConnection.getAllType(type);
			    DatabaseConnection.getAllFlowers(flowerView);
			    
				flowerWindow = new Window();
				flowerWindow.getRightIcons().add(new CloseIcon(flowerWindow));
			    
				flowerWindow.setTitle("Manage Flower");
				flowerWindow.setContentPane(flowerForm);
				
			    rootMain.setCenter(flowerWindow);
			}
		});
	    menuManage.getItems().add(menuItemFlower);
	    
	    MenuItem menuItemType = new MenuItem("Manage Type");
	    menuItemType.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DatabaseConnection.getAllTypes(typeView);
				
				typeWindow = new Window();
				typeWindow.getRightIcons().add(new CloseIcon(typeWindow));
				
				typeWindow.setTitle("Manage Type");
				typeWindow.setContentPane(typeForm);
				
				rootMain.setCenter(typeWindow);
			}
		});
	    menuManage.getItems().add(menuItemType);
	    
	    mb.getMenus().add(menuUser);
	    mb.getMenus().add(menuTransaction);
	    
	    rootMain.setTop(mb);
	}
	
	public void initBuyFlowerForm() {

		
	    marketView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    
	    TableColumn<Flower, Integer> column1 = new TableColumn<>("Flower ID");
	    column1.setCellValueFactory(new PropertyValueFactory<>("id"));
	    TableColumn<Flower, String> column2 = new TableColumn<>("Flower Name");
	    column2.setCellValueFactory(new PropertyValueFactory<>("name"));
	    TableColumn<Flower, String> column3 = new TableColumn<>("Flower Type");
	    column3.setCellValueFactory(new PropertyValueFactory<>("typeName"));
	    TableColumn<Flower, String> column4 = new TableColumn<>("Flower Price");
	    column4.setCellValueFactory(new PropertyValueFactory<>("price"));
	    TableColumn<Flower, Integer> column5 = new TableColumn<>("Flower Stock");
	    column5.setCellValueFactory(new PropertyValueFactory<>("stock"));

	    marketView.getColumns().add(column1);
	    marketView.getColumns().add(column2);
	    marketView.getColumns().add(column3);
	    marketView.getColumns().add(column4);
	    marketView.getColumns().add(column5);
	    
	    BuyFlowerForm.getChildren().add(marketView);
		VBox.setMargin(marketView, new Insets(0, 20, 20, 20));
	    
	    CartView.setPrefWidth(500);
	    CartView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    
	    TableColumn<Cart, Integer> detailColumn1 = new TableColumn<>("Flower ID");
	    detailColumn1.setCellValueFactory(new PropertyValueFactory<>("flowerId"));
	    TableColumn<Cart, String> detailColumn2 = new TableColumn<>("Quantity");
	    detailColumn2.setCellValueFactory(new PropertyValueFactory<>("qty"));

	    CartView.getColumns().add(detailColumn1);
	    CartView.getColumns().add(detailColumn2);
	    
	    GridPane buyFlowerForm = new GridPane();
	    
	    Label lblId = new Label("Flower ID");
	    buyFlowerForm.add(lblId, 0, 0);
	    
        idInput = new TextField("Flower ID");
        idInput.setDisable(true);
        buyFlowerForm.add(idInput, 1, 0, 1, 1);
        
	    Label lblName = new Label("Flower Name");
	    buyFlowerForm.add(lblName, 0, 1);
	    
        TextField nameInput = new TextField("Flower Name");
        nameInput.setDisable(true);
        buyFlowerForm.add(nameInput, 1, 1, 1, 1);
	    
	    Label lblType = new Label("Flower Type");
	    buyFlowerForm.add(lblType, 0, 2);
	    
        TextField typeInput = new TextField("Flower Type");
        typeInput.setDisable(true);
        typeInput.setPrefWidth(300);
        buyFlowerForm.add(typeInput, 1, 2, 1, 1);
        
	    Label lblQty = new Label("Quantity");
	    buyFlowerForm.add(lblQty, 0, 3);
	    
	    Spinner<Integer> qtyInput = new Spinner<Integer>(1, 100, 1);
	    qtyInput.setEditable(true);
	    qtyInput.setPrefWidth(300);
	    buyFlowerForm.add(qtyInput, 1, 3, 1, 1);
	    
	    marketView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	            Flower flower = marketView.getSelectionModel().getSelectedItem();
	            idInput.setText(String.valueOf(flower.getId()));
	            nameInput.setText(flower.getName());
	            typeInput.setText(flower.getTypeName());	        }
	    });
        
        Button btnAddToCart = new Button("Add to Cart");
        btnAddToCart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(marketView.getSelectionModel().getSelectedItem() == null) {
					AlertWindow.show(AlertType.ERROR, "Please select a flower!");
					return;
				}
				
				if(!Validation.checkQuantity(marketView.getSelectionModel().getSelectedItem().getId(), qtyInput.getValue())) {
					return;
				}
				
				if(DatabaseConnection.addCart(userId, marketView.getSelectionModel().getSelectedItem().getId(), qtyInput.getValue())) {
					DatabaseConnection.getAllCarts(CartView, userId);
					qtyInput.getValueFactory().setValue(1);
				}
			}
		});
        btnAddToCart.setPrefWidth(405);
        buyFlowerForm.add(btnAddToCart, 0, 4, 2, 1);
        
        Button btnCheckout = new Button("Checkout");
        btnCheckout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(CartView.getItems().size() == 0) {
					AlertWindow.show(AlertType.ERROR, "Please select a flower!");
					return;
				}
				
				if(DatabaseConnection.checkoutCart(userId)) {
					AlertWindow.show(AlertType.INFORMATION, "Successfully Checkout!");
					qtyInput.getValueFactory().setValue(1);
					DatabaseConnection.getAllCarts(CartView, userId);
				}
				
			}
		});
        btnCheckout.setPrefWidth(405);
        buyFlowerForm.add(btnCheckout, 0, 5, 2, 1);
	    
	    HBox hbox = new HBox();
	    hbox.getChildren().add(CartView);
	    HBox.setMargin(buyFlowerForm, new Insets(40));
	    hbox.getChildren().add(buyFlowerForm);
	    VBox.setMargin(hbox, new Insets(0, 20, 20, 20));
	    BuyFlowerForm.getChildren().add(hbox);
	    
	}
	
	public void initHistoryForm() {
	    historyView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    
	    TableColumn<Transaction, Integer> column1 = new TableColumn<>("Transaction ID");
	    column1.setCellValueFactory(new PropertyValueFactory<>("id"));

	    TableColumn<Transaction, String> column2 = new TableColumn<>("User ID");
	    column2.setCellValueFactory(new PropertyValueFactory<>("userId"));
	    
	    TableColumn<Transaction, String> column3 = new TableColumn<>("Transaction Date");
	    column3.setCellValueFactory(new PropertyValueFactory<>("date"));
	    
	    TableColumn<Transaction, String> column4 = new TableColumn<>("Total Price");
	    column4.setCellValueFactory(new PropertyValueFactory<>("total"));

	    historyView.getColumns().add(column1);
	    historyView.getColumns().add(column2);
	    historyView.getColumns().add(column3);
	    historyView.getColumns().add(column4);

	    historyView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	            DatabaseConnection.getAllDetailTransaction(detailView, newSelection.getId());
	        }
	    });
	    
		VBox.setMargin(historyView, new Insets(20, 20, 20, 20));
	    historyForm.getChildren().add(historyView);
	    
	    
	    detailView.setSelectionModel(null);
	    detailView.setPrefWidth(800);
	    detailView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    
	    TableColumn<TransactionDetail, Integer> detailColumn1 = new TableColumn<>("Flower ID");
	    detailColumn1.setCellValueFactory(new PropertyValueFactory<>("flowerId"));
	    TableColumn<TransactionDetail, String> detailColumn2 = new TableColumn<>("Flower Name");
	    detailColumn2.setCellValueFactory(new PropertyValueFactory<>("flowerName"));
	    TableColumn<TransactionDetail, String> detailColumn3 = new TableColumn<>("Flower Price");
	    detailColumn3.setCellValueFactory(new PropertyValueFactory<>("flowerPrice"));
	    TableColumn<TransactionDetail, Integer> detailColumn4 = new TableColumn<>("Flower Type");
	    detailColumn4.setCellValueFactory(new PropertyValueFactory<>("flowerType"));
	    TableColumn<TransactionDetail, Integer> detailColumn5 = new TableColumn<>("Quantity");
	    detailColumn5.setCellValueFactory(new PropertyValueFactory<>("flowerQuantity"));

	    detailView.getColumns().add(detailColumn1);
	    detailView.getColumns().add(detailColumn2);
	    detailView.getColumns().add(detailColumn3);
	    detailView.getColumns().add(detailColumn4);
	    detailView.getColumns().add(detailColumn5);

		VBox.setMargin(detailView, new Insets(20, 20, 20, 20));
	    historyForm.getChildren().add(detailView);
	}
	
	public void initflowerForm() {
	    flowerView.setPrefWidth(800);
	    flowerView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    
	    TableColumn<Flower, Integer> column1 = new TableColumn<>("Flower ID");
	    column1.setCellValueFactory(new PropertyValueFactory<>("id"));
	    TableColumn<Flower, String> column2 = new TableColumn<>("Flower Name");
	    column2.setCellValueFactory(new PropertyValueFactory<>("name"));
	    TableColumn<Flower, String> column3 = new TableColumn<>("Flower Type");
	    column3.setCellValueFactory(new PropertyValueFactory<>("typeName"));
	    TableColumn<Flower, String> column4 = new TableColumn<>("Flower Price");
	    column4.setCellValueFactory(new PropertyValueFactory<>("price"));
	    TableColumn<Flower, Integer> column5 = new TableColumn<>("Flower Stock");
	    column5.setCellValueFactory(new PropertyValueFactory<>("stock"));

	    flowerView.getColumns().add(column1);
	    flowerView.getColumns().add(column2);
	    flowerView.getColumns().add(column3);
	    flowerView.getColumns().add(column4);
	    flowerView.getColumns().add(column5);
	    
	    GridPane manageFlowerForm = new GridPane();
	    manageFlowerForm.setHgap(20);
	    flowerForm.setMargin(flowerView, new Insets(20));
	    flowerForm.getChildren().add(flowerView);
	    
	    Label lblId = new Label("Flower ID");
	    manageFlowerForm.add(lblId, 0, 0);
	    
        idInput = new TextField("Flower ID");
        idInput.setDisable(true);
	    manageFlowerForm.add(idInput, 1, 0);
	    
	    Label lblName = new Label("Flower Name");
	    VBox.setMargin(lblName, new Insets(5, 0, 0, 0));
	    manageFlowerForm.add(lblName, 0, 1);
	    
        TextField nameInput = new TextField();
        nameInput.setPromptText("Flower Name");
	    manageFlowerForm.add(nameInput, 1, 1);
        
	    Label lblPrice = new Label("Flower Price");
	    VBox.setMargin(lblPrice, new Insets(5, 0, 0, 0));
	    manageFlowerForm.add(lblPrice, 0, 2);
        
        TextField priceInput = new TextField();
        priceInput.setPromptText("Flower Price");
	    manageFlowerForm.add(priceInput, 1, 2);
        
	    Label lblType = new Label("Flower Type");
	    VBox.setMargin(lblType, new Insets(5, 0, 0, 0));
	    manageFlowerForm.add(lblType, 0, 3);

        type = new ComboBox<>();
        type.setConverter(new StringConverter<FlowerType>() {
			
			@Override
			public String toString(FlowerType object) {
				 return object == null ?  "" : object.getName(); 
			}
			
			@Override
			public FlowerType fromString(String string) {
				return null;
			}
		});
        type.setPrefWidth(300);
	    manageFlowerForm.add(type, 1, 3);
        
	    Label lblStock = new Label("Flower Stock");
	    VBox.setMargin(lblStock, new Insets(5, 0, 0, 0));
	    manageFlowerForm.add(lblStock, 0, 4);
        
	    Spinner<Integer> stockInput = new Spinner<Integer>(0, 99, 0);
	    stockInput.setEditable(true);
	    stockInput.setPrefWidth(300);
	    manageFlowerForm.add(stockInput, 1, 4);
        
	    flowerView.setRowFactory(bv -> {
	        TableRow<Flower> row = new TableRow<>();
	        row.setOnMouseClicked(event -> {
	            if (!row.isEmpty()) {
	                Flower rowData = row.getItem();
	                idInput.setText(String.valueOf(rowData.getId()));
	                nameInput.setText(rowData.getName());
	                priceInput.setText(String.valueOf(rowData.getPrice()));
					stockInput.getValueFactory().setValue(rowData.getStock());
	                
	            }
	        });
	        return row;
	    });
        
	    VBox buttonVBox = new VBox();
        Button btnInsert = new Button("Add Flower");
        btnInsert.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(Validation.validateFlower(nameInput.getText(), priceInput.getText(), (type.getValue() != null) ? type.getValue().getName() : "", stockInput.getValue()) && 
						DatabaseConnection.insertFlower(nameInput.getText(), type.getValue().getId(), stockInput.getValue(), Integer.parseInt(priceInput.getText()))) {
					 DatabaseConnection.getAllFlowers(flowerView);
					AlertWindow.show(AlertType.INFORMATION, "Successfully Add Flower!");
					
					idInput.clear();
					nameInput.clear();
					type.valueProperty().set(null);
					priceInput.clear();
					stockInput.getValueFactory().setValue(0);
				}
			}
		});
        buttonVBox.setMargin(btnInsert, new Insets(10, 0, 0, 0));
        btnInsert.setPrefWidth(300);
        buttonVBox.getChildren().add(btnInsert);
        
        Button btnUpdate = new Button("Update Flower");
        btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(flowerView.getSelectionModel().getSelectedItem() == null) {
					AlertWindow.show(AlertType.ERROR, "Please select a flower!");
					return;
				}
				
				if(Validation.validateFlower(nameInput.getText(), priceInput.getText(), (type.getValue() != null) ? type.getValue().getName() : "", stockInput.getValue()) && 
						DatabaseConnection.updateFlower(flowerView.getSelectionModel().getSelectedItem().getId(), nameInput.getText(), type.getValue().getId(), stockInput.getValue(), Integer.parseInt(priceInput.getText()))) {
					 DatabaseConnection.getAllFlowers(flowerView);
					AlertWindow.show(AlertType.INFORMATION, "Successfully Update Flower!");
					
					idInput.clear();
					nameInput.clear();
					type.valueProperty().set(null);
					priceInput.clear();
					stockInput.getValueFactory().setValue(0);
				}
			}
		});
        btnUpdate.setPrefWidth(300);
        buttonVBox.getChildren().add(btnUpdate);
        
        Button btnDelete = new Button("Delete Flower");
        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(flowerView.getSelectionModel().getSelectedItem() == null) {
					AlertWindow.show(AlertType.ERROR, "Please select a flower!");
					return;
				}
				
				if(DatabaseConnection.deleteFlower(flowerView.getSelectionModel().getSelectedItem().getId())) {
					 DatabaseConnection.getAllFlowers(flowerView);
					AlertWindow.show(AlertType.INFORMATION, "Successfully Delete Flower!");
					
					idInput.clear();
					nameInput.clear();
					type.valueProperty().set(null);
					priceInput.clear();
					stockInput.getValueFactory().setValue(0);
				}
			}
		});
        btnDelete.setPrefWidth(300);
        buttonVBox.getChildren().add(btnDelete);
        
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(250);
        hBox.getChildren().add(manageFlowerForm);
        hBox.getChildren().add(buttonVBox);
        flowerForm.getChildren().add(hBox);
        flowerForm.setMargin(hBox, new Insets(0, 0, 20, 0));
	}
	
	public void inittypeForm() {
	    typeView.setPrefWidth(500);
	    typeView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    
	    TableColumn<FlowerType, String> column1 = new TableColumn<>("ID");
	    column1.setCellValueFactory(new PropertyValueFactory<>("id"));

	    TableColumn<FlowerType, String> column2 = new TableColumn<>("Name");
	    column2.setCellValueFactory(new PropertyValueFactory<>("name"));

	    typeView.getColumns().add(column1);
	    typeView.getColumns().add(column2);

	    VBox vbox = new VBox();
	    vbox.setAlignment(Pos.CENTER_LEFT);
	    HBox.setMargin(vbox, new Insets(20));
	    typeForm.getChildren().add(typeView);
	    typeForm.setMargin(typeView, new Insets(20));
	    typeForm.getChildren().add(vbox);
	    
	    GridPane manageTypeForm = new GridPane();
	    manageTypeForm.setHgap(20);
//	    manageTypeForm.setMargin(manageTpeForm, new Insets(20));

	    
	    Label lblId = new Label("Type ID");
	    manageTypeForm.add(lblId, 0, 0);
	    
	    TextField idInput = new TextField("Type ID");
        idInput.setDisable(true);
        manageTypeForm.add(idInput, 1, 0);
	    
	    Label lblName = new Label("Name");
	    manageTypeForm.add(lblName, 0, 1);
	    
        TextField nameInput = new TextField();
        nameInput.setPromptText("Type Name");
        manageTypeForm.add(nameInput, 1, 1);
	    
        vbox.getChildren().add(manageTypeForm);
        
        typeView.setRowFactory(bv -> {
	        TableRow<FlowerType> row = new TableRow<>();
	        row.setOnMouseClicked(event -> {
	            if (!row.isEmpty()) {
	            	FlowerType rowData = row.getItem();
	                idInput.setText(String.valueOf(rowData.getId()));
	                nameInput.setText(rowData.getName());
	            }
	        });
	        return row;
	    });
        
        Button btnInsert = new Button("Add Type");
        btnInsert.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(Validation.validateType(nameInput.getText()) &&
						DatabaseConnection.insertType(nameInput.getText())) {
					DatabaseConnection.getAllTypes(typeView);
					AlertWindow.show(AlertType.INFORMATION, "Genre "+nameInput.getText()+" added!");
					idInput.clear();
					nameInput.clear();
				}
			}
		});
        VBox.setMargin(btnInsert, new Insets(0, 0, 0, 0));
        btnInsert.setPrefWidth(400);
        manageTypeForm.add(btnInsert, 0, 2, 2, 1);
        
        Button btnUpdate = new Button("Update Type");
        btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(typeView.getSelectionModel().getSelectedItem() == null) {
					AlertWindow.show(AlertType.ERROR, "Please select a type!");
					return;
				}
				
				if(Validation.validateType(nameInput.getText()) &&
						DatabaseConnection.updateType(typeView.getSelectionModel().getSelectedItem().getId(), nameInput.getText())) {
					DatabaseConnection.getAllTypes(typeView);
					AlertWindow.show(AlertType.INFORMATION, "Successfully Update Type!");
					idInput.clear();
					nameInput.clear();
				}
			}
		});
        btnUpdate.setPrefWidth(400);
        manageTypeForm.add(btnUpdate, 0, 3, 2, 1);
        
        Button btnDelete = new Button("Delete Type");
        btnDelete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(typeView.getSelectionModel().getSelectedItem() == null) {
					AlertWindow.show(AlertType.ERROR, "Please select a type!");
					return;
				}
				
				if(DatabaseConnection.deleteType(typeView.getSelectionModel().getSelectedItem().getId())) {
					DatabaseConnection.getAllTypes(typeView);
					AlertWindow.show(AlertType.INFORMATION, "Successfully Delete Type!");
					idInput.clear();
					nameInput.clear();
				}
			}
		});
        btnDelete.setPrefWidth(400);
        manageTypeForm.add(btnDelete, 0, 4, 2, 1);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			currentStage = primaryStage;
			
			init();
			initLoginForm();
			initRegisterForm();
			initMainForm();
			initBuyFlowerForm();
			initHistoryForm();
			initflowerForm();
			inittypeForm();
			
		    rootLogin.setCenter(loginForm);
		    rootRegister.setCenter(registerForm);
		    rootMain.setCenter(mainForm);
		    primaryStage.setTitle("FloWGer Store");
		    primaryStage.setResizable(false);
			primaryStage.setScene(sceneLogin);
			primaryStage.show();
			
			DatabaseConnection.getInstance();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
