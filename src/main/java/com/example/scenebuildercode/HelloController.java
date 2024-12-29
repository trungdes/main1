package com.example.scenebuildercode;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableView<Product> SelectedtableView;
    //table1
    @FXML
    private TableColumn<Product, Integer> idcolumn;
    @FXML
    private TableColumn<Product, String> namecolumn;
    @FXML
    private TableColumn<Product, String> colorcolumn;
    @FXML
    private TableColumn<Product, String> generationcolumn;
    @FXML
    private TableColumn<Product, Double> pricecolumn;
    @FXML
    private TableColumn<Product, Boolean> selectedColumn;
    //table2
    @FXML
    private TableColumn<Product, Integer> selectedIdColumn;
    @FXML
    private TableColumn<Product, String> selectedNameColumn;
    @FXML
    private TableColumn<Product, String> selectedColorColumn;
    @FXML
    private TableColumn<Product, String> selectedGenerationColumn;
    @FXML
    private TableColumn<Product, Double> selectedPriceColumn;
    @FXML
    private TableColumn<Product, Boolean> table2SelectedColumn;




    @FXML
    private ObservableList<Product> productList;
    @FXML
    private ObservableList<Product> selectedProductList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productList = FXCollections.observableArrayList();
        idcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecolumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colorcolumn.setCellValueFactory(new PropertyValueFactory<>("Color"));
        generationcolumn.setCellValueFactory(new PropertyValueFactory<>("generation"));
        pricecolumn.setCellValueFactory(new PropertyValueFactory<>("Price"));

        //table2
        selectedIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        selectedNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        selectedColorColumn.setCellValueFactory(new PropertyValueFactory<>("Color"));
        selectedGenerationColumn.setCellValueFactory(new PropertyValueFactory<>("Generation"));
        selectedPriceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));

        //checkBox cho table1
        selectedColumn.setCellFactory(tc -> new TableCell<>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setOnAction(e -> {
                    Product product = getTableView().getItems().get(getIndex());
                    if (product != null) {
                        product.setSelected(checkBox.isSelected());
                    }
                });
            }

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Product product = getTableView().getItems().get(getIndex());
                    if (product != null) {
                        checkBox.setSelected(product.isSelected());
                    }
                    setGraphic(checkBox);
                }
            }
        });

        //checkBox cho table2
        table2SelectedColumn.setCellFactory(tc -> new TableCell<>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setOnAction(e -> {
                    Product product = getTableView().getItems().get(getIndex());
                    if (product != null) {
                        product.setSelected(checkBox.isSelected());
                    }
                });
            }

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Product product = getTableView().getItems().get(getIndex());
                    if (product != null) {
                        checkBox.setSelected(product.isSelected());
                    }
                    setGraphic(checkBox);
                }
            }
        });


        SelectedtableView.setItems(selectedProductList);
        tableView.setItems(productList);

        loadData();

    }

    @FXML
    private void proceed(ActionEvent event) {
        ObservableList<Product> selectedProducts = FXCollections.observableArrayList();
        for (Product product : productList) {
            if (product.isSelected()) {
                selectedProducts.add(product);
                product.setSelected(false); // Bỏ tick sau khi chuyển
            }
        }
        if (selectedProducts.isEmpty()) {
            System.out.println("No product selected.");
        } else {
            System.out.println("Products selected:");
            for (Product product : selectedProducts) {
                System.out.println("ID: " + product.getId() + ", Name: " + product.getName());
            }
            selectedProductList.addAll(selectedProducts);
            tableView.getItems().removeAll(selectedProducts); // xóa item đã chọn
            tableView.refresh(); // Làm mới bảng chính
        }
    }
    @FXML
    private void deleteSelected(ActionEvent event) {
        ObservableList<Product> selectedProducts = FXCollections.observableArrayList();
        for (Product product : selectedProductList) {
            if (product.isSelected()) {
                selectedProducts.add(product);
            }
        }
        SelectedtableView.getItems().removeAll(selectedProducts);
    }

    private void loadData(){
        String query = "SELECT \n" +
                "    p.id AS id,\n" +
                "    p.name AS name,\n" +
                "    MAX(CASE WHEN pd.key_name = 'color' THEN pd.key_value ELSE NULL END) AS color,\n" +
                "    MAX(CASE WHEN pd.key_name = 'generation' THEN pd.key_value ELSE NULL END) AS generation,\n" +
                "    MAX(CASE WHEN pd.key_name = 'price' THEN pd.key_value ELSE NULL END) AS price\n" +
                "FROM \n" +
                "    Products p\n" +
                "LEFT JOIN \n" +
                "    ProductDetails pd\n" +
                "ON \n" +
                "    p.id = pd.product_id\n" +
                "GROUP BY \n" +
                "    p.id, p.name;\n"   ;

        try (Connection connection = jdbc.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                try {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String color = resultSet.getString("color");
                    String generation = resultSet.getString("generation");
                    double price = resultSet.getDouble("price");

                    productList.add(new Product(id, name, color, generation, price));
                } catch (SQLException | NullPointerException e) {
                    System.err.println("Error processing row: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}