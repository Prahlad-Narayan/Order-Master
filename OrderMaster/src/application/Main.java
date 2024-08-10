package application;
	
import dashboard.Dashboard;
import javafx.application.Application;
import javafx.stage.Stage;
import menu.MenuUI;
import menu.MenuModifyUI;
import order.OrderModifyUI;
import order.OrderUI;
import javafx.scene.Scene;

public class Main extends Application {
	private static Stage stage;
	
	private static Dashboard dashboard;
	private static MenuUI menu;
	private static MenuModifyUI menuModify;
	private static OrderUI order;	
	
	@Override
	public void start(Stage primaryStage) {		
		try {
			stage = primaryStage;
			stage.show();
			initialize();
			
			setScene(dashboard.getScene());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void setScene(Scene scene) {
		stage.setScene(scene);
	}
	
	public static void initialize() {
		dashboard = new Dashboard();
		menu = new MenuUI();
		menuModify = new MenuModifyUI();
		order = new OrderUI();
	}
	
	public static void toMenu() {
		setScene(menu.getScene());
	}
	
	public static void toDashboard() {
		setScene(dashboard.getScene());
	}
	
	public static void toMenuModify() {
		setScene(menuModify.getScene());
	}
	
	public static void toOrder() {
		setScene(order.getScene());
	}
	
	public static void toOrderModify(Scene scene) {
		setScene(scene);
	}
}
