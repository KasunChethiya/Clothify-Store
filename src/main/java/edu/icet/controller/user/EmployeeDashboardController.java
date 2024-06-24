package edu.icet.controller.user;

import edu.icet.bo.orders.OrderBoImpl;
import edu.icet.bo.user.EmployeeDashboardService;
import edu.icet.bo.user.SignInService;
import edu.icet.entity.CartEntity;
import edu.icet.entity.ProductEntity;
import edu.icet.model.Order;
import edu.icet.util.IdGenerator;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class EmployeeDashboardController implements Initializable {
    public Button btnAddProduct;
    public TableView productTbl;
    public TableView cartTbl;
    public Label totalPriceLbl;
    public Label orderIdlbl;
    public Label dateLbl;
    public Label welcomeNoteLbl;
    public Button btnSignout;
    private double cartTotal = 0.0;

    EmployeeDashboardService employeeDashboardService = new EmployeeDashboardService();
    OrderBoImpl orderBoImpl = new OrderBoImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderIdlbl.setText(IdGenerator.genarateOrderId());
        welcomeNoteLbl.setText("Welcome "+SignInService.name);
        loadDate();
        allProducts();

        TableColumn<ProductEntity, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<ProductEntity, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<ProductEntity, byte[]> imageColumn = new TableColumn<>("Image");
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        imageColumn.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(byte[] imageBytes, boolean empty) {
                super.updateItem(imageBytes, empty);
                if (empty || imageBytes == null) {
                    setGraphic(null);
                } else {
                    Image image = new Image(new ByteArrayInputStream(imageBytes));
                    imageView.setImage(image);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    setGraphic(imageView);
                }
            }
        });

        TableColumn<ProductEntity, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<ProductEntity, Integer> qtyColumn = new TableColumn<>("Stock");
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));

        TableColumn<ProductEntity, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<ProductEntity, Void> cartColumn = new TableColumn<>("Cart");

        cartColumn.setCellFactory(param -> new TableCell<>() {
            private final Spinner<Integer> quantitySpinner = new Spinner<>(1, 10, 1);
            private final Button addButton = new Button("Add to Cart");
            private final HBox hBox = new HBox(10, quantitySpinner, addButton);
            {
                addButton.setOnAction(event -> {
                    ProductEntity product = getTableView().getItems().get(getIndex());
                    int quantity = quantitySpinner.getValue();
                    double totalPrice = product.getPrice() * quantity;

                    CartEntity cartItem = new CartEntity(
                            product.getId(),
                            product.getTitle(),
                            quantity,
                            totalPrice
                    );
                    cartTbl.getItems().add(cartItem);
                    cartTotal += totalPrice;
                    totalPriceLbl.setText("Rs."+String.valueOf(cartTotal));

//                    new OrderedItems(
//                            orderIdlbl.getText(),
//                            product.getId(),
//                            product.getQty(),
//                            product.getPrice()
//                    );

                    quantitySpinner.getValueFactory().setValue(0);
                });

            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(hBox);
                }
            }
        });

        idColumn.prefWidthProperty().bind(productTbl.widthProperty().multiply(0.05));
        titleColumn.prefWidthProperty().bind(productTbl.widthProperty().multiply(0.2));
        imageColumn.prefWidthProperty().bind(productTbl.widthProperty().multiply(0.1));
        descriptionColumn.prefWidthProperty().bind(productTbl.widthProperty().multiply(0.25));
        qtyColumn.prefWidthProperty().bind(productTbl.widthProperty().multiply(0.05));
        priceColumn.prefWidthProperty().bind(productTbl.widthProperty().multiply(0.1));
        cartColumn.prefWidthProperty().bind(productTbl.widthProperty().multiply(0.35));

        productTbl.getColumns().add(idColumn);
        productTbl.getColumns().add(titleColumn);
        productTbl.getColumns().add(imageColumn);
        productTbl.getColumns().add(descriptionColumn);
        productTbl.getColumns().add(qtyColumn);
        productTbl.getColumns().add(priceColumn);
        productTbl.getColumns().add(cartColumn);



        TableColumn<CartEntity, String> cartIdColumn = new TableColumn<>("Product ID");
        cartIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));

        TableColumn<CartEntity, String> cartTitleColumn = new TableColumn<>("Title");
        cartTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<CartEntity, Integer> cartQtyColumn = new TableColumn<>("Qty");
        cartQtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));

        TableColumn<CartEntity, Double> cartTotalColumn = new TableColumn<>("Total");
        cartTotalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        cartTbl.getColumns().add(cartIdColumn);
        cartTbl.getColumns().add(cartTitleColumn);
        cartTbl.getColumns().add(cartQtyColumn);
        cartTbl.getColumns().add(cartTotalColumn);
    }

    private Date loadDate() {
        Date date = new Date();
        SimpleDateFormat f =new SimpleDateFormat("yyyy-MM-dd");
        dateLbl.setText(f.format(date));
        return date;
    }

    public void btnAddProductOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/product-management-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        Image image = new Image("icon.png");
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();
        Stage currentStage = (Stage) btnAddProduct.getScene().getWindow();
        currentStage.close();
    }

    public void btnSettingsOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/settings-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        Image image = new Image("icon.png");
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();
        Stage currentStage = (Stage) btnAddProduct.getScene().getWindow();
        currentStage.close();
    }

    private void allProducts(){
        employeeDashboardService.retriveProductsToTable(productTbl);
    }

    public void allBtnOnAction(ActionEvent actionEvent) {
        allProducts();
    }

    public void mensBtnOnAction(ActionEvent actionEvent) {
        employeeDashboardService.retriveMensWear(productTbl);
    }

    public void ladiesBtnOnAction(ActionEvent actionEvent) {
        employeeDashboardService.retriveLadiesWear(productTbl);
    }

    public void kidsBtnOnAction(ActionEvent actionEvent) {
        employeeDashboardService.retriveKidsWear(productTbl);
    }

    public void btnPaymentProceedOnAction(ActionEvent actionEvent) {

        Order order = new Order(
                orderIdlbl.getText(),
                SignInService.userId,
                dateLbl.getText(),
                cartTotal
        );
        orderBoImpl.persistOrder(order);

        orderBoImpl.persistOrderItems();

        cartTbl.getItems().clear();
        cartTotal = 0.0;
        totalPriceLbl.setText("Rs." + String.valueOf(cartTotal));


        orderIdlbl.setText(IdGenerator.genarateOrderId());
    }

    public void btnSalesHistoryOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/sales-history-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        Image image = new Image("icon.png");
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();
        Stage currentStage = (Stage) btnAddProduct.getScene().getWindow();
        currentStage.close();
    }

    public void btnSignOutOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/signin-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        Image image = new Image("icon.png");
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();
        Stage currentStage = (Stage) btnSignout.getScene().getWindow();
        currentStage.close();
    }
}
