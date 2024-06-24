package edu.icet.bo.product;

import edu.icet.bo.SuperBo;
import edu.icet.entity.ProductEntity;
import edu.icet.model.Product;
import javafx.scene.control.TableView;

import java.util.List;

public interface ProductBo extends SuperBo {
    void saveProduct(Product product);
    void retrive(TableView productTbl);
    List<ProductEntity> searchById(Object value);
    void update(Object productId, double price, int qty);
    void Delete(Object value);
}
