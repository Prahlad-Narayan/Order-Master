package menu;

import java.io.IOException;
import java.util.ArrayList;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import item.MenuItem;
import item.MenuItem.Type;

public class MenuUI {
	private TreeView<String> menuTree;
	private Button backBtn;
	private Button modifyBtn;
	public static Menu<MenuItem> menu = populateTree();
	
	@SuppressWarnings("unchecked")
	public Scene getScene() {
		Scene scene = null;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
			
			this.menuTree = (TreeView<String>) root.lookup("#menuTree");
			this.backBtn = (Button) root.lookup("#backBtn");
			this.modifyBtn = (Button) root.lookup("#modifyBtn");
						
			TreeItem<String> rootItem = this.createMenuTreeRecursive(menu, menu.getRootItem(), null);
			this.menuTree.setRoot(rootItem);
			
			this.backBtn.setOnAction(e -> {
				Main.toDashboard();
			});
			
			this.modifyBtn.setOnAction(e -> {
				Main.toMenuModify();
			});

	        	        
			scene = new Scene(root);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return scene;
	}
	
	private TreeItem<String> createMenuTreeRecursive(Menu<MenuItem> menu, MenuItem parent, TreeItem<String> item) {
		TreeItem<String> treeItem = null;
		
		if(parent.getType() == MenuItem.Type.CATEGORY) {
			treeItem = new TreeItem<String>(parent.getName());
		} else {
			treeItem = new TreeItem<String>(parent.getName() + " ($" + parent.getPrice() + ")");
		}
		
		treeItem.setExpanded(true);
		
		if(item != null) {
			item.getChildren().add(treeItem);
		} else {
			item = treeItem;
		}
		
		try {
			ArrayList<MenuItem> children = menu.getChildren(parent);
			for(MenuItem child : children) {
				this.createMenuTreeRecursive(menu, child, treeItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return item;
	}
	
	private static Menu<MenuItem> populateTree() {
		
		MenuItem menuItem = new MenuItem("Menu", Type.CATEGORY);
		MenuItem drinks = new MenuItem("Drinks", Type.CATEGORY);
		MenuItem food = new MenuItem("Food", Type.CATEGORY);
		MenuItem coldDrinks = new MenuItem("Cold", Type.CATEGORY);
		MenuItem hotDrinks = new MenuItem("Hot", Type.CATEGORY);
		MenuItem coffee = new MenuItem("Coffee", Type.ITEM, 4);
		MenuItem coke = new MenuItem("Coke", Type.ITEM, 3);
		MenuItem pizza = new MenuItem("Pizza", Type.ITEM, 10);
		MenuItem burger = new MenuItem("Burger", Type.ITEM, 9);
		
		Menu<MenuItem> menu = new Menu<MenuItem>(menuItem);
		try {
			menu.addChild(menuItem, drinks);
			menu.addChild(menuItem, food);
			menu.addChild(drinks, coldDrinks);
			menu.addChild(drinks, hotDrinks);
			menu.addChild(hotDrinks, coffee);
			menu.addChild(coldDrinks, coke);
			menu.addChild(food, pizza);
			menu.addChild(food, burger);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return menu;
	}
}
