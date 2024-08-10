package menu;

import java.util.ArrayList;

import application.Main;
import item.MenuItem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MenuModifyUI {
	
	private Button backBtn;
	private VBox modifyMenuVbox;
	private ComboBox<String> typeComboBox;
	private ArrayList<ComboBox<String>> menuComboBoxArray;
	private TextField nameTextField;
	private MenuItem latestSelectedMenuItem;
	private Button addBtn;
	private Button removeBtn;
	private static final String defaultComboBoxValue = "--NO SELECTION--";
	private Text statusText;
	private TextField priceTextField;

	@SuppressWarnings("unchecked")
	public Scene getScene() {
		Scene scene = null;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MenuModify.fxml"));
			
			this.backBtn = (Button) root.lookup("#backBtn");
			this.modifyMenuVbox = (VBox) root.lookup("#modifyMenuVbox");
			this.typeComboBox = (ComboBox<String>) root.lookup("#typeComboBox");
			this.nameTextField = (TextField) root.lookup("#nameTextField");
			this.addBtn = (Button) root.lookup("#addBtn");
			this.removeBtn = (Button) root.lookup("#removeBtn");
			this.statusText = (Text) root.lookup("#statusText");
			this.priceTextField = (TextField) root.lookup("#priceTextField");
			
			this.backBtn.setOnAction(e -> {
				Main.toMenu();
			});
			
			this.addBtn.setOnAction(e -> {
				this.statusText.setFill(Color.RED);
				
				if(latestSelectedMenuItem == null) {
					this.statusText.setText("Select a parent category to add the entry to");
					return;
				}
				
				if(this.nameTextField.getText().isEmpty()) {
					this.statusText.setText("Name field cannot be empty");
					return;
				}
								
				if(this.typeComboBox.getValue() == null) {
					this.statusText.setText("Select a type of the new entry");
					return;
				}
				
				if(latestSelectedMenuItem.getType() == MenuItem.Type.ITEM) {
					this.statusText.setText("Parent for the new entry must be a category");
					return;
				}
				
				if(this.typeComboBox.getValue().equals(MenuItem.Type.ITEM.getStringValue())) {
					if(this.priceTextField.getText().isEmpty()) {
						this.statusText.setText("Price field cannot be empty");
						return;
					}
					
					try {
						Double.parseDouble(this.priceTextField.getText());
					} catch (NumberFormatException numFormatException) {
						this.statusText.setText("Invalid price value");
						return;
					}
				}
				
				addMenuItem(this.nameTextField.getText(),this.typeComboBox.getValue());
				
				resetMenuComboBox();
			});
			
			this.removeBtn.setOnAction(e -> {
				this.statusText.setFill(Color.RED);
				
				if(latestSelectedMenuItem == null) {
					this.statusText.setText("Select an entry to remove");
					return;
				}
				
				if(latestSelectedMenuItem == MenuUI.menu.getRootItem()) {
					this.statusText.setText("You cannot remove root item");
					return;
				}
				
				removeMenuItem();
				
				resetMenuComboBox();
			});
			
			this.menuComboBoxArray = new ArrayList<>();
			
			resetMenuComboBox();
			
			scene = new Scene(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return scene;
	}
	
	private void resetMenuComboBox() {
		this.typeComboBox.getItems().clear();
		this.typeComboBox.getItems().add(MenuItem.Type.CATEGORY.getStringValue());
		this.typeComboBox.getItems().add(MenuItem.Type.ITEM.getStringValue());
		
		this.nameTextField.clear();
		this.priceTextField.clear();
		
		for(ComboBox<String> comboBox: this.menuComboBoxArray) {
			this.modifyMenuVbox.getChildren().remove(comboBox);
		}
		
		this.menuComboBoxArray.clear();
		
		latestSelectedMenuItem = MenuUI.menu.getRootItem();
		addMenuComboBox(MenuUI.menu, latestSelectedMenuItem);
	}
	
	private void addMenuComboBox(Menu<MenuItem> menu, MenuItem parent) {
		ComboBox<String> menuComboBox = new ComboBox<String>();
		
		try {
			ArrayList<MenuItem> children = menu.getChildren(parent);
			
			if(children.size() == 0) {
				return;
			}
			
			menuComboBox.getItems().add(defaultComboBoxValue);
			menuComboBox.getSelectionModel().selectFirst();
			
			for(MenuItem child: children) {
				menuComboBox.getItems().add(child.getName());
			}
			
			menuComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
				MenuItem selectedItem = null;
				
				if(!newValue.equals(defaultComboBoxValue)) {
					for(MenuItem child: children) {
						if(child.getName().equals(newValue)) {
							selectedItem = child;
							break;
						}
					}
				}
				
				boolean isFound = false;
				for(Object comboBox: this.menuComboBoxArray.toArray()) {
					if(isFound) {
						this.modifyMenuVbox.getChildren().remove(comboBox);
						this.menuComboBoxArray.remove(comboBox);
					}
					
					if(!isFound && comboBox == menuComboBox) {
						isFound = true;
					}
				}
								
				if(selectedItem != null) {
					this.latestSelectedMenuItem = selectedItem;
					addMenuComboBox(menu, selectedItem);
				} else {
					this.latestSelectedMenuItem = parent;
				}
	        });
			
			this.menuComboBoxArray.add(menuComboBox);
			this.modifyMenuVbox.getChildren().add(this.menuComboBoxArray.size() - 1, menuComboBox);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addMenuItem(String menuEntry, String type) {
		try {
			MenuItem.Type entryType = type.equals(MenuItem.Type.CATEGORY.getStringValue()) ? MenuItem.Type.CATEGORY : MenuItem.Type.ITEM;
			MenuItem newItem = null;
			if(entryType == MenuItem.Type.CATEGORY) {
				newItem = new MenuItem(menuEntry, entryType);
			} else {
				newItem = new MenuItem(menuEntry, entryType, Double.parseDouble(this.priceTextField.getText()));
			}
			MenuUI.menu.addChild(latestSelectedMenuItem, newItem);
			this.statusText.setFill(Color.GREEN);
			this.statusText.setText("Entry added successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeMenuItem() {
		try {
			boolean isRemoved = MenuUI.menu.remove(latestSelectedMenuItem);
			if(isRemoved) {
				this.statusText.setFill(Color.GREEN);
				this.statusText.setText("Entry removed successfully!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
