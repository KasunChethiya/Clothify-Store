package edu.icet.controller.product;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import edu.icet.bo.BoFactory;
import edu.icet.bo.SuperBo;
import edu.icet.bo.orders.OrderBoImpl;
import edu.icet.bo.product.ProductBoImpl;
import edu.icet.entity.ProductEntity;
import edu.icet.model.Product;
import edu.icet.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ProductManageViewController implements Initializable {

    public TextField txtFieldImg;
    public Button btnSettings;
    public TextField titleTxtField;
    public TextField colorTxtField;
    public TextField qtyTxtField;
    public TextField priceTxtField;
    public JFXButton addProductBtn;
    public ComboBox sizeCmb;
    public ComboBox catagoryCmb;
    public JFXTextArea descriptionTxtField;
    public Button btnDashboard;
    public TableView productTbl;
    public ComboBox cmbProductId;
    ProductBoImpl productBo = BoFactory.getInstance().getBo(BoType.PRODUCT);

    public void BtnUploadImgOnAction(ActionEvent actionEvent) {
        String imagePath = productBo.imageUpload();
        txtFieldImg.setText(imagePath);
    }

    public void btnUpdateProductOnAction(ActionEvent actionEvent) {
        productBo.update(cmbProductId.getValue(), Double.parseDouble(priceTxtField.getText()),Integer.parseInt(qtyTxtField.getText()));

        loadProductTbl();
        cleartextBox();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update Product");
        alert.setHeaderText(null);
        alert.setContentText("The Product is Successfully Updated");
        alert.showAndWait();
    }

    public void btnAddProductOnAction(ActionEvent actionEvent) {
        Product product = new Product(
                titleTxtField.getText(),
                txtFieldImg.getText().getBytes(),
                descriptionTxtField.getText(),
                sizeCmb.getValue().toString(),
                colorTxtField.getText(),
                Double.parseDouble(priceTxtField.getText()),
                catagoryCmb.getValue().toString(),
                Integer.parseInt(qtyTxtField.getText())
        );
        productBo.saveProduct(product);

        loadProductTbl();
        cleartextBox();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Product");
        alert.setHeaderText(null);
        alert.setContentText("The Product is Successfully Added.");
        alert.showAndWait();
    }

    public void btnDeleteProductOnAction(ActionEvent actionEvent) {
        productBo.Delete(cmbProductId.getValue());

        loadProductTbl();
        cleartextBox();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Delete Product");
        alert.setHeaderText(null);
        alert.setContentText("The Product is Successfully deleted.");
        alert.showAndWait();

    }

    public void btnDashboardOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/employee-dashboard-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        Image image = new Image("icon.png");
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();
        Stage currentStage = (Stage) btnDashboard.getScene().getWindow();
        currentStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productBo.retrive(productTbl);
        loadProductTbl();
        loadComboBoxes();
    }

    private void loadComboBoxes() {
        ObservableList<Object> categories = FXCollections.observableArrayList();
        categories.add("Ladies wear");
        categories.add("Mens wear");
        categories.add("Kids wear");
        catagoryCmb.setItems(categories);

        ObservableList<Object> sizes = FXCollections.observableArrayList();
        sizes.add("None");
        sizes.add("XS");
        sizes.add("S");
        sizes.add("M");
        sizes.add("L");
        sizes.add("XL");
        sizes.add("XXL");
        sizeCmb.setItems(sizes);

        cmbProductId.setItems(productBo.productIds);
    }

    private void loadProductTbl() {
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

        productTbl.getColumns().add(idColumn);
        productTbl.getColumns().add(titleColumn);
        productTbl.getColumns().add(imageColumn);
        productTbl.getColumns().add(descriptionColumn);
        productTbl.getColumns().add(qtyColumn);
        productTbl.getColumns().add(priceColumn);
    }


    public void btnSearchOnAction(ActionEvent actionEvent) {
        List<ProductEntity> productById = productBo.searchById(cmbProductId.getValue());
        productById.forEach(product -> {
            titleTxtField.setText(product.getTitle());
            descriptionTxtField.setText(product.getDescription());
            catagoryCmb.setValue(product.getCategory());
            sizeCmb.setValue(product.getSize());
            colorTxtField.setText(product.getColor());
            qtyTxtField.setText(String.valueOf(product.getQty()));
            priceTxtField.setText(String.valueOf(product.getPrice()));
        });
    }

    private void cleartextBox(){
        titleTxtField.clear();
        descriptionTxtField.clear();
        catagoryCmb.getItems().clear();
        sizeCmb.getItems().clear();
        colorTxtField.clear();
        priceTxtField.clear();
        txtFieldImg.clear();
        qtyTxtField.clear();
    }
}
