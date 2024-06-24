package edu.icet.bo.product;

import edu.icet.dao.DaoFactory;
import edu.icet.dao.product.ProductDao;
import edu.icet.entity.ProductEntity;
import edu.icet.model.Product;
import edu.icet.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


public class ProductBoImpl implements ProductBo {
    Stage stage;
    ModelMapper mapper = new ModelMapper();
    ProductDao productDao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);

    String imagePath;
    public String imageUpload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose the image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All","*.png","*.jpg"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG","*.png")
                );
        File file = fileChooser.showOpenDialog(stage);
        imagePath = file.getPath();
        return imagePath;
    }

    @Override
    public void saveProduct(Product product) {
        productDao.persist(product, imagePath);
    }

    public ObservableList<Integer> productIds = FXCollections.observableArrayList();

    @Override
    public void retrive(TableView productTbl) {
        List<ProductEntity> products = productDao.getAllProducts();

        ObservableList<ProductEntity> productList = FXCollections.observableArrayList();

        for (ProductEntity product : products) {
            productIds.add(product.getId());
            productList.add(product);
        }
        productTbl.setItems(productList);
    }

    @Override
    public List<ProductEntity> searchById(Object value) {
        List<ProductEntity> byid = productDao.getByid(value);
        return byid;
    }

    @Override
    public void update(Object productId, double price, int qty) {
        productDao.updateById(productId,price,qty);
    }

    @Override
    public void Delete(Object productId) {
        productDao.deleteById(productId);
    }
}
