package dashboard;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class Dashboard {
	private Button orderBtn;
	private Button MenuBtn;
	
	public Scene getScene() {
		Scene scene = null;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
			
			this.orderBtn = (Button) root.lookup("#order");
			this.MenuBtn = (Button) root.lookup("#menu");
			
			this.MenuBtn.setOnAction(e -> {
				Main.toMenu();
			});
			
			this.orderBtn.setOnAction(e -> {
				Main.toOrder();
			});
			
			
			scene = new Scene(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return scene;
	}
}
