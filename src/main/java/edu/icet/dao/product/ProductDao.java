package edu.icet.dao.product;

import edu.icet.dao.SuperDAO;
import edu.icet.entity.ProductEntity;
import edu.icet.model.Product;

import java.util.List;

public interface ProductDao extends SuperDAO {
    List<ProductEntity> getByid(Object value);
    List<ProductEntity> getAllProducts();
    List<ProductEntity> getMenWearProducts();
    List<ProductEntity> getLadiesWearProducts();
    List<ProductEntity> getKidsWearProducts();
    void deleteById(Object productId);
    void updateById(Object productId, double price, int qty);
    void persist(Product product, String imagePath);
}
