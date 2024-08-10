package order;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import menu.Menu;
import menu.MenuUI;

import java.io.IOException;
import application.Main;
import item.MenuItem;
import item.OrderItem;

public class OrderUI {
    private TableView<Order> ordersTable;
    private Button backBtn;
    private Button newOrderBtn;
    private Button modifyOrderBtn;
    private static OrderHashMap<Integer, Order> orderHashMap = new OrderHashMap<>();
    
    @SuppressWarnings("unchecked")
    public Scene getScene() {
        Scene scene = null;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Order.fxml"));

            this.ordersTable = (TableView<Order>) root.lookup("#ordersTable");
            this.backBtn = (Button) root.lookup("#backBtn");
            this.newOrderBtn = (Button) root.lookup("#newOrderBtn");
            this.modifyOrderBtn = (Button) root.lookup("#modifyOrderBtn");

            this.ordersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            // Initialize the table columns
            TableColumn<Order, Integer> idColumn = new TableColumn<>("Id");
            idColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());

            TableColumn<Order, Double> priceColumn = new TableColumn<>("Price");
            priceColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getTotalPrice()).asObject());
            
            TableColumn<Order, String> statusColumn = new TableColumn<>("Status");
            statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));
            
            TableColumn<Order, String> itemColumn = new TableColumn<>("Items");
            itemColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getItems()));
            
            this.ordersTable.getColumns().addAll(idColumn, priceColumn, statusColumn, itemColumn);
            
            ordersTable.setItems(FXCollections.observableList(orderHashMap.getValues()));

            // Set event handlers
            this.backBtn.setOnAction(e -> {
                Main.toDashboard();
            });

            this.newOrderBtn.setOnAction(e -> {
                createNewOrder();
            });

            this.modifyOrderBtn.setOnAction(e -> {
                modifySelectedOrder(MenuUI.menu);
            });

            scene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scene;
    }

    private Order createNewOrder() {
        Order order = new Order();
        orderHashMap.put(order.getId(), order);
        ordersTable.setItems(FXCollections.observableList(orderHashMap.getValues()));
        return order;
    }

    private void modifySelectedOrder(Menu<MenuItem> menu) {
        // Retrieve the selected order item from the table
        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();

        if (selectedOrder != null) {
            OrderModifyUI orderModifyUI = new OrderModifyUI(selectedOrder);
            Main.toOrderModify(orderModifyUI.getScene());
        }
    }


    private void populateOrderTable() {
        // Add items to the order and update the table
        try {
            // Hardcoded items
        	createNewOrder();
        	            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
